import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Mohamed Ghanem
 * @version 1.0
 * @userid mghanem8
 * @GTID 903533880
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort a null array or with null comparator.");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int k = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[k], arr[j]) > 0) {
                    k = j;
                }
            }
            T temp = arr[k];
            arr[k] = arr[i];
            arr[i] = temp;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort a null array or with null comparator.");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j], arr[j - 1]) < 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort a null array or with null comparator.");
        }
        int stopIndex = arr.length - 1;
        while (stopIndex != 0) {
            int i = 0;
            int swapIndex = 0;
            while (i < stopIndex) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = temp;
                    swapIndex = i;
                }
                i++;
            }
            stopIndex = swapIndex;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort a null array or with null comparator.");
        }
        if (arr.length <= 1) {
            return;
        }
        int midIndex = (arr.length) / 2;
        T[] leftArray = (T[]) new Object[midIndex];
        T[] rightArray = (T[]) new Object[arr.length - midIndex];
        for (int i = 0; i < midIndex; i++) {
            leftArray[i] = arr[i];
            rightArray[i] = arr[i + midIndex];
        }
        if (arr.length % 2 != 0) {
            rightArray[rightArray.length - 1] = arr[arr.length - 1];
        }
        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);
        int i = 0;
        int j = 0;
        while (i < leftArray.length && j < rightArray.length)  {
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                arr[i + j] = leftArray[i];
                i++;
            } else {
                arr[i + j] = rightArray[j];
                j++;
            }
        }
        while (i < leftArray.length) {
            arr[i + j] = leftArray[i];
            i++;
        }
        while (j < rightArray.length) {
            arr[i + j] = rightArray[j];
            j++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort a null array.");
        }
        boolean cont = true;
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        int divide = 1;
        while (cont) {
            cont = false;
            for (int num: arr) {
                if (num / divide / 10 != 0) {
                    cont = true;
                }
                int index = (num / divide) % 10 + 9;
                if (buckets[index] == null) {
                    buckets[index] = new LinkedList<Integer>();
                }
                buckets[index].add(num);
            }
            int index = 0;
            for (LinkedList<Integer> bucket: buckets) {
                if (bucket != null) {
                    for (int num: bucket) {
                        arr[index] = num;
                        index++;
                    }
                    bucket.clear();
                }
            }
            divide *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot sort a null data.");
        }
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>(data);
        int[] arr = new int[data.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = heap.remove();
        }
        return arr;
    }
}