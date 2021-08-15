package com.ylc;

import java.util.HashMap;

public class Trie<V> {
    private int size;
    private Node<V> root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node != null && node.isWord ? node.value : null;
    }

    public boolean contains(String key) {
        Node<V> node = node(key);
        return node != null && node.isWord;
    }

    public V add(String key, V value) {
        keyCheck(key);
        //创建根节点
        if (root == null) {
            root = new Node<>(null);
        }

        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            char c = key.charAt(i);
            boolean emptyChildren = node.children == null;
            Node<V> childNode = emptyChildren ? null : node.children.get(c);
            if (childNode == null) {
                childNode = new Node<>(node);
                childNode.character = c;
                node.children = emptyChildren ? new HashMap<>() : node.children;
                node.children.put(c, childNode);
            }
            node = childNode;
        }
        if (node.isWord) { //覆盖
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        node.isWord = true;
        node.value = value;
        size++;
        return null;
    }

    public V remove(String key) {
        Node<V> node = node(key);
        //这个节点不存在或者不是一个单词，返回
        if (node == null || !node.isWord) return null;
        V oldValue = node.value;

        //到这说明存在这个单词
        size--;
        //如果这个节点有子节点，说明他是其他单词的前缀，只要把是否是单词的标记位改成false即可
        if (node.children != null && !node.children.isEmpty()) {
            node.isWord = false;
            node.value = null;
            return oldValue;
        }
        //如果没有子节点
        Node<V> parent = null;
        while ((parent = node.parent) != null) {
            parent.children.remove(node.character);
            if (parent.isWord || !parent.children.isEmpty())  break;

            node = parent;
        }


        return oldValue;
    }

    public boolean startsWith(String prefix) {
        return node(prefix) != null;
    }

    private static class Node<V> {
        HashMap<Character, Node<V>> children;  //存放前缀字符和节点的映射关系
        Character character;  //保存映射关系的字符
        Node<V> parent;
        V value;
        boolean isWord; //判断是否为一个单词的结尾

        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }

    private Node<V> node(String key) {
        keyCheck(key);

        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            if (node == null
                    || node.children == null
                    || node.children.isEmpty()) {
                return null;
            }
            char c = key.charAt(i);
            node = node.children.get(c);

        }
        return node;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }
}
