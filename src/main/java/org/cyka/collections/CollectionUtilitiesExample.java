package org.cyka.collections;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Set;

public class CollectionUtilitiesExample {
  public static void main(String[] args) {
    ArrayList<Integer> withCapacity = Lists.newArrayListWithCapacity(100);

    Set<String> wordsWithPrimeLength =
        ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
    Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");

    // 交集
    Sets.SetView<String> intersection =
        Sets.intersection(primes, wordsWithPrimeLength); // contains "two", "three", "seven"
    // I can use intersection as a Set directly, but copying it can be more efficient if I use it a
    // lot.
    ImmutableSet<String> stringImmutableSet = intersection.immutableCopy();
  }
}
