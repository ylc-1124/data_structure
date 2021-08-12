package com.ylc.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class TreeMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private Node<K, V> root;
    private Comparator<K> comparator;
    private int size;


    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
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

    private static class Node<K, V> {
        K key;
        V value;
        boolean color;
        Node<K,V> left;
        Node<K,V> right;
        Node<K,V> parent;

        public Node(K key,V value, Node<K,V> parent) {
            this.key = key;
            this.value = value;
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

        public Node<K,V> sibling() {
            if (isLeftChild()) return parent.right;
            if (isRightChild()) return parent.left;
            return null;
        }
    }

    @Override
    public V put(K key, V value) {
        keyNotNullCheck(key);

        if (root == null) { //添加第一个节点
            root = new Node<>(key, value, null);
            black(root);
            size++;
            return null;
        }
        //添加的不是第一个节点
        //拿到根节点
        Node<K, V> node = root;
        Node<K, V> parent = root;
        int cmp = 0;
        while (node != null) {  //通过循环找到要插入节点位置的父节点是谁
            cmp = compare(key, node.key);
            parent = node;  //精髓：向左向右之前得先保存一下节点，这个节点就是要添加元素的父节点！！！
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { //两个元素相等
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }
        //看看插入节点应该放在父节点的左边还是右边，通过比较最后一次的 cmp是 < 0还是 > 0
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        size++;  //添加完成
        //新添加节点后的处理
        afterPut(newNode);

        return null;
    }
    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }

    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        //如果添加的是根节点
        if (parent == null) {
            black(node);
            return;
        }
        //如果父节点是黑色，不用处理
        if (isBlack(parent)) return;

        //uncle节点
        Node<K, V> uncle = parent.sibling();
        //祖父节点 ,因为不管哪种情况grand都要染红，所以在这里统一染红
        Node<K, V> grand = red(parent.parent);
        if (isRed(uncle)) { //叔父节点是红色 属于上溢情况
            black(parent);
            black(uncle);
            //祖父节点当作新添加的节点
            afterPut(grand);
            return;
        }
        //叔父节点不是红色
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) { //LL
                black(parent);
            } else { //LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else {  //R
            if (node.isLeftChild()) {  //RL
                black(node);
                rotateRight(parent);
            } else {  //RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }
    private int compare(K e1, K e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<K>) e1).compareTo(e2);
    }
    /**
     * 给节点染色
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node==null) return null;
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    /**
     * 查看一个节点的颜色
     *
     * @return
     */
    private boolean colorOf(Node<K, V> node) {

        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {

        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }
    /**
     * 左旋
     * @param grand
     */
    protected void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);

    }

    /**
     * 右旋
     * @param grand
     */
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }
    private void afterRotate(Node<K, V> grand,Node<K, V> parent,Node<K, V> child) {
        //让parent成为根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { //grand是根节点
            root = parent;
        }
        //更新child的parent
        if (child != null) {
            child.parent = grand;
        }
        //更新grand的parent
        grand.parent = parent;


    }
    @Override
    public V get(K key) {
        keyNotNullCheck(key);
        return node(key).value;
    }

    private Node<K, V> node(K key) {
        Node<K, V> node = root;
        while (node != null) {
            int cmp = compare(key, node.key);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    private V remove(Node<K, V> node) {
        if (node==null) return null;
        size--;
        V oldValue = node.value;
        if (node.hasTwoChildren()) { //度为2的节点
            Node<K, V> s = successor(node); //找到后继节点
            //用后继结点的值覆盖度为2节点的值
            node.key = s.key;
            node.value = s.value;
            //删除后继节点
            node = s;
        }
        //删除node节点 到这里的node 度必然为0或者1
        Node<K, V> replacement = node.left != null ? node.left : node.right;
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
            afterRemove(replacement); //删完之后调整
        } else if (node.parent == null) { //叶子节点并且是根节点
            root = null;
            afterRemove(node); //删完之后调整
        } else {//叶子节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            afterRemove(node); //删完之后调整
        }
        return oldValue;
    }

    /**
     * 求前驱节点
     *
     * @param node
     * @return
     */
    private Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) return null;

        //前驱节点在左子树当中
        Node<K, V> prev = node.left;
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
     *
     * @param node
     * @return
     */
    private Node<K, V> successor(Node<K, V> node) {

        if (node == null) return null;

        //前驱节点在右子树当中
        Node<K, V> succ = node.right;
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

    protected void afterRemove(Node<K, V> node) {
        //如果删除节点是红色 或者 用于取代的节点颜色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        if (parent==null) return;
        //删除黑色节点
        //判断被删除node之前是父节点左节点还是又节点
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { //被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { //兄弟节点是红色

                black(sibling);
                red(parent);
                rotateLeft(parent);
                //更换兄弟
                sibling = parent.right;
            }
            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有红色子节点，只能父节点向下合并
                if (isBlack(parent)) {
                    afterRemove(parent);
                }
                black(parent);
                red(sibling);
            } else { //兄弟节点至少有一个红色节点

                //兄弟节点的左边是黑色，要先进行左旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);

            }
        } else {//被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { //兄弟节点是红色

                black(sibling);
                red(parent);
                rotateRight(parent);
                //更换兄弟
                sibling = parent.left;
            }
            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {

                //兄弟节点没有红色子节点，只能父节点向下合并
                if (isBlack(parent)) {
                    afterRemove(parent);
                }
                black(parent);
                red(sibling);
            } else { //兄弟节点至少有一个红色节点

                //兄弟节点的左边是黑色，要先进行左旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);

            }
        }

    }


    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root==null) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (valEquals(value, node.value)) return true;

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }

    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor==null) return;
        traversal(root, visitor);
    }

    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        if (node==null||visitor.stop) return;
        traversal(node.left, visitor);
        if (visitor.stop) return;
        visitor.visit(node.key, node.value);
        traversal(node.right, visitor);
    }
}
