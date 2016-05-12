import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
 
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
 
import org.junit.Before;
import org.junit.Test;
 
/**
 * @author Micah Halter
 *
 */
public class BSTTest {
    private BST<Integer>    bst;
    public static final int TIMEOUT = 200;
 
    @Before
    public void setup() {
        bst = new BST<Integer>();
    }
 
    @Test(timeout = TIMEOUT)
    public void testHeight() {
        assertEquals(0, bst.size());
 
        assertEquals(-1, bst.height());
 
        bst.add(2);
 
        assertEquals(1, bst.size());
        assertEquals(0, bst.height());
 
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
    public void testSize() {
        assertEquals(0, bst.size());
 
        bst.add(2);
 
        assertEquals(1, bst.size());
 
        bst.add(1);
        bst.add(3);
 
        assertEquals(3, bst.size());
 
        bst.remove(2);
        assertEquals(2, bst.size());
        bst.remove(1);
        assertEquals(1, bst.size());
        bst.remove(3);
        assertEquals(0, bst.size());
 
        bst.add(2);
        bst.add(1);
        bst.add(3);
        bst.add(5);
        bst.add(6);
 
        assertEquals(5, bst.size());
    }
 
    @Test(timeout = TIMEOUT)
    public void testInorder() {
        assertEquals(0, bst.size());
 
        List<Integer> list = new ArrayList<Integer>();
 
        assertEquals(list, bst.inorder());
 
        bst.add(2);
        list.add(2);
 
        assertEquals(list, bst.inorder());
 
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
 
        list.clear();
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
    public void testPreorder() {
        assertEquals(0, bst.size());
 
        List<Integer> list = new ArrayList<Integer>();
 
        assertEquals(list, bst.preorder());
 
        bst.add(2);
        list.add(2);
 
        assertEquals(list, bst.preorder());
 
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
 
        list.clear();
        list.add(2);
        list.add(1);
        list.add(0);
        list.add(3);
        list.add(5);
        list.add(4);
        list.add(6);
        list.add(100);
        list.add(50);
        list.add(99);
        list.add(80);
        assertEquals(list, bst.preorder());
    }
 
    @Test(timeout = TIMEOUT)
    public void testPostorder() {
        assertEquals(0, bst.size());
 
        List<Integer> list = new ArrayList<Integer>();
 
        assertEquals(list, bst.postorder());
 
        bst.add(2);
        list.add(2);
 
        assertEquals(list, bst.postorder());
 
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
 
        list.clear();
        list.add(0);
        list.add(1);
        list.add(4);
        list.add(80);
        list.add(99);
        list.add(50);
        list.add(100);
        list.add(6);
        list.add(5);
        list.add(3);
        list.add(2);
        assertEquals(list, bst.postorder());
    }
 
    @Test(timeout = TIMEOUT)
    public void testAdd() {
        assertEquals(0, bst.size());
 
        bst.add(3);
        bst.add(1);
        bst.add(2);
        bst.add(5);
        bst.add(4);
        bst.add(6);
        bst.add(7);
 
        assertEquals(7, bst.size());
        assertEquals((Integer) 3, bst.getRoot().getData());
        assertEquals((Integer) 1, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 2, bst.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 5, bst.getRoot().getRight().getData());
        assertEquals((Integer) 4, bst.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 6,
                bst.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 7,
                bst.getRoot().getRight().getRight().getRight().getData());
    }
 
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNull() {
        assertEquals(0, bst.size());
 
        bst.add(null);
    }
 
    @Test(timeout = TIMEOUT)
    public void testContains() {
        assertEquals(0, bst.size());
 
        assertFalse(bst.contains(9));
 
        bst.add(5);
 
        assertTrue(bst.contains(5));
        assertFalse(bst.contains(9));
 
        bst.add(3);
        bst.add(6);
        bst.add(2);
        bst.add(4);
        bst.add(10);
        bst.add(1);
        bst.add(8);
        bst.add(11);
        bst.add(7);
        bst.add(9);
 
        assertEquals(11, bst.size());
        assertTrue(bst.contains(2));
        assertTrue(bst.contains(3));
        assertTrue(bst.contains(11));
        assertFalse(bst.contains(0));
    }
 
    @Test(timeout = TIMEOUT)
    public void testRemove() {
        assertEquals(0, bst.size());
 
        bst.add(2);
 
        assertEquals((Integer) 2, bst.remove(2));
        assertEquals(0, bst.size());
 
        bst.add(5);
        bst.add(3);
        bst.add(6);
        bst.add(2);
        bst.add(4);
        bst.add(10);
        bst.add(1);
        bst.add(8);
        bst.add(11);
        bst.add(7);
        bst.add(9);
 
        assertEquals((Integer) 6, bst.remove(6));
        assertEquals((Integer) 2, bst.remove(2));
        assertEquals((Integer) 4, bst.remove(4));
        assertEquals((Integer) 10, bst.remove(10));
        assertEquals(7, bst.size());
        assertEquals((Integer) 5, bst.getRoot().getData());
        assertEquals((Integer) 3, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 1, bst.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 9, bst.getRoot().getRight().getData());
        assertEquals((Integer) 8, bst.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 7,
                bst.getRoot().getRight().getLeft().getLeft().getData());
        assertEquals((Integer) 11,
                bst.getRoot().getRight().getRight().getData());
 
        bst.add(5);
        assertEquals((Integer) 5, bst.getRoot().getData());
        assertEquals((Integer) 3, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 1, bst.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 9, bst.getRoot().getRight().getData());
        assertEquals((Integer) 8, bst.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 7,
                bst.getRoot().getRight().getLeft().getLeft().getData());
        assertEquals((Integer) 11,
                bst.getRoot().getRight().getRight().getData());
 
        assertEquals((Integer) 5, bst.remove(5));
        assertFalse(bst.contains(5));
        assertEquals((Integer) 3, bst.getRoot().getData());
        assertEquals((Integer) 1, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 9, bst.getRoot().getRight().getData());
        assertEquals((Integer) 8, bst.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 7,
                bst.getRoot().getRight().getLeft().getLeft().getData());
        assertEquals((Integer) 11,
                bst.getRoot().getRight().getRight().getData());
    }
 
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveEmpty() {
        assertEquals(0, bst.size());
 
        bst.remove(2);
    }
 
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNull() {
        assertEquals(0, bst.size());
 
        bst.add(3);
 
        bst.remove(null);
    }
 
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNullEmpty() {
        assertEquals(0, bst.size());
 
        bst.remove(null);
    }
 
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveNotThere() {
        assertEquals(0, bst.size());
 
        bst.add(3);
 
        bst.remove(2);
    }
 
    @Test(timeout = TIMEOUT)
    public void testGet() {
        bst.add(24);
 
        assertEquals((Integer) 24, bst.get(24));
 
        bst.add(1);
        bst.add(7);
        bst.add(12);
        bst.add(94);
        bst.add(58);
        bst.add(73);
 
        assertEquals((Integer) 58, bst.get(58));
        assertEquals((Integer) 12, bst.get(12));
        assertEquals((Integer) 94, bst.get(94));
        assertEquals((Integer) 24, bst.get(24));
    }
 
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetNotThere() {
        bst.add(2);
 
        bst.get(4);
    }
 
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetNotThereEmpty() {
        bst.get(4);
    }
 
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNull() {
        bst.add(3);
 
        bst.get(null);
    }
 
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNullEmpty() {
        bst.get(null);
    }
 
    @Test(timeout = TIMEOUT)
    public void testClear() {
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
 
        bst.clear();
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
 
        bst.add(3);
        bst.add(2);
        bst.add(1);
        bst.add(5);
        bst.add(4);
 
        assertEquals(5, bst.size());
        assertEquals(2, bst.height());
 
        bst.clear();
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
 
        bst.add(2);
        assertEquals(1, bst.size());
        assertEquals(0, bst.height());
 
        bst.clear();
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
    }
 
    @Test(timeout = TIMEOUT)
    public void testLevelorder() {
        List<Integer> levelorder = new ArrayList<>();
        assertEquals(levelorder, bst.levelorder());
 
        bst.add(24);
        levelorder.add(24);
        assertEquals(levelorder, bst.levelorder());
 
        bst.add(1);
        bst.add(7);
        bst.add(12);
        bst.add(94);
        bst.add(58);
        bst.add(73);
        bst.add(77);
        bst.add(68);
 
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
 
    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.add(5);
        list.add(4);
        list.add(6);
        list.add(7);
 
        bst = new BST<Integer>(list);
        assertEquals(7, bst.size());
        assertEquals((Integer) 3, bst.getRoot().getData());
        assertEquals((Integer) 1, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 2, bst.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 5, bst.getRoot().getRight().getData());
        assertEquals((Integer) 4, bst.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 6,
                bst.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 7,
                bst.getRoot().getRight().getRight().getRight().getData());
    }
 
    @Test(timeout = TIMEOUT)
    public void testConstructorEmpty() {
        bst = new BST<Integer>(new ArrayList<Integer>());
 
        assertEquals(0, bst.size());
        assertEquals(-1, bst.height());
        assertNull(bst.getRoot());
    }
 
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructorNull() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(9);
        list.add(null);
        list.add(9);
        bst = new BST<Integer>(list);
    }
 
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructorListNull() {
        bst = new BST<Integer>(null);
    }
}