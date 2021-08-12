package com.ylc.list;

public abstract class AbstractList<E> implements List<E>{
    protected int size;

    /**
     * 抛出异常的方法
     * @param index
     */
    protected void outOfBound(int index) {
        throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
    }

    /**
     * 判断索引是否越界
     * @param index
     */
    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBound(index);
        }
    }

    /**
     * 判断加法索引是否越界
     * @param index
     */
    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBound(index);
        }
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
     *  在 ArrayList中 最好:O(1)     最坏:O(n)   平均：O(1)   均摊：O(1)
     *  在 LinkedList中   最好:O(1)     最坏:O(n)   平均：O(n)
     * @param element
     */
    public void add(E element) {
        add(size,element);
    }

}
