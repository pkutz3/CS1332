import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 Update as of 10/10: added randomized test based on canonical implementation.
 */
public class SmalliganLinearProbingHashMapTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    private LinearProbingHashMap<Integer, Integer> map;

    @Before
    public void setUp() {
        map = new LinearProbingHashMap<>();
    }

    @Test
    public void putNoResize() {
        assertNull(map.put(0, 0));
        assertNull(map.put(13, 13));
        assertNull(map.put(1, 1));
        assertNull(map.put(4, 4));

        LinearProbingMapEntry<Integer, Integer>[] expected = new LinearProbingMapEntry[13];
        expected[0] = new LinearProbingMapEntry<>(0, 0);
        expected[1] = new LinearProbingMapEntry<>(13, 13);
        expected[2] = new LinearProbingMapEntry<>(1, 1);
        expected[4] = new LinearProbingMapEntry<>(4, 4);
        assertEquals(4, map.size());

        assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void putResize() {
        map = new LinearProbingHashMap<>(5);

        map.put(0, 0);
        assertEquals(5, map.getTable().length);

        map.put(5, 5);
        assertEquals(5, map.getTable().length);

        map.put(1, 1);
        assertEquals(5, map.getTable().length);

        LinearProbingMapEntry<Integer, Integer>[] expected = new LinearProbingMapEntry[5];
        expected[0] = new LinearProbingMapEntry<>(0, 0);
        expected[1] = new LinearProbingMapEntry<>(5, 5);
        expected[2] = new LinearProbingMapEntry<>(1, 1);
        assertArrayEquals(expected, map.getTable());
        assertEquals(3, map.size());

        // resize should happen here from 5 -> 11
        map.put(4, 4);
        map.put(15, 15);
        assertEquals(11, map.getTable().length);
        assertEquals(5, map.size());

        expected = new LinearProbingMapEntry[11];
        expected[0] = new LinearProbingMapEntry<>(0, 0);
        expected[5] = new LinearProbingMapEntry<>(5, 5);
        expected[1] = new LinearProbingMapEntry<>(1, 1);
        expected[4] = new LinearProbingMapEntry<>(4, 4);
        expected[6] = new LinearProbingMapEntry<>(15, 15);

        assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void putReplace() {
        map.put(3, 3);
        map.put(13, 13);

        LinearProbingMapEntry<Integer, Integer>[] expected = new LinearProbingMapEntry[13];
        expected[0] = new LinearProbingMapEntry<>(13, 13);
        expected[3] = new LinearProbingMapEntry<>(3, 3);
        assertArrayEquals(expected, map.getTable());

        assertEquals((Integer) 13, map.put(13, 10));
        expected[0] = new LinearProbingMapEntry<>(13, 10);
        assertArrayEquals(expected, map.getTable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void putNullKey() {
        assertNull(map.put(null, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void putNullValue() {
        map.put(0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putNullBoth() {
        map.put(null, null);
    }

    @Test
    public void putWrapAround() {
        map.put(12, 12);
        map.put(25, 25);

        LinearProbingMapEntry<Integer, Integer>[] expected = new LinearProbingMapEntry[13];
        expected[0] = new LinearProbingMapEntry<>(25, 25);
        expected[12] = new LinearProbingMapEntry<>(12, 12);

        assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void remove() {
        map.put(0, 0);
        map.put(12, 12);
        map.put(3, 3);
        map.put(16, 16);

        LinearProbingMapEntry<Integer, Integer>[] expected = new LinearProbingMapEntry[13];
        expected[0] = new LinearProbingMapEntry<>(0, 0);
        expected[12] = new LinearProbingMapEntry<>(12, 12);
        expected[3] = new LinearProbingMapEntry<>(3, 3);
        expected[4] = new LinearProbingMapEntry<>(16, 16);
        assertArrayEquals(expected, map.getTable());
        assertEquals(4, map.size());

        map.remove(16);
        expected[4].setRemoved(true);
        assertArrayEquals(expected, map.getTable());
        assertEquals(3, map.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeIllegalArgument() {
        map.put(0, 0);
        map.remove(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeNoSuchElementNeverAdded() {
        map.put(1, 1);
        map.put(4, 4);

        map.remove(17);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeNoSuchElementPrevAdded() {
        map.put(14, 14);
        map.put(1, 1);

        LinearProbingMapEntry<Integer, Integer>[] expected = new LinearProbingMapEntry[13];
        expected[1] = new LinearProbingMapEntry<>(14, 14);
        expected[2] = new LinearProbingMapEntry<>(1, 1);

        assertArrayEquals(expected, map.getTable());

        map.remove(1);
        expected[2].setRemoved(true);
        assertArrayEquals(expected, map.getTable());

        map.remove(1);
    }

    // this test is for situations where, while removing,
    // you encounter the element being sought with the remove flag
    // set
    @Test(expected = NoSuchElementException.class)
    public void removeNoSuchElementPrevAddedEarlyStop() {
        map.put(14, 14);
        map.put(1, 1);
        map.put(27, 27);

        LinearProbingMapEntry<Integer, Integer>[] expected = new LinearProbingMapEntry[13];
        expected[1] = new LinearProbingMapEntry<>(14, 14);
        expected[2] = new LinearProbingMapEntry<>(1, 1);
        expected[3] = new LinearProbingMapEntry<>(27, 27);

        assertArrayEquals(expected, map.getTable());

        map.remove(1);
        expected[2].setRemoved(true);
        assertArrayEquals(expected, map.getTable());

        map.remove(1);
    }

    @Test
    public void resizeAfterRemoves() {
        map = new LinearProbingHashMap<>(5);

        map.put(10, 10);
        map.put(4, 4);
        map.put(5, 5);
        assertEquals((Integer) 4, map.remove(4));

        LinearProbingMapEntry<Integer, Integer>[] expected = new LinearProbingMapEntry[5];
        expected[0] = new LinearProbingMapEntry<>(10, 10);
        expected[1] = new LinearProbingMapEntry<>(5, 5);
        expected[4] = new LinearProbingMapEntry<>(4, 4);
        expected[4].setRemoved(true);
        assertArrayEquals(expected, map.getTable());
        assertEquals(2, map.size());
        assertEquals(5, map.getTable().length);

        map.put(0, 0);
        map.put(3, 3); // this one should trigger a resize to 11
        expected = new LinearProbingMapEntry[11];
        expected[10] = new LinearProbingMapEntry<>(10, 10);
        expected[5] = new LinearProbingMapEntry<>(5, 5);
        expected[0] = new LinearProbingMapEntry<>(0, 0);
        expected[3] = new LinearProbingMapEntry<>(3, 3);
        // the deleted (4, 4) node should not be copied
    }

    @Test
    public void removeWrapAround() {
        map.put(12, 12);
        map.put(25, 25);
        map.put(0, 0);
        map.put(38, 38);

        LinearProbingMapEntry<Integer, Integer>[] expected = new LinearProbingMapEntry[13];
        expected[12] = new LinearProbingMapEntry<>(12, 12);
        expected[0] = new LinearProbingMapEntry<>(25, 25);
        expected[1] = new LinearProbingMapEntry<>(0, 0);
        expected[2] = new LinearProbingMapEntry<>(38, 38);

        assertArrayEquals(expected, map.getTable());
        assertEquals((Integer) 38, map.remove(38));
        expected[2].setRemoved(true);
        assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void putAfterRemove() {
        map.put(0, 0);
        map.put(1, 1);
        map.put(14, 14);
        map.put(13, 13);

        LinearProbingMapEntry<Integer, Integer>[] expected = new LinearProbingMapEntry[13];
        expected[0] = new LinearProbingMapEntry<>(0, 0);
        expected[1] = new LinearProbingMapEntry<>(1, 1);
        expected[2] = new LinearProbingMapEntry<>(14, 14);
        expected[3] = new LinearProbingMapEntry<>(13, 13);
        assertArrayEquals(expected, map.getTable());
        assertEquals((Integer) 1, map.remove(1));
        assertEquals((Integer) 14, map.remove(14));

        expected[1].setRemoved(true);
        expected[2].setRemoved(true);
        assertArrayEquals(expected, map.getTable());
        map.put(26, 26);
        expected[1] = new LinearProbingMapEntry<>(26, 26);
        assertArrayEquals(expected, map.getTable());
    }

    @Test(expected = NoSuchElementException.class)
    public void getAfterRemoveNoSuchElement() {
        map.put(1, 1);
        map.put(11, 12);
        assertEquals((Integer) 12, map.get(11));
        map.remove(11);

        // cause no such element
        map.get(11);
    }

    @Test
    public void get() {
        map.put(5, 10);
        map.put(11, 22);
        map.put(24, 48);
        map.put(12, 24);
        map.put(0, 1);
        map.put(13, 26);

        assertEquals((Integer) 10, map.get(5));
        assertEquals((Integer) 22, map.get(11));
        assertEquals((Integer) 48, map.get(24));
        assertEquals((Integer) 24, map.get(12));
        assertEquals((Integer) 1, map.get(0));
        assertEquals((Integer) 26, map.get(13));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNull() {
        map.put(0, 0);
        map.get(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void getNoSuch() {
        map.put(0, 0);
        map.put(14, 14);
        map.put(2, 2);
        map.put(3, 3);

        // no such
        map.get(1);
    }

    @Test
    public void containsKey() {
        map.put(11, 11);
        map.put(24, 24);
        map.put(37, 37);
        assertTrue(map.containsKey(11));
        assertTrue(map.containsKey(24));
        assertTrue(map.containsKey(37));
        assertFalse(map.containsKey(0));
        assertFalse(map.containsKey(12));
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsKeyNull() {
        map.put(11, 11);
        map.put(24, 24);
        map.put(37, 37);
        map.containsKey(null);
    }

    @Test
    public void containsKeyAfterRemove() {
        map.put(11, 11);
        map.put(24, 24);
        map.put(37, 37);
        assertTrue(map.containsKey(11));
        assertTrue(map.containsKey(24));
        assertTrue(map.containsKey(37));
        map.remove(11);
        assertFalse(map.containsKey(11));
        assertTrue(map.containsKey(24));
        assertTrue(map.containsKey(37));
    }

    @Test
    public void keySet() {
        map.put(0, 10);
        map.put(1, 11);
        map.put(2, 12);
        map.put(3, 13);
        map.remove(1);
        map.remove(3);
        map.put(1, 1);

        HashSet<Integer> set = new HashSet<>();
        set.add(0);
        set.add(1);
        set.add(2);
        assertEquals(set, map.keySet());
    }

    @Test
    public void values() {
        map.put(0, 10); // 0
        map.put(13, 11); // 1
        map.put(12, 15); // 12
        map.put(1, 1); // 2
        map.put(3, 6); // 3 (removed)
        map.put(5, 7); // 5

        map.remove(3);
        map.put(4, 1); // 4

        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(new Integer[] {10, 11, 1, 1, 7, 15}));
        assertEquals(list, map.values());
    }

    @Test
    public void resizeBackingTable() {
        map.put(1, 1);
        map.put(2, 2);
        map.put(18, 5);
        map.put(20, 7);
        map.put(22, 9);
        map.put(14, 14);
        LinearProbingMapEntry<Integer, Integer>[] expected = new LinearProbingMapEntry[13];
        expected[1] = new LinearProbingMapEntry<>(1, 1);
        expected[2] = new LinearProbingMapEntry<>(2, 2);
        expected[3] = new LinearProbingMapEntry<>(14, 14);
        expected[5] = new LinearProbingMapEntry<>(18, 5);
        expected[7] = new LinearProbingMapEntry<>(20, 7);
        expected[9] = new LinearProbingMapEntry<>(22, 9);

        assertArrayEquals(expected, map.getTable());
        assertEquals(6, map.size());

        map.resizeBackingTable(17);
        expected = new LinearProbingMapEntry[17];
        expected[1] = new LinearProbingMapEntry<>(1, 1);
        expected[2] = new LinearProbingMapEntry<>(2, 2);
        expected[3] = new LinearProbingMapEntry<>(18, 5);
        expected[4] = new LinearProbingMapEntry<>(20, 7);
        expected[5] = new LinearProbingMapEntry<>(22, 9);
        expected[14] = new LinearProbingMapEntry<>(14, 14);
        assertArrayEquals(expected, map.getTable());
        assertEquals(6, map.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void resizeBackingTableInsufficientSize() {
        map.put(0, 0);
        map.put(3, 3);
        map.put(1, 1);
        map.put(2, 2);

        map.resizeBackingTable(3);
    }

    @Test
    public void clear() {
        map.put(5, 5);
        map.put(9, 9);
        map.put(10, 10);

        map.clear();
        assertEquals(0, map.size());

        assertArrayEquals(new LinearProbingMapEntry[13], map.getTable());
    }

    @Test
    public void randomTest() {
        java.util.HashMap<Integer, Integer> canon = new HashMap<>();

        for (int i = 0; i < 100000; i++) {
            java.util.Random rand = new Random();
            int key = rand.nextInt(100);
            int val = rand.nextInt(100000);
            double r = Math.random();
            if (r > 0.67) {
                assertEquals(canon.put(key, val), map.put(key, val));
                assertTrue(map.containsKey(key));
            } else if (r > 0.33) {
                Integer out = canon.remove(key);
                if (out != null) {
                    assertEquals(out, map.get(key));
                    assertEquals(out, map.remove(key));
                    assertFalse(map.containsKey(key));
                } else {
                    try {
                        assertFalse(map.containsKey(key));
                        map.remove(key);
                        fail("No such element should have thrown for remove().");
                    } catch (NoSuchElementException e) { }
                }
            } else if (r > 0.01) {
                Integer got = canon.get(key);

                if (got != null) {
                    assertEquals(got, map.get(key));
                } else {
                    try {
                        assertFalse(map.containsKey(key));
                        map.get(key);
                        fail("NoSuchElement should have been thrown for get()");
                    } catch (NoSuchElementException e) { }
                }
            } else {
                canon.clear();
                map.clear();
            }

            assertEquals(canon.keySet(), map.keySet());

            Collection<Integer> can = canon.values();
            Collection<Integer> maps = map.values();
            assertTrue(can.containsAll(maps));
            assertTrue(maps.containsAll(can));
        }
    }
}