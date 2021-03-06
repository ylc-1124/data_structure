package com.ylc.single;

import com.ylc.AbstractList;

public class SingleLinkedList<E> extends AbstractList<E> {
    private Node<E> first;

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }


    @Override
    public E get(int index) {
        /**
         * 时间复杂度：
         *   最好：O(1)
         *   最坏：O(n)
         *   平均：O(n)
         */
        return node(index).element;
    }

    /**
     * 对指定索引的元素设置值
     * @param index
     * @param element
     * @return  被替换的元素值
     */
    @Override
    public E set(int index, E element) {
        /**
         * 时间复杂度：
         *   最好：O(1)
         *   最坏：O(n)
         *   平均：O(n)
         */
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        /**
         * 时间复杂度：
         *   最好：O(1)
         *   最坏：O(n)
         *   平均：O(n)
         */
        if (index == 0) { //细节：index为0要特殊处理，否则会报错
            first = new Node<E>(element, first);

        } else {
            Node<E> prev = node(index - 1); //获取前趋节点
            prev.next = new Node<>(element, prev.next);

        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        /**
         * 时间复杂度：
         *   最好：O(1)
         *   最坏：O(n)
         *   平均：O(n)
         */
        Node<E> node = first;
        if (index == 0) {
            first = first.next;
        } else {
            Node<E> prev = node(index - 1);
            node = prev.next;
            prev.next = node.next;
        }
        size--;
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        if (element == null) { //细节： element可能为null，要特殊处理
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
                node = node.next;
            }
        } else {
            //来到这里说明element肯定不为null
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) return i;  //细节：用equals比较对象是否相等
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
    }

    /**
     * 返回index位置的节点对象
     * @param index
     * @return
     */
    private Node<E> node(int index) {
        rangeCheck(index);

        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Size:").append(size).append(" [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                s.append(",");
            }
            s.append(node.element);
            node = node.next;
        }
        s.append("]");
        return s.toString();
    }
}
