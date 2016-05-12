import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.NoSuchElementException;

public class PaulJUnit4 {

    private static final int TIMEOUT = 200;
    private MinHeap<Integer> minHeap;
    private MinPriorityQueue<Integer> minQ;

    @Before
    public void setUp() {
        minHeap = new MinHeap<>();
        minQ = new MinPriorityQueue<>();
    }

    /********** TEST HEAP **********/

    // test heap constructor

    @Test(timeout = TIMEOUT)
    public void constructor1() {
        // checks that size = 0
        assertEquals(0, minHeap.size());
        // checks that backingArray is an Interger array with capacity
        // STARTING_SIZE
        assertArrayEquals(minHeap.getBackingArray(),
                new Integer[HeapInterface.STARTING_SIZE]);
    }

    // test add

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addException() {
        minHeap.add(null);
    }

    @Test(timeout = TIMEOUT)
    public void add1() {
        minHeap.add(1);

        assertEquals(1, minHeap.size());
        assertEquals(1, minHeap.getBackingArray()[1]);
    }

    @Test(timeout = TIMEOUT)
    public void add2() {
        minHeap.add(1);
        minHeap.add(2);

        assertEquals(2, minHeap.size());
        assertEquals(1, minHeap.getBackingArray()[1]);
        assertEquals(2, minHeap.getBackingArray()[2]);
    }

    @Test(timeout = TIMEOUT)
    public void add3() {
        minHeap.add(1);
        minHeap.add(2);
        minHeap.add(3);


        assertEquals(3, minHeap.size());
        assertEquals(1, minHeap.getBackingArray()[1]);
        assertEquals(2, minHeap.getBackingArray()[2]);
        assertEquals(3, minHeap.getBackingArray()[3]);
    }

    @Test(timeout = TIMEOUT)
    public void add4() {
        minHeap.add(1);
        minHeap.add(2);
        minHeap.add(3);
        minHeap.add(4);
        minHeap.add(5);
        minHeap.add(6);
        minHeap.add(7);
        minHeap.add(8);
        minHeap.add(9);
        minHeap.add(10);
        minHeap.add(11);
        minHeap.add(12);
        minHeap.add(13);
        minHeap.add(14);

        assertEquals(14, minHeap.size());

        assertEquals(1, minHeap.getBackingArray()[1]);
        assertEquals(2, minHeap.getBackingArray()[2]);
        assertEquals(3, minHeap.getBackingArray()[3]);
        assertEquals(4, minHeap.getBackingArray()[4]);
        assertEquals(5, minHeap.getBackingArray()[5]);
        assertEquals(6, minHeap.getBackingArray()[6]);
        assertEquals(7, minHeap.getBackingArray()[7]);
        assertEquals(8, minHeap.getBackingArray()[8]);
        assertEquals(9, minHeap.getBackingArray()[9]);
        assertEquals(10, minHeap.getBackingArray()[10]);
        assertEquals(11, minHeap.getBackingArray()[11]);
        assertEquals(12, minHeap.getBackingArray()[12]);
        assertEquals(13, minHeap.getBackingArray()[13]);
        assertEquals(14, minHeap.getBackingArray()[14]);
    }

    // test adding that involves swapping nodes in heap

    @Test(timeout = TIMEOUT)
    public void addSwap1() {
        minHeap.add(2);
        minHeap.add(1);

        assertEquals(2, minHeap.size());
        assertEquals(1, minHeap.getBackingArray()[1]);
        assertEquals(2, minHeap.getBackingArray()[2]);
    }

    @Test(timeout = TIMEOUT)
    public void addSwap2() {
        minHeap.add(3);
        minHeap.add(2);
        minHeap.add(1);

        assertEquals(3, minHeap.size());
        assertEquals(1, minHeap.getBackingArray()[1]);
        assertEquals(3, minHeap.getBackingArray()[2]);
        assertEquals(2, minHeap.getBackingArray()[3]);
    }

    @Test(timeout = TIMEOUT)
    public void addSwap3() {
        minHeap.add(7);
        minHeap.add(6);
        minHeap.add(5);
        minHeap.add(4);
        minHeap.add(3);
        minHeap.add(2);
        minHeap.add(1);

        assertEquals(7, minHeap.size());
        assertEquals(1, minHeap.getBackingArray()[1]);
        assertEquals(4, minHeap.getBackingArray()[2]);
        assertEquals(2, minHeap.getBackingArray()[3]);
        assertEquals(7, minHeap.getBackingArray()[4]);
        assertEquals(5, minHeap.getBackingArray()[5]);
        assertEquals(6, minHeap.getBackingArray()[6]);
        assertEquals(3, minHeap.getBackingArray()[7]);
    }

