# Collection Helpers

## 介绍

有时需要自己实现新的集合，比如在元素添加的时候进行一些操作，或者是基于数据库查询实现`Iterable`，Guava提供了一系列此方面工具类。

## Forwarding Decorators

对于所有的集合接口，Guava基于装饰器模式提供了`Forwarding`抽象类来简化使用

`Forwarding`定义了一个抽象方法`delegate()`,需要在子类重写并放回被装饰对象，所有的其他方法都将代理到这个对象，比如：`ForwardingList.get(int)`将被`delegate().get(int)`实现

通过继承`ForwardingXXX`实现`delegate()`方法，可以选择重写我们需要的方法，不需要自己代理每一个方法

额外，许多方法都提供了`standardMethod`实现，可以用来获取原生实现

案例：实现一个`List`能够在添加元素的时候进行记录，-- `add(int, E)`, `add(E)`或 `addAll(Collection)`方法，可以进行如下实现：

```java
class AddLoggingList<E> extends ForwardingList<E> {
  final List<E> delegate; // backing list
  @Override protected List<E> delegate() {
    return delegate;
  }
  @Override public void add(int index, E elem) {
    log(index, elem);
    super.add(index, elem);
  }
  @Override public boolean add(E elem) {
    return standardAdd(elem); // implements in terms of add(int, E)
  }
  @Override public boolean addAll(Collection<? extends E> c) {
    return standardAddAll(c); // implements in terms of add
  }
}
```

默认所有的方法都会直接代理到`delegate()`对象上，所以重写`ForwardingMap.put`，不会改变`ForwardingMap.putAll`的行为，一定要小心重写那些需要改变默认行为的方法，并确保包装后的集合满足JDK 集合接口规范

提供特殊视图的借口通常会提供这些试图的 `Standard` 实现。比如, `ForwardingMap` 提供了 `StandardKeySet`, `StandardValues`, 和 `StandardEntrySet`, 每个方法都会委托到`delegate()`实现，无法委托的则使用abstract声明

| Interface       | Forwarding Decorator                                         |
| --------------- | ------------------------------------------------------------ |
| `Collection`    | [`ForwardingCollection`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingCollection.html) |
| `List`          | [`ForwardingList`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingList.html) |
| `Set`           | [`ForwardingSet`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingSet.html) |
| `SortedSet`     | [`ForwardingSortedSet`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingSortedSet.html) |
| `Map`           | [`ForwardingMap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingMap.html) |
| `SortedMap`     | [`ForwardingSortedMap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingSortedMap.html) |
| `ConcurrentMap` | [`ForwardingConcurrentMap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingConcurrentMap.html) |
| `Map.Entry`     | [`ForwardingMapEntry`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingMapEntry.html) |
| `Queue`         | [`ForwardingQueue`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingQueue.html) |
| `Iterator`      | [`ForwardingIterator`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingIterator.html) |
| `ListIterator`  | [`ForwardingListIterator`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingListIterator.html) |
| `Multiset`      | [`ForwardingMultiset`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingMultiset.html) |
| `Multimap`      | [`ForwardingMultimap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingMultimap.html) |
| `ListMultimap`  | [`ForwardingListMultimap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingListMultimap.html) |
| `SetMultimap`   | [`ForwardingSetMultimap`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/ForwardingSetMultimap.html) |

## PeekingIterator

有时候Jdk的`Iterator`并不能完全满足需求

`Iterators`支持[`Iterators.peekingIterator(Iterator)`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/Iterators.html#peekingIterator-java.util.Iterator-)将`Iterator`包装为[`PeekingIterator`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/PeekingIterator.html)，其为`Iterator`的子类，能够支持使用[`peek()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/PeekingIterator.html#peek--)方法，调用`peek()`能够获取`next()`的下一个元素但索引不会改变.

注意:    `Iterators.peekingIterator`得到的`PeekingIterator` 在调用 `peek()`后不支持 `remove()`

```java
PeekingIterator<String> peekingIterator =
     Iterators.peekingIterator(Iterators.forArray("a", "b"));
 String a1 = peekingIterator.peek(); // returns "a"
 String a2 = peekingIterator.peek(); // also returns "a"
 String a3 = peekingIterator.next(); // also returns "a"
```

案例：消除连续相同的元素：

```java
List<E> result = Lists.newArrayList();
PeekingIterator<E> iter = Iterators.peekingIterator(source.iterator());
while (iter.hasNext()) {
  E current = iter.next();
  while (iter.hasNext() && iter.peek().equals(current)) {
    // skip this duplicate element
    iter.next();
  }
  result.add(current);
}
```

## AbstractIterator

实现自己的 `Iterator`， [`AbstractIterator`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/AbstractIterator.html)能够让它变得更简单

实现一个跳过null的Iterator ：

```java
public static Iterator<String> skipNulls(final Iterator<String> in) {
  return new AbstractIterator<String>() {
    protected String computeNext() {
      while (in.hasNext()) {
        String s = in.next();
        if (s != null) {
          return s;
        }
      }
      return endOfData();
    }
  };
}
```

只需要重写一个方法[`computeNext()`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/AbstractIterator.html#computeNext--)，这个方法只会计算下一个值，当遍历到最后一个元素，直接返回`endOfData()` 标记此次迭代结束。

*注意:* `AbstractIterator` 继承自 `UnmodifiableIterator`, 所以静止调用 `remove()`.如果需要支持 `remove()`, 则不能继承 `AbstractIterator`.

## AbstractSequentialIterator

 [`AbstractSequentialIterator`](http://google.github.io/guava/releases/snapshot/api/docs/com/google/common/collect/AbstractSequentialIterator.html) 提供了一种简单的方式表述一次迭代

```java
Iterator<Integer> powersOfTwo = new AbstractSequentialIterator<Integer>(1) { // note the initial value!
  protected Integer computeNext(Integer previous) {
    return (previous == 1 << 30) ? null : previous * 2;
  }
};
```

注意：必须传递初始值，如果为null，将会立即返回，`AbstractSequentialIterator` 不能被用来实现返回 `null`的迭代器。