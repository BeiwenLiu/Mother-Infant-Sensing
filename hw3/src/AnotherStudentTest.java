import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class AnotherStudentTest {
    private BST<Integer> bst;
    public static final int TIMEOUT = 200;

    @Before
    public void setup() {
        bst = new BST<Integer>();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addExpectException1() {
        bst.add(null);
    }

    // add

    @Test(timeout = TIMEOUT)
    public void testAdd1() {
        bst.add(2);

        assertEquals(1, bst.size());
        assertEquals((Integer) 2, bst.getRoot().getData());
        assertNull(bst.getRoot().getLeft());
        assertNull(bst.getRoot().getRight());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd2() {
        bst.add(2);
        bst.add(1);

        assertEquals(2, bst.size());
        assertEquals((Integer) 2, bst.getRoot().getData());
        assertEquals((Integer) 1, bst.getRoot().getLeft().getData());
        assertNull(bst.getRoot().getRight());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd3() {
        assertEquals(0, bst.size());

        bst.add(7);
        bst.add(5);
        bst.add(10);

        assertEquals(3, bst.size());
        assertEquals((Integer) 7, bst.getRoot().getData());
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 10, bst.getRoot().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd4() {
        assertEquals(0, bst.size());

        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(4);
        bst.add(6);

        assertEquals(5, bst.size());
        assertEquals((Integer) 7, bst.getRoot().getData());
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 10, bst.getRoot().getRight().getData());
        assertEquals((Integer) 4, bst.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 6, bst.getRoot().getLeft().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd5() {
        assertEquals(0, bst.size());

        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(4);
        bst.add(6);
        bst.add(9);
        bst.add(11);

        assertEquals(7, bst.size());
        assertEquals((Integer) 7, bst.getRoot().getData());
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 4, bst.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 6, bst.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 10, bst.getRoot().getRight().getData());
        assertEquals((Integer) 9, bst.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 11, bst.getRoot().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd6() {
        assertEquals(0, bst.size());

        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(4);
        bst.add(6);
        bst.add(9);
        bst.add(11);
        bst.add(10); // should not be added because duplicate

        assertEquals(7, bst.size());
        assertEquals((Integer) 7, bst.getRoot().getData());
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 4, bst.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 6, bst.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 10, bst.getRoot().getRight().getData());
        assertEquals((Integer) 9, bst.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 11, bst.getRoot().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd7() {
        bst.add(2);
        bst.add(2);
        bst.add(2);
        bst.add(2);
        bst.add(2);
        bst.add(2);

        assertEquals(1, bst.size());
        assertEquals((Integer) 2, bst.getRoot().getData());
        assertNull(bst.getRoot().getLeft());
        assertNull(bst.getRoot().getRight());
    }

    // remove

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void removeExpectException1() {
        bst.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeExpectException2() {
        bst.remove(1);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeExpectException3() {
        bst.add(5);
        bst.remove(1);
    }

    @Test(timeout = TIMEOUT)
    public void testRemove1() {
        bst.add(2);
        bst.remove(2);

        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove2() {

        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.remove(5);

        assertEquals(2, bst.size());
        assertEquals((Integer) 7, bst.getRoot().getData());
        assertNull(bst.getRoot().getLeft());
        assertEquals((Integer) 10, bst.getRoot().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove3() {

        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.remove(10);

        assertEquals(2, bst.size());
        assertEquals((Integer) 7, bst.getRoot().getData());
        assertNull(bst.getRoot().getRight());
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove4() {

        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.remove(7);

        assertEquals(2, bst.size());
        assertEquals((Integer) 5, bst.getRoot().getData());
        assertNull(bst.getRoot().getLeft());
        assertEquals((Integer) 10, bst.getRoot().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove5() {

        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(6);
        bst.add(9);
        bst.add(11);
        bst.remove(5);

        assertEquals(5, bst.size());
        assertEquals((Integer) 7, bst.getRoot().getData());
        assertEquals((Integer) 6, bst.getRoot().getLeft().getData());
        assertNull(bst.getRoot().getLeft().getLeft());
        assertNull(bst.getRoot().getLeft().getRight());
        assertEquals((Integer) 10, bst.getRoot().getRight().getData());
        assertEquals((Integer) 11, bst.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 9, bst.getRoot().getRight().getLeft().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove6() {
        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(6);
        bst.add(9);
        bst.add(11);
        bst.remove(10);

        assertEquals(5, bst.size());
        assertEquals((Integer) 7, bst.getRoot().getData());
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 6, bst.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 9, bst.getRoot().getRight().getData());
        assertNull(bst.getRoot().getRight().getLeft());
        assertEquals((Integer) 11, bst.getRoot().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove7() {
        bst.add(8);
        bst.add(4);
        bst.add(12);
        bst.add(3);
        bst.add(5);
        bst.add(11);
        bst.add(15);
        bst.add(7);
        bst.add(6);
        bst.remove(8);

        assertEquals(8, bst.size());
        assertEquals((Integer) 7, bst.getRoot().getData());
        assertEquals((Integer) 4, bst.getRoot().getLeft().getData());
        assertEquals((Integer) 3, bst.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 5, bst.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 6, bst.getRoot().getLeft().getRight().getRight().getData());
        assertEquals((Integer) 12, bst.getRoot().getRight().getData());
        assertEquals((Integer) 11, bst.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 15, bst.getRoot().getRight().getRight().getData());
    }

    // get

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void getExpectException() {
        bst.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getExpectException2() {
        bst.get(1);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getExpectException3() {
        bst.add(5);
        bst.get(1);
    }

    @Test(timeout = TIMEOUT)
    public void testGet1() {
        bst.add(7);
        assertEquals((Integer) 7, bst.get(7));
    }

    @Test(timeout = TIMEOUT)
    public void testGet2() {
        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(6);
        bst.add(9);
        bst.add(11);

        assertEquals((Integer) 7, bst.get(7));
        assertEquals((Integer) 5, bst.get(5));
        assertEquals((Integer) 10, bst.get(10));
        assertEquals((Integer) 6, bst.get(6));
        assertEquals((Integer) 9, bst.get(9));
        assertEquals((Integer) 11, bst.get(11));
    }

    // contains

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void containsExpectException1() {
        bst.contains(null);
    }

    @Test(timeout = TIMEOUT)
    public void testContains1() {
        assertFalse(bst.contains(1));
    }

    @Test(timeout = TIMEOUT)
    public void testContains2() {
        bst.add(5);
        assertFalse(bst.contains(1));
    }

    @Test(timeout = TIMEOUT)
    public void testContains3() {
        bst.add(7);
        assertTrue(bst.contains(7));
    }

    @Test(timeout = TIMEOUT)
    public void testContains4() {
        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(6);
        bst.add(9);
        bst.add(11);

        assertTrue(bst.contains(7));
        assertTrue(bst.contains(5));
        assertTrue(bst.contains(10));
        assertTrue(bst.contains(6));
        assertTrue(bst.contains(9));
        assertTrue(bst.contains(11));
    }

    @Test(timeout = TIMEOUT)
    public void testContains5() {
        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(6);
        bst.add(9);
        bst.add(11);

        assertFalse(bst.contains(1));
        assertFalse(bst.contains(2));
        assertFalse(bst.contains(3));
        assertFalse(bst.contains(4));
        assertFalse(bst.contains(8));
        assertFalse(bst.contains(12));
    }

    // size

    @Test(timeout = TIMEOUT)
    public void testSize1() {
        assertEquals(0, bst.size());
    }


    @Test(timeout = TIMEOUT)
    public void testSize2() {
        bst.add(7);
        assertEquals(1, bst.size());
    }

    @Test(timeout = TIMEOUT)
    public void testSize3() {
        bst.add(7);
        bst.add(5);

        assertEquals(2, bst.size());
    }

    @Test(timeout = TIMEOUT)
    public void testSize4() {
        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(6);
        bst.add(9);
        bst.add(11);

        assertEquals(6, bst.size());
    }

    // preorder

    @Test(timeout = TIMEOUT)
    public void testPreorder1() {
        List<Integer> preorder = new ArrayList<>();
        assertEquals(preorder, bst.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void testPreorder2() {
        bst.add(7);

        List<Integer> preorder = new ArrayList<>();
        preorder.add(7);

        assertEquals(preorder, bst.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void testPreorder3() {
        bst.add(7);
        bst.add(5);
        bst.add(10);

        List<Integer> preorder = new ArrayList<>();
        preorder.add(7);
        preorder.add(5);
        preorder.add(10);

        assertEquals(preorder, bst.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void testPreorder4() {
        assertEquals(0, bst.size());
        bst.add(24);
        bst.add(1);
        bst.add(7);
        bst.add(12);
        bst.add(94);
        bst.add(58);
        bst.add(73);
        bst.add(77);
        bst.add(68);
        assertEquals(9, bst.size());

        List<Integer> preorder = new ArrayList<>();
        preorder.add(24);
        preorder.add(1);
        preorder.add(7);
        preorder.add(12);
        preorder.add(94);
        preorder.add(58);
        preorder.add(73);
        preorder.add(68);
        preorder.add(77);

        assertEquals(preorder, bst.preorder());
    }

    // postorder

    @Test(timeout = TIMEOUT)
    public void testPostorder1() {
        List<Integer> postorder = new ArrayList<>();
        assertEquals(postorder, bst.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void testPostorder2() {
        bst.add(7);

        List<Integer> postorder = new ArrayList<>();
        postorder.add(7);

        assertEquals(postorder, bst.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void testPostorder3() {
        bst.add(7);
        bst.add(5);
        bst.add(10);

        List<Integer> postorder = new ArrayList<>();
        postorder.add(5);
        postorder.add(10);
        postorder.add(7);

        assertEquals(postorder, bst.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void testPostorder4() {
        assertEquals(0, bst.size());
        bst.add(24);
        bst.add(1);
        bst.add(7);
        bst.add(12);
        bst.add(94);
        bst.add(58);
        bst.add(73);
        bst.add(77);
        bst.add(68);
        assertEquals(9, bst.size());

        List<Integer> postorder = new ArrayList<>();
        postorder.add(12);
        postorder.add(7);
        postorder.add(1);
        postorder.add(68);
        postorder.add(77);
        postorder.add(73);
        postorder.add(58);
        postorder.add(94);
        postorder.add(24);

        assertEquals(postorder, bst.postorder());
    }

    // inorder

    @Test(timeout = TIMEOUT)
    public void testInorder1() {
        List<Integer> inorder = new ArrayList<>();
        assertEquals(inorder, bst.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void testInorder2() {
        bst.add(7);

        List<Integer> inorder = new ArrayList<>();
        inorder.add(7);

        assertEquals(inorder, bst.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void testInorder3() {
        bst.add(7);
        bst.add(5);
        bst.add(10);

        List<Integer> inorder = new ArrayList<>();
        inorder.add(5);
        inorder.add(7);
        inorder.add(10);

        assertEquals(inorder, bst.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void testInorder4() {
        assertEquals(0, bst.size());
        bst.add(24);
        bst.add(1);
        bst.add(7);
        bst.add(12);
        bst.add(94);
        bst.add(58);
        bst.add(73);
        bst.add(77);
        bst.add(68);
        assertEquals(9, bst.size());

        List<Integer> inorder = new ArrayList<>();
        inorder.add(1);
        inorder.add(7);
        inorder.add(12);
        inorder.add(24);
        inorder.add(58);
        inorder.add(68);
        inorder.add(73);
        inorder.add(77);
        inorder.add(94);

        assertEquals(inorder, bst.inorder());
    }

    // levelorder

    @Test(timeout = TIMEOUT)
    public void testLevelorder1() {
        List<Integer> levelorder = new ArrayList<>();
        assertEquals(levelorder, bst.levelorder());
    }

    @Test(timeout = TIMEOUT)
    public void testLevelorder2() {
        bst.add(7);

        List<Integer> inorder = new ArrayList<>();
        inorder.add(7);

        assertEquals(inorder, bst.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void testLevelorder3() {
        bst.add(7);
        bst.add(5);
        bst.add(10);

        List<Integer> levelorder = new ArrayList<>();
        levelorder.add(7);
        levelorder.add(5);
        levelorder.add(10);

        assertEquals(levelorder, bst.levelorder());
    }

    @Test(timeout = TIMEOUT)
    public void testLevelorder4() {
        assertEquals(0, bst.size());
        bst.add(24);
        bst.add(1);
        bst.add(7);
        bst.add(12);
        bst.add(94);
        bst.add(58);
        bst.add(73);
        bst.add(77);
        bst.add(68);
        assertEquals(9, bst.size());

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

    // clear
    @Test(timeout = TIMEOUT)
    public void testClear1() {
        bst.clear();

        assertEquals(-1, bst.height());
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testClear2() {
        bst.add(7);
        assertEquals(1, bst.size());
        bst.clear();

        assertEquals(-1, bst.height());
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testClear3() {
        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(6);
        bst.add(9);
        bst.add(11);
        assertEquals(6, bst.size());
        bst.clear();

        assertEquals(-1, bst.height());
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
    }

    // height

    @Test(timeout = TIMEOUT)
    public void testHeight1() {
        assertEquals(-1, bst.height());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight2() {
        bst.add(7);

        assertEquals(0, bst.height());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight3() {
        bst.add(7);
        bst.add(5);

        assertEquals(1, bst.height());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight4() {
        bst.add(7);
        bst.add(5);
        bst.add(10);

        assertEquals(1, bst.height());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight5() {
        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(6);

        assertEquals(2, bst.height());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight6() {
        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(6);
        bst.add(9);
        assertEquals(2, bst.height());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight7() {
        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(6);
        bst.add(9);
        bst.add(11);
        assertEquals(2, bst.height());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight8() {
        bst.add(7);
        bst.add(5);
        bst.add(10);
        bst.add(6);
        bst.add(9);
        bst.add(11);
        bst.add(12);
        assertEquals(3, bst.height());
    }

}