package org.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LinkedList<E> implements List<E> {

    private final Node<E> first = new Node<>(null);
    private Node<E> last = first;
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        Node<E> node = first.next;
        while (node != null) {
            if (o.equals(node.elem)) return true;
            node = node.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];

        Node<E> node = first.next;
        int idx = 0;
        while (node != null) {
            result[idx++] = node.elem;
            node = node.next;
        }

        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) toArray();
    }

    @Override
    public boolean add(E e) {
        Node node = new Node(e);
        last.next = node;
        last = node;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> node = first.next;
        Node<E> prev = first;
        while (node != null) {
            if (o.equals(node.elem)) {
                prev.next = node.next;
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E elem : c) {
            this.add(elem);
        }
        return c.size() > 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        final int initialSize = size;

        for (Object elem : c) {
            this.remove(elem);
        }

        return initialSize > size;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        this.last = first;
        this.first.next = null;
    }

    @Override
    public E get(int index) {
        if (index < 0) throw new IndexOutOfBoundsException();

        int currIdx = 0;
        Node<E> node = first.next;

        while (currIdx < index) {
            if (node == null) {
                throw new IndexOutOfBoundsException();
            }
            node = node.next;
            currIdx++;
        }

        return node.elem;
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class LinkedListIterator implements Iterator<E> {

        private Node<E> current;

        LinkedListIterator() {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public E next() {
            current = current.next;
            return current.elem;
        }
    }
}
