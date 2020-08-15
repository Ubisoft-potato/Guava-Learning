package org.cyka.collections;

import com.google.common.collect.Lists;
import org.junit.Test;

public class CollectionHelperTest {
  @Test
  public void addLoggingListTest() {

    AddLoggingList<String> stringAddLoggingList =
        new AddLoggingList<>(Lists.newArrayListWithCapacity(10));

    stringAddLoggingList.add("java");
    stringAddLoggingList.add("perl");
    stringAddLoggingList.add("ruby");

    stringAddLoggingList.addAll(Lists.newArrayList("spring","go"));
  }
}
