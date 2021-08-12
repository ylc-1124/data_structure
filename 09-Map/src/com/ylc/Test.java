package com.ylc;

import com.ylc.set.Set;
import com.ylc.set.TreeSet;

public class Test {
    public static void main(String[] args) {
        test3();
    }

    static void test3() {
        Set<String> set = new TreeSet<>();
        set.add("c");
        set.add("b");
        set.add("c");
        set.add("c");
        set.add("a");

        set.traversal(new Set.Visitor<String>() {
            public boolean visit(String element) {
                System.out.println(element);
                return false;
            }
        });
    }
}
