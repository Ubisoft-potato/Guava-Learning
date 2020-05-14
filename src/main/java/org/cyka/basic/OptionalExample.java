package org.cyka.basic;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @ClassName OptionalExample @Description optional example @Author long @Date 2020/5/14
 * 9:55 @Version 1.0
 */
@Slf4j
public class OptionalExample {
  public static void main(String[] args) {
    int randomInt = new Random().nextInt(15);
    // optional.of 参数不能为空，为空直接抛NPE
    Optional<Integer> possible = Optional.of(9);
    // optional.fromNullable 允许参数为空
    Optional<Integer> integerOptional =
        Optional.fromNullable(methodMayMakeNullReturnValue(randomInt));
    log.info("value isPresent:{}", possible.isPresent());
    log.info("value isPresent:{}", integerOptional.isPresent());

    log.info("integerOptional get value: {}", integerOptional.or(10));
  }

  private static Integer methodMayMakeNullReturnValue(Integer integer) {
    if (integer > 5) {
      return null;
    }
    return 0;
  }
}
