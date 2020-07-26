package org.cyka.collections;

import com.google.common.collect.ImmutableSet;

/** Immutable Collections */
public class ImmutableSetExample {
  public static void main(String[] args) {
    ImmutableSet<String> immutableSet =
        ImmutableSet.of("red", "orange", "yellow", "green", "blue", "purple");
    for (String s : immutableSet) {
      System.out.println(s);
    }
  }
}