    @Test(timeout = TIMEOUT)
    public void addSwap4() {
        minHeap.add(14);
        minHeap.add(13);
        minHeap.add(12);
        minHeap.add(11);
        minHeap.add(10);
        minHeap.add(9);
        minHeap.add(8);
        minHeap.add(7);
        minHeap.add(6);
        minHeap.add(5);
        minHeap.add(4);
        minHeap.add(3);
        minHeap.add(2);
        minHeap.add(1);

        assertEquals(14, minHeap.size());
        assertEquals(1, minHeap.getBackingArray()[1]);
        assertEquals(5, minHeap.getBackingArray()[2]);
        assertEquals(2, minHeap.getBackingArray()[3]);
        assertEquals(8, minHeap.getBackingArray()[4]);
        assertEquals(6, minHeap.getBackingArray()[5]);
        assertEquals(4, minHeap.getBackingArray()[6]);
        assertEquals(3, minHeap.getBackingArray()[7]);
        assertEquals(14, minHeap.getBackingArray()[8]);
        assertEquals(11, minHeap.getBackingArray()[9]);
        assertEquals(12, minHeap.getBackingArray()[10]);
        assertEquals(7, minHeap.getBackingArray()[11]);
        assertEquals(13, minHeap.getBackingArray()[12]);
        assertEquals(9, minHeap.getBackingArray()[13]);
        assertEquals(10, minHeap.getBackingArray()[14]);
    }

    // testing add that requires doubly of array capacity

    @Test(timeout = TIMEOUT)
    public void addDoubleCapacity1() {
        minHeap.add(1);
        minHeap.add(2);
        minHeap.add(3);
        minHeap.add(4);
        minHeap.add(5);
        minHeap.add(6);
        minHeap.add(7);
        minHeap.add(8);
        minHeap.add(9);
        minHeap.add(10);
        minHeap.add(11);
        minHeap.add(12);
        minHeap.add(13);
        minHeap.add(14);

        assertEquals(14, minHeap.size());
        assertEquals(1, minHeap.getBackingArray()[1]);
        assertEquals(2, minHeap.getBackingArray()[2]);
        assertEquals(3, minHeap.getBackingArray()[3]);
        assertEquals(4, minHeap.getBackingArray()[4]);
        assertEquals(5, minHeap.getBackingArray()[5]);
        assertEquals(6, minHeap.getBackingArray()[6]);
        assertEquals(7, minHeap.getBackingArray()[7]);
        assertEquals(8, minHeap.getBackingArray()[8]);
        assertEquals(9, minHeap.getBackingArray()[9]);
        assertEquals(10, minHeap.getBackingArray()[10]);
        assertEquals(11, minHeap.getBackingArray()[11]);
        assertEquals(12, minHeap.getBackingArray()[12]);
        assertEquals(13, minHeap.getBackingArray()[13]);
        assertEquals(14, minHeap.getBackingArray()[14]);
        // checking that backingArray did not double it's size when it contains
        // 14 elements
        assertEquals(15, minHeap.getBackingArray().length);
    }

    @Test(timeout = TIMEOUT)
    public void addDoubleCapacity2() {
        minHeap.add(1);
        minHeap.add(2);
        minHeap.add(3);
        minHeap.add(4);
        minHeap.add(5);
        minHeap.add(6);
        minHeap.add(7);
        minHeap.add(8);
        minHeap.add(9);
        minHeap.add(10);
        minHeap.add(11);
        minHeap.add(12);
        minHeap.add(13);
        minHeap.add(14);
        minHeap.add(15);

        assertEquals(15, minHeap.size());
        assertEquals(1, minHeap.getBackingArray()[1]);
        assertEquals(2, minHeap.getBackingArray()[2]);
        assertEquals(3, minHeap.getBackingArray()[3]);
        assertEquals(4, minHeap.getBackingArray()[4]);
        assertEquals(5, minHeap.getBackingArray()[5]);
        assertEquals(6, minHeap.getBackingArray()[6]);
        assertEquals(7, minHeap.getBackingArray()[7]);
        assertEquals(8, minHeap.getBackingArray()[8]);
        assertEquals(9, minHeap.getBackingArray()[9]);
        assertEquals(10, minHeap.getBackingArray()[10]);
        assertEquals(11, minHeap.getBackingArray()[11]);
        assertEquals(12, minHeap.getBackingArray()[12]);
        assertEquals(13, minHeap.getBackingArray()[13]);
        assertEquals(14, minHeap.getBackingArray()[14]);
        assertEquals(15, minHeap.getBackingArray()[15]);
        // checking that array doubled it size to accommodate 15th element,
        // which is located at index 15 (original array only has
        // indexes 0 - 14)
        assertEquals(30, minHeap.getBackingArray().length);
    }

