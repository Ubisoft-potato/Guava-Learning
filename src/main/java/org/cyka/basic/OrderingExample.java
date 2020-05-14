package org.cyka.basic;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cyka.model.Person;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName OrderingExample @Description Ordering Usage @Author long @Date 2020/5/14
 * 15:16 @Version 1.0
 */
@Slf4j
public class OrderingExample {

  private static final List<Person> persons =
      Arrays.asList(new Person("Michael", 10), new Person("Alice", 3), new Person("Thomas", null));

  public static void main(String[] args) {
    // 自然排序
    natureList();
    // 链式api使用
    chainOrder();
  }

  private static void natureList() {
    List<Integer> integers = Arrays.asList(3, 2, 1);

    integers.sort(Ordering.natural());

    log.info("sorted list: {}", integers);
  }

  private static void chainOrder() {
    Ordering<Person> personOrdering =
        Ordering.natural()
            .nullsLast()
            .onResultOf(
                new Function<Person, Comparable>() {
                  @Nullable
                  @Override
                  public Comparable apply(@Nullable Person person) {
                    return person.getAge();
                  }
                });
    persons.sort(personOrdering);
    log.info("person order: {}", persons);
  }
}
