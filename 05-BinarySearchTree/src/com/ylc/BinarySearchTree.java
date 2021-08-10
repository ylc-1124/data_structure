package com.ylc;

import com.ylc.printer.BinaryTreeInfo;

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
    }

    public static interface Visitor<E> {
        void visit(E element);
    }

    public void levelOrder(Visitor<E> visitor) {
        if (root==null||visitor==null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            visitor.visit(node.element);
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
        preorder(root,visitor);
    }

    private void preorder(Node<E> node, Visitor<E> visitor) {
        if (node==null||visitor==null) return;

        visitor.visit(node.element);
        preorder(node.left, visitor);
        preorder(node.right, visitor);
    }
    public void inorder(Visitor<E> visitor) {
        inorder(root,visitor);
    }

    private void inorder(Node<E> node, Visitor<E> visitor) {
        if (node==null||visitor==null) return;
        inorder(node.left, visitor);
        visitor.visit(node.element);
        inorder(node.right, visitor);
    }
    public void postorder(Visitor<E> visitor) {
        postorder(root,visitor);
    }

    private void postorder(Node<E> node, Visitor<E> visitor) {
        if (node==null||visitor==null) return;
        postorder(node.left, visitor);
        postorder(node.right, visitor);
        visitor.visit(node.element);
    }
    /**
     * 前序遍历
     *//*
    public void preorderTraversal() {
        preorderTraversal(root);
    }

    private void preorderTraversal(Node<E> node) {
        if (node==null) return;
        System.out.println(node.element);
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }

    *//**
     * 中序遍历
     *//*
    public void inorderTraversal() {
        inorderTraversal(root);
    }

    private void inorderTraversal(Node<E> node) {
        if (node==null) return;
        inorderTraversal(node.left);
        System.out.println(node.element);
        inorderTraversal(node.right);
    }

    *//**
     * 后序遍历
     *//*
    public void postorderTraversal() {
        postorderTraversal(root);
    }

    private void postorderTraversal(Node<E> node) {
        if (node==null) return;
        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.println(node.element);
    }

    *//**
     * 层序遍历:使用队列
     *//*
    public void levelOrderTraversal() {
        if (root==null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            System.out.println(node.element);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }*/


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

    }

    public boolean contains(E element) {
        return false;
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }
}