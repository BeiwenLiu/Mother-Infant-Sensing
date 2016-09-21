/**
 * The interface describing the methods you will implement for your Heap.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public interface HeapInterface<T extends Comparable<? super T>> {

    public static final int STARTING_SIZE = 15;

    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its size.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    public void add(T item);

    /**
     * Removes and returns the first item of the heap.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the item removed
     */
    public T remove();

    /**
     * Finds if the heap is empty
     * @return a boolean representing if the heap is empty
     */
    public boolean isEmpty();

    /**
     * Finds the size of the heap
     * @return the size of the heap
     */
    public int size();

    /**
     * Clears the heap and returns array to starting size.
     */
    public void clear();
}
