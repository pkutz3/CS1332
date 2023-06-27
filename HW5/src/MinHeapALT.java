import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeapALT<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeapALT() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeapALT(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        size = data.size();
        backingArray = (T[]) new Comparable[1 + (2 * size)];

        for (int i = 0; i < size ; i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("An element in data is null.");
            }
            backingArray[i + 1] = data.get(i);
        }

        for (int i = size / 2; i > 0; i--) {
            minHeapify(i);
        }
    }

    private void minHeapify(int index) {
        if (index > size /2) {
            return;
        } else {
            int cIndex = index * 2; // child index

            if (cIndex < size) {
                if (backingArray[cIndex].compareTo(backingArray[cIndex + 1]) > 0) {
                    cIndex++;
                }
            }

            if (backingArray[index].compareTo(backingArray[cIndex]) < 0) {
                return;
            }
            T temp = backingArray[index];
            backingArray[index] = backingArray[cIndex];
            backingArray[cIndex] = temp;
            index = cIndex;
            minHeapify(index);
        }
        while(index <= size / 2) {
            System.out.println("a");
            int cIndex = index * 2; // child index

            if (cIndex < size) {
                if (backingArray[cIndex].compareTo(backingArray[cIndex + 1]) > 0) {
                    cIndex++;
                }
            }

            if (backingArray[index].compareTo(backingArray[cIndex]) < 0) {
                return;
            }
            T temp = backingArray[index];
            backingArray[index] = backingArray[cIndex];
            backingArray[cIndex] = temp;
            index = cIndex;
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        if (size + 1 == backingArray.length){
            expand();
        }

        size++;
        backingArray[size] = data;
        int i = size;

        while (i > 1 && backingArray[i].compareTo(backingArray[i / 2]) < 0) {
            swap(i / 2, i);
            i /= 2;
        }
    }

    /**
     * Private method used to swap the parent and child in a heap. Executed by
     * swapping the values at two indices.
     * @param pPos the index of the parent
     * @param cPos the index of the child
     */
    private void swap(int pPos, int cPos) {
        T temp = backingArray[pPos];
        backingArray[pPos] = backingArray[cPos];
        backingArray[cPos] = temp;
    }

    /**
     * Private method used to expand the heap's backingArray when it reaches
     * capacity. It is expanded to double the length.
     */
    private void expand() {
        T[] temp = (T[]) new Comparable[backingArray.length * 2];
        for (int i = 0; i < backingArray.length; i++) {
            temp[i] = backingArray[i];
        }
        backingArray = temp;
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("The heap is empty.");
        }
        T temp = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;

        minHeapify(1);
        return temp;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("The heap is empty.");
        }

        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return backingArray[1] == null;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
