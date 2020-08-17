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

字符匹配器

```java
String noControl = CharMatcher.javaIsoControl().removeFrom(string); // remove control characters
String theDigits = CharMatcher.digit().retainFrom(string); // only the digits
String spaced = CharMatcher.whitespace().trimAndCollapseFrom(string, ' ');
  // trim whitespace at ends, and replace/collapse whitespace into single spaces
String noDigits = CharMatcher.javaDigit().replaceFrom(string, "*"); // star out all digits
String lowerAndDigit = CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom(string);
  // eliminate all characters that aren't digits or lowercase
```

### 获取 CharMatchers

- [`any()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#any--)
- [`none()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#none--)
- [`whitespace()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#whitespace--)
- [`breakingWhitespace()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#breakingWhitespace--)
- [`invisible()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#invisible--)
- [`digit()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#digit--)
- [`javaLetter()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#javaLetter--)
- [`javaDigit()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#javaDigit--)
- [`javaLetterOrDigit()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#javaLetterOrDigit--)
- [`javaIsoControl()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#javaIsoControl--)
- [`javaLowerCase()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#javaLowerCase--)
- [`javaUpperCase()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#javaUpperCase--)
- [`ascii()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#ascii--)
- [`singleWidth()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#singleWidth--)


其他获取方法

| Method                                                       | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [`anyOf(CharSequence)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#anyOf-java.lang.CharSequence-) | 指定字符序列匹配， `CharMatcher.anyOf("aeiou")` 匹配小些英文元音 |
| [`is(char)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#is-char-) | 指定单个字符匹配                                             |
| [`inRange(char, char)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#inRange-char-char-) | 指定字符范围匹配 `CharMatcher.inRange('a', 'z')`.            |

### 使用 CharMatchers

`CharMatcher`提供了一系列操作在`CharSequence`中的出现的字符：

| Method                                                       | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [`collapseFrom(CharSequence, char)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#collapseFrom-java.lang.CharSequence-char-) | `CharMatcher.anyOf("eko").collapseFrom("bookkeeper", '-')` -> "b-p-r" |
| [`matchesAllOf(CharSequence)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#matchesAllOf-java.lang.CharSequence-) | `ASCII.matchesAllOf(string)` 查看是否所有的字符都是 ASCII.   |
| [`removeFrom(CharSequence)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#removeFrom-java.lang.CharSequence-) | 从字符串中移除匹配的字符                                     |
| [`retainFrom(CharSequence)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#retainFrom-java.lang.CharSequence-) | 移除所有不匹配的字符                                         |
| [`trimFrom(CharSequence)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#trimFrom-java.lang.CharSequence-) | 移除开头和结尾配置的字符                                     |
| [`replaceFrom(CharSequence, CharSequence)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CharMatcher.html#replaceFrom-java.lang.CharSequence-java.lang.CharSequence-) | `CharMatcher.is('a').replaceFrom("yaha", "oo")`  ->   "yoohoo". |

除了 `matchesAllOf`返回 `boolean`，其余都返回`String`

## Charsets

不要使用：

```java
try {
  bytes = string.getBytes("UTF-8");
} catch (UnsupportedEncodingException e) {
  // how can this possibly happen?
  throw new AssertionError(e);
}
```

通过Guava：

```java
bytes = string.getBytes(Charsets.UTF_8);
```

[`Charsets`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/Charsets.html) 提供了 `Charset`标准实现的常量实现。所以不要直接使用`Charset`的名称进而使用Guava提供的`Charset`枚举

## CaseFormat

| Format                                                       | Example            |
| ------------------------------------------------------------ | ------------------ |
| [`LOWER_CAMEL`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CaseFormat.html#LOWER_CAMEL) | `lowerCamel`       |
| [`LOWER_HYPHEN`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CaseFormat.html#LOWER_HYPHEN) | `lower-hyphen`     |
| [`LOWER_UNDERSCORE`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CaseFormat.html#LOWER_UNDERSCORE) | `lower_underscore` |
| [`UPPER_CAMEL`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CaseFormat.html#UPPER_CAMEL) | `UpperCamel`       |
| [`UPPER_UNDERSCORE`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/base/CaseFormat.html#UPPER_UNDERSCORE) | `UPPER_UNDERSCORE` |

```java
CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME")); // returns "constantName"
```

