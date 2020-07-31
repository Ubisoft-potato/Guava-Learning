package org.cyka.collections;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName MultisetExample @Description Multiset Usage @Author long @Date 2020/5/14
 * 18:54 @Version 1.0
 */
@Slf4j
public class MultisetExample {
  public static void main(String[] args) {
    bookstoreHashMultiset();
  }

  private static void bookstoreHashMultiset() {
    Multiset<String> bookStore = HashMultiset.create();
    bookStore.add("Potter");
    bookStore.add("Potter");
    bookStore.add("Potter");

    log.info("Potter count: {}", bookStore.count("Potter"));

    bookStore.add("Tom", 6);
    log.info("Tom count:{}", bookStore.count("Tom"));

    //  iterate all element
    bookStore.forEach(System.out::println);

    // set Tom's occurrences to 10
    bookStore.setCount("Tom", 10);

    // print distinct element
    bookStore.elementSet().forEach(System.out::println);

    bookStore
        .entrySet()
        .forEach(
            entry -> log.info("entry element: {}, size: {}", entry.getElement(), entry.getCount()));

  }
}
