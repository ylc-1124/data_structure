package com.ylc;

import com.ylc.set.ListSet;
import com.ylc.set.Set;
import com.ylc.set.TreeSet;

public class Test {
    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>();
        set.add(11);
        set.add(11);
        set.add(44);
        set.add(22);
        set.add(33);
        set.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });

    }
}
