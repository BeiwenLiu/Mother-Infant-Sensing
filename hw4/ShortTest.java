import org.junit.Before;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Simple test cases for heaps and priority queues.
 * Write your own tests to ensure you cover all edge cases.
 *
 * @author JUNGHYUN KIM
 * @version 1.0
 */
public class ShortTest {

    private static final int TIMEOUT = 200;
    private HeapInterface<Integer> minHeap;
    private PriorityQueueInterface<Integer> minPriorityQueue;

    @Before
    public void setUp() {
        minHeap = new MinHeap<>();
        minPriorityQueue = new MinPriorityQueue<>();
    }

    @Test(timeout = 1000)
    public void testHeapSort() {
        Integer[] arr = new Integer[300001];
        Integer[] arr2 = new Integer[300001];
        for(int i=1;i<=300000;i++) {
            arr[i] = (int)(Math.random() * 100000);
            arr2[i] = arr[i];
        }
        Arrays.sort(arr2, 1, 300001);

        for(int i=1;i<=300000;i++) {
            minPriorityQueue.enqueue(arr[i]);
        }
        for(int i=1;i<=300000;i++) {
            arr[i] = minPriorityQueue.dequeue();
        }
        for(int i=1;i<=300000;i++) {
            assertEquals(arr2[i], arr[i]);

        }
    }

    @Test(timeout = 1000)
    public void testHeapSort2() {
        Integer[] arr = new Integer[1000001];
        Integer[] arr2 = new Integer[1000001];
        for(int i=1;i<=1000000;i++) {
            arr[i] = (int)(Math.random() * 10000000);
            arr2[i] = arr[i];
        }
        Arrays.sort(arr2,1,1000001);

        for(int i=1;i<=1000000;i++) {
            minPriorityQueue.enqueue(arr[i]);
        }
        for(int i=1;i<=1000000;i++) {
            arr[i] = minPriorityQueue.dequeue();
        }
        for(int i=1;i<=1000000;i++) {
            assertEquals(arr2[i], arr[i]);

        }
    }
}