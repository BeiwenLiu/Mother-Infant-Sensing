import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jianing Yang
 *
 */
public class BSTTest2 {
    private BST<Integer> bst;
    public static final int TIMEOUT = 200;

    @Before
    public void setup() {
        bst = new BST<Integer>();
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        assertEquals(0, bst.size());

        bst.add(2);
        bst.add(1);
        bst.add(3);

        assertEquals(3, bst.size());
        assertEquals(1, bst.height());

        bst.remove(2);
        assertEquals(1, bst.height());
        bst.remove(1);
        assertEquals(0, bst.height());
        bst.remove(3);
        assertEquals(-1, bst.height());

        bst.add(2);
        bst.add(1);
        bst.add(3);
        bst.add(5);
        bst.add(6);

        assertEquals(3, bst.height());
    }

    @Test(timeout = TIMEOUT)
    public void testInorderOne() {
        assertEquals(0, bst.size());

        bst.add(2);

        List<Integer> list = new ArrayList<Integer>();
        list.add(2);

        assertEquals(list, bst.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void testInorderLarge() {
        assertEquals(0, bst.size());

        bst.add(2);
        bst.add(1);
        bst.add(3);
        bst.add(0);
        bst.add(5);
        bst.add(4);
        bst.add(6);
        bst.add(100);
        bst.add(50);
        bst.add(99);
        bst.add(80);

        List<Integer> list = new ArrayList<Integer>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(50);
        list.add(80);
        list.add(99);
        list.add(100);

        assertEquals(list, bst.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        assertEquals(0, bst.size());

        bst.add(2);
        bst.add(1);
        bst.add(3);

        assertEquals(3, bst.size());
        assertEquals((Integer) 2, bst.getRoot().getData());
        assertEquals((Integer) 1, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 3, bst.getRoot().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        assertEquals(0, bst.size());

        bst.add(2);
        bst.add(1);
        bst.add(3);

        assertEquals(3, bst.size());
        assertEquals(true, bst.contains(2));
        assertEquals(true, bst.contains(3));
        assertEquals(true, bst.contains(1));
        assertEquals(false, bst.contains(0));
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        assertEquals(0, bst.size());

        bst.add(2);
        bst.add(1);
        bst.add(3);
        bst.add(4);
        bst.add(5);

        assertEquals((Integer) 3, bst.remove(3));
        assertEquals(4, bst.size());
        assertEquals((Integer) 2, bst.getRoot().getData());
        assertEquals((Integer) 1, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 4, bst.getRoot().getRight().getData());
        assertEquals((Integer) 5,
                bst.getRoot().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveEmpty() {
        assertEquals(0, bst.size());

        bst.remove(2);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveOne() {
        assertEquals(0, bst.size());

        Integer testingInteger = new Integer(1);
        bst.add(testingInteger);
        assertEquals(1, bst.size());

        assertSame(testingInteger, bst.remove(1));
        assertEquals(0, bst.size());
        assertEquals(null, bst.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveReturn() {
        Integer testingInteger = new Integer(12);

        bst.add(24);
        bst.add(1);
        bst.add(7);
        bst.add(testingInteger);
        bst.add(94);
        bst.add(58);
        bst.add(73);

        assertSame(testingInteger, bst.remove(new Integer(12)));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveTwo() {
        assertEquals(0, bst.size());

        bst.add(1);
        bst.add(2);

        assertEquals(2, bst.size());

        assertEquals((Integer) 1, bst.remove(1));
        assertEquals(1, bst.size());
        assertEquals((Integer) 2, bst.getRoot().getData());
        assertEquals((Integer) 2, bst.remove(2));
        assertEquals(0, bst.size());
        assertEquals(null, bst.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLarge() {
        assertEquals(0, bst.size());

        bst.add(2);
        bst.add(1);
        bst.add(3);
        bst.add(4);
        bst.add(5);

        assertEquals((Integer) 3, bst.remove(3));
        assertEquals(4, bst.size());
        assertEquals((Integer) 2, bst.getRoot().getData());
        assertEquals((Integer) 1, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 4, bst.getRoot().getRight().getData());
        assertEquals((Integer) 5,
                bst.getRoot().getRight().getRight().getData());

        bst.add(3);
        bst.add(8);
        bst.add(6);
        bst.add(9);

        assertEquals((Integer) 6, bst.getRoot().getRight().getRight().getRight()
                .getLeft().getData());

        assertEquals((Integer) 4, bst.remove(4));
        assertEquals(7, bst.size());
        assertEquals((Integer) 6, bst.getRoot().getRight().getRight().getRight()
                .getLeft().getData());
        assertEquals((Integer) 9, bst.getRoot().getRight().getRight().getRight()
                .getRight().getData());

        bst.remove(2);

        assertEquals((Integer) 1, bst.getRoot().getData());
        assertEquals(null, bst.getRoot().getLeft());
        assertEquals((Integer) 5,
                bst.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 8,
                bst.getRoot().getRight().getRight().getRight().getData());

        bst.remove(1);
        bst.remove(3);
        bst.remove(5);
        bst.remove(8);
        bst.remove(6);

        assertEquals(1, bst.size());
        assertEquals((Integer) 9, bst.getRoot().getData());
        assertEquals(null, bst.getRoot().getLeft());
        assertEquals(null, bst.getRoot().getRight());

        assertEquals((Integer) 9, bst.remove(9));
        assertEquals(null, bst.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        bst.add(24);
        bst.add(1);
        bst.add(7);
        bst.add(12);
        bst.add(94);
        bst.add(58);
        bst.add(73);

        assertTrue(bst.contains(58));
        assertEquals((Integer) 58, bst.get(58));
        assertTrue(bst.contains(12));
        assertEquals((Integer) 12, bst.get(12));
        assertTrue(bst.contains(94));
        assertEquals((Integer) 94, bst.get(94));
        assertTrue(bst.contains(24));
        assertEquals((Integer) 24, bst.get(24));
    }

    @Test(timeout = TIMEOUT)
    public void testGetDifferent() {
        Integer testingInteger = new Integer(12);

        bst.add(24);
        bst.add(1);
        bst.add(7);
        bst.add(testingInteger);
        bst.add(94);
        bst.add(58);
        bst.add(73);

        assertSame(testingInteger, bst.get(new Integer(12)));
    }

    @Test(timeout = TIMEOUT)
    public void testLevelorder() {
        bst.add(24);
        bst.add(1);
        bst.add(7);
        bst.add(12);
        bst.add(94);
        bst.add(58);
        bst.add(73);
        bst.add(77);
        bst.add(68);

        List<Integer> levelorder = new ArrayList<>();
        levelorder.add(24);
        levelorder.add(1);
        levelorder.add(94);
        levelorder.add(7);
        levelorder.add(58);
        levelorder.add(12);
        levelorder.add(73);
        levelorder.add(68);
        levelorder.add(77);

        assertEquals(levelorder, bst.levelorder());
    }

    @Test
    public void testAddGet() {
        int[] data = {9, 5, 3, 1, 4, 8, 15, 11, 21, 20, 29};
        for (int i : data) {
            bst.add(i);
        }
        for (int i : data) {
            assertEquals((Integer) i, bst.get(i));
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addExpectException() {
        bst.add(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructor() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(9);
        list.add(null);
        list.add(9);
        bst = new BST<Integer>(list);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructor2() {
        bst = new BST<Integer>(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getEmpty() {
        bst.get(20);
    }
}