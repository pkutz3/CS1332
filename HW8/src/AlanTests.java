import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class AlanTests {

    private static final int TIMEOUT = 200;
    private Integer[] nums;
    private final Comparator<Integer> cmp = new Comparator<>() {

        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 < o2)
                return -1;
            else if (o1 > o2)
                return 1;
            else
                return 0;
        }
    };

    @Before
    public void setUp() {
        Random rd = new Random();
        final int length = 100;
        nums = new Integer[length];

        for (int i = 0; i < length; ++i) {
            nums[i] = rd.nextInt();
        }
    }

    @Test(timeout = TIMEOUT)
    public void quicksortTest() {
        Sorting.quickSort(nums, cmp, new Random());
        assertTrue(isSorted());
    }

    @Test(timeout = TIMEOUT)
    public void mergesortTest() {
        Sorting.mergeSort(nums, cmp);
        assertTrue(isSorted());
    }

    @Test(timeout = TIMEOUT)
    public void shakersortTest() {
        Sorting.cocktailSort(nums, cmp);
        assertTrue(isSorted());
    }

    @Test(timeout = TIMEOUT)
    public void insertionsortTest() {
        Sorting.insertionSort(nums, cmp);
        boolean sorted = isSorted();
        assertTrue(sorted);

        if (!sorted) {
            for (int i = 0; i < nums.length; ++i) {
                System.out.print(nums[i] + ", ");
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void radixsortTest() {
        Random rd = new Random();
        int[] nums = new int[20];
        for (int i = 0; i < nums.length; ++i) {
            nums[i] = rd.nextInt(1000);
        }
        Sorting.lsdRadixSort(nums);

        boolean sorted = true;
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i - 1] > nums[i]) {
                sorted = false;
                break;
            }
        }
        assertTrue(sorted);
    }

    @Test(timeout = TIMEOUT)
    public void heapsortTest() {
        int[] sortedArr = Sorting.heapSort(Arrays.asList(nums));
        boolean sorted = true;
        for (int i = 1; i < sortedArr.length; ++i) {
            if (sortedArr[i - 1] > sortedArr[i])
                sorted = false;
        }
        assertTrue(sorted);
    }

    private boolean isSorted() {
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i - 1] > nums[i]) {
                return false;
            }
        }
        return true;
    }
}