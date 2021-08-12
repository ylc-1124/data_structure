package ylc;


import ylc.printer.BinaryTrees;

import ylc.tree.RBTree;

public class Test {
    public static void main(String[] args) {
        test1();
    }
    static void test1() {
        Integer[] data = new Integer[] {
                78, 31, 27, 18, 77, 12, 46, 17, 11, 2, 97, 52
        };

        RBTree<Integer> rbTree = new RBTree<>();
        for (Integer datum : data) {
            rbTree.add(datum);

        }
        BinaryTrees.println(rbTree);
    }

}
