package org.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class HashSet<E> implements Set<E> {


    private int modulo = 128;

    private LinkedList<E>[] tab =  new LinkedList[modulo];

    private int size = 0;

    public HashSet() {
        for (int i = 0; i < tab.length; i++) {
            tab[i] = new LinkedList<>();
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return listForObject(o).contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new HashSetIterator();
    }

    @Override
    public Object[] toArray() {
        LinkedList<E> result = new LinkedList<>();

        for (int i = 0; i < tab.length; i++) {
            result.addAll(tab[i]);
        }

        return result.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) toArray();
    }

    @Override
    public boolean add(E e) {
        if (this.contains(e)) {
            return false;
        }
        size++;
        listForObject(e).add(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (this.contains(o)) {
            size --;
            return listForObject(o).remove(o);
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!listForObject(o).contains(o)) return false;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        int initialSize = size;

        for (E e : c) {
            this.add(e);
        }

        return initialSize < size;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int initialSize = size;

        for (Object e : c) {
            this.remove(e);
        }

        return initialSize > size;
    }

    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < tab.length; i++) {
            tab[i] = new LinkedList<>();
        }
    }

    private LinkedList<E> listForObject(Object o) {
        return tab[Math.abs(o.hashCode()) % tab.length];
    }

    private class HashSetIterator implements Iterator<E> {

        private int position = 0;
        private int returned = 0;
        private Iterator<E> currentListIterator = tab[0].iterator();

        @Override
        public boolean hasNext() {
            return returned < size;
        }

        @Override
        public E next() {
            while( !currentListIterator.hasNext()) {
                position++;
                currentListIterator = tab[position].iterator();
            }
            returned++;
            return currentListIterator.next();
        }
    }
}
