package com.ylc;

import com.ylc.list.ArrayList;
import com.ylc.list.List;

public class Stack<E> {
    private List<E> list = new ArrayList<>();

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void clear() {
        list.clear();
    }
    public void push(E element) {
        list.add(element);
    }

    public E pop() {
        return list.remove(list.size() - 1);
    }

    public E top() {
        return list.get(list.size() - 1);
    }

}
