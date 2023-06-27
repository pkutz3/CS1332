import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 *
 * This is a small set of JUnits to test each of the sorting algorithms
 * for the following criteria.
 * 1.) Correctness
 * 2.) Efficiency
 * 3.) Stability
 * 4.) Adapativity
 * 5.) Edge Cases
 *
 * IMPORTANT NOTES:
 *   * Did not test for space complexity
 *   * Did not explicitly test for stability or efficiency of LSD Radix Sort
 *   * Did not test heapsort efficiency
 *
 * Good luck yall!
 *
 * Also, I used the same class structure as the one provided by the TAs in
 * SortingStudentTest.java but renamed it to DataStructure for some spice!
 *
 * @author Manu Gupta
 * @version 1.0
 */
public class ManuSortingTest {

    private static final int TIMEOUT = 200;
    private static final int LONG_TIMEOUT = 20000;
    private DataStructure[] dataStructures;
    private DataStructure[] dataStructuresByName;
    Random rand = new Random();
    private DataStructure[] dataStructuresByNameReverse;
    private ComparatorPlus<DataStructure> comp;

    @Before
    public void setUp() {
        dataStructures = new DataStructure[21];
        dataStructures[0] = new DataStructure("Singly Linked List");
        dataStructures[1] = new DataStructure("Doubly Linked List");
        dataStructures[2] = new DataStructure("Array");
        dataStructures[3] = new DataStructure("Binary Search Tree");
        dataStructures[4] = new DataStructure("AVL Tree");
        dataStructures[5] = new DataStructure("HashMap");
        dataStructures[6] = new DataStructure("Graph");
        dataStructures[7] = new DataStructure("SkipList");
        dataStructures[8] = new DataStructure("Circular Singly Linked List");
        dataStructures[9] = new DataStructure("Queue");
        dataStructures[10] = new DataStructure("Stack");
        dataStructures[11] = new DataStructure("Deque");
        dataStructures[12] = new DataStructure("ArrayList");
        dataStructures[13] = new DataStructure("2-4 Tree");
        dataStructures[14] = new DataStructure("Red Black Tree");
        dataStructures[15] = new DataStructure("Splay Tree");
        dataStructures[16] = new DataStructure("Trie");
        dataStructures[17] = new DataStructure("Segment Tree");
        dataStructures[18] = new DataStructure("B Tree");
        dataStructures[19] = new DataStructure("Set");
        dataStructures[20] = new DataStructure("Trie");


        dataStructuresByName = new DataStructure[21];
        dataStructuresByName[0] = dataStructures[13];
        dataStructuresByName[1] = dataStructures[4];
        dataStructuresByName[2] = dataStructures[2];
        dataStructuresByName[3] = dataStructures[12];
        dataStructuresByName[4] = dataStructures[18];
        dataStructuresByName[5] = dataStructures[3];
        dataStructuresByName[6] = dataStructures[8];
        dataStructuresByName[7] = dataStructures[11];
        dataStructuresByName[8] = dataStructures[1];
        dataStructuresByName[9] = dataStructures[6];
        dataStructuresByName[10] = dataStructures[5];
        dataStructuresByName[11] = dataStructures[9];
        dataStructuresByName[12] = dataStructures[14];
        dataStructuresByName[13] = dataStructures[17];
        dataStructuresByName[14] = dataStructures[19];
        dataStructuresByName[15] = dataStructures[0];
        dataStructuresByName[16] = dataStructures[7];
        dataStructuresByName[17] = dataStructures[15];
        dataStructuresByName[18] = dataStructures[10];
        dataStructuresByName[19] = dataStructures[16];
        dataStructuresByName[20] = dataStructures[20];

        dataStructuresByNameReverse = generateReverseArray(dataStructuresByName);

        comp = DataStructure.getNameComparator();

    }

