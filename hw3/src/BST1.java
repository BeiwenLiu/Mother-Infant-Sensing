import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;


public class BST1<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public BST1() {
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
    public BST1(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data or any element"
                    + " in data is null!");
        } else {
            for (T d : data) {
                add(d);
            }
        }
    }

    /**
     * This helper method allows traversing through a BST tree recursively in
     * order to add data according to its value.
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
            throw new IllegalArgumentException("There is nothing in the data!");
        }
        if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            add(root, data);
        }
    }

    /**
     * This helper method is used to traverse through the remaining tree
     * recursively and find the next highest value to replace the removed node.
     * This helper method will only execute if the removed node
     * has two children.
     * @param current is the Node that is to the left of the node of the
     *                Node that is being removed.  This node will recurse
     *                through the remaining tree in order to find the
     *                next highest value that will replace the node.
     * @param previous is the node that will set the current node to null
     *                 or the left child of the node that is found
     *                 to replace the original node
     * @return the node of  that is used to replace the removed
     * node in the BST node
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
     * This helper method traverses through the tree recursively and finds the
     * data that is matched with the node that should be removed.
     * This helper method accounts for the situations such that if the
     * node has no children, one on either side, or two children.
     *
     * @param current is the node that is passed in, in order to traverse
     *                through the BST tree
     * @param data is the data that will be used to compare and traverse
     *             through the tree until it matches a data in the tree
     * @return returns the information of the node that is matched
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
            throw new IllegalArgumentException("There is nothing in the data!");
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
     * This helper method takes in a node and data in order to traverse
     * through the BST tree recursively and find the matching data
     * @param current is the node that will be used to track
     *                and traverse the tree
     * @param data is the data used to compare to data in the tree
     * @return returns the data that is matched in the tree
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
     * This helper method will traverse through the tree and return true or
     * false depending on whether the data matches any data in the tree
     * @param current is the node used to traverse through the tree
     * @param data is the data used to compare to data in the tree
     * @return returns true if data is matched. returns false if data
     * is not matched
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
            throw new IllegalArgumentException("There is nothing in the data!");
        }
        BSTNode<T> current = root;
        return contains(current, data);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * This helper method will traverse through a tree recursively and
     * add to a List in "preorder"
     * @param current is the node used to traverse through the tree recursively
     * @param list is the list used to keep track of the order of data
     * @return returns a list of the data in "preorder"
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
     * This helper method will traverse through a tree recursively and
     * add to a List in "postorder"
     * @param current is the node used to traverse through the tree recursively
     * @param list is the list used to keep track of the order of data
     * @return returns a list of the data in "postorder"
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
     * This helper method will traverse through the tree recursively and
     * add to a List in "inorder"
     * @param current is the node used to traverse through the tree recursively
     * @param list is the list used to keep track of the order of data
     * @return returns a list of the data in "inorder"
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
     * This helper method will traverse through the tree and increment a single
     * value depending on how many levels of nodes it traverses through. It will
     * take the maximum value between left and right
     * @param current is used as a node to traverse through the tree
     * @return returns the height of the tree as the max between the
     * left and right traversal of nodes
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
