import java.util.Iterator;


/* Implementation of a stack based on my implementation of a singly linked list*/

public class Stack<E> {

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

    private Node<E> top = null;
    private int size = 0;

    // Returns the number of elements in the list
    private int size() {
        return this.size;
    }

    private boolean isEmpty() {
        return (this.size() == 0);
    }

    public E top(E element) {
        return top.getElement();
    }

    public void push(E element) {
        Node<E> tmpNode = top;

        top = new Node<>(element, tmpNode);

        this.size++;
    }

    public E pop() {
        if (this.size() != 0) {

            Node<E> tmpNode = top;

            top = top.getNext();

            this.size--;

            return tmpNode.getElement();
        } else {
            return null;
        }
    }

    public String toString() {

        // Start out with an empty string we will return after concating all elements into it
        String tmpString = "";

        //If the list is empty we can return null
        if (top == null) {
            return null;
        }

        Node<E> tmpNode = top;

        // Iterate through the loop and add the elements into the string to return
        for (int i = 1; i < this.size() + 1; i++) {

            tmpString += tmpNode.getElement();

            tmpString += " ";

            tmpNode = tmpNode.getNext();
        }

        return tmpString;
    }

}
