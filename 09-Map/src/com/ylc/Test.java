package com.ylc;

import com.ylc.map.Map;
import com.ylc.map.TreeMap;

public class Test {
    public static void main(String[] args) {
        test1();
    }
    static void test1() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("c", 2);
        map.put("a", 5);
        map.put("b", 6);
        map.put("a", 8);

        map.traversal(new Map.Visitor<String, Integer>() {
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }
}