    private DataStructure[] generateReverseArray(DataStructure[] arr) {
        DataStructure[] reverseArr = new DataStructure[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            reverseArr[i] = arr[arr.length - 1 - i];
        }
        return reverseArr;
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortAdaptivity() {
        //optimal insertion sort uses n - 1 comparisons for sorted arrays
        Sorting.insertionSort(dataStructuresByName, comp);
        assertArrayEquals(dataStructuresByName, dataStructuresByName);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 20 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortEfficiency() {
        //optimal insertion sort uses (n - 1) * n / 2 comparisons for reverse sorted arrays
        Sorting.insertionSort(dataStructuresByNameReverse, comp);
        assertArrayEquals(dataStructuresByName, dataStructuresByNameReverse);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 20 * 21 / 2 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortWorks() {
        Sorting.insertionSort(dataStructures, comp);
        assertArrayEquals(dataStructures, dataStructuresByName);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortStability() {
        DataStructure firstTrie = dataStructures[16];
        DataStructure secondTrie = dataStructures[20];
        Sorting.insertionSort(dataStructures, comp);
        assertArrayEquals(dataStructuresByName, dataStructures);

        assertSame(dataStructuresByName[20], secondTrie);
        assertSame(dataStructuresByName[19], firstTrie);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertionSortNullArray() {
        Sorting.insertionSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertionSortNullComparator() {
        Sorting.insertionSort(dataStructures, null);
    }

    private String generateRandomString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

    @Test(timeout=LONG_TIMEOUT)
    public void testInsertionSortRandomized() {
        int numTests = 100;
        int size = 100;

        DataStructure[] temp;
        DataStructure[] other;
        for (int i = 0; i < numTests; ++i) {
            temp = new DataStructure[size];
            other = new DataStructure[size];

            for (int j = 0; j < size; ++j) {
                temp[j] = new DataStructure(generateRandomString());
                other[j] = temp[j];
            }



            Arrays.sort(other, comp);
            Sorting.insertionSort(temp, comp);
            assertArrayEquals(temp, other);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortWorks() {
        Sorting.cocktailSort(dataStructures, comp);
        assertArrayEquals(dataStructures, dataStructuresByName);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortAdaptivity() {
        //optimal cocktail sort uses n - 1 comparisons for sorted arrays
        Sorting.cocktailSort(dataStructuresByName, comp);
        assertArrayEquals(dataStructuresByName, dataStructuresByName);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 20 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortEfficiency() {
        //optimal insertion sort uses (n - 1) * n / 2 comparisons for reverse sorted arrays
        Sorting.cocktailSort(dataStructuresByNameReverse, comp);
        assertArrayEquals(dataStructuresByName, dataStructuresByNameReverse);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 20 * 21 / 2 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortStability() {
        DataStructure firstTrie = dataStructures[16];
        DataStructure secondTrie = dataStructures[20];
        Sorting.cocktailSort(dataStructures, comp);
        assertArrayEquals(dataStructuresByName, dataStructures);

        assertSame(dataStructuresByName[20], secondTrie);
        assertSame(dataStructuresByName[19], firstTrie);
    }

    @Test(timeout=LONG_TIMEOUT)
    public void testCocktailSortRandomized() {
        int numTests = 100;
        int size = 100;

        DataStructure[] temp;
        DataStructure[] other;
        for (int i = 0; i < numTests; ++i) {
            temp = new DataStructure[size];
            other = new DataStructure[size];

            for (int j = 0; j < size; ++j) {
                temp[j] = new DataStructure(generateRandomString());
                other[j] = temp[j];
            }

            Arrays.sort(other, comp);
            Sorting.cocktailSort(temp, comp);
            assertArrayEquals(temp, other);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCocktailSortNullArray() {
        Sorting.cocktailSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCocktailSortNullComparator() {
        Sorting.cocktailSort(dataStructures, null);
    }


    @Test(timeout = TIMEOUT)
    public void testMergeSortWorks() {
        Sorting.mergeSort(dataStructures, comp);
        assertArrayEquals(dataStructures, dataStructuresByName);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortEfficiencyRandomized() {
        /**
         * Constructing the worst case for this isn't easy,
         * so for now I performed a random test on tons of
         * difference possible arrays and made sure
         * they do less than n * log(n) comparisons.
         *
         * Mathematically proven that merge sort won't exceed
         * nlogn comparisons, in fact there is an explicit formula
         * in the worst case.
         *
         */

        int numTests = 100;
        int size = 100;
        ComparatorPlus<DataStructure> comp2 = DataStructure.getNameComparator();
        DataStructure[] temp;
        DataStructure[] other;
        for (int i = 0; i < numTests; ++i) {
            comp.setCount(0);
            comp2.setCount(0);
            temp = new DataStructure[size];
            other = new DataStructure[size];

            for (int j = 0; j < size; ++j) {
                temp[j] = new DataStructure(generateRandomString());
                other[j] = temp[j];
            }


            Arrays.sort(other, comp2);
            Sorting.mergeSort(temp, comp);
            assertArrayEquals(temp, other);

            assertTrue("Number of comparisons: " + comp.getCount(),
                    comp.getCount() <= 100 * Math.log(100) / Math.log(2) && comp.getCount() != 0);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortStability() {
        DataStructure firstTrie = dataStructures[16];
        DataStructure secondTrie = dataStructures[20];
        Sorting.mergeSort(dataStructures, comp);
        assertArrayEquals(dataStructuresByName, dataStructures);

        assertSame(dataStructuresByName[20], secondTrie);
        assertSame(dataStructuresByName[19], firstTrie);
    }

    @Test(timeout=LONG_TIMEOUT)
    public void testMergeSortRandomized() {
        int numTests = 100;
        int size = 100;

        DataStructure[] temp;
        DataStructure[] other;
        for (int i = 0; i < numTests; ++i) {
            temp = new DataStructure[size];
            other = new DataStructure[size];

            for (int j = 0; j < size; ++j) {
                temp[j] = new DataStructure(generateRandomString());
                other[j] = temp[j];
            }

            Arrays.sort(other, comp);
            Sorting.mergeSort(temp, comp);
            assertArrayEquals(temp, other);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMergeSortNullArray() {
        Sorting.mergeSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMergeSortNullComparator() {
        Sorting.mergeSort(dataStructures, null);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSortWorks() {
        Sorting.quickSort(dataStructures, comp, rand);
        assertArrayEquals(dataStructures, dataStructuresByName);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSortEfficiencyRandomized() {
        /**
         * Constructing the worst case requires taking
         * the max or min pivot of the array at all times.
         *
         * I didn't want to construct a worst case like this
         * so I ran a bunch of random tests.
         *
         * Worst Case basically degenerates into selection sort
         * and takes n * (n-1) /2 comparisons.
         *
         */

        int numTests = 100;
        int size = 100;
        ComparatorPlus<DataStructure> comp2 = DataStructure.getNameComparator();
        DataStructure[] temp;
        DataStructure[] other;
        for (int i = 0; i < numTests; ++i) {
            comp.setCount(0);
            comp2.setCount(0);
            temp = new DataStructure[size];
            other = new DataStructure[size];

            for (int j = 0; j < size; ++j) {
                temp[j] = new DataStructure(generateRandomString());
                other[j] = temp[j];
            }


            Arrays.sort(other, comp2);
            Sorting.quickSort(temp, comp, rand);
            assertArrayEquals(temp, other);

            assertTrue("Number of comparisons: " + comp.getCount(),
                    comp.getCount() <= 100 * 99 /2  && comp.getCount() != 0);
        }
    }

    @Test(timeout=LONG_TIMEOUT)
    public void testQuickSortRandomized() {
        int numTests = 100;
        int size = 100;

        DataStructure[] temp;
        DataStructure[] other;
        for (int i = 0; i < numTests; ++i) {
            temp = new DataStructure[size];
            other = new DataStructure[size];

            for (int j = 0; j < size; ++j) {
                temp[j] = new DataStructure(generateRandomString());
                other[j] = temp[j];
            }

            Arrays.sort(other, comp);
            Sorting.quickSort(temp, comp, rand);
            assertArrayEquals(temp, other);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuickSortNullArray() {
        Sorting.quickSort(null, comp, rand);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuickSortNullComparator() {
        Sorting.quickSort(dataStructures, null, rand);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuickSortNullRandom() {
        Sorting.quickSort(dataStructures, comp, null);
    }

    @Test(timeout=LONG_TIMEOUT)
    public void testLSDRadixSortRandomized() {
        int numTests = 100;
        int size = 100;

        int[] temp;
        int[] other;
        for (int i = 0; i < numTests; ++i) {
            temp = new int[size];
            other = new int[size];


            for (int j = 0; j < size; ++j) {
                temp[j] = rand.nextInt(10000);
                other[j] = temp[j];
            }

            Arrays.sort(other);
            Sorting.lsdRadixSort(temp);
            assertArrayEquals(temp, other);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLSDRadixSortNullArray() {
        Sorting.lsdRadixSort(null);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortWorks() {
        List<Integer> q = new ArrayList<>();
        q.add(1); q.add(2); q.add(0); q.add(-1);
        q.add(3); q.add(-2); q.add(8);

        int[] copy = new int[q.size()];

        for (int i = 0; i < q.size(); ++i) {
            copy[i] = q.get(i);
        }
        Arrays.sort(copy);

        int[] o = Sorting.heapSort(q);
        assertArrayEquals(o, copy);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortRandomized() {
        int numTests = 100;
        int size = 100;

        List<Integer> temp;
        int[] other;
        for (int i = 0; i < numTests; ++i) {
            temp = new ArrayList<>();
            other = new int[size];


            for (int j = 0; j < size; ++j) {
                temp.add(rand.nextInt(10000));
                other[j] = temp.get(j);
            }

            Arrays.sort(other);
            int[] o = Sorting.heapSort(temp);
            assertArrayEquals(o, other);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHeapSortNullList() {
        Sorting.heapSort(null);
    }












    private static class DataStructure {
        private String name;

        public DataStructure (String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public static ComparatorPlus<DataStructure> getNameComparator() {
            return new ComparatorPlus<DataStructure>() {
                @Override
                public int compare(DataStructure struct1,
                                   DataStructure struct2) {
                    incrementCount();
                    return struct1.getName().compareTo(struct2.getName());
                }
            };
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            return other instanceof DataStructure
                    && ((DataStructure) other).name.equals(this.name);
        }
    }

    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }
        public void incrementCount() {
            count++;
        }
    }
}