package org.cyka.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/** @ClassName Person @Description  @Author long @Date 2020/5/14 15:27 @Version 1.0 */
@Data
@AllArgsConstructor
public class Person {
  private String name;
  private Integer age;
}
