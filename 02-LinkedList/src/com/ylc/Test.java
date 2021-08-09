package com.ylc;

public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList2<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        for (int i = 0; i < 50; i++) {
            list.remove(0);
        }
        System.out.println(list);
    }
}
