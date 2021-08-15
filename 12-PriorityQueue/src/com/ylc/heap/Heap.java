package com.ylc.heap;

public interface Heap<E> {
    int size();

    boolean isEmpty();

    void clear();

    void add(E element);

    E get();  //获取堆顶元素

    E remove(); //删除堆顶元素

    E replace(E element);

}
