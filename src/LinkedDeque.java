import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedDeque.
 *
 * @author Peter Kutz
 * @version 1.0
 * @userid pkutz3
 * @GTID 903637824
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        LinkedNode<T> temp = new LinkedNode<>(data);

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        if (head == null) {
            head = temp;
            tail = temp;
        } else {
            head.setPrevious(temp);
            temp.setNext(head);
            head = temp;
        }
        size++;
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        LinkedNode<T> temp = new LinkedNode<>(data);

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        if (head == null) {
            head = temp;
            tail = temp;
        } else {
            tail.setNext(temp);
            temp.setPrevious(tail);
            tail = temp;
        }
        size++;
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        LinkedNode<T> temp = head;

        if (head == null) {
            throw new NoSuchElementException("The deque is empty.");
        }

        if (tail == head) {
            temp = head;
            head = null;
            tail = null;
        } else {
            head.getNext().setPrevious(null);
            head = head.getNext();
        }

        size--;
        return temp.getData();
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        LinkedNode<T> temp = tail;

        if (head == null) {
            throw new NoSuchElementException("The deque is empty.");
        }

        if (tail == head) {
            temp = tail;
            tail = null;
            head = null;
        } else {
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
        }

        size--;
        return temp.getData();
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("The deque is empty.");
        }

        return head.getData();
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (head == null) {
            throw new NoSuchElementException("The deque is empty.");
        }

        return tail.getData();
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