    @Test(timeout = TIMEOUT)
    public void addDoubleCapacity3() {
        // add values 1 through 30 to minHeap from index 1 through index 30
        for (int i = 1; i <= 30; i++) {
            minHeap.add(i);
        }
        // check that index 1 holds 1, index 2 holds 2, index 3 holds 3, etc
        for (int i = 1; i <= 30; i++) {
            assertEquals(i, minHeap.getBackingArray()[i]);
        }
        // check that array size has doubled from 30 to 60
        assertEquals(60, minHeap.getBackingArray().length);
    }

    @Test(timeout = TIMEOUT)
    public void addDoubleCapacity4() {
        for (int i = 1; i <= 60; i++) {
            minHeap.add(i);
        }

        for (int i = 1; i <= 60; i++) {
            assertEquals(i, minHeap.getBackingArray()[i]);
        }
        assertEquals(120, minHeap.getBackingArray().length);
    }

    @Test(timeout = TIMEOUT)
    public void addDoubleCapacity5() {
        for (int i = 1; i <= 120; i++) {
            minHeap.add(i);
        }

        for (int i = 1; i <= 120; i++) {
            assertEquals(i, minHeap.getBackingArray()[i]);
        }
        assertEquals(240, minHeap.getBackingArray().length);
    }

    @Test(timeout = TIMEOUT)
    public void addDoubleCapacity6() {
        for (int i = 1; i <= 240; i++) {
            minHeap.add(i);
        }

        for (int i = 1; i <= 240; i++) {
            assertEquals(i, minHeap.getBackingArray()[i]);
        }
        assertEquals(480, minHeap.getBackingArray().length);
    }

    // remove

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeException() {
        minHeap.remove();
    }

    @Test(timeout = TIMEOUT)
    public void remove1() {
        minHeap.add(1);
        // remove top element from list and check that it has a value of 1
        assertEquals((Integer)1, minHeap.remove());
        // after removing element, check if indexes that are supposed to be
        // empty are actually empty
        for (int i = 1; i <= 14; i++) {
            assertNull(minHeap.getBackingArray()[i]);
        }
        assertEquals(0, minHeap.size());
    }

    @Test(timeout = TIMEOUT)
    public void remove2() {
        minHeap.add(1);
        minHeap.add(2);
        assertEquals((Integer)1, minHeap.remove());

        assertEquals(2, minHeap.getBackingArray()[1]);
        for (int i = 2; i <= 14; i++) {
            assertNull(minHeap.getBackingArray()[i]);
        }
        assertEquals(1, minHeap.size());
    }

    @Test(timeout = TIMEOUT)
    public void remove3() {
        minHeap.add(1);
        minHeap.add(2);
        minHeap.add(3);
        assertEquals((Integer)1, minHeap.remove());

        assertEquals(2, minHeap.getBackingArray()[1]);
        assertEquals(3, minHeap.getBackingArray()[2]);
        for (int i = 3; i <= 14; i++) {
            assertNull(minHeap.getBackingArray()[i]);
        }
        assertEquals(2, minHeap.size());
    }

    @Test(timeout = TIMEOUT)
    public void remove4() {
        minHeap.add(1);
        minHeap.add(2);
        minHeap.add(3);
        minHeap.add(4);
        minHeap.add(5);
        minHeap.add(6);
        minHeap.add(7);
        assertEquals((Integer)1, minHeap.remove());

        assertEquals(2, minHeap.getBackingArray()[1]);
        assertEquals(4, minHeap.getBackingArray()[2]);
        assertEquals(3, minHeap.getBackingArray()[3]);
        assertEquals(7, minHeap.getBackingArray()[4]);
        assertEquals(5, minHeap.getBackingArray()[5]);
        assertEquals(6, minHeap.getBackingArray()[6]);
        for (int i = 7; i <= 14; i++) {
            assertNull(minHeap.getBackingArray()[i]);
        }
        assertEquals(6, minHeap.size());
    }

