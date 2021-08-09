package com.ylc.circle;

import com.ylc.list.LinkedList;
import com.ylc.list.List;

public class CircleDeque<E> {
    private int front;
    private int size;
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public CircleDeque() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        size = 0;
        front = 0;

    }
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 从尾部入队
     * @param element
     */
    public void enQueueRear(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        size++;
    }

    /**
     * 从头部出队
     * @return
     */
    public E deQueueFront() {
        E frontElement = elements[front];
        elements[front] = null;
        front = index(1);
        size--;
        return frontElement;
    }

    /**
     * 从头部入队
     * @param element
     */
    public void enQueueFront(E element) {
        ensureCapacity(size + 1);
        front = index(-1);
        elements[front] = element;
        size++;
    }

    /**
     * 从尾部出队
     *
     * @return
     */
    public E deQueueRear() {
        int realIndex = index(size - 1);
        E rear = elements[realIndex];
        elements[realIndex] = null;
        size--;
        return rear;
    }


    public E front() {
        return elements[front];
    }

    public E rear() {
        return elements[index(size - 1)];
    }
    private int index(int index) {
        index += front;
        if (index < 0) {
            return index + elements.length;
        }
        return index % elements.length;
    }
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        int newCapacity = oldCapacity + (oldCapacity >> 1); //扩容的容量为原来的1.5倍
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[index(i)];
        }
        elements = newElements;
        front = 0;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("capacity=").append(elements.length)
                .append("  size=").append(size)
                .append("  front=").append(front)
                .append(", [");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
