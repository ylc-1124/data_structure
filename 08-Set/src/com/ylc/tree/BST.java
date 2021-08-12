package com.ylc.tree;


import java.util.Comparator;


public class BST<E> extends BinaryTree<E> {

    private Comparator<E> comparator;

    public BST() {
        this(null);
    }

    public BST(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void add(E element) {
        elementNotNullCheck(element);

        if (root == null) { //添加第一个节点
            root = createNode(element, null);
            size++;
            //新添加节点后的处理
            afterAdd(root);
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
        Node<E> newNode = createNode(element, parent);
        if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        size++;  //添加完成
        //新添加节点后的处理
        afterAdd(newNode);

    }

    /**
     * 添加node后 调整
     * @param node
     */
    protected void afterAdd(Node<E> node) {

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

    /**
     * 删除node后调整
     * @param node 被删除的节点 或者 被删除节点的子节点（当删除节点度为1时）
     */
    protected void afterRemove(Node<E> node) {

    }

    /**
     * 删除节点
     * @param node
     */
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
