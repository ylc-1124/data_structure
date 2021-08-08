package com.ylc;

import java.util.Arrays;

public class ArrayList<E> {
    private int size;
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;
    private static final int ELEMENT_NOT_FOUND = -1;

    public ArrayList(int capacity) {
        capacity = capacity < DEFAULT_CAPACITY ? DEFAULT_CAPACITY : capacity;
        elements = (E[]) new Object[capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }
    /**
     * 元素数量
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否包含某元素
     *
     * @param element
     * @return
     */
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 添加到元素末尾
     *
     * @param element
     */
    public void add(E element) {
        add(size,element);
    }

    /**
     * 返回索引对应的元素
     *
     * @param index
     * @return
     */
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    /**
     * 设置索引位置的元素
     *
     * @param index
     * @param element
     * @return 原来该位置的元素
     */
    public E set(int index, E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    /**
     * 往索引位置添加元素
     *
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacity(size + 1); //至少保证容量有size+1，否则不能添加
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        size++;
    }

    /**
     * 删除索引位置的元素
     *
     * @param index
     * @return
     */
    public E remove(int index) {
        rangeCheck(index);
        E old = elements[index];
        for (int i = index + 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }
        size--;
        elements[size] = null;  //细节：删除一个元素最后一个索引得置null，否则会内存泄漏
        return old;
    }

    /**
     * 删除指定元素
     * @param element
     * @return
     */
    public void remove(E element) {
        remove(indexOf(element));
    }

    /**
     * 查看元素所在索引位置
     *
     * @param element
     * @return
     */
    public int indexOf(E element) {

        if (element==null) { //细节： element可能为null，要特殊处理
            for (int i = 0; i < size; i++) {
                if (elements[i]==null) return i;
            }
        } else {
            //来到这里说明element肯定不为null
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(element)) return i;  //细节：用equals比较对象是否相等
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 清除所有元素
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;  //细节：使用泛型后数组里面都是地址，必须置null，否则会造成内存泄露
        }
        size = 0;  //数组是可以重复利用的留下
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Size:").append(size).append(" [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                s.append(",");
            }
            s.append(elements[i]);
        }
        s.append("]");
        return s.toString();
    }

    /**
     * 抛出异常的方法
     * @param index
     */
    private void outOfBound(int index) {
        throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
    }

    /**
     * 判断索引是否越界
     * @param index
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBound(index);
        }
    }

    /**
     * 判断加法索引是否越界
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBound(index);
        }
    }

    /**
     * 保证容量大小有capacity
     * @param capacity
     */
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
}

