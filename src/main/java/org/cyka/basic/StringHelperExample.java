package org.cyka.basic;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringHelperExample {

  private final Joiner joiner = Joiner.on(";").useForNull("default");

  private final Splitter splitter = Splitter.on(',').trimResults().omitEmptyStrings();

  public String joinStrings(Iterable<?> iterable) {
    return joiner.join(iterable);
  }

  public Iterable<String> splitStrings(String s) {
    return splitter.split(s);
  }
}
