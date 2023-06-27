import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Peter Kutz
 * @version 1.0
 * @userid pkutz3
 * @GTID 903637824
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: CS1331 Notes
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index must be greater "
                    + "than 0.");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("The index is greater than"
                    + "the size of the list.");
        } else if (data == null) {
            throw new IllegalArgumentException("The data entered is null.");
        }

        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(data);

        if (head == null) {
            addToFront(data);
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            if (index < (size / 2)) {
                DoublyLinkedListNode<T> ref = head;

                for (int i = 1; i < index; i++) {
                    ref = ref.getNext();
                }

                temp.setNext(ref.getNext());
                ref.setNext(temp);
                temp.setPrevious(ref);
                temp.getNext().setPrevious(temp);

                size++;
            } else {
                DoublyLinkedListNode<T> ref = tail;

                for (int i = size; i > index + 1; i--) {
                    ref = ref.getPrevious();
                }

                temp.setPrevious(ref.getPrevious());
                ref.setPrevious(temp);
                temp.getPrevious().setNext(temp);
                temp.setNext(ref);

                size++;
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(data);

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        if (head != null) {
            temp.setNext(head);
            head.setPrevious(temp);
            head = temp;
        } else {
            head = temp;
            tail = temp;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(data);

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        if (tail != null) {
            tail.setNext(temp);
            temp.setPrevious(tail);
            tail = temp;
        } else {
            head = temp;
            tail = temp;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(null);

        if (index < 0) {
            throw new IndexOutOfBoundsException("The index must be positive.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index must be less than"
                    + "the size of list.");
        }

        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            if (index < (size / 2)) {
                DoublyLinkedListNode<T> ref = head;

                for (int i = 0; i < index; i++) {
                    ref = ref.getNext();
                }

                ref.getNext().setPrevious(ref.getPrevious());
                ref.getPrevious().setNext(ref.getNext());

                size--;
                temp = ref;
            } else {
                DoublyLinkedListNode<T> ref = tail;

                for (int i = size; i > index + 1; i--) {
                    ref = ref.getPrevious();
                }

                ref.getNext().setPrevious(ref.getPrevious());
                ref.getPrevious().setNext(ref.getNext());

                size--;
                temp = ref;
            }
        }
        return temp.getData();
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {

        if (head == null) {
            throw new NoSuchElementException("The list is empty. Cannot "
                    + "remove any data.");

        } else {
            DoublyLinkedListNode<T> temp = head;

            head = head.getNext();
            head.setPrevious(null);

            size--;
            return temp.getData();
        }
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        DoublyLinkedListNode<T> temp = tail;

        if (head == null) {
            throw new NoSuchElementException("The list is empty. Cannot "
                    + "remove any data.");
        } else {
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
        }
        size--;
        return temp.getData();
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index must be greater "
                    + "than 0.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index is greater than"
                    + "the size of the list.");
        }

        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            if (index < (size / 2)) {
                DoublyLinkedListNode<T> ref = head;

                for (int i = 0; i < index; i++) {
                    ref = ref.getNext();
                }

                return ref.getData();
            } else {
                DoublyLinkedListNode<T> ref = tail;

                for (int i = size; i > index + 1; i--) {
                    ref = ref.getPrevious();
                }

                return ref.getData();
            }
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head.setNext(null);
        head = null;
        tail.setPrevious(null);
        tail = null;

        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        boolean here = false;
        int index = size - 1;

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        if (tail.getData() == data) {
            return removeFromBack();
        } else {
            DoublyLinkedListNode<T> ref = tail;

            while (!here) {
                index--;

                ref = ref.getPrevious();

                if (ref.getData() == data) {
                    here = true;
                } else if (ref == head) {
                    throw new NoSuchElementException("The data is not stored"
                            + "in this list.");
                }
            }
            return removeAtIndex(index);
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        DoublyLinkedListNode<T> ref = head;

        for (int i = 0; i < size; i++) {
            array[i] = ref.getData();
            ref = ref.getNext();
        }

        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
