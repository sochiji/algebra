package util;

/**
 * Node
 */
public class LinkedList<E> {
    Node<E> head;
    Node<E> tail;
    int size;

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E o, Node<E> p, Node<E> n) {
            data = o;
            prev = p;
            next = n;
        }

        Node(E o) {
            this(o, null, null);
        }

        boolean insertBefore(Node<E> o) {
            if (prev == null)
                return false;
            prev.next = o;
            o.prev = prev;
            o.next = this;
            prev = o;
            return true;
        }

        boolean insertAfter(Node<E> o) {
            if (next == null)
                return false;
            next.prev = o;
            o.next = next;
            o.prev = this;
            next = o;
            return true;
        }

        E remove() {
            prev.next = next;
            next.prev = prev;
            return data;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object o) {
            Node<E> op = (Node<E>) o;
            boolean ret = true;
            if (!op.data.equals(data))
                ret = false;
            return ret;
        }
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (!(o instanceof LinkedList<?>))
            return false;
        boolean ret = true;
        Node<E> n = head;
        LinkedList<E> op = (LinkedList<E>) o;
        Node<E> on = op.head;
        while (n.next != tail && on.next != op.tail) {
            n = n.next;
            on = on.next;
            if (!n.equals(on)) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    public int getIndex(E o) {
        int i = -1;
        Node<E> n = head;
        int ret = -1;
        while (n.next != tail) {
            i++;
            n = n.next;
            if (n.data.equals(o)) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    public int indexOf(E o) {
        return getIndex(o);
    }

    public E get(int index) {
        Node<E> n = head;
        int i = -1;
        while (n.next != tail) {
            i++;
            n = n.next;
            if (i == index)
                return n.data;
        }
        return null;
    }

    public boolean add(E e) {
        return addToTail(e);
    }

    public void add(int index, E element) {
        int i = -1;
        Node<E> n = head;
        while (n.next != tail) {
            i++;
            n = n.next;
            if (i == index) {
                n.insertBefore(new Node<E>(element));
                size++;
            }
            break;
        }
    }

    public LinkedList() {
        head = new Node<E>(null);
        tail = new Node<E>(null);
        head.next = tail;
        tail.next = head;
    }

    public boolean addToTail(E o) {
        size++;
        return new Node<E>(o).insertBefore(tail);
    }

    public boolean addToHead(E o) {
        size++;
        return new Node<E>(o).insertAfter(head);
    }

    public ListIterator listIterator() {
        return new ListIterator();
    }

    public E remove(int index) {
        Node<E> n = head;
        int i = 0;
        while (n.next != tail) {
            n = n.next;
            i++;
            if (i == index)
                return n.remove();
        }
        return null;
    }

    protected void removeRange(int fromIndex, int toIndex) {
        Node<E> fromNode = null, toNode = null;
        int i = -1;
        Node<E> currentNode = head;
        while (currentNode.next != tail) {
            currentNode = currentNode.next;
            i++;
            if (i == fromIndex) {
                fromNode = currentNode;
            }
            if (i == toIndex) {
                toNode = currentNode;
            }
        }
        removeRange(fromNode, toNode);
        size -= toIndex - fromIndex + 1;
    }

    private void removeRange(Node<E> from, Node<E> to) {
        Node<E> prevNode = from.prev;
        Node<E> nextNode = to.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    LinkedList<E> subList(int fromIndex, int toIndex) {
        Node<E> currentNode = head;
        LinkedList<E> ret = new LinkedList<E>();// TODO
        return null;
    }

    E set(int index, E element) {
        int i = -1;
        Node<E> n = head;
        E ret = null;
        while (n.next != tail) {
            i++;
            n = n.next;
            if (i == index) {
                ret = n.data;
                n.data = element;
                break;
            }
        }
        return ret;
    }

    int firstIndexOf(E o) {
        return getIndex(o);
    }

    int lastIndexOf(E o) {
        Node<E> n = tail;
        int i = size;
        int ret = -1;
        while (n.prev != head) {
            i--;
            n = n.prev;
            if (n.data.equals(o)) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    public int[] allIndexOf(E o) {
        LinkedList<Integer> ret = new LinkedList<Integer>();
        int i = -1;
        Node<E> n = head;
        while (n.next != tail) {
            n = n.next;
            i++;
            if (n.data.equals(o))
                ret.add(i);
        }
        int[] reti = new int[ret.size];
        int index = 0;
        for (LinkedList<Integer>.ListIterator it = ret.listIterator(); it.hasNext(); i++) {
            reti[index] = it.next();
        }
        return reti;
    }

    public E[] toArray(E[] arr) {
        if (arr.length < size)
            return null;
        Node<E> n = head;
        int i = -1;
        while (n.next != tail) {
            i++;
            n = n.next;
            arr[i] = n.data;
        }
        return arr;
    }

    private class ListIterator implements java.util.ListIterator<E> {
        Node<E> p;

        ListIterator() {
            p = head;
        }

        public int previousIndex() {
            return 0;// TODO
        }

        public boolean hasNext() {
            return p.next != tail;
        }

        public E get() {
            return p.data;
        }

        @Override
        public E next() {
            p = p.next;
            return p.data;
        }

        @Override
        public E previous() {
            p = p.prev;
            return p.data;
        }

        public boolean hasPrevious() {
            return p.prev != head;
        }

        public void remove() {
            if (p != head && p != tail)
                p.remove();
        }

        @Override
        public void add(E e) {
            // TODO Auto-generated method stub

        }

        @Override
        public int nextIndex() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void set(E e) {
            // TODO Auto-generated method stub

        }

    }
}