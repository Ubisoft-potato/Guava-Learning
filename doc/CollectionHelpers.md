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

