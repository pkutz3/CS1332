import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * This is a basic set of unit tests for ArrayDeque and LinkedDeque.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class DequeStudentTest {

    private static final int TIMEOUT = 200;
    private ArrayDeque<String> array;
    private LinkedDeque<String> linked;

    @Before
    public void setup() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    /** These tests test the resizing mechanics of addFirst and addLast

     Tests ensure that your resizing unwraps your current deque

     testWrappedResizeAddFront() will also test that the new data is placed at index 0
     (With the rest of the old data unwrapped after)

     */


    @Test(timeout = TIMEOUT)
    public void testWrappedResizeAddLast() {
        array.addLast("H1"); // H1, _, _, _, _, _, _, _, _, _
        array.addFirst("H2"); // H1, _, _, _, _, _, _, H2
        array.addFirst("H3"); // H1, _, _, _, _, _, _, H3 H2
        array.addFirst("H4"); // H1, _, _, _, _, _, H4, H3, H2
        array.addFirst("H5");
        array.addFirst("H6");
        array.addFirst("H7");
        array.addFirst("H8");
        array.addFirst("H9");
        array.addFirst("H10");
        array.addFirst("H11");// H1 H11 H10 H9 H8 H7 H6 H5 H4 H3 H2

        Object[] expected = new Object[ArrayDeque.INITIAL_CAPACITY];

        expected[0] = "H1";
        expected[1] = "H11";
        expected[2] = "H10";
        expected[3] = "H9";
        expected[4] = "H8";
        expected[5] = "H7";
        expected[6] = "H6";
        expected[7] = "H5";
        expected[8] = "H4";
        expected[9] = "H3";
        expected[10] = "H2";

        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(11, array.size());
        assertEquals("H11", array.getFirst());
        assertEquals("H1", array.getLast());

        array.addLast("NEW"); // H11, 10, 9, .... 2, 1, NEW

        expected = new Object[ArrayDeque.INITIAL_CAPACITY * 2];
        for (int i = 0; i < 11; i++) {
            expected[i] = "H" + (11 - i);
        }

        expected[11] = "NEW";

        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(12, array.size());
        assertEquals("H11", array.getFirst());
        assertEquals("NEW", array.getLast());

    }

    @Test(timeout = TIMEOUT)
    public void testWrappedResizeAddFront() {
        array.addLast("H1"); // H1, _, _, _, _, _, _, _, _, _
        array.addFirst("H2"); // H1, _, _, _, _, _, _, H2
        array.addFirst("H3"); // H1, _, _, _, _, _, _, H3 H2
        array.addFirst("H4"); // H1, _, _, _, _, _, H4, H3, H2
        array.addFirst("H5");
        array.addFirst("H6");
        array.addFirst("H7");
        array.addFirst("H8");
        array.addFirst("H9");
        array.addFirst("H10");
        array.addFirst("H11");// H1 H11 H10 H9 H8 H7 H6 H5 H4 H3 H2

        Object[] expected = new Object[ArrayDeque.INITIAL_CAPACITY];

        expected[0] = "H1";
        expected[1] = "H11";
        expected[2] = "H10";
        expected[3] = "H9";
        expected[4] = "H8";
        expected[5] = "H7";
        expected[6] = "H6";
        expected[7] = "H5";
        expected[8] = "H4";
        expected[9] = "H3";
        expected[10] = "H2";

        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(11, array.size());
        assertEquals("H11", array.getFirst());
        assertEquals("H1", array.getLast());

        array.addFirst("NEW"); // NEW, H11, 10, 9, .... 2, 1

        expected = new Object[ArrayDeque.INITIAL_CAPACITY * 2];
        for (int i = 0; i < 11; i++) {
            expected[i + 1] = "H" + (11 - i);
        }

        expected[0] = "NEW";

        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(12, array.size());
        assertEquals("NEW", array.getFirst());
        assertEquals("H1", array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, array.size());
        assertArrayEquals(new Object[ArrayDeque.INITIAL_CAPACITY],
            array.getBackingArray());
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeNoWrapAround() {
        array.addLast("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        array.addLast("1a"); // 0a, 1a, _, _, _, _, _, _, _, _, _
        array.addLast("2a"); // 0a, 1a, 2a,  _, _, _, _, _, _, _, _
        array.addLast("3a"); // 0a, 1a, 2a, 3a, _, _, _, _, _, _, _
        array.addLast("4a"); // 0a, 1a, 2a, 3a, 4a, _, _, _, _, _, _

        assertEquals(5, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("4a", array.getLast());

        // _, 1a, 2a, 3a, 4a, _, _, _, _, _, _
        assertEquals("0a", array.removeFirst());
        // _, _, 2a, 3a, 4a, _, _, _, _, _, _
        assertEquals("1a", array.removeFirst());
        // _, _, 2a, 3a, _, _, _, _, _, _, _
        assertEquals("4a", array.removeLast());
        // _, _, 2a, _, _, _, _, _, _, _, _
        assertEquals("3a", array.removeLast());

        assertEquals(1, array.size());
        expected[0] = null;
        expected[1] = null;
        expected[3] = null;
        expected[4] = null;
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("2a", array.getFirst());
        assertEquals("2a", array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeWrapAround() {
        array.addFirst("4a"); // _, _, _, _, _, _, _, _, _, _, 4a
        array.addFirst("3a"); // _, _, _, _, _, _, _, _, _, 3a, 4a
        array.addFirst("2a"); // _, _, _, _, _, _, _, _, 2a, 3a, 4a
        array.addFirst("1a"); // _, _, _, _, _, _, _, 1a, 2a, 3a, 4a
        array.addFirst("0a"); // _, _, _, _, _, _, 0a, 1a, 2a, 3a, 4a

        assertEquals(5, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[10] = "4a";
        expected[9] = "3a";
        expected[8] = "2a";
        expected[7] = "1a";
        expected[6] = "0a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("4a", array.getLast());

        // _, _, _, _, _, _, 0a, 1a, 2a, 3a, _
        assertEquals("4a", array.removeLast());

        assertEquals(4, array.size());
        expected[10] = null;
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("3a", array.getLast());

        array.addLast("5a"); // _, _, _, _, _, _, 0a, 1a, 2a, 3a, 5a
        array.addLast("6a"); // 6a, _, _, _, _, _, 0a, 1a, 2a, 3a, 5a

        assertEquals(6, array.size());
        expected[10] = "5a";
        expected[0] = "6a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("6a", array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeAdd() {
        linked.addFirst("1a"); // 1a
        linked.addFirst("0a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals(4, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("0a", cur.getData());

        LinkedNode<String> prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("1a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotEquals(null, cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("2a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("3a", cur.getData());
        assertEquals(linked.getTail(), cur);
        assertNull(cur.getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeRemove() {
        linked.addFirst("1a"); // 1a
        linked.addFirst("0a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals(4, linked.size());

        assertEquals("0a", linked.removeFirst()); // 1a, 2a, 3a
        assertEquals("3a", linked.removeLast()); // 1a, 2a

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("1a", cur.getData());

        LinkedNode<String> prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("2a", cur.getData());
        assertEquals(linked.getTail(), cur);

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeGet() {
        linked.addLast("0a"); // 0a
        linked.addLast("1a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals("0a", linked.getFirst());
        assertEquals("3a", linked.getLast());
    }
}