    @Test(timeout = TIMEOUT)
    public void remove5() {
        minHeap.add(13);
        minHeap.add(12);
        minHeap.add(11);
        minHeap.add(10);
        minHeap.add(9);
        minHeap.add(8);
        minHeap.add(7);
        minHeap.add(6);
        minHeap.add(5);
        minHeap.add(4);
        minHeap.add(3);
        minHeap.add(2);
        minHeap.add(1);
        assertEquals((Integer)1, minHeap.remove());

        assertEquals(2, minHeap.getBackingArray()[1]);
        assertEquals(4, minHeap.getBackingArray()[2]);
        assertEquals(3, minHeap.getBackingArray()[3]);
        assertEquals(7, minHeap.getBackingArray()[4]);
        assertEquals(5, minHeap.getBackingArray()[5]);
        assertEquals(8, minHeap.getBackingArray()[6]);
        assertEquals(9, minHeap.getBackingArray()[7]);
        assertEquals(13, minHeap.getBackingArray()[8]);
        assertEquals(10, minHeap.getBackingArray()[9]);
        assertEquals(11, minHeap.getBackingArray()[10]);
        assertEquals(6, minHeap.getBackingArray()[11]);
        assertEquals(12, minHeap.getBackingArray()[12]);
    }

    @Test(timeout = TIMEOUT)
    public void remove6() {
        minHeap.add(14);
        minHeap.add(13);
        minHeap.add(12);
        minHeap.add(11);
        minHeap.add(10);
        minHeap.add(9);
        minHeap.add(8);
        minHeap.add(7);
        minHeap.add(6);
        minHeap.add(5);
        minHeap.add(4);
        minHeap.add(3);
        minHeap.add(2);
        minHeap.add(1);
        assertEquals((Integer)1, minHeap.remove());

        assertEquals(2, minHeap.getBackingArray()[1]);
        assertEquals(5, minHeap.getBackingArray()[2]);
        assertEquals(3, minHeap.getBackingArray()[3]);
        assertEquals(8, minHeap.getBackingArray()[4]);
        assertEquals(6, minHeap.getBackingArray()[5]);
        assertEquals(4, minHeap.getBackingArray()[6]);
        assertEquals(10, minHeap.getBackingArray()[7]);
        assertEquals(14, minHeap.getBackingArray()[8]);
        assertEquals(11, minHeap.getBackingArray()[9]);
        assertEquals(12, minHeap.getBackingArray()[10]);
        assertEquals(7, minHeap.getBackingArray()[11]);
        assertEquals(13, minHeap.getBackingArray()[12]);
        assertEquals(9, minHeap.getBackingArray()[13]);
    }

    @Test(timeout = TIMEOUT)
    public void remove7() {
        // add values 29 through 1 to minHeap in descending order
        for (int i = 29; i >= 1; i--) {
            minHeap.add(i);
        }
        // remove node with smallest value (node with value 1 in this case)
        assertEquals((Integer)1, minHeap.remove());

        // after removing the smallest node, minHeap should have the
        // same sequence of numbers as this new Integer array, a
        Integer[] a = new Integer[]{
                null, 2, 8, 3, 13, 9, 5, 4, 20, 14, 12, 10, 16, 6, 17,
                18, 29, 23, 26, 15, 27, 21, 22, 11, 28, 19, 24, 7, 25,
                null
        };
        assertArrayEquals(minHeap.getBackingArray(), a);
    }

    // testing isEmpty

