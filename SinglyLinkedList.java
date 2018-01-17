import java.util.Iterator;

public class SinglyLinkedList<E extends Comparable<E>> implements Iterable<E> {

    // Node class I will use to store each element of the list
    private static class Node<E> {

        private E element;
        private Node<E> next;

        public Node(E e, Node<E> n) {
            this.element = e;
            this.next = n;
        }

        public void setNext(Node<E> n) {
            this.next = n;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setElement(E element) {
            this.element = element;
        }
    }

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;


    // Iterator code to allow the "for each" loop to be used with this class
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    class ListIterator implements Iterator<E> {
        Node<E> currentNode;

        // When we want to the first node, we set currentNode to the first node after the header
        public ListIterator() {
            currentNode = SinglyLinkedList.this.head.getNext();
        }

        @Override
        // The list has a next node if the 'next' field does not point to null
        public boolean hasNext() {
            try {
                return currentNode.next != null;
            } catch (NullPointerException e) {
                return false;
            }
        }

        // As we iterate, update currentNode by making it point to the next node, and return the next node
        // if it is needed
        @Override
        public E next() {
            Node<E> tmpNode = currentNode;

            currentNode = currentNode.next;

            return tmpNode.getElement();
        }
    }

    // Returns the number of elements in the list
    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return (this.size() == 0);
    }

    public E get(int i) {

        Node<E> currentNode;

        try {
            currentNode = head;

            // Iterate up to the ith element
            for (int j = 0; j < i; j++) {
                currentNode = currentNode.getNext();
            }

        // If the ith element does not exist, return null
        } catch (NullPointerException e) {
            return null;
        }

        return currentNode.getElement();
    }

    public void set(int i, E element) {

        Node<E> currentNode;


        currentNode = head;

        // Iterate up to the ith element
        for (int j = 1; j < i; i++) {
           currentNode = currentNode.getNext();
        }

        currentNode.setElement(element);
    }

    public void addFirst(E element) {
        // If the list isn't empty, make sure the new node points to the old first node
        if (this.size() != 0) {
           Node<E> tmpNode = head;

           head = new Node<>(element, tmpNode);

        } else {
            head = new Node<>(element, null);
            tail = head;
        }

        // Since we have added an element, the size has increased
        this.size++;
    }

    public void addLast(E element) {
        if (this.size() != 0) {
            Node<E> tmpNode = tail;

            tmpNode.setNext(new Node<>(element, null));

            tail = tmpNode.getNext();
        } else {
            tail = new Node<>(element, null);
            head = tail;
        }

        this.size++;
    }

    public E remove(int i) {
        Node<E> currentNode;
        Node<E> removedElement;

        try {
            currentNode = head;
            // Iterate up to one before the ith element
            for (int j = 1; j < i - 1; i++) {
                currentNode = currentNode.getNext();
            }

            removedElement = currentNode.getNext();

            // Set the node before the node to be remove to point to the node after
            if (removedElement.getNext() != null) {
                currentNode.setNext(removedElement.getNext());
            } else {
                currentNode.setNext(null);
            }

            if (i == 0) {
                head = head.getNext();
            }
            // The list is now smaller
            this.size--;
        } catch(NullPointerException e) {
            return null;
        }

        return removedElement.getElement();
    }

    public String toString() {

        // Start out with an empty string we will return after concating all elements into it
        String tmpString = "";

        //If the list is empty we can return null
        if (head == null) {
            return null;
        }

        Node<E> tmpNode = head;

        // Iterate through the loop and add the elements into the string to return
        for (int i = 1; i < this.size() + 1; i++) {

            tmpString += tmpNode.getElement();

            tmpString += " ";

            tmpNode = tmpNode.getNext();
        }

        return tmpString;
    }

}
