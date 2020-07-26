# Immutable Collections: 不可变集合

------

### 案例

------

```java
public class ImmutableSetExample {
  public static void main(String[] args) {
    ImmutableSet<String> immutableSet =
        ImmutableSet.of("red", "orange", "yellow", "green", "blue", "purple");
    for (String s : immutableSet) {
      System.out.println(s);
    }
  }
}
```

### 为什么要使用不可变集合?

------

不可变集合有许多优势：

- 使用不可信任的依赖包时，能保证数据安全性
- 线程安全：可以在多线程环境下使用，不会出现线程竞速现象
- 不需要支持修改，所以可以节约时间与空间。所有的不可变集合实现类都比可变的集合在内存使用方面都更高效
- 可以被使用为常量

使用不可变的对象拷贝是一个非常有用的防御式编程的技巧，Guava提供简单易用的jdk集合不可变版本的实现，包括Guava自己提供的集合。

jdk虽然提供了`Collections.unmodifiableXXX` 方法让我们能够转变集合为不可变集合，但是存在以下不足：

- 笨重和冗余：使用起来过于笨重以及不便
- 安全性：只有当原集合的引用没有被任何对象持有时，方法返回的集合才是不可变集合
- 性能：底层使用的数据结构仍然是基于可变集合的数据结构，包括并发修改检查、hash表的额外空间占用等

**当不希望去修改集合或者期望一个集合保持不变时，使用不可变集合是一个很好的选择**

**PS：Guava实现的不可变集合不允许出现null，所以如果需要不可变集合中出现null则考虑使用 `Collections.unmodifiableXXX` **

### 如何使用?

------

`ImmutableXXX`可以被几种方式创建：

- 使用`copyOf`方法创建，`ImmutableSet.copyOf(set)`
- 使用 `of`方法，`ImmutableSet.of("a", "b", "c" `  或者 `ImmutableMap.of("a", 1, "b", 2)`
- 使用 `Builder`，案例：

```java
public static final ImmutableSet<Color> GOOGLE_COLORS =
   ImmutableSet.<Color>builder()
       .addAll(WEBSAFE_COLORS)
       .add(new Color(0, 191, 255))
       .build();
```

**元素的顺序会在集合构造时进行维护：**

```java
ImmutableSet.of("a", "b", "c", "a", "d", "b")
```