    @Test(timeout = TIMEOUT)
    public void isEmpty1() {
        assertTrue(minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void isEmpty2() {
        minHeap.add(1);

        assertFalse(minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void isEmpty3() {
        minHeap.add(1);
        minHeap.remove();

        assertTrue(minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void isEmpty4() {
        minHeap.add(1);
        minHeap.add(2);
        minHeap.add(3);
        minHeap.add(4);

        assertFalse(minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void isEmpty5() {
        minHeap.add(1);
        minHeap.add(2);
        minHeap.add(3);
        minHeap.add(4);
        minHeap.remove();

        assertFalse(minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void isEmpty6() {
        minHeap.add(1);
        minHeap.add(2);
        minHeap.add(3);
        minHeap.add(4);
        minHeap.remove();
        minHeap.remove();
        minHeap.remove();
        minHeap.remove();

        assertTrue(minHeap.isEmpty());
    }

    // testing size

    @Test(timeout = TIMEOUT)
    public void size1() {
        assertEquals(0, minHeap.size());
    }

    @Test(timeout = TIMEOUT)
    public void size2() {
        minHeap.add(1);
        assertEquals(1, minHeap.size());
    }

    @Test(timeout = TIMEOUT)
    public void size3() {
        minHeap.add(1);
        minHeap.remove();
        assertEquals(0, minHeap.size());
    }

    @Test(timeout = TIMEOUT)
    public void size4() {
        for (int i = 1; i <= 50; i++) {
            minHeap.add(i);
        }
        assertEquals(50, minHeap.size());
    }

    @Test(timeout = TIMEOUT)
    public void size5() {
        for (int i = 1; i <= 50; i++) {
            minHeap.add(i);
        }
        for (int i = 1; i <= 25; i++) {
            minHeap.remove();
        }
        assertEquals(25, minHeap.size());
    }

    @Test(timeout = TIMEOUT)
    public void size6() {
        for (int i = 1; i <= 50; i++) {
            minHeap.add(i);
        }
        for (int i = 1; i <= 50; i++) {
            minHeap.remove();
        }
        assertEquals(0, minHeap.size());
    }

    // testing clear

    @Test(timeout = TIMEOUT)
    public void clear1() {
        minHeap.clear();

        assertEquals(0, minHeap.size());
        assertTrue(minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void clear2() {
        minHeap.add(1);
        minHeap.clear();

        assertEquals(0, minHeap.size());
        assertTrue(minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void clear3() {
        for (int i = 1; i <= 50; i++) {
            minHeap.add(i);
        }
        minHeap.clear();

        assertEquals(0, minHeap.size());
        assertTrue(minHeap.isEmpty());
    }

    /********** TEST PRIORITY QUEUE **********/

    // test priority queue constructor

    @Test(timeout = TIMEOUT)
    public void qConstructor1() {
        assertNotNull(minQ.getBackingHeap());
    }

    // test enqueue

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void enqueueException() {
        minQ.enqueue(null);
    }

    @Test(timeout = TIMEOUT)
    public void enqueue1() {
        minQ.enqueue(5);
        // checks that index 1 of backingArray holds value of 5
        assertEquals(5, minQ.getBackingHeap().getBackingArray()[1]);
        // checks that all other indexes are null
        for (int i = 2; i<= 14; i++) {
            assertNull(minQ.getBackingHeap().getBackingArray()[i]);
        }
        // checks that size is correct
        assertEquals(1, minQ.size());
    }

    @Test(timeout = TIMEOUT)
    public void enqueue2() {
        // enqueues values 1 through 14 to minQ and checks the heap's
        // backingArray if value of 1 is at index 1, value of 2 is at index 2,
        //  value 3 is at index 3, etc...
        for (int i = 1; i <= 14; i++) {
            minQ.enqueue(i);
            assertEquals(i, minQ.getBackingHeap().getBackingArray()[i]);
        }

        assertEquals(14, minQ.size());
    }

    @Test(timeout = TIMEOUT)
    public void enqueue3() {
        for (int i = 14; i >= 1; i--) {
            minQ.enqueue(i);
        }

        assertEquals(1, minQ.getBackingHeap().getBackingArray()[1]);
        assertEquals(5, minQ.getBackingHeap().getBackingArray()[2]);
        assertEquals(2, minQ.getBackingHeap().getBackingArray()[3]);
        assertEquals(8, minQ.getBackingHeap().getBackingArray()[4]);
        assertEquals(6, minQ.getBackingHeap().getBackingArray()[5]);
        assertEquals(4, minQ.getBackingHeap().getBackingArray()[6]);
        assertEquals(3, minQ.getBackingHeap().getBackingArray()[7]);
        assertEquals(14, minQ.getBackingHeap().getBackingArray()[8]);
        assertEquals(11, minQ.getBackingHeap().getBackingArray()[9]);
        assertEquals(12, minQ.getBackingHeap().getBackingArray()[10]);
        assertEquals(7, minQ.getBackingHeap().getBackingArray()[11]);
        assertEquals(13, minQ.getBackingHeap().getBackingArray()[12]);
        assertEquals(9, minQ.getBackingHeap().getBackingArray()[13]);
        assertEquals(10, minQ.getBackingHeap().getBackingArray()[14]);
        assertEquals(14, minQ.size());
    }

    // test dequeue

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void dequeue1() {
        minQ.dequeue();
    }

    @Test(timeout = TIMEOUT)
    public void dequeue2() {
        minQ.enqueue(5);
        assertEquals((Integer)5, minQ.dequeue());
        assertEquals(0, minQ.size());
    }

    @Test(timeout = TIMEOUT)
    public void dequeue3() {
        for (int i = 14; i >= 1; i--) {
            minQ.enqueue(i);
        }
        assertEquals((Integer)1, minQ.dequeue());
        assertEquals((Integer)2, minQ.dequeue());
        assertEquals((Integer)3, minQ.dequeue());
        assertEquals((Integer)4, minQ.dequeue());
        assertEquals((Integer)5, minQ.dequeue());
        assertEquals((Integer)6, minQ.dequeue());
        assertEquals((Integer)7, minQ.dequeue());
        assertEquals((Integer)8, minQ.dequeue());
        assertEquals((Integer)9, minQ.dequeue());
        assertEquals((Integer)10, minQ.dequeue());
        assertEquals((Integer)11, minQ.dequeue());
        assertEquals((Integer)12, minQ.dequeue());
        assertEquals((Integer)13, minQ.dequeue());
        assertEquals((Integer)14, minQ.dequeue());
        assertEquals(0, minQ.size());
    }

    @Test(timeout = TIMEOUT)
    public void dequeue4() {
        for (int i = 100; i >= 1; i--) {
            minQ.enqueue(i);
        }

        for (int i = 1; i <= 100; i++) {
            assertEquals((Integer)i, minQ.dequeue());
        }
    }

    // testing queue isEmpty

    @Test(timeout = TIMEOUT)
    public void qIsEmpty1() {
        assertTrue(minQ.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void qIsEmpty2() {
        minQ.enqueue(1);

        assertFalse(minQ.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void qIsEmpty3() {
        minQ.enqueue(1);
        minQ.dequeue();

        assertTrue(minQ.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void qIsEmpty4() {
        minQ.enqueue(1);
        minQ.enqueue(2);
        minQ.enqueue(3);
        minQ.enqueue(4);

        assertFalse(minQ.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void qIsEmpty5() {
        minQ.enqueue(1);
        minQ.enqueue(2);
        minQ.enqueue(3);
        minQ.enqueue(4);
        minQ.dequeue();

        assertFalse(minQ.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void qIsEmpty6() {
        minQ.enqueue(1);
        minQ.enqueue(2);
        minQ.enqueue(3);
        minQ.enqueue(4);
        minQ.dequeue();
        minQ.dequeue();
        minQ.dequeue();
        minQ.dequeue();

        assertTrue(minQ.isEmpty());
    }

    // testing queue size

    @Test(timeout = TIMEOUT)
    public void qSize1() {
        assertEquals(0, minQ.size());
    }

    @Test(timeout = TIMEOUT)
    public void qSize2() {
        minQ.enqueue(1);
        assertEquals(1, minQ.size());
    }

    @Test(timeout = TIMEOUT)
    public void qSize3() {
        minQ.enqueue(1);
        minQ.dequeue();
        assertEquals(0, minQ.size());
    }

    @Test(timeout = TIMEOUT)
    public void qSize4() {
        for (int i = 1; i <= 50; i++) {
            minQ.enqueue(i);
        }
        assertEquals(50, minQ.size());
    }

    @Test(timeout = TIMEOUT)
    public void qSize5() {
        for (int i = 1; i <= 50; i++) {
            minQ.enqueue(i);
        }
        for (int i = 1; i <= 25; i++) {
            minQ.dequeue();
        }
        assertEquals(25, minQ.size());
    }

    @Test(timeout = TIMEOUT)
    public void qSize6() {
        for (int i = 1; i <= 50; i++) {
            minQ.enqueue(i);
        }
        for (int i = 1; i <= 50; i++) {
            minQ.dequeue();
        }
        assertEquals(0, minQ.size());
    }

    // testing clear

    @Test(timeout = TIMEOUT)
    public void qClear1() {
        minQ.clear();

        assertEquals(0, minQ.size());
        assertTrue(minQ.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void qClear2() {
        minQ.enqueue(1);
        minQ.clear();

        assertEquals(0, minQ.size());
        assertTrue(minQ.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void qClear3() {
        for (int i = 1; i <= 50; i++) {
            minQ.enqueue(i);
        }
        minQ.clear();

        assertEquals(0, minQ.size());
        assertTrue(minQ.isEmpty());
    }
}