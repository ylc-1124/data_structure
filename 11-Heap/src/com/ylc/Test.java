package com.ylc;

import com.ylc.heap.BinaryHeap;
import com.ylc.printer.BinaryTrees;

import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        /*test2();
        System.out.println();
        test3();*/
        test4();
    }

    static void test4() { // top k 问题  用小根堆
        Integer[] date = {35, 62, 87, 58, 38, 49, 68, 6, 91, 94,
                9, 72, 3, 43, 61, 28, 18, 32, 15, 47};
        BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>() { //小顶堆
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        int k = 5;
        for (int i = 0; i < date.length; i++) {
            if (heap.size() < k) {
                heap.add(date[i]); //logk

            } else {
                if (date[i] > heap.get()) {
                    heap.replace(date[i]); //logk
                }

            }
        }
        BinaryTrees.print(heap);

    }

    static void test3() {
        Integer[] date = {54, 82, 48, 80, 72, 12, 94, 77, 78};
        BinaryHeap<Integer> heap = new BinaryHeap<>(date, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        BinaryTrees.print(heap);

    }
    static void test2() {
        Integer[] date = {54, 82, 48, 80, 72, 12, 94, 77, 78};
        BinaryHeap<Integer> heap = new BinaryHeap<>(date);
        BinaryTrees.print(heap);

    }

    static void test1() {

        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(50);
        heap.add(38);
        heap.add(10);
        heap.add(90);
        heap.add(65);
        BinaryTrees.print(heap);
        System.out.println();
        System.out.println(heap.replace(70));
        BinaryTrees.print(heap);
    }
}
