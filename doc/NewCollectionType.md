# New Collection Types:新集合实现类

------

Guava实现了一些jdk没有提供的新集合类型，但是它们却能被广泛使用，和jdk的集合实现类是互补关系，并且guava的实现的新集合是符合jdk的集合规范的。

## Multiset

------

使用jdk的实现集合类，统计单词出现的次数需要这样实现：

```java
Map<String, Integer> counts = new HashMap<String, Integer>();
for (String word : words) {
  Integer count = counts.get(word);
  if (count == null) {
    counts.put(word, 1);
  } else {
    counts.put(word, count + 1);
  }
}
```

这种实现方式不方便、容易出错，不支持收集各种各样的状态，就像统计单词出现的次数等场景，通过guava的实现可以做的更好：

[`Multiset`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html)能够添加多个相同的元素，维基百科定义Multiset：其中成员可以不止一次出现在集合中，如在集中，与元组相反，元素的顺序无关紧要：多集{a，a，b}和{ a，b，a}相等。

可以用2种方式来理解Multiset：

- 没有顺序限制的`ArrayList<E>`
- key是元素，value是count的`Map<E, Integer>`

Guava 的 `Multiset`组合了以上思想：

- 看成 `Collection`时, `Multiset` 表现更像未排序的 `ArrayList`
  - [ ] 调用 `add(E)` 增加当前元素的出现次数
  - [ ]  `iterator()` 迭代得到每一个元素
  - [ ]  `size()` 获取所有所有元素的总数量

- 查询方法就像`Map<E, Integer>`一样：
  - [ ] `count(Object)` 返回相关元素的个数. 对`HashMultiset`,时间复杂度是O(1), 对于`TreeMultiset`, 时间复杂度是 O(log n)
  - [ ] `entrySet()` 返回 `Set<Multiset.Entry<E>>` 就像 `Map`返回的EntrySet一样，用来遍历结果集
  - [ ] `elementSet()`返回元素的去重后的 `Set<E>` 
  - [ ]  `Multiset` 在内存消耗上是线性的

------

​	值得注意的是，Multiset与Collection接口的协定完全一致，在极少数情况下，JDK本身具有先例，特别是TreeMultiset和TreeSet一样，使用比较进行相等性而不是Object.equals。 特别是，Multiset.addAll（Collection）每次出现时都会在Collection中添加每个元素的出现一次，这比上面的Map方法所需的for循环方便得多。

