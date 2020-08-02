package org.cyka.collections;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;

public class MultiMapExample {

  private static final String key = "Key";

  public static void main(String[] args) {
    // use list value
    ListMultimap<String, String> listMultimap =
        MultimapBuilder.treeKeys().arrayListValues(10).build();
    listMultimap.put(key, "122");
    listMultimap.put(key, "1id");
    listMultimap.put(key, "982");
    listMultimap.put(key, "jvo");
    listMultimap.put(key, "jvo");
    listMultimap.put("one", "asddd");

    listMultimap.entries().forEach(System.out::println);
    System.out.println(listMultimap.keySet());
    System.out.println(listMultimap.keys());
    listMultimap.values().forEach(System.out::println);

    System.out.println("-----------------------------------------------");

    // use set value
    SetMultimap<String, String> setMultimap = MultimapBuilder.treeKeys().hashSetValues().build();

    setMultimap.put(key, "asd");
    setMultimap.put(key, "asd");

    setMultimap.entries().forEach(System.out::println);
  }
}
