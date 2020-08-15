package org.cyka.collections;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PeekingIteratorExample {
  private final List<String> result = Lists.newArrayList();
  private final List<String> source = Lists.newArrayList("java", "java", "python", "go");
  private final PeekingIterator<String> iter = Iterators.peekingIterator(source.iterator());

  public void removeDuplicateItem() {
    while (iter.hasNext()) {
      String current = iter.next();
      while (iter.hasNext() && iter.peek().equals(current)) {
        // skip this duplicate element
        iter.next();
      }
      result.add(current);
    }
    log.info("result:{}", result);
  }
}
