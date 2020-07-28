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
| [`count(E)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#count-java.lang.Object-) | Count the number of occurrences of an element that have been added to this multiset. |
| [`elementSet()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#elementSet--) | View the distinct elements of a `Multiset<E>` as a `Set<E>`. |
| [`entrySet()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#entrySet--) | Similar to `Map.entrySet()`, returns a `Set<Multiset.Entry<E>>`, containing entries supporting `getElement()` and `getCount()`. |
| [`add(E, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#add-java.lang.Object-int-) | Adds the specified number of occurrences of the specified element. |
| [`remove(E, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#remove-java.lang.Object-int--) | Removes the specified number of occurrences of the specified element. |
| [`setCount(E, int)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Multiset.html#setCount-E-int-) | Sets the occurrence count of the specified element to the specified nonnegative value. |
| `size()`                                                     | Returns the total number of occurrences of all elements in the `Multiset`. |

