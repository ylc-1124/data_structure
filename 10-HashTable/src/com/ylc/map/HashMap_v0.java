package com.ylc.map;

import com.ylc.printer.BinaryTreeInfo;
import com.ylc.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class HashMap_v0<K, V> implements Map<K, V> {
    private int size;
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private Node<K, V>[] table;  //桶
    private static final int DEFAULT_CAPACITY = 1 << 4;

    public HashMap_v0() {
        table = new Node[DEFAULT_CAPACITY];

    }

    private static class Node<K, V> {
        int hash;
        K key;
        V value;
        boolean color;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.hash = key == null ? 0 : key.hashCode();
            this.value = value;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
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

        public Node<K, V> sibling() {
            if (isLeftChild()) return parent.right;
            if (isRightChild()) return parent.left;
            return null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        //取出索引位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = new Node<>(key, value, null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        }
        //添加新的节点到红黑树上面
        //拿到根节点
        Node<K, V> node = root;
        Node<K, V> parent = root;
        int cmp = 0;
        K k1 = key;
        int h1 = k1 == null ? 0 : k1.hashCode();
        Node<K, V> result = null;
        boolean searched = false;  //是否扫描过了
        do {  //通过循环找到要插入节点位置的父节点是谁
            parent = node;  //精髓：向左向右之前得先保存一下节点，这个节点就是要添加元素的父节点！！！
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(k1, k2)) {
                cmp = 0;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {

            } else if (searched) {  // 扫描过了
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            } else { //还没有扫描，先扫描，再根据内存地址往左往右
                if (node.left != null && (result = node(node.left, k1)) != null
                        || node.right != null && (result = node(node.right, k1)) != null) {
                    //已经存在这个key
                    cmp = 0;
                    node = result;
                } else {    //不存在这个key，新添加
                    searched = true;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }

            }
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
        } while (node != null);
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

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    /**
     * 找节点
     * @param key
     * @return
     */
    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        return root == null ? null : node(root, key);
    }

    private Node<K, V> node(Node<K, V> node, K k1) {
        int h1 = k1 == null ? 0 : k1.hashCode();
        Node<K, V> result = null;
        int cmp = 0;
        while (node != null) {
            int h2 = node.hash;
            K k2 = node.key;
            //比较哈希值
            if (h1 > h2) {
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            } else if (Objects.equals(k1, k2)) {
                return node;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (((Comparable) k1).compareTo(k2)) != 0) {
                node = cmp > 0 ? node.right : node.left;
            } else if (node.right != null && (result = node(node.right, k1)) != null) {  //哈希值相等，但不具备可比较性,也不equals
                return result;
            } else {  //只能往左边找
                node = node.left;
            }

        }

        return null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) {
            return false;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) {
                    return true;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) {
            return;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visitor.visit(node.key, node.value)) return;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

    }

    public void print() {
        if (size==0) return;
        for (int i = 0; i < table.length; i++) {
           final Node<K, V> root = table[i];
            System.out.println("index 【"+i+"】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>) node).left;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>) node).right;
                }

                @Override
                public Object string(Object node) {
                    return node;
                }
            });
            System.out.println("--------------------------------------------");
        }
    }

    /**
     * 根据传入的key生成索引
     * @param key
     * @return
     */
    private int index(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        return (hash ^ (hash >>> 16)) & (table.length - 1);
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
    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        //让parent成为根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { //grand是根节点
            table[index(grand)] = parent;
        }
        //更新child的parent
        if (child != null) {
            child.parent = grand;
        }
        //更新grand的parent
        grand.parent = parent;


    }


    private int index(Node<K, V> node) {
        return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
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
            node.hash = s.hash;
            //删除后继节点
            node = s;
        }
        //删除node节点 到这里的node 度必然为0或者1
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = index(node);
        if (replacement != null) {
            //度为1
            //更改parent
            replacement.parent = node.parent;
            if (node.parent == null) {
                table[index] = replacement;
            }
            if (node == node.parent.left) {
                node.parent.left = replacement;
            } else if (node == node.parent.right){
                node.parent.right = replacement;
            }
            afterRemove(replacement); //删完之后调整
        } else if (node.parent == null) { //叶子节点并且是根节点
            table[index] = null;
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

}
