package org.cyka.collections;

import com.google.common.collect.ForwardingList;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
class AddLoggingList<E> extends ForwardingList<E> {
  private final List<E> delegate; // backing list

  AddLoggingList(List<E> delegate) {
    this.delegate = delegate;
  }

  @Override
  protected List<E> delegate() {
    return delegate;
  }

  @Override
  public void add(int index, E elem) {
    log(index, elem);
    super.add(index, elem);
  }

  @Override
  public boolean add(E elem) {
    return standardAdd(elem); // implements in terms of add(int, E)
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    return standardAddAll(c); // implements in terms of add
  }

  private void log(int index, E elem) {
    log.info("element: {},has been added to index: {}", elem, index);
  }
}
