# String 工具类

## Joiner

用分隔符将一串字符串连接在一起可能会很棘手-但事实并非如此。 如果序列包含空值，则可能会更加困难。[`Joiner`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Joiner.html)的链式调用api风格使得问题变得简单。

```java
Joiner joiner = Joiner.on("; ").skipNulls();
return joiner.join("Harry", null, "Ron", "Hermione"); //return "Harry; Ron; Hermione"
```

如果不使用`skipNulls`，可以使用`useForNull(String)`来指定代替null的字符串

使用`Joiner`连接object，`Joiner`会调用他们的`toString()`方法后进行连接

```java
Joiner.on(",").join(Arrays.asList(1, 5, 7)); // returns "1,5,7"
```

**警告:** joiner 实例是不可变的。joiner每次配置完都会返回一个新的 `Joiner`。这使得 `Joiner是线程安全的,可以作为一个 `static final` 常量使用。

## Splitter

Jdk内置的string split方法使用起来会有点古怪，比如`String.split`会将末尾的分隔符忽略掉。

`",a,,b,".split(",")` 将会得到`"", "a", "", "b"`，

[`Splitter`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html)的链式调用api将会完全支持控制这种分割行为。

```java
Splitter.on(',')
    .trimResults()
    .omitEmptyStrings()
    .split("foo,bar,,   qux");
```

将会返回  `Iterable<String>`包含 "foo", "bar", "qux"。 `Splitter` 可以使用任何 `Pattern`, `char`, `String`,或者 `CharMatcher`.

### 基本的构造方法

| Method                                                       | Description                                    | Example                                                      |
| ------------------------------------------------------------ | ---------------------------------------------- | ------------------------------------------------------------ |
| [`Splitter.on(char)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html#on-char-) | 基于字符分隔                                   | `Splitter.on(';')`                                           |
| [`Splitter.on(CharMatcher)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html#on-com.google.common.base.CharMatcher-) | 基于字符分隔                                   | `Splitter.on(CharMatcher.BREAKING_WHITESPACE)` `Splitter.on(CharMatcher.anyOf(";,."))` |
| [`Splitter.on(String)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html#on-java.lang.String-) | 基于 `String`分割                              | `Splitter.on(", ")`                                          |
| [`Splitter.on(Pattern)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html#on-java.util.regex.Pattern-) [`Splitter.onPattern(String)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html#onPattern-java.lang.String-) | 基于正则分隔                                   | `Splitter.onPattern("\r?\n")`                                |
| [`Splitter.fixedLength(int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html#fixedLength-int-) | 按`length`分割，最后一个元素始终不会为空字符串 | `Splitter.fixedLength(3)`                                    |

### 修改字符串方法

| Method                                                       | Description                                                  | Example                                                      |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| [`omitEmptyStrings()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html#omitEmptyStrings--) | 忽略为空的字符串                                             | `Splitter.on(',').omitEmptyStrings().split("a,,c,d")` returns `"a", "c", "d"` |
| [`trimResults()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html#trimResults--) | 去出每个子字符串的空格相当于`trimResults(CharMatcher.WHITESPACE)`. | `Splitter.on(',').trimResults().split("a, b, c, d")` returns `"a", "b", "c", "d"` |
| [`trimResults(CharMatcher)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html#trimResults-com.google.common.base.CharMatcher-) | 去除指定的 `CharMatcher` 字符                                | `Splitter.on(',').trimResults(CharMatcher.is('_')).split("_a ,_b_ ,c__")` returns `"a ", "b_ ", "c"`. |
| [`limit(int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html#limit-int-) | 限制分隔后返回的元素个数                                     | `Splitter.on(',').limit(3).split("a,b,c,d")` returns `"a", "b", "c,d"` |

如果需要得到 `List`, 使用 [`splitToList()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Splitter.html#splitToList-java.lang.CharSequence-) 而不是 `split()`。

**警告:** splitter 实例总是不可变的。 splitter配置方法总是返回新的 `Splitter`，所以 `Splitter` 是线程安全的可以用为 `static final` 常量。

### Map Splitters

```java
String toSplit = " x -> y, z-> a ";
 Splitter outerSplitter = Splitter.on(',').trimResults();
 MapSplitter mapSplitter = outerSplitter.withKeyValueSeparator(Splitter.on("->"));
 Map<String, String> result = mapSplitter.split(toSplit);
 assertThat(result).isEqualTo(ImmutableMap.of("x ", " y", "z", " a"));
```

## CharMatcher