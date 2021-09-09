package com.ylc.homework;
//链栈
public class Stack<E> {
    private Node<E> first;
    private int size;

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 入栈
     */
    public void push(E element) {
        Node<E> node = node(element);
        if (first == null) {
            first = node;
            size++;
            return;
        }

        node.next = first;
        first = node;
        size++;
    }

    /**
     * 出栈
     */
    public E pop() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("size == 0");
        }
        E element= first.element;
        first = first.next;
        size--;
        return element;
    }

    /**
     * 瞥一眼栈顶元素
     */
    public E peek() {
        return first.element;
    }

    /**
     * 清空栈
     */
    public void clear() {
        first = null;
        size = 0;
    }

    /**
     * 将元素封装进节点
     */
    private Node<E> node(E element) {
        return new Node<>(element, null);
    }
}
