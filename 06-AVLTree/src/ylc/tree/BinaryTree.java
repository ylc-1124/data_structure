package ylc.tree;


import ylc.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

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
    private void postorder(Node<E> node, Visitor<E> visitor) {
        if (node==null||visitor.stop) return;
        postorder(node.left, visitor);
        postorder(node.right, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
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
        abstract boolean visit(E element);
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

    }
}
