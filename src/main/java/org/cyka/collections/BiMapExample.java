package org.cyka.collections;

import com.google.common.collect.HashBiMap;

public class BiMapExample {
  public static void main(String[] args) {
    HashBiMap<String, Integer> hashBiMap = HashBiMap.create(10);
    hashBiMap.put("as", 11);
    // inverse map to  get  the key mapped to value
    System.out.println(hashBiMap.inverse().get(11));
  }
}
