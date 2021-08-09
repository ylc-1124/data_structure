package com.ylc;

import com.ylc.circle.CircleLinkedList;
import com.ylc.circle.SingleCircleLinkedList;

public class Test {
    public static void main(String[] args) {
//        testList(new ArrayList<>());
//		testList(new LinkedList<>());
    //    testList(new SingleCircleLinkedList<>());
     //   testList(new CircleLinkedList<>());
        josephus(3); //喊三次，死一个

    }

    static void josephus(int n) {
        CircleLinkedList<Integer> list = new CircleLinkedList<>();
        int loap = n; //保存要叫多少声死一个人
        for (int i = 1; i <= 8; i++) {
            list.add(i);
        }
        list.reset();
        while (!list.isEmpty()) {
            n = loap;
            while (n > 1) {
                list.next();
                n--;
            }
//            list.next();
//            list.next();
            System.out.println(list.remove());
        }
    }
    static void testList(List<Integer> list) {
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);

        list.add(0, 55); // [55, 11, 22, 33, 44]
        list.add(2, 66); // [55, 11, 66, 22, 33, 44]
        list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]

        list.remove(0); // [11, 66, 22, 33, 44, 77]
        list.remove(2); // [11, 66, 33, 44, 77]
        list.remove(list.size() - 1); // [11, 66, 33, 44]

        Asserts.test(list.indexOf(44) == 3);
        Asserts.test(list.indexOf(22) == List.ELEMENT_NOT_FOUND);
        Asserts.test(list.contains(33));
        Asserts.test(list.get(0) == 11);
        Asserts.test(list.get(1) == 66);
        Asserts.test(list.get(list.size() - 1) == 44);

        System.out.println(list);
    }
}
