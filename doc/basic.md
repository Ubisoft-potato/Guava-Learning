# Guava Basic Example

## avoid null: 防止空指针

使用`null`一不小心会导致很严重的bug，Guava开发者发现有95%的Collection不支持null作为元素。

### Optional

```java
Optional<Integer> possible = Optional.of(5);
possible.isPresent(); // returns true
possible.get(); // returns 5
```

### 创建Oprional

| Method                                                       | Description                                              |
| ------------------------------------------------------------ | -------------------------------------------------------- |
| [`Optional.of(T)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Optional.html#of-T-) | 创建一个Optional使用非空值，如果使用null将直接报错       |
| [`Optional.absent()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Optional.html#absent--) | 返回包含任何引用的Optional相当于Jdk8的`Optional.empty()` |
| [`Optional.fromNullable(T)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Optional.html#fromNullable-T-) | 使用可能为null的值进行创建                               |

### 查询方法

| Method                                                       | Description                                          |
| ------------------------------------------------------------ | ---------------------------------------------------- |
| [`boolean isPresent()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Optional.html#isPresent--) | 如果包含非null的值返回`true`                         |
| [`T get()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Optional.html#get--) | 返回范型`T` 的实例，否则抛出`IllegalStateException`. |
| [`T or(T)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Optional.html#or-T-) | 返回 `Optional`中的值, 如果为null, 则返回指定值      |
| [`T orNull()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Optional.html#orNull--) | 返回 `Optional`中的值, 如果为null, 则返回null        |
| [`Set asSet()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Optional.html#asSet--) | 返回一个包含`Optional`中值的 `Set` 。                |

### Guava提供的便利方法

无论何时想要使用默认值替换`null`，使用 [`MoreObjects.firstNonNull(T, T)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/MoreObjects.html#firstNonNull-T-T-)

`Strings`提供了处理string类的`null`情况的方法

- [`emptyToNull(String)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Strings.html#emptyToNull-java.lang.String-)
- [`isNullOrEmpty(String)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Strings.html#isNullOrEmpty-java.lang.String-)
- [`nullToEmpty(String)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Strings.html#nullToEmpty-java.lang.String-)

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




