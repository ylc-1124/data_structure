package com.ylc;


import com.ylc.map.HashMap;
import com.ylc.map.Map;
import com.ylc.model.Key;
import com.ylc.model.Person;

public class Test {
    public static void main(String[] args) {
        test2();

    }

     static void test2() {
         HashMap<Object, Integer> map = new HashMap<>();
         for (int i = 1; i <= 9; i++) {
             map.put(new Key(i), i);
         }
        map.print();

    }

    static void test1() {
        Person p1 = new Person("jack", 11, 1.55f);
        Person p2 = new Person("jack", 11, 1.55f);

        Map<Object, Integer> map = new HashMap<>();
        map.put(p1, 2);
        map.put(p2, 4);
        map.put("jack", 12);
        map.put("rose", 3);
        map.put("jack", 6);
        map.put(null, 9);

        map.traversal(new Map.Visitor<Object, Integer>() {
            @Override
            public boolean visit(Object key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
        System.out.println(map.containsValue(6));
        System.out.println(map.containsKey(null));

    }
}
