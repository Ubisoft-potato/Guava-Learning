package org.cyka.basic;

import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Preconditions.*;

/**
 * @ClassName PreConditions @Description PreConditions @Author long @Date 2020/5/14 10:19 @Version
 * 1.0
 */
@Slf4j
public class PreConditionsExample {
  public static void main(String[] args) {
    Integer i = -12;
    checkArgument(i >= 0, "Argument was %s but expected nonNegative", i);
    checkNotNull(i);
    checkElementIndex(2, 2);
    checkState(true);
  }
}
