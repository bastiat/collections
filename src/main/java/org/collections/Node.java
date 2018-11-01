package org.collections;

public class Node<E> {

    E elem;
    Node<E> next;

    Node(E elem) {
        this.elem = elem;
    }

    Node(E elem, Node<E> next) {
        this.elem = elem;
        this.next = next;
    }

}
