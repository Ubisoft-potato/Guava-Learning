package org.cyka.collections;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class CollectionUtilitiesExample {
  public static void main(String[] args) {
    ArrayList<Integer> withCapacity = Lists.newArrayListWithCapacity(100);
    setsUtil();
    iterableUtil();
  }

  private static void iterableUtil() {
    Iterable<Integer> concatenated = Iterables.concat(Ints.asList(1, 2, 3), Ints.asList(4, 5, 6));

    Integer last = Iterables.getLast(concatenated);
    log.info("last element: {}", last);

    Iterable<List<Integer>> partition = Iterables.partition(concatenated, 2);
    log.info("partition: {}", partition);

    //  throw IllegalArgumentException
    //    Integer onlyElement = Iterables.getOnlyElement(concatenated);
  }

  private static void setsUtil() {
    Set<String> wordsWithPrimeLength =
        ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
    Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");

    Sets.SetView<String> intersection =
        Sets.intersection(primes, wordsWithPrimeLength); // contains "two", "three", "seven"
    // I can use intersection as a Set directly, but copying it can be more efficient if I use it a
    // lot.
    ImmutableSet<String> stringImmutableSet = intersection.immutableCopy();
    log.info("stringImmutableSet: {}", stringImmutableSet);
  }
}
