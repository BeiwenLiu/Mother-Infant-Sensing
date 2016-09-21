public class Recitation4 {
    public int size() {
        return size;
    }

    //What if we dont have size?

    public int size (Node<T> node) {
        if (node.left == null && null.right == null) {
            return 1;
        } else if (node.left != null && node.right == null) {
            return 1 + size(node.left);
        } else if (node.left == null && node.right != null) {
            return 1 + size(node.left);
        } else {
            return 1 + size(node.left) + size(node.right);
        }
    }
}


    private T remove(BSTNode<T> current, T data) {
        T answer = null;
        System.out.println("Executed");
        if (current != null && current.getLeft() != null) {
            if (data.equals(current.getLeft().getData())) {
                size--;
                answer = current.getLeft().getData();
                if (current.getLeft().getLeft() == null && current.getLeft().getRight() == null) {
                    current.setLeft(null);
                    return answer;
                } else if (current.getLeft().getLeft() != null && current.getLeft().getRight() == null) {
                    current.setLeft(current.getLeft().getLeft());
                    return answer;
                } else if (current.getLeft().getRight() != null && current.getLeft().getLeft() == null) {
                    current.setLeft(current.getLeft().getRight());
                    return answer;
                } else if (current.getLeft().getLeft() != null && current.getLeft().getRight() != null) {
                    BSTNode<T> nextRight = current.getLeft().getRight();
                    BSTNode<T> nextLeft = current.getLeft().getLeft();
                    current.setLeft(find(current.getLeft().getLeft(), current.getLeft()));
                    current = current.getLeft();
                    current.setRight(nextRight);
                    current.setLeft(nextLeft);
                    return answer;
                }
            }
        } else if (current != null && current.getRight() != null) {
            if (data.equals(current.getRight().getData())) {
                size--;
                answer = current.getRight().getData();
                if (current.getRight().getLeft() == null && current.getRight().getRight() == null) {
                    current.setRight(null);
                    return answer;
                } else if (current.getRight().getLeft() != null && current.getRight().getRight() == null) {
                    current.setRight(current.getRight().getLeft());
                    return answer;
                } else if (current.getRight().getRight() != null && current.getRight().getLeft() == null) {
                    current.setRight(current.getRight().getRight());
                    return answer;
                } else if (current.getRight().getRight() != null && current.getRight().getLeft() != null) {
                    BSTNode<T> nextRight = current.getRight().getRight();
                    BSTNode<T> nextLeft = current.getRight().getLeft();
                    current.setRight(find(current.getRight().getLeft(), current.getRight()));
                    current = current.getRight();
                    current.setRight(nextRight);
                    current.setLeft(nextLeft);
                    return answer;
                }
            }
        } else if (current != null) {
            if (data.equals(current.getData())) {
                answer = current.getData();
                size--;
                if (current.getLeft() == null && current.getRight() == null) {
                    root = null;
                    return answer;
                } else if (current.getLeft() != null && current.getRight() == null) {
                    root = current.getLeft();
                    return answer;
                } else if (current.getRight() != null && current.getLeft() == null) {
                    root = current.getRight();
                    return answer;
                } else if (current.getLeft() != null && current.getRight() != null) {
                    root = find(current.getLeft(), current);
                    return answer;
                }
            }
        } else {
            if (data.compareTo(current.getData()) < 0) {
                return remove(current.getLeft(), data);
            } else if (data.compareTo(current.getData()) > 0) {
                return remove(current.getRight(), data);
            }
        }
//        if (answer == null) {
//            throw new NoSuchElementException("The inputted data is not found!");
//        }
        return answer;
    }