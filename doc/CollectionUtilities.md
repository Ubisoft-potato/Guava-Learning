# Collection Utilities：集合工具类

------

任何一个使用过JDK集合工具类的程序员都会喜欢 [`java.util.Collections`](http://docs.oracle.com/javase/7/docs/api/java/util/Collections.html)，Guava提供了更多的工具类：使用静态方法即可操作。

Guava集合工具类列表：

| Interface                                                    | JDK or Guava? | Corresponding Guava utility class                            |
| ------------------------------------------------------------ | ------------- | ------------------------------------------------------------ |
| `Collection`                                                 | JDK           | [`Collections2`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Collections2.html) |
| `List`                                                       | JDK           | [`Lists`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html) |
| `Set`                                                        | JDK           | [`Sets`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html) |
| `SortedSet`                                                  | JDK           | [`Sets`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html) |
| `Map`                                                        | JDK           | [`Maps`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Maps.html) |
| `SortedMap`                                                  | JDK           | [`Maps`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Maps.html) |
| `Queue`                                                      | JDK           | [`Queues`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Queues.html) |
| [`Multiset`](https://github.com/google/guava/wiki/NewCollectionTypesExplained#Multiset) | Guava         | [`Multisets`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multisets.html) |
| [`Multimap`](https://github.com/google/guava/wiki/NewCollectionTypesExplained#Multimap) | Guava         | [`Multimaps`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimaps.html) |
| [`BiMap`](https://github.com/google/guava/wiki/NewCollectionTypesExplained#BiMap) | Guava         | [`Maps`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Maps.html) |
| [`Table`](https://github.com/google/guava/wiki/NewCollectionTypesExplained#Table) | Guava         | [`Tables`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Tables.html) |

### 静态构造器

------

在JDK7之前，构造范型集合会使用到令人不愉快的代码：

```java
List<TypeThatsTooLongForItsOwnGood> list = new ArrayList<TypeThatsTooLongForItsOwnGood>();
```

Guava提供了静态方法去构造范型集合：

```java
List<TypeThatsTooLongForItsOwnGood> list = Lists.newArrayList();
Map<KeyType, LongishValueType> map = Maps.newLinkedHashMap();
```

JDK7出现的菱形表示法避免了这种麻烦：

```java
List<TypeThatsTooLongForItsOwnGood> list = new ArrayList<>();
```

但是Guava比这提供更多的便利，使用工厂方法设计模式，我们可以方便的使用元素初始化集合：

```java
Set<Type> copySet = Sets.newHashSet(elements);
List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");
```

额外的，使用工厂方法在使用初始化集合大小时可以提高代码的可读性：

```java
List<Type> exactly100 = Lists.newArrayListWithCapacity(100);
List<Type> approx100 = Lists.newArrayListWithExpectedSize(100);
Set<Type> approx100Set = Sets.newHashSetWithExpectedSize(100);
```

注意：Guava提供的新的集合类并没有暴露原生的构造器或者相关的初始化工具类，相反提供的是静态工厂方法：

```java
Multiset<String> multiset = HashMultiset.create();
```

### 迭代

------

​		无论何时，Guava都更倾向于提供接受 `Iterable` 而不是一个 `Collection`，在Google，并不是所有的数据都通过集合存储在主内存中，它们可能来自数据库、或者其他的数据中心，在没有实际的获取到所有元素时不能支持使用 像 `size()`这样的集合操作。

​		所有的 `Iterable` 操作都被封装在 [`Iterables`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html)中，另外，大多数 `Iterables` 方法在 [`Iterators`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterators.html) 都有一个对应的版本，可以接受JDK原生的Iterator。

​	 `Iterables`中大多数操作是惰性的 : 它们仅在必要的时候进行后备迭代。 本身返回 `Iterable` 的方法将返回延迟计算的视图,而不是在内存中显示构造一个集合。

在Guava12中， `Iterables` 被 [`FluentIterable`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/FluentIterable.html) 类所增强，包装了一个 `Iterable` 并且提供了链式调用的API

### 通用方法列表

------

| Method                                                       | Description                                                  | See Also                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| [`concat(Iterable)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#concat-java.lang.Iterable-) | 返回多个Iterable实现类的延迟加载视图                         | [`concat(Iterable...)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#concat-java.lang.Iterable...-) |
| [`frequency(Iterable, Object)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#frequency-java.lang.Iterable-java.lang.Object-) | 返回元素出现的次数                                           | Compare `Collections.frequency(Collection, Object)`; see [`Multiset`](https://github.com/google/guava/wiki/NewCollectionTypesExplained#Multiset) |
| [`partition(Iterable, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#partition-java.lang.Iterable-int-) | 返回Iterable实现类的指定大小分块不可变视图。例子：[a, b, c, d, e] -> [[a, b, c], [d, e]] | [`Lists.partition(List, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html#partition-java.util.List-int-), [`paddedPartition(Iterable, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#paddedPartition-java.lang.Iterable-int-) |
| [`getFirst(Iterable, T default)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#getFirst-java.lang.Iterable-T-) | 返回iterable的第一个元素，如果为空返回默认值                 | Compare `Iterable.iterator().next()`, [`FluentIterable.first()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/FluentIterable.html#first--) |
| [`getLast(Iterable)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#getLast-java.lang.Iterable-) | 返回最后一个元素，或者抛出`NoSuchElementException` 如果iterable为空。 | [`getLast(Iterable, T default)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#getLast-java.lang.Iterable-T-), [`FluentIterable.last()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/FluentIterable.html#last--) |
| [`elementsEqual(Iterable, Iterable)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#elementsEqual-java.lang.Iterable-java.lang.Iterable-) | 判断2个iterable是否有同样的元素和顺序                        | Compare `List.equals(Object)`                                |
| [`unmodifiableIterable(Iterable)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#unmodifiableIterable-java.lang.Iterable-) | 返回ietrable不可变视图                                       | Compare `Collections.unmodifiableCollection(Collection)`     |
| [`limit(Iterable, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#limit-java.lang.Iterable-int-) | 返回一个具有指定数量的最大值的 `Iterable` .                  | [`FluentIterable.limit(int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/FluentIterable.html#limit-int-) |
| [`getOnlyElement(Iterable)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#getOnlyElement-java.lang.Iterable-) | 获取 `Iterable`的一个元素，如果iterable为空或者有多个值该方法会立即失败 | [`getOnlyElement(Iterable, T default)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#getOnlyElement-java.lang.Iterable-T-) |

```java
Iterable<Integer> concatenated = Iterables.concat(
  Ints.asList(1, 2, 3),
  Ints.asList(4, 5, 6));
// concatenated has elements 1, 2, 3, 4, 5, 6

String lastAdded = Iterables.getLast(myLinkedHashSet);

String theElement = Iterables.getOnlyElement(thisSetIsDefinitelyASingleton);
  // if this set isn't a singleton, something is wrong!
```

#### 类似集合的操作

------

通常来说，集合原生支持以上操作，但对Iterable接口对象不提供支持。

每种操作代理了其对应的 `Collection`接口方法当输入的参数是`Collection`时，例如：如果`Iterables.size`传递了一个`Collection`参数，它本质上是使用`Collection.size`方法而不是去遍历iterator。

| Method                                                       | Analogous `Collection` method      | `FluentIterable` equivalent                                  |
| ------------------------------------------------------------ | ---------------------------------- | ------------------------------------------------------------ |
| [`addAll(Collection addTo, Iterable toAdd)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#addAll-java.util.Collection-java.lang.Iterable-) | `Collection.addAll(Collection)`    |                                                              |
| [`contains(Iterable, Object)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#contains-java.lang.Iterable-java.lang.Object-) | `Collection.contains(Object)`      | [`FluentIterable.contains(Object)`](http://google.github.io/guava/releases/12.0/api/docs/com/google/common/collect/FluentIterable.html#contains-java.lang.Object-) |
| [`removeAll(Iterable removeFrom, Collection toRemove)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#removeAll-java.lang.Iterable-java.util.Collection-) | `Collection.removeAll(Collection)` |                                                              |
| [`retainAll(Iterable removeFrom, Collection toRetain)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#retainAll-java.lang.Iterable-java.util.Collection-) | `Collection.retainAll(Collection)` |                                                              |
| [`size(Iterable)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#size-java.lang.Iterable-) | `Collection.size()`                | [`FluentIterable.size()`](http://google.github.io/guava/releases/12.0/api/docs/com/google/common/collect/FluentIterable.html#size--) |
| [`toArray(Iterable, Class)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#toArray-java.lang.Iterable-java.lang.Class-) | `Collection.toArray(T[])`          | [`FluentIterable.toArray(Class)`](http://google.github.io/guava/releases/12.0/api/docs/com/google/common/collect/FluentIterable.html#toArray-java.lang.Class-) |
| [`isEmpty(Iterable)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#isEmpty-java.lang.Iterable-) | `Collection.isEmpty()`             | [`FluentIterable.isEmpty()`](http://google.github.io/guava/releases/12.0/api/docs/com/google/common/collect/FluentIterable.html#isEmpty--) |
| [`get(Iterable, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#get-java.lang.Iterable-int-) | `List.get(int)`                    | [`FluentIterable.get(int)`](http://google.github.io/guava/releases/12.0/api/docs/com/google/common/collect/FluentIterable.html#get-int-) |
| [`toString(Iterable)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterables.html#toString-java.lang.Iterable-) | `Collection.toString()`            | [`FluentIterable.toString()`](http://google.github.io/guava/releases/12.0/api/docs/com/google/common/collect/FluentIterable.html#toString--) |

### FluentIterable

------

`FluentIterable`有一些便利的方法来得到不可变集合：

| Result Type          | Method                                                       |
| -------------------- | ------------------------------------------------------------ |
| `ImmutableList`      | [`toImmutableList()`](http://google.github.io/guava/releases/12.0/api/docs/com/google/common/collect/FluentIterable.html#toImmutableList--) |
| `ImmutableSet`       | [`toImmutableSet()`](http://google.github.io/guava/releases/12.0/api/docs/com/google/common/collect/FluentIterable.html#toImmutableSet--) |
| `ImmutableSortedSet` | [`toImmutableSortedSet(Comparator)`](http://google.github.io/guava/releases/12.0/api/docs/com/google/common/collect/FluentIterable.html#toImmutableSortedSet-java.util.Comparator-) |

### Lists

------

除静态构造方法和函数式方法之外， [`Lists`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html)为 `List` 对象提供了一些有价值的工具方法：

| Method                                                       | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [`partition(List, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html#partition-java.util.List-int-) | 返回底层数组的视图，并分为指定的大小分块                     |
| [`reverse(List)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html#reverse-java.util.List-) | 反转指定数组。注意: 如果目标集合不可变，考虑使用 [`ImmutableList.reverse()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ImmutableList.html#reverse--) |

```java
List<Integer> countUp = Ints.asList(1, 2, 3, 4, 5);
List<Integer> countDown = Lists.reverse(theList); // {5, 4, 3, 2, 1}

List<List<Integer>> parts = Lists.partition(countUp, 2); // {{1, 2}, {3, 4}, {5}}
```

### 静态工厂

------

`Lists` 提供一下静态工厂方法：

| Implementation | Factories                                                    |
| -------------- | ------------------------------------------------------------ |
| `ArrayList`    | [basic](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html#newArrayList--), [with elements](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html#newArrayList-E...-), [from `Iterable`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html#newArrayList-java.lang.Iterable-), [with exact capacity](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html#newArrayListWithCapacity-int-), [with expected size](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html#newArrayListWithExpectedSize-int-), [from `Iterator`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html#newArrayList-java.util.Iterator-) |
| `LinkedList`   | [basic](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html#newLinkedList--), [from `Iterable`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Lists.html#newLinkedList-java.lang.Iterable-) |

## Comparators

------

#### 找到元素里的最大或最小值

​	看似简单的任务（查找某些元素的最小值或最大值）由于希望最大限度地减少分布在各个位置的分配，装箱和API的需求而变得复杂。 下表总结了此任务的最佳做法。

| What you're comparing                                        | Exactly 2 instances                                          | More than 2 instances                                        |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| unboxed numeric primitives (e.g., `long`, `int`, `double`, or `float`) | [`Math.max(a, b)`](https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#max-long-long-) | [`Longs.max(a, b, c)`](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/primitives/Longs.html#max-long...-), [`Ints.max(a, b, c)`](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/primitives/Ints.html#max-int...-), etc. |
| `Comparable` instances (e.g., `Duration`, `String`, `Long`, etc.) | [`Comparators.max(a, b)`](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/collect/Comparators.html#max-T-T-) | [`Collections.max(asList(a, b, c))`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#max-java.util.Collection-) |
| using a custom `Comparator` (e.g., `MyType` with `myComparator`) | [`Comparators.max(a, b, cmp)`](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/collect/Comparators.html#max-T-T-java.util.Comparator-) | [`Collections.max(asList(a, b, c), cmp)`](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#max-java.util.Collection-java.util.Comparator-) |



## Sets

------

#### Set理论操作

Guava提供的集合操作返回[`SetView`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.SetView.html)，能够用来：

- 直接用作 `Set` 因为实现了`Set`接口
- 使用 [`copyInto(Set)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.SetView.html#copyInto-S-)复制到另一个可变的集合中
- 通过 [`immutableCopy()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.SetView.html#immutableCopy--)获取不可变集合

| Method                                                       |
| ------------------------------------------------------------ |
| [`union(Set, Set)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#union-java.util.Set-java.util.Set-) |
| [`intersection(Set, Set)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#intersection-java.util.Set-java.util.Set-) |
| [`difference(Set, Set)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#difference-java.util.Set-java.util.Set-) |
| [`symmetricDifference(Set, Set)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#symmetricDifference-java.util.Set-java.util.Set-) |

例子：

```java
Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");

SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength); // contains "two", "three", "seven"
// I can use intersection as a Set directly, but copying it can be more efficient if I use it a lot.
return intersection.immutableCopy();
```

#### 其他的集合实用方法：

| Method                                                       | Description        | See Also                                                     |
| ------------------------------------------------------------ | ------------------ | ------------------------------------------------------------ |
| [`cartesianProduct(List)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#cartesianProduct-java.util.List-) | 返回集合的笛卡尔积 | [`cartesianProduct(Set...)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#cartesianProduct-java.util.Set...-) |
| [`powerSet(Set)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#powerSet-java.util.Set-) | 返回集合的所有子集 |                                                              |

```java
Set<String> animals = ImmutableSet.of("gerbil", "hamster");
Set<String> fruits = ImmutableSet.of("apple", "orange", "banana");

Set<List<String>> product = Sets.cartesianProduct(animals, fruits);
// {{"gerbil", "apple"}, {"gerbil", "orange"}, {"gerbil", "banana"},
//  {"hamster", "apple"}, {"hamster", "orange"}, {"hamster", "banana"}}

Set<Set<String>> animalSets = Sets.powerSet(animals);
// {{}, {"gerbil"}, {"hamster"}, {"gerbil", "hamster"}}
```

### Static Factories

------

`Sets` 提供以下静态工厂方法：

| Implementation  | Factories                                                    |
| --------------- | ------------------------------------------------------------ |
| `HashSet`       | [basic](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#newHashSet--), [with elements](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#newHashSet-E...-), [from `Iterable`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#newHashSet-java.lang.Iterable-), [with expected size](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#newHashSetWithExpectedSize-int-), [from `Iterator`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#newHashSet-java.util.Iterator-) |
| `LinkedHashSet` | [basic](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#newLinkedHashSet--), [from `Iterable`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#newLinkedHashSet-java.lang.Iterable-), [with expected size](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#newLinkedHashSetWithExpectedSize-int-) |
| `TreeSet`       | [basic](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#newTreeSet--), [with `Comparator`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#newTreeSet-java.util.Comparator-), [from `Iterable`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Sets.html#newTreeSet-java.lang.Iterable-) |

