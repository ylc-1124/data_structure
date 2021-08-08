package com.ylc;

public interface List<E> {
    int ELEMENT_NOT_FOUND = -1;
    /**
     * 元素数量
     *
     * @return
     */
    int size();

    /**
     * 是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 是否包含某元素
     *
     * @param element
     * @return
     */
    boolean contains(E element);

    /**
     * 添加到元素末尾
     *
     * @param element
     */
    void add(E element);

    /**
     * 返回索引对应的元素
     *
     * @param index
     * @return
     */
    E get(int index);

    /**
     * 设置索引位置的元素
     *
     * @param index
     * @param element
     * @return 原来该位置的元素
     */
    E set(int index, E element);

    /**
     * 往索引位置添加元素
     *
     * @param index
     * @param element
     */
    void add(int index, E element);

    /**
     * 删除索引位置的元素
     *
     * @param index
     * @return
     */
    E remove(int index);

    /**
     * 查看元素所在索引位置
     *
     * @param element
     * @return
     */
    int indexOf(E element);

    /**
     * 清除所有元素
     */
    void clear();
}
