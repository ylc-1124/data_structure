package com.ylc.circle;

import com.ylc.AbstractList;

/**
 * 双向循环链表
 * @param <E>
 */
public class CircleLinkedList<E> extends AbstractList<E> {
    private Node<E> first;
    private Node<E> last;

    private static class Node<E> {
        E element;
        Node<E> prev;
        Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (prev != null) {
                sb.append(prev.element);
            } else {
                sb.append("null");
            }
            sb.append("_").append(element).append("_");
            if (next != null) {
                sb.append(next.element);
            } else {
                sb.append("null");
            }
            return sb.toString();
        }
    }


    @Override
    public E get(int index) {
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
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if (index == size) { //往最后面添加元素
            Node<E> oldLast = last;
            last = new Node<>(element, oldLast, first);
            if (oldLast == null) {
                //说明这是链表的第一个元素
                first = last;
                first.prev = first;
                first.next = first;
            } else {
                oldLast.next = last;
                first.prev = last;
            }


        } else {
            Node<E> next = node(index);
            Node<E> prev = next.prev;  //当index为0 可能为null
            Node<E> node = new Node<>(element, prev, next);
            next.prev = node;
            prev.next = node;
            if (index == 0) {
                first = node;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = first;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            node = node(index);
            Node<E> prev = node.prev; //可能为null
            Node<E> next = node.next; //可能为null
            prev.next = next;
            next.prev = prev;
            if (node == first) {//index == 0
                first = next;
            }
            if (node == last) { //index == size - 1
                last = prev;
            }
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
        last = null;
    }

    /**
     * 返回index位置的节点对象
     * @param index
     * @return
     */
    private Node<E> node(int index) {
        rangeCheck(index);
        if (index < (size >> 1)) {
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<E> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }

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
            s.append(node);
            node = node.next;
        }
        s.append("]");
        return s.toString();
    }
}
