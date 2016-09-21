import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
  * Simple test cases for heaps and priority queues.
  * Write your own tests to ensure you cover all edge cases.
  *
  * @author CS 1332 TAs
  * @version 1.0
  */
public class HeapPQStudentTests {

    private static final int TIMEOUT = 200;
    private HeapInterface<Integer> minHeap;
    private PriorityQueueInterface<Integer> minPriorityQueue;

    @Before
    public void setUp() {
        minHeap = new MinHeap<>();
        minPriorityQueue = new MinPriorityQueue<>();
    }

    @Test(timeout = TIMEOUT)
    public void testHeap() {
        minHeap.add(10);
        minHeap.add(9);
        minHeap.add(1);
        minHeap.add(8);
        minHeap.add(5);

        Integer[] expected = new Integer[15];
        expected[1] = 1;
        expected[2] = 5;
        expected[3] = 9;
        expected[4] = 10;
        expected[5] = 8;
        assertArrayEquals(expected,
                ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals(new Integer(1), minHeap.remove());
        assertEquals(new Integer(5), minHeap.remove());
        assertEquals(3, minHeap.size());
        assertFalse(minHeap.isEmpty());
        assertEquals(new Integer(8), minHeap.remove());
        assertEquals(new Integer(9), minHeap.remove());
        assertEquals(new Integer(10), minHeap.remove());
        assertTrue(minHeap.isEmpty());
        Integer[] finalExpected = new Integer[15];
        assertArrayEquals(finalExpected,
                ((MinHeap<Integer>) minHeap).getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testPriorityQueue() {
        minPriorityQueue.enqueue(10);
        minPriorityQueue.enqueue(9);
        minPriorityQueue.enqueue(1);
        minPriorityQueue.enqueue(8);
        minPriorityQueue.enqueue(5);

        assertEquals(new Integer(1), minPriorityQueue.dequeue());
        assertEquals(new Integer(5), minPriorityQueue.dequeue());
        assertEquals(3, minPriorityQueue.size());
        assertFalse(minPriorityQueue.isEmpty());
        assertEquals(new Integer(8), minPriorityQueue.dequeue());
        assertEquals(new Integer(9), minPriorityQueue.dequeue());
        assertEquals(new Integer(10), minPriorityQueue.dequeue());
        assertTrue(minPriorityQueue.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testResize() {
        for (int i = 0; i < 14; i++) {
            minHeap.add(i * i * i - 3);
        }

        assertEquals(14, minHeap.size());
        assertEquals(15, ((MinHeap<Integer>) minHeap).getBackingArray().length);

        minHeap.add(14 * 14 * 14 - 3);

        assertEquals(15, minHeap.size());
        assertEquals(30, ((MinHeap<Integer>) minHeap).getBackingArray().length);
    }
}
