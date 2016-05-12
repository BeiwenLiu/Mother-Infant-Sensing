import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;


public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public BST() {
        root = null;
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element
     *                                  in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data or any element in data "
                    + "is null!");
        } else {
            for (T d : data) {
                add(d);
            }
        }
    }

    /**
     * This helper method allows traversing through a BST tree recursively
     * in order to add data according to its value.
     * @param current the current node that is used to traverse
     *                through the BST tree
     * @param data the data used to add to the BST tree
     */
    private void add(BSTNode<T> current, T data) {
        if (data.compareTo(current.getData()) < 0) {
            if (current.getLeft() == null) {
                current.setLeft(new BSTNode<T>(data));
                size++;
            } else {
                add(current.getLeft(), data);
            }
        } else if (data.compareTo(current.getData()) > 0) {
            if (current.getRight() == null) {
                current.setRight(new BSTNode<T>(data));
                size++;
            } else {
                add(current.getRight(), data);
            }
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("There is nothing "
                    + "in the data!");
        }
        if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            add(root, data);
        }
    }

    /**
     * This helper method finds the next largest node that will replace
     * the removed node by traversing down towards the right.
     * @param current is the node passed in from the left of the removed node
     * @param previous is a holder to set the place of the next largest
     *                 node to null or the left child if it is present
     * @return the node of next largest node that is used to
     * replace the removed node in the BST node
     */
    private BSTNode<T> find(BSTNode<T> current, BSTNode<T> previous) {
        if (current.getRight() == null && current.getLeft() == null) {
            BSTNode<T> answer = current;
            previous.setRight(null);
            return current;
        } else if (current.getRight() == null && current.getLeft() != null) {
            previous.setRight(current.getLeft());
            return current;
        } else {
            previous = current;
            return find(current.getRight(), previous);
        }
    }

    /**
     * This helper method is used to traverse down the tree to find the
     * node matchingto the data that is passed in and remove it.
     * If node has no children, the next left and right will be set to null.
     * If it has one, then the removed node will be replaced by the child.
     * If it has two children, then it will call find()
     * @param current is the node passed in used to traverse through the tree
     * @param data is the data used to compare to the data in the tree
     *             to remove
     * @return the node that is found to be removed
     */

    private T remove(BSTNode<T> current, T data) {
        T answer = null;
        if (data.equals(current.getLeft().getData())) {
            size--;
            answer = current.getLeft().getData();
            if (current.getLeft().getLeft() == null
                    && current.getLeft().getRight() == null) {
                current.setLeft(null);
                return answer;
            } else if (current.getLeft().getLeft() != null
                    && current.getLeft().getRight() == null) {
                current.setLeft(current.getLeft().getLeft());
                return answer;
            } else if (current.getLeft().getRight() != null
                    && current.getLeft().getLeft() == null) {
                current.setLeft(current.getLeft().getRight());
                return answer;
            } else if (current.getLeft().getLeft() != null
                    && current.getLeft().getRight() != null) {
                BSTNode<T> nextRight = current.getLeft().getRight();
                BSTNode<T> nextLeft = current.getLeft().getLeft();
                current.setLeft(find(current.getLeft().getLeft(),
                        current.getLeft()));
                current = current.getLeft();
                current.setRight(nextRight);
                if (!current.getData().equals(nextLeft.getData())) {
                    current.setLeft(nextLeft);
                }
                return answer;
            }
        } else if (data.equals(current.getRight().getData())) {
            size--;
            answer = current.getRight().getData();
            if (current.getRight().getLeft() == null
                    && current.getRight().getRight() == null) {
                current.setRight(null);
                return answer;
            } else if (current.getRight().getLeft() != null
                    && current.getRight().getRight() == null) {
                current.setRight(current.getRight().getLeft());
                return answer;
            } else if (current.getRight().getRight() != null
                    && current.getRight().getLeft() == null) {
                current.setRight(current.getRight().getRight());
                return answer;
            } else if (current.getRight().getRight() != null
                    && current.getRight().getLeft() != null) {
                BSTNode<T> nextRight = current.getRight().getRight();
                BSTNode<T> nextLeft = current.getRight().getLeft();
                current.setRight(find(current.getRight().getLeft(),
                        current.getRight()));
                current = current.getRight();
                current.setRight(nextRight);
                if (!current.getData().equals(nextLeft.getData())) {
                    current.setLeft(nextLeft);
                }
                return answer;
            }
        } else {
            if (data.compareTo(current.getData()) < 0) {
                return remove(current.getLeft(), data);
            } else if (data.compareTo(current.getData()) > 0) {
                return remove(current.getRight(), data);
            }
        }

        return answer;
    }



    @Override
    public T remove(T data) {
        T answer = null;
        if (data == null) {
            throw new IllegalArgumentException("There is nothing in"
                    + " the data!");
        }
        if (root != null) {
            if (data.equals(root.getData()) && root.getLeft() == null
                    && root.getRight() == null) {
                answer = root.getData();
                root = null;
                size--;
            } else if (root.getLeft() != null && root.getRight() == null
                    && data.equals(root.getData())) {
                answer = root.getData();
                root = root.getLeft();
                size--;
            } else if (root.getRight() != null && root.getLeft() == null
                    && data.equals(root.getData())) {
                answer = root.getData();
                root = root.getRight();
                size--;
            } else if (root.getRight() != null && root.getLeft() != null
                    && data.equals(root.getData())) {
                size--;
                answer = root.getData();
                BSTNode<T> nodeRight = root.getRight();
                BSTNode<T> nodeLeft = root.getLeft();
                root = find(root.getLeft(), root);
                root.setRight(nodeRight);
                if (!root.getData().equals(nodeLeft.getData())) {
                    root.setLeft(nodeLeft);
                }
            } else {
                if (root != null) {
                    if (root.getLeft() != null && root.getRight() != null) {
                        answer = remove(root, data);
                    }
                }
            }
        }
        if (answer == null) {
            throw new NoSuchElementException("Data does not exist in list!");
        }
        return answer;
    }

    /**
     * This method is used to traverse through the tree and find the
     * data passed in
     * @param current is the node used to traverse through the tree
     * @param data is the data passed in to compare to the data in the tree
     * @return the data that is matched to the node in the tree
     */
    private T get(BSTNode<T> current, T data) {
        if (current != null) {
            if (data.equals(current.getData())) {
                return current.getData();
            } else if (data.compareTo(current.getData()) < 0) {
                return get(current.getLeft(), data);
            } else if (data.compareTo(current.getData()) > 0) {
                return get(current.getRight(), data);
            }
        }
        throw new NoSuchElementException("Element does not exist in list!");
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("There is nothing in the data!");
        }
        return get(root, data);
    }

    /**
     * This helper returns true or false depending on whether the data inputted
     * matches any data in the tree by traversing through it
     * @param current is the node passed in to traverse through the tree
     * @param data is the data used to compare to data in the tree
     * @return true if data is found, false if not
     */

    private boolean contains(BSTNode<T> current, T data) {
        if (current != null) {
            if (data.equals(current.getData())) {
                return true;
            } else if (data.compareTo(current.getData()) < 0) {
                return contains(current.getLeft(), data);
            } else if (data.compareTo(current.getData()) > 0) {
                return contains(current.getRight(), data);
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("There is nothing in"
                    + " the data!");
        }
        BSTNode<T> current = root;
        return contains(current, data);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * This helper method will traverse through the tree and add to a list
     * in preorder
     * @param current is the node used to traverse through the tree
     * @param list is the list used to keep track of data
     * @return the list
     */
    private List<T> preorder(BSTNode<T> current, List<T> list) {
        if (current != null) {
            list.add(current.getData());
            preorder(current.getLeft(), list);
            preorder(current.getRight(), list);
        } else if (list.size() == size()) {
            return list;
        }
        return list;
    }

    @Override
    public List<T> preorder() {
        BSTNode<T> current = root;
        List<T> list = new ArrayList<T>();
        return preorder(current, list);
    }

    /**
     * This helper method will traverse through the tree and add to a list
     * in postorder
     * @param current is the node used to traverse through the tree
     * @param list is the list used to keep track of data
     * @return the list
     */
    private List<T> postorder(BSTNode<T> current, List<T> list) {
        if (current != null) {
            postorder(current.getLeft(), list);
            postorder(current.getRight(), list);
            list.add(current.getData());
        } else if (list.size() == size()) {
            return list;
        }
        return list;
    }

    @Override
    public List<T> postorder() {
        BSTNode<T> current = root;
        List<T> list = new ArrayList<T>();
        return postorder(current, list);
    }

    /**
     * This helper method will traverse through the tree and add to a list
     * in inorder
     * @param current is the node used to traverse through the tree
     * @param list is the list used to keep track of data
     * @return the list
     */
    private List<T> inorder(BSTNode<T> current, List<T> list) {
        if (current != null) {
            inorder(current.getLeft(), list);
            list.add(current.getData());
            inorder(current.getRight(), list);
        } else if (list.size() == size()) {
            return list;
        }
        return list;
    }

    @Override
    public List<T> inorder() {
        BSTNode<T> current = root;
        List<T> list = new ArrayList<T>();
        return inorder(current, list);

    }

    @Override
    public List<T> levelorder() {
        BSTNode<T> current = root;
        BSTNode<T> temp = current;
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        } else {
            queue.add(root);
            while (!queue.isEmpty()) {
                current = queue.remove();
                list.add(current.getData());
                if (current.getLeft() != null) {
                    queue.add(current.getLeft());
                }
                if (current.getRight() != null) {
                    queue.add(current.getRight());
                }
            }
            return list;
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * This helper mmethod will traverse through the tree and add one everytime
     * it traverses up a row. It will determine the max of the left
     * and right sides
     * @param current is the node passed in to travese through the tree
     * @return returns the height of tree
     */
    private int height(BSTNode<T> current) {
        if (current == null) {
            return -1;
        } else {
            return Math.max(height(current.getLeft()),
                    height(current.getRight())) + 1;
        }
    }


    @Override
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return height(root);
        }
    }


    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }
}
