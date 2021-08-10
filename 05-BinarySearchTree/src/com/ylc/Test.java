package com.ylc;

import com.ylc.file.Files;
import com.ylc.printer.BinaryTrees;

import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        test1();
    }

    static void test4() {
        BinarySearchTree<Person> bst = new BinarySearchTree<>();
        bst.add(new Person(10, "jack"));
        bst.add(new Person(8, "marry"));
        bst.add(new Person(15, "tom"));

        bst.add(new Person(10,"lucy"));
        BinaryTrees.println(bst);
    }
    static void test1() {
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);
        bst.levelOrder(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(" _" + element + "_ ");
                return element == 2 ? true : false;
            }
        });
    }
  /*  static void test2() {
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };

        BinarySearchTree<Person> bst1 = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst1.add(new Person(data[i]));
        }

        BinaryTrees.println(bst1);

        BinarySearchTree<Person> bst2 = new BinarySearchTree<>(new Comparator<Person>() {
            public int compare(Person o1, Person o2) {
                return o2.getAge() - o1.getAge();
            }
        });
        for (int i = 0; i < data.length; i++) {
            bst2.add(new Person(data[i]));
        }
        BinaryTrees.println(bst2);
    }*/
    static void test3() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < 40; i++) {
            bst.add((int)(Math.random() * 100));
        }
        String str = BinaryTrees.printString(bst);
        str += "\n";
        Files.writeToFile("C:\\Users\\85370\\学习资料\\恋上数据结构与算法第一季笔记\\1.txt",
                str,true);
     //   BinaryTrees.println(bst);
    }

}
