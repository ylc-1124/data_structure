package ylc;


import ylc.printer.BinaryTrees;
import ylc.tree.AVLTree;

public class Test {
    public static void main(String[] args) {
        test1();
    }
    static void test1() {
        Integer data[] = new Integer[] {
                17, 35, 78, 27, 5, 32, 34, 83, 81, 39, 56, 62, 14, 43, 80, 66, 61, 98
        };

        AVLTree<Integer> avl = new AVLTree<>();
        for (int i = 0; i < data.length; i++) {
            avl.add(data[i]);

        }
        avl.remove(78);
        avl.remove(35);
        BinaryTrees.println(avl);
    }

}
