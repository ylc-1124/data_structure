package com.ylc;

import com.ylc.model.Person;
import com.ylc.queue.PriorityQueue;

public class Test {
    public static void main(String[] args) {
        PriorityQueue<Person> queue = new PriorityQueue<>();
        queue.enQueue(new Person("jack", 10));
        queue.enQueue(new Person("tom", 5));
        queue.enQueue(new Person("lucy", 15));
        queue.enQueue(new Person("mary", 20));
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }
}
