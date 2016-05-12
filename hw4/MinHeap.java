import java.util.NoSuchElementException;
/**
 * Your implementation of a min heap.
 * @author Beiwen Liu
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables

    /**
     * Creates a Heap.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    /**
     * This helper method doubles the original size of the array
     * by creating a new array twice the length and assigning
     * each old element into the new array
     */
    private void doubleSize() {
        T[] temp = (T[]) new Comparable[2 * backingArray.length];
        for (int i = 1; i < backingArray.length; i++) {
            temp[i] = backingArray[i];
        }
        backingArray = temp;
    }

    /**
     * This helper method will traverse upwards through the binary heap
     * and switch the elements if the element added is smaller than its parent
     */
    private void arrangeUp() {
        int i = size;
        T element = backingArray[size];
        for (; i > 1 && element.compareTo(backingArray[i / 2]) < 0; i = i / 2) {
            backingArray[i] = backingArray[i / 2];
        }
        backingArray[i] = element;
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        if (size == backingArray.length - 1) {
            doubleSize();
        }
        backingArray[++size] = item;
        arrangeUp();
    }

    /**
     * This helper method detects if a left child exists
     * @param index is used to determine the left child
     * @return true if left child exists
     */
    private boolean hasLeft(int index) {
        return 2 * index <= size();
    }

    /**
     * This helper method detects if a right child exists
     * @param index is used to determine the right child
     * @return true if right child exists
     */
    private boolean hasRight(int index) {
        return 2 * index + 1 <= size();
    }

    /**
     * Thie helper method will return the left child
     * @param index is used to determine the left child
     * @return returns the element of the left child
     */
    private T getLeft(int index) {
        return backingArray[2 * index];
    }

    /**
     * This helper method will return the right child
     * @param index is used to determine the right child
     * @return returns the element of the right child
     */
    private T getRight(int index) {
        return backingArray[2 * index + 1];
    }

    /**
     * This helper method will switch the parent with the left child
     * This method is only used when traversing down the heap
     * @param index is used to determine the left child
     */
    private void switchLeft(int index) {
        T element = backingArray[index];
        backingArray[index] = backingArray[2 * index];
        backingArray[2 * index] = element;
    }

    /**
     * This helper method will switch the parent with the right child
     * This method is only used when traversing down the heap
     * @param index is used to determine the right child
     */
    private void switchRight(int index) {
        T element = backingArray[index];
        backingArray[index] = backingArray[2 * index + 1];
        backingArray[2 * index + 1] = element;
    }

    /**
     * This helper method will traverse down the heap after the last element
     * has been assigned to the root position of the tree.
     * If parent is higher than its children, then it will be switched with the
     * children.
     */
    private void arrangeDown() {
        int i = 1;
        while (i < size) {
            if (hasLeft(i) && hasRight(i)) {
                if (backingArray[i].compareTo(getLeft(i)) > 0
                        && getLeft(i).compareTo(getRight(i)) < 0) {
                    switchLeft(i);
                    i = 2 * i;
                } else if (backingArray[i].compareTo(getRight(i)) > 0
                        && getRight(i).compareTo(getLeft(i)) < 0) {
                    switchRight(i);
                    i = 2 * i + 1;
                } else {
                    i = size;
                }
            } else if (hasLeft(i) && !hasRight(i)
                    && backingArray[i].compareTo(getLeft(i)) > 0) {
                switchLeft(i);
                i = 2 * i;
            } else {
                i = size;
            }
        }
    }


    @Override
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("There is nothing in the heap!");
        }
        T element = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size--] = null;
        arrangeDown();
        return element;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        backingArray = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    /**
     * Used for grading purposes only. Do not use or edit.
     * @return the backing array
     */
    public Comparable[] getBackingArray() {
        return backingArray;
    }
}
