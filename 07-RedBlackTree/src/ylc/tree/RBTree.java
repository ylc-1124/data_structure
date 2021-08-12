package ylc.tree;

import java.util.Comparator;

public class RBTree<E> extends BBST<E> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }
    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

            private static class RBNode<E> extends Node<E> {
                boolean color = RED;
                public RBNode(E element, Node<E> parent) {

                    super(element, parent);
                }

                @Override
                public String toString() {
                    String s = "";
                    if (color == RED) {
                        s = "R_";
                    }
                    return s + element;
                }
            }

    /**
     * 维护红黑树5条性质
     * @param node 新添加的节点
     */
    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        //如果添加的是根节点
        if (parent == null) {
            black(node);
            return;
        }
        //如果父节点是黑色，不用处理
        if (isBlack(parent)) return;

        //uncle节点
        Node<E> uncle = parent.sibling();
        //祖父节点 ,因为不管哪种情况grand都要染红，所以在这里统一染红
        Node<E> grand = red(parent.parent);
        if (isRed(uncle)) { //叔父节点是红色 属于上溢情况
            black(parent);
            black(uncle);
            //祖父节点当作新添加的节点
            afterAdd(grand);
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

    @Override
    protected void afterRemove(Node<E> node) {
        super.afterRemove(node);
    }

    /**
     * 给节点染色
     * @param node
     * @param color
     * @return
     */
    private Node<E> color(Node<E> node, boolean color) {
        if (node==null) return null;
        ((RBNode<E>) node).color = color;
        return node;
    }

    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }
    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    /**
     * 查看一个节点的颜色
     * @param node
     * @return
     */
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }
}
