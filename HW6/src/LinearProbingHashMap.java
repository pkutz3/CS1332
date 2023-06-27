import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of a LinearProbingHashMap.
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
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of initialCapacity.
     * <p>
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * <p>
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     * <p>
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        }
        if (value == null) {
            throw new IllegalArgumentException("The value is null.");
        }

        if ((double) (size + 1) / (double) table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }

        LinearProbingMapEntry<K, V> entry = new LinearProbingMapEntry<>(key, value);
        LinearProbingMapEntry<K, V> eTemp;
        V temp;

        int i = Math.abs(key.hashCode() % table.length);
        int numProbes = 0;
        int delI = -1;

        while (table[i] != null && numProbes < size && !table[i].getKey().equals(key)) {
            if (!table[i].isRemoved()) {
                numProbes++;
            } else if (table[i].isRemoved() && delI == -1) {
                delI = i;
            }
            i = (i + 1) % table.length;
        }

        if (table[i] == null) {
            if (delI > 0) {
                table[delI] = entry;
            } else {
                table[i] = entry;
            }
            size++;
            return null;
        } else {
            if (table[i].isRemoved()) {
                if (delI > 0) {
                    table[delI] = entry;
                } else {
                    table[i] = entry;
                }
                size++;
                return null;
            } else {
                temp = table[i].getValue();
                table[i].setValue(value);
                return temp;
            }
        }

        /*do {
            eTemp = table[i];
            if (eTemp == null) {
                table[i] = entry;
                size++;
                return null;
            }
            if (eTemp.isRemoved()) {
                table[i] = entry;
                size++;
                return null;
            }
            if (eTemp.getKey().equals(key)) {
                temp = table[i].getValue();
                eTemp.setValue(value);
                return temp;
            }
            i = (i + 1) % table.length;
            numProbes++;
        } while (i != hashNum);*/
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        }
        if (!containsKey(key)) {
            throw new NoSuchElementException("The key is not in the map.");
        }

        int hashNum = Math.abs(key.hashCode() % table.length);

        while (!key.equals(table[hashNum].getKey())) {
            hashNum = (hashNum + 1) % table.length;
        }
        V temp = table[hashNum].getValue();
        table[hashNum].setRemoved(true);

        size--;
        return temp;
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        }

        int hashNum = Math.abs(key.hashCode() % table.length);
        while (table[hashNum] != null) {
            if (table[hashNum].getKey().equals(key)) {
                if (!table[hashNum].isRemoved()) {
                    return table[hashNum].getValue();
                }
            }
            hashNum = (hashNum + 1) % table.length;
        }
        throw new NoSuchElementException("The key is not in the map.");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        }
        int hashNum = Math.abs(key.hashCode() % table.length);

        if (table[hashNum] == null) {
            return false;
        }
        if (table[hashNum].getKey().equals(key)) {
            if (!table[hashNum].isRemoved()) {
                return true;
            }
        } else {
            int probes = 0;
            while (probes != table.length) {
                LinearProbingMapEntry<K, V> temp = table[(hashNum + probes) % table.length];
                if (temp != null && temp.getKey().equals(key)) {
                    if (!temp.isRemoved()) {
                        return true;
                    }
                }
                probes++;
            }
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * <p>
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>(size);
        for (LinearProbingMapEntry<K, V> entry : table) {
            if (entry != null) {
                if (!entry.isRemoved()) {
                    keys.add(entry.getKey());
                }
            }
        }
        return keys;
    }

    /**
     * Returns a List view of the values contained in this map.
     * <p>
     * Use java.util.ArrayList or java.util.LinkedList.
     * <p>
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> values = new ArrayList<>(size);
        for (LinearProbingMapEntry<K, V> entry : table) {
            if (entry != null) {
                if (!entry.isRemoved()) {
                    values.add(entry.getValue());
                }
            }
        }
        return values;
    }

    /**
     * Resize the backing table to length.
     * <p>
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     * <p>
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     * You should NOT copy over removed elements to the resized backing table.
     * <p>
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     * <p>
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("The length is less than the items in hash map.");
        }

        LinearProbingMapEntry<K, V>[] tempT
                = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[length];

        for (int i = 0; i < table.length; i++) {
            LinearProbingMapEntry<K, V> entry = table[i];
            if (entry != null && !entry.isRemoved()) {
                int hashNum = Math.abs(entry.getKey().hashCode() % tempT.length);

                if (tempT[hashNum] == null) {
                    tempT[hashNum] = entry;
                } else {
                    int j = 0;

                    while (tempT[(hashNum + j) % tempT.length] != null
                            && !tempT[(hashNum + j) % tempT.length].isRemoved()) {
                        j++;
                    }
                    tempT[(hashNum + j) % tempT.length] = entry;
                }
            }
        }
        table = tempT;
    }

    /**
     * Clears the map.
     * <p>
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}