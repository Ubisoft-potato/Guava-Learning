# Guava Basic Example

## avoid null: 防止空指针

## preConditions：参数预检

## Ordering: 排序

### 概览
`Ordering` 类是Guava封装的一个Comparator的实现类，非常便利，可以用来构建复杂的comparator然后
用于Collection集合排序中。

从本质上讲，Ordering实例不过是一个特殊的Comparator实例。排序仅采用依赖于Comparator的方法（例如Collections.max），并将它们用作实例方法。
为了增加功能，Ordering类提供了链式调用来调整和增强现有的比较器。

### 创建Ordering
常见的静态方法：

|  方法   | 描述 |
|  ----  | ----  |
| natural()  | 使用自然排序 |
| usingToString()  | 通过toString（）返回的字符串表示形式的字典顺序对对象进行比较。 |

如果要使用现有的comparator，可以直接使用 `Ordering.from(Comparator)`




