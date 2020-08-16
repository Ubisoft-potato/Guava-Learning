package org.cyka.basic;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class StringsTest {

  private final StringHelperExample stringHelperExample = new StringHelperExample();

  @Test
  public void joinStringTest() {
    String joinStrings =
        stringHelperExample.joinStrings(Lists.newArrayList("Harry", null, "Ron", "Hermione"));
    log.info("join string: {}", joinStrings);
  }

  @Test
  public void splitStringTest() {
    stringHelperExample.splitStrings("foo,bar,,   qux").forEach(System.out::println);
  }
}
