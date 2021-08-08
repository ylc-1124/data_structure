package com.ylc;

public class Test {
    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(0,1111);
        list.add(list.size(),2222);
        System.out.println(list);
    }
}
