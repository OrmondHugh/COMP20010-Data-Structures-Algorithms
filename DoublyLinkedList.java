import java.util.Iterator;
import java.util.Random;

public class DoublyLinkedList<E extends Comparable<E>> implements Iterable<E>{

    private class Node<E> {

        private E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(E e, Node<E> n, Node<E> p) {
            this.element = e;
            this.next = n;
            this.prev = p;
        }

        public void setNext(Node<E> n) {
            this.next = n;
        }

        public void setPrev(Node<E> p) {
            this.prev = p;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }
    }

    private Node<E> header;
    private Node<E> trailer;
    private int size;

    public DoublyLinkedList() {
        this.header = new Node<>(null, null, null);
        this.trailer = new Node<>(null, null, null);
        this.size = 0;
        header.setNext(trailer);
        trailer.setPrev(header);
    }

    public void addFirst(E element) {
        Node<E> tmpNode = header.next;

        header.next = new Node<>(element, tmpNode, header);

        tmpNode.prev = header.next;

        size++;
    }

    public void addLast(E element) {
        Node<E> tmpNode = trailer.prev;

        trailer.prev = new Node<>(element, trailer, tmpNode);

        tmpNode.next = trailer.prev;

        size++;
    }

    public E removeFirst() {

        // Make a temporary node pointing to the old first node
        Node<E> tmpNode = header.next;

        // Update the header's next pointer
        header.next = tmpNode.next;

        // And make the new first point back to the header
        header.next.prev = header;

        size--;

        return tmpNode.getElement();
    }

    public E removeLast() {
        Node<E> tmpNode = trailer.prev;

        trailer.prev = tmpNode.prev;

        trailer.prev.next = trailer;

        size--;

        return tmpNode.getElement();
    }

    public void remove(E element) {

        Node<E> curr = header.getNext();

        // Iterate through the list
        for (int i = 1; i < this.size() + 1; i++) {

            // If we find a node containing the element to be removed, remove it from the list

            if (curr.getElement().compareTo(element) == 0) {

                curr.getPrev().setNext(curr.getNext());
                curr.getNext().setPrev(curr.getPrev());

                this.size--;

                // If we have removed the node we can end the loop
                break;
            }

            //Otherwise keep iterating through the list
            curr = curr.getNext();
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {

        // Start out with an empty string we will return after concating all elements into it
        String tmpString = "";

        //If the list is empty we can return null
        if (header.next == trailer) {
            return null;
        }

        Node<E> tmpNode = header.next;

        // Iterate through the loop and add the elements into the string to return
        for (int i = 1; i < this.size() + 1; i++) {

            tmpString += tmpNode.element;

            tmpString += " ";

            tmpNode = tmpNode.getNext();
        }

        return tmpString;
    }

    public Node<E> getKth(int k) {
        // The node to be returned once the function is finished
        Node<E> tmpNode = header.next;

        // Iterate up to the Kth node
        for (int i = 1;i < k; i++) {
            tmpNode = tmpNode.next;
        }

        return tmpNode;
    }

    public boolean isPalindrome() {
        // The null check is not relevant as the header and trailer are always defined

        // Left and right are set to 1 and this.size rather than 0 and this.size -1 to
        // account for the header and trailer nodes
        int left = 1;
        int right = this.size();


        while (left < right) {
            // Elements at indexes 'left' and 'right' in the list
            E leftElement = this.getKth(left).getElement();
            E rightElement = this.getKth(right).getElement();

            if (leftElement.equals(rightElement) == false) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    public void reverse() {
        Node<E> prev = null;
        Node<E> current = this.header;

        Node<E> oldFirst = this.header.next;

        while (current != null) {

            Node<E> next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        this.header = prev;
        this.trailer.setPrev(oldFirst);
    }

    public E findMinimum() {

        // Set the minimum to the first node by default
        E minValue = this.header.next.element;

        // Iterate through the whole list
        for (E n: DoublyLinkedList.this) {

            // If a value is lower than the current min, make it the min
            if (minValue.compareTo(n) > 0) {
                minValue = n;
            }
        }
        return  minValue;
    }

    public E popMinimum() {
        // To pop the minimum we simply find it, remove it and return it

        E min = this.findMinimum();

        this.remove(min);

        return min;
    }

    public  Iterator<E> iterator() {
        return new ListIterator();
    }


    class ListIterator implements Iterator<E> {
        Node<E> currentNode;

        // When we want to the first node, we set currentNode to the first node after the header
        public ListIterator() {
            currentNode = DoublyLinkedList.this.header.getNext();
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
            currentNode = currentNode.next;

            return currentNode.prev.getElement();
        }
    }


    // From here down is the 4 tests of the functions provided in the questions
    public static void testPalindrome() {
        String [] data = {"a", "m", "a", "n", "a", "p", "l", "a", "n", "a", "c", "a", "n", "a", "l", "p", "a", "n", "a", "m", "a"};

        DoublyLinkedList<String> li = new DoublyLinkedList<>();

        for (String s : data) {
            li.addLast(s);
        }

        System.out.println("isPalindrome(): " + li.isPalindrome());
    }

    public static void testKth() {
        String [] data = {"one", "two", "three", "four", "five", "six"};
        DoublyLinkedList<String> ll = new DoublyLinkedList<String>();

        for(String s : data) {
            ll.addLast(s);
        }
        for(int k = 0; k < data.length; ++k) {
            System.out.println("k: " + k + " " + data[k] + " " + ll.getKth(k));
        }
    }

    public static void testReverse() {
        String [] data = {"one", "two", "three", "four", "five", "six"};
        DoublyLinkedList<String> ll = new DoublyLinkedList<String>();

        for(String s : data) {
            ll.addLast(s);
        }

        System.out.println("before reverse: " + ll);
        ll.reverse();
        System.out.println("after reverse: " + ll);
    }

    public static void selectionSort() {
        Random random = new Random(20010);
        DoublyLinkedList<Integer> ll = new DoublyLinkedList<Integer>();
        int n = 100;
        for(int i = 0; i < n; i++) {
            ll.addLast(new Integer(random.nextInt(1000)));
        }
        System.out.println("before sorting : " + ll);

        DoublyLinkedList<Integer> sorted_ll = new DoublyLinkedList<Integer>();
        while(!ll.isEmpty()) {
            sorted_ll.addLast(ll.popMinimum());
        }
        System.out.println("after sorting : " + sorted_ll);
    }

    public static void main(String[] args) {

        // Run the tests of the functions
        testPalindrome();

        testKth();

        testReverse();

        selectionSort();
    }
}
