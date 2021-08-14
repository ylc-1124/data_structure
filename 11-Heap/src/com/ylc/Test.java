package com.ylc;

import com.ylc.heap.BinaryHeap;
import com.ylc.printer.BinaryTrees;

public class Test {
    public static void main(String[] args) {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(50);
        heap.add(38);
        heap.add(10);
        heap.add(90);
        heap.add(65);
        heap.remove();
        BinaryTrees.print(heap);

    }
}
