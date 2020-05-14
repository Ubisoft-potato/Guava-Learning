package org.cyka.concurrency;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ListenableFuture @Description ListenableFuture @Author long @Date 2020/5/13
 * 11:15 @Version 1.0
 *
 * <p>Concurrency is a hard problem, but it is significantly simplified by working with powerful and
 * simple abstractions. To simplify matters, Guava extends the Future interface of the JDK with
 * ListenableFuture.
 */
@Slf4j
public class ListenableFutureExample {

  // 创建固定线程池
  private static final ExecutorService executor =
      Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

  public static void main(String[] args) {
    // 使用MoreExecutors封装jdk的线程池
    ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executor);

    // 异步提交
    ListenableFuture<Integer> listenableFuture =
        listeningExecutorService.submit(
            () -> {
              log.info("{}", LocalDateTime.now());
              // 休眠2秒，默认耗时
              TimeUnit.SECONDS.sleep(2);
              log.info("{}", LocalDateTime.now());
              return 666;
            });

    // 添加回调函数
    Futures.addCallback(
        listenableFuture,
        new FutureCallback<Integer>() {
          @Override
          public void onSuccess(@Nullable Integer integer) {
            log.info("listenable future get result: {}", integer);
            // 关闭线程池
            shutdownExecutorService(listeningExecutorService);
          }

          @Override
          public void onFailure(@Nullable Throwable throwable) {
              if (throwable != null) {
                  log.error(throwable.getLocalizedMessage());
              }
              // 关闭线程池
            shutdownExecutorService(listeningExecutorService);
          }
        },
        listeningExecutorService);
  }

  /**
   * 优雅关闭关闭线程池(oracle recommend)
   *
   * @param executorService
   */
  private static void shutdownExecutorService(ExecutorService executorService) {
    executorService.shutdown();
    try {
      if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
        // 打印尚未执行的任务方便手动处理
        executorService.shutdownNow().forEach(runnable -> log.info(runnable.toString()));
      }
    } catch (InterruptedException e) {
      executorService.shutdownNow();
    }
  }
}