| Method                                                       | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [`count(E)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#count-java.lang.Object-) | 统计给定元素的出现次数                                       |
| [`elementSet()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#elementSet--) | 将Multiset中的元素去重作为Set<E>返回                         |
| [`entrySet()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#entrySet--) | 与 `Map.entrySet()`相似, 返回 `Set<Multiset.Entry<E>>`, 返回的实体支持getElement()` 和 `getCount(). |
| [`add(E, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#add-java.lang.Object-int-) | 添加指定的元素的出现次数                                     |
| [`remove(E, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#remove-java.lang.Object-int--) | 移除指定元素出现次数                                         |
| [`setCount(E, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#setCount-E-int-) | 指定一个元素的出现次数（非负数）                             |
| `size()`                                                     | 返回 `Multiset`的所有元素数量总和.                           |

### Multiset 不是Map的实现类

`Multiset<E>`并不是`Map<E, Integer>`的封装，它只是`MultiSet`的部分实现。`MultiSet`是一个正真的集合（`Collection`）类，满足所有的集合接口定义的方法，还有一些重要的特性：

- MultiSet的元素个数必须是非负数，元素不可能出现负数出现次数，如果元素的count是0则其被认为没有出现在MultiSet中，也不会出现在`elementSet()`或`entrySet()`的返回结果中
- `multiset.size()`返回集合的元素个数，即集合中所有元素的总数之和（非去重后），如果要得到去重后的元素个数使用`elementSet().size()` （所以`add(E)`将会使得`multiset.size()`结果加一）
- `multiset.iterator()`将会遍历每一个出现的元素，所以其遍历元素个数会等于 `multiset.size()`.
- `Multiset<E>` 支持添加元素和移除元素， 或者直接设置元素的出现次数。 `setCount(elem, 0)` 等同于移除一个元素。
- `multiset.count(elem)` 查询一个不存在的元素时总是返回0

### MultiSet实现类

Guava提供了许多MultiSet的实现类，大致对应JDK的map实现

| Map                 | 对应的 Multiset                                              | Supports `null` elements |
| ------------------- | ------------------------------------------------------------ | ------------------------ |
| `HashMap`           | [`HashMultiset`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/HashMultiset.html) | Yes                      |
| `TreeMap`           | [`TreeMultiset`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/TreeMultiset.html) | Yes                      |
| `LinkedHashMap`     | [`LinkedHashMultiset`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/LinkedHashMultiset.html) | Yes                      |
| `ConcurrentHashMap` | [`ConcurrentHashMultiset`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ConcurrentHashMultiset.html) | No                       |
| `ImmutableMap`      | [`ImmutableMultiset`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ImmutableMultiset.html) | No                       |

### SortedMultiset

SortedMultiset是Multiset接口上的一种变体，它支持有效地提取指定范围内的子多集。 例如，可以使用`latencies.subMultiset（0，BoundType.CLOSED，100，BoundType.OPEN）.size（）`来确定网站在100ms延迟内的匹配数，然后将其与latencies.size（）进行比较 确定整体比例。

`TreeMultiset` 实现了`SortedMultiset` 接口。 

### Multimap

每个有经验的Java程序员都在某一方面实现了`Map <K，List <V >>`或`Map <K，Set <V >>`，并处理了该结构的尴尬。 例如，Map <K，Set <V >>是表示未标记有向图的典型方法。 Guava的Multimap框架使处理键到多个值的映射变得容易。 Multimap是将键与任意多个值相关联的通用方法。

有2种方式去理解MultiMap的概念：

- 从单个键到单个值的映射的集合

  ```
  a -> 1
  a -> 2
  a -> 4
  b -> 3
  c -> 5
  ```

- 从唯一个键到一个集合的映射

  ```
  a -> [1, 2, 4]
  b -> [3]
  c -> [5]
  ```

通常来说， `Multimap` 接口最好从第一种视图方式来使用, 但是也是允许使用 `asMap()`  来得到 `Map<K, Collection<V>>`来查看。最重要的是不存在一个key对应一个空集合 : 一个key至少对应一个值，要么更根本不存在于 `Multimap`中。

很少直接使用`Multimap`接口,更多的是使用 `ListMultimap` 或者  `SetMultimap`，各自将key对应到`List`或者 `Set`

### 构造

创建`Multimap`最直接的方法是使用[`MultimapBuilder`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/MultimapBuilder.html)，可以配置键值如何构造，例子：

```java
// creates a ListMultimap with tree keys and array list values
ListMultimap<String, Integer> treeListMultimap =
    MultimapBuilder.treeKeys().arrayListValues().build();

// creates a SetMultimap with hash keys and enum set values
SetMultimap<Integer, MyEnum> hashEnumMultimap =
    MultimapBuilder.hashKeys().enumSetValues(MyEnum.class).build();
```

我没可能会更多的选择使用实现类的`create()`方法，但是还是建议使用`MultimapBuilder`进行构造。

### 修改

[`Multimap.get(key)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimap.html#get-K-)，返回相关的key对应的视图，即使目前还没有。对于`ListMultimap`返回`List`，对`SetMultimap`返回`Set`。

修改`Multimap`案例：

```java
Set<Person> aliceChildren = childrenMultimap.get(alice);
aliceChildren.clear();
aliceChildren.add(bob);
aliceChildren.add(carol);
```

更直接操作`multimap`的方式：

| 方法签名                                                     | 描述                                                         | 等同方式                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| [`put(K, V)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimap.html#put-K-V-) | Adds an association from the key to the value.               | `multimap.get(key).add(value)`                               |
| [`putAll(K, Iterable)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimap.html#putAll-K-java.lang.Iterable-) | Adds associations from the key to each of the values in turn. | `Iterables.addAll(multimap.get(key), values)`                |
| [`remove(K, V)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimap.html#remove-java.lang.Object-java.lang.Object-) | Removes one association from `key` to `value` and returns `true` if the multimap changed. | `multimap.get(key).remove(value)`                            |
| [`removeAll(K)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimap.html#removeAll-java.lang.Object-) | Removes and returns all the values associated with the specified key. The returned collection may or may not be modifiable, but modifying it will not affect the multimap. (Returns the appropriate collection type.) | `multimap.get(key).clear()`                                  |
| [`replaceValues(K, Iterable)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimap.html#replaceValues-K-java.lang.Iterable-) | Clears all the values associated with `key` and sets `key` to be associated with each of `values`. Returns the values that were previously associated with the key. | `multimap.get(key).clear(); Iterables.addAll(multimap.get(key), values)` |

### 查看视图

`Multimap`支持一系列有效的视图

- [`asMap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimap.html#asMap--) 将任何 `Multimap<K, V>` 转换为 `Map<K, Collection<V>>`。 返回的map支持 `remove`以及对返回集合的进行操作， 但是不支持 `put` 或者 `putAll`. 至关重要的是，可以使用 `asMap().get(key)` 得到 `null`在一个不存在的键上，而不是一个新的可写的空集合。 (应该将 `asMap.get(key)` 结果强转为合适的集合类型 --   `SetMultimap`对应`Set` ， `ListMultimap`对应`List` --但是Java的范型不允许 `ListMultimap` 返回 `Map<K, List<V>>` )
- [`entries`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimap.html#entries--) 查看`Multimap`中所有的实体作为  `Collection<Map.Entry<K, V>>` 。 (对于 `SetMultimap` 来说集合是 `Set`)
- [`keySet`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimap.html#keySet--) 查看 `Multimap`去重后的key作为 `Set`返回
- [`keys`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimap.html#keys--) 查看 `Multimap`中的key ，返回 `Multiset`，with multiplicity equal to the number of values associated to that key. Elements can be removed from the `Multiset`, but not added; changes will write through.
- [`values()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimap.html#values--) 查看 `Multimap` 中所有的值，作为一个 `Collection<V>`返回。 和使用 `Iterables.concat(multimap.asMap().values())`效果一样 。 

### Multimap 不是 Map

 `Multimap<K, V>` 并不是 `Map<K, Collection<V>>`，只是可能通过`Map<K, Collection<V>>`来实现 `Multimap<K, V>`，重要的不同点：

- `Multimap.get(key)` 总是返回一个非null但是可能为空的集合。 这并不意味着multiMap将为这个key占用更多的内存。相反这个集合是一个可以用来关联我们所使用的key
- 如果更想使用 `Map`一样的特性-像返回 `null` 如果key不存在于 multimap，使用 `asMap()` 得到 `Map<K, Collection<V>>`. ( 使用 [`Multimaps.asMap()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multimaps.html#asMap-com.google.common.collect.ListMultimap-) 方法从 `ListMultimap`获取 `Map<K,`**`List`**`<V>>` 。同样对 `SetMultimap` 来说可以获取 `SortedSetMultimap`。)
- `Multimap.containsKey(key)` 当且仅当有值关联到key是返回true， 特别的，如果键 `k` 之前关联了一个或多个value但是又被移除了， `Multimap.containsKey(k)` 将会返回false。
- `Multimap.entries()` 返回 `Multimap`中所有的键值对（包含重复的key）， 如果需要得到 key与collection的entries则使用`asMap().entrySet()`。
- `Multimap.size()`  返回multimap的实体个数，但不是去重后的数量，使用 `Multimap.keySet().size()` 获取去重后的数量。

### 实现类

`Multimap` 提供了许多实现类，可以在要使用 `Map<K, Collection<V>>`的场景进行使用

| Implementation                                               | Keys behave like... | Values behave like.. |
| ------------------------------------------------------------ | ------------------- | -------------------- |
| [`ArrayListMultimap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ArrayListMultimap.html) | `HashMap`           | `ArrayList`          |
| [`HashMultimap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/HashMultimap.html) | `HashMap`           | `HashSet`            |
| [`LinkedListMultimap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/LinkedListMultimap.html) `*` | `LinkedHashMap``*`  | `LinkedList``*`      |
| [`LinkedHashMultimap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/LinkedHashMultimap.html)`**` | `LinkedHashMap`     | `LinkedHashSet`      |
| [`TreeMultimap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/TreeMultimap.html) | `TreeMap`           | `TreeSet`            |
| [`ImmutableListMultimap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ImmutableListMultimap.html) | `ImmutableMap`      | `ImmutableList`      |
| [`ImmutableSetMultimap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ImmutableSetMultimap.html) | `ImmutableMap`      | `ImmutableSet`       |

以上的实现，除了不可变版本，都支持key和value为null的情况。

