package com.ylc.heap;

import com.ylc.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * 二叉堆（最大堆）
 * @param <E>
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(Comparator<E> comparator) {
        super(comparator);
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public BinaryHeap() {
        this(null);
    }



    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);

        elements[size++] = element;
        siftUp(size - 1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        int lastIndex = --size;
        E root = elements[0];
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;

        siftDown(0);

        return root;
    }

    @Override
    public E replace(E element) {
        return null;
    }



    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        int newCapacity = oldCapacity + (oldCapacity >> 1); //扩容的容量为原来的1.5倍
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    /**
     * 让index位置的元素上滤
     * @param index
     */
    private void siftUp(int index) {
       /* E e = elements[index];
        while (index > 0) {
            int pIndex = (index - 1) >> 1;
            E p = elements[pIndex];
            if (compare(e, p) <= 0) return;
            //交换索引为index和pIndex位置的内容
            E tmp = elements[index];
            elements[index] = elements[pIndex];
            elements[pIndex] = tmp;
            //使index成为他父节点的索引
            index = pIndex;
        }*/
        E element = elements[index];
        while (index > 0) {
            int parentIndex = (index - 1) >> 1;
            E parent = elements[parentIndex];
            if (compare(element, parent) <= 0) break;

            //将父元素存储在index位置
            elements[index] = parent;

            //使index成为他父节点的索引
            index = parentIndex;
        }
        elements[index] = element;
    }

    /**
     * 下滤
     * @param index
     */
    private void siftDown(int index) {
        E element = elements[index];
        int half = size >> 1;  //非叶子节点数量

        while (index < half) {  //必须保证index节点是非叶子节点
            //index的子节点只有两种情况
            //1.只有左节点
            //2.同时有左右子节点

            // 默认使用左子节点跟它进行比较
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];

            //右子节点
            int rightIndex = childIndex + 1;
            //选出左右子节点最大的
            if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
                child = elements[childIndex = rightIndex];
            }
            if (compare(element, child)>=0) break;

            //将子节点存放到index位置
            elements[index] = child;
            index = childIndex;
        }
        elements[index] = element;
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int) node << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        Integer index = (Integer) node;
        index = (index << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        Integer index = (Integer) node;
        return elements[index];
    }

}
