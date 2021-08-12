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


    /**
     * 维护红黑树性质
     *
     * @param node 被删除的节点
     */
    @Override
    protected void afterRemove(Node<E> node, Node<E> replacement) {
        //如果删除节点是红色
        if (isRed(node)) return;

        //用于取代的节点颜色
        if (isRed(replacement)) {
            black(replacement);
            return;
        }

        Node<E> parent = node.parent;
        //删除的是根节点
        if (parent==null) return;
        //删除黑色节点
        //判断被删除node之前是父节点左节点还是又节点
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
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
                    afterRemove(parent, null);
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
                    afterRemove(parent, null);
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
