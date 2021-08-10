package com.ylc;

import com.ylc.printer.BinaryTreeInfo;
import sun.reflect.generics.visitor.Visitor;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/*
小细节：传入泛型要实现Comparable接口，因为泛型不确定性可能传入的是一个接口，
       接口只能继承接口，所以这里必须用extends关键字
       (这个设计耦合度高 虽然这里不用了 改成了组合一个Comparator接口来达到类似的目的，但这个小细节还是挺重要)
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {
    private Node<E> root;
    private int size;
    private Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> myNode = (Node<E>) node;
        String parentStr = "null";
        if (myNode.parent != null) {
            parentStr = myNode.parent.element.toString();
        }
        return myNode.element + "_p(" + parentStr + ")";
    }

    private static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }
    }

    public static abstract class Visitor<E>
    {
        boolean stop;
        abstract boolean visit(E element);
    }

    public void levelOrder(Visitor<E> visitor) {
        if (root==null||visitor==null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (visitor.visit(node.element)) return;

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public void preorder(Visitor<E> visitor) {
        if (visitor==null) return;
        preorder(root,visitor);
    }

    private void preorder(Node<E> node, Visitor<E> visitor) {
        if (node==null||visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        preorder(node.left, visitor);
        preorder(node.right, visitor);
    }
    public void inorder(Visitor<E> visitor) {
        if (visitor==null) return;
        inorder(root,visitor);
    }

    private void inorder(Node<E> node, Visitor<E> visitor) {
        if (node==null||visitor.stop) return;
        inorder(node.left, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        inorder(node.right, visitor);
    }
    public void postorder(Visitor<E> visitor) {
        if (visitor==null) return;
        postorder(root,visitor);
    }

    public int height() {
        return height(root);
    }

    /**
     * 求出某个节点的高度
     * @param node
     * @return
     */
    private int height(Node<E> node) {
        if (node==null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    public int height2() {
        if (root==null) return 0;
        int height = 0;
        int levelSize = 1;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node= queue.poll();
            levelSize--;
            //....
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (levelSize == 0) { //意味着即将访问下一层
                levelSize= queue.size();
                height++;
            }
        }
        return height;
    }

    /**
     * 求前驱节点
     * @param node
     * @return
     */
    private Node<E> predecessor(Node<E> node) {
        if (node==null) return null;

        //前驱节点在左子树当中
        Node<E> prev = node.left;
        if (prev != null) {
            while (prev.right != null) {
                prev = prev.right;
            }
            return prev;
        }
        //从父节点，祖父节点寻找前驱节点
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        //node.parent == null
        //node == node.parent.right  前趋节点是他的父节点
        return node.parent;
    }

    /**
     * 找后继结点
     * @param node
     * @return
     */
    private Node<E> successor(Node<E> node) {
        if (node==null) return null;

        //前驱节点在右子树当中
        Node<E> succ = node.right;
        if (succ != null) {
            while (succ.left != null) {
                succ = succ.left;
            }
            return succ;
        }
        //从父节点，祖父节点寻找前驱节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    /**
     * 判断是否为完全二叉树
     * @return
     */
    public boolean isComplete() {
        if (root==null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
           if (leaf && !node.isLeaf()) return false;

            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                //node.left == null && node.right != null
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else {
                //node.left == null && node.right == null
                //node.left != null && node.right == null
                leaf = true;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root, sb,"");
        return sb.toString();
    }

    private void toString(Node<E> node, StringBuilder sb,String prefix) {
        if (node==null) return;
        sb.append(prefix).append(node.element).append("\n");
        toString(node.left, sb,prefix+"[L]");
        toString(node.right, sb,prefix+"[R]");
    }

    private void postorder(Node<E> node, Visitor<E> visitor) {
        if (node==null||visitor.stop) return;
        postorder(node.left, visitor);
        postorder(node.right, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
    }

    public void add(E element) {
        elementNotNullCheck(element);

        if (root == null) { //添加第一个节点
            root = new Node<>(element, null);
            size++;
            return;
        }
        //添加的不是第一个节点
        //拿到根节点
        Node<E> node = root;
        Node<E> parent = root;
        int cmp = 0;
        while (node != null) {  //通过循环找到要插入节点位置的父节点是谁
            cmp = compare(element, node.element);
            parent = node;  //精髓：向左向右之前得先保存一下节点，这个节点就是要添加元素的父节点！！！
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { //两个元素相等
                node.element = element;
                return;
            }
        }
        //看看插入节点应该放在父节点的左边还是右边，通过比较最后一次的 cmp是 < 0还是 > 0
        Node<E> newNode = new Node<>(element, parent);
        if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        size++;  //添加完成
    }

    /**
     * 比较节点元素的大小
     * @param e1
     * @param e2
     * @return 返回值等于0说明e1 == e2 ，大于0 说明e1 > e2
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>) e1).compareTo(e2);
    }

    public void remove(E element) {
        remove(node(element));
    }

    private void remove(Node<E> node) {
        if (node==null) return;
        size--;
        if (node.hasTwoChildren()) { //度为2的节点
            Node<E> s = successor(node); //找到后继节点
            //用后继结点的值覆盖度为2节点的值
            node.element = s.element;
            //删除后继节点
            node = s;
        }
        //删除node节点 到这里的node 度必然为0或者1
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            //度为1
            //更改parent
            replacement.parent = node.parent;
            if (node.parent == null) {
                root = replacement;
            }
            if (node == node.parent.left) {
                node.parent.left = replacement;
            } else if (node == node.parent.right){
                node.parent.right = replacement;
            }
        } else if (node.parent == null) { //叶子节点并且是根节点
            root = null;
        } else {//叶子节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
    }

    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp==0) return node;
            if (cmp > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    public boolean contains(E element) {
        return node(element) != null;
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }
}
