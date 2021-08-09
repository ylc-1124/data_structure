package com.ylc;

import com.ylc.list.LinkedList;
import com.ylc.list.List;

/**
 * 双端队列：队头队尾都可以出队入队 底层使用双向链表实现
 * @param <E>
 */
public class Deque<E> {
    private List<E> list = new LinkedList<>();
    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueueRear(E element) {
        list.add(element);
    }

    public E deQueueFront() {
        return list.remove(0);
    }

    public void enQueueFront(E element) {
        list.add(0, element);
    }

    public E deQueueRear() {
        return list.remove(list.size() - 1);
    }

    public E front() {
        return list.get(0);
    }

    public E rear() {
        return list.get(list.size() - 1);
    }

    public void clear() {
        list.clear();
    }
}
