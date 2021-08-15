package com.ylc.tree;

import com.ylc.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree<E>  implements BinaryTreeInfo {
    protected Node<E> root;
    protected int size;

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

    /**
     * 前序遍历，非递归
     * @param visitor
     */
    public void preorder(Visitor<E> visitor) {
        if (visitor==null||root==null) return;
        Node<E> node = root;
        Stack<Node<E>> stack = new Stack<>();
        while (true) {
            if (node != null) {
                // do something ... 访问node节点
                if (visitor.visit(node.element)) {
                    return;
                }
                //右节点入栈
                if (node.right != null) {
                    stack.push(node.right);
                }
                //向左走
                node = node.left;
            } else if (stack.isEmpty()) {
                return;
            } else {
                node = stack.pop(); //右节点出栈

            }


        }
    }

    /**
     * 中序遍历，非递归版
     * @param visitor
     */
    public void inorder(Visitor<E> visitor) {
        if (visitor==null||root==null) return;

        Node<E> node = root;
        Stack<Node<E>> stack = new Stack<>();

        while (true) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else if (stack.isEmpty()) {
                return;
            } else {
                node = stack.pop();
                //访问node节点
                if (visitor.visit(node.element)) return;
                //让右节点进行中序遍历
                node = node.right;

            }
        }
    }

    public void postorder(Visitor<E> visitor) {
        if (visitor==null||root==null) return;

        //记录上一次访问节点
        Node<E> prev = null;
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<E> top = stack.peek();
            if (top.isLeaf() || (prev != null) && prev.parent == top) {
                prev = stack.pop();
                //访问node节点
                if (visitor.visit(prev.element)) return;
            } else {

                if (top.right != null) {
                    stack.push(top.right);
                }
                if (top.left != null) {
                    stack.push(top.left);
                }
            }
        }
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

        return node;
    }
    /**
     * 求前驱节点
     * @param node
     * @return
     */
    protected Node<E> predecessor(Node<E> node) {
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

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    /**
     * 找后继结点
     * @param node
     * @return
     */
    protected Node<E> successor(Node<E> node) {
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

    public static abstract class Visitor<E>
    {
        boolean stop;
        protected abstract boolean visit(E element);
    }

    protected static class Node<E> {
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

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<E> sibling() {
            if (isLeftChild()) return parent.right;
            if (isRightChild()) return parent.left;
            return null;
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }
}
