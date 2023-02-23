import java.util.Collection;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Mohamed Ghanem
 * @userid mghanem8
 * @GTID 903533880
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot initialize AVL with null data.");
        }
        root = null;
        for (T temp : data) {
            add(temp);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to AVL.");
        }
        root = addHelper(root, data);
    }

    /**
     * Private helper function that implements add recursively
     * @param curr current traversal node
     * @param data data to add
     * @return node for pointer reinforcement
     */
    private AVLNode<T> addHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
        } else if ((data.compareTo(curr.getData()) < 0)) {
            curr.setLeft(addHelper(curr.getLeft(), data));
        }
        return balance(curr);
    }

    /**
     * Private helper function that updates the height and balance factor of a node
     * @param curr node to update
     */
    private void updateHeightBalanceFactor(AVLNode<T> curr) {
        if (curr.getRight() == null && curr.getLeft() == null) {
            curr.setHeight(0);
            curr.setBalanceFactor(0);
        } else if (curr.getLeft() == null) {
            curr.setHeight(curr.getRight().getHeight() + 1);
            curr.setBalanceFactor(-1 - curr.getRight().getHeight());
        } else if (curr.getRight() == null) {
            curr.setHeight(curr.getLeft().getHeight() - -1);
            curr.setBalanceFactor(curr.getLeft().getHeight() + 1);
        } else {
            curr.setHeight(Math.max(curr.getLeft().getHeight(), curr.getRight().getHeight()) + 1);
            curr.setBalanceFactor(curr.getLeft().getHeight() - curr.getRight().getHeight());
        }
    }

    /**
     * Private helper function that performs a left rotation
     * @param nodeA node to rotate at
     * @return new node after rotation
     */
    private AVLNode<T> leftRotation(AVLNode<T> nodeA) {
        AVLNode<T> nodeB = nodeA.getRight();
        nodeA.setRight(nodeB.getLeft());
        nodeB.setLeft(nodeA);
        if (nodeA == root) {
            root = nodeB;
        }
        updateHeightBalanceFactor(nodeA);
        updateHeightBalanceFactor(nodeB);
        return nodeB;
    }

    /**
     * Private helper function that performs a right rotation
     * @param nodeA node to rotate at
     * @return new node after rotation
     */
    private AVLNode<T> rightRotation(AVLNode<T> nodeA) {
        AVLNode<T> nodeB = nodeA.getLeft();
        nodeA.setLeft(nodeB.getRight());
        nodeB.setRight(nodeA);
        if (nodeA == root) {
            root = nodeB;
        }
        updateHeightBalanceFactor(nodeA);
        updateHeightBalanceFactor(nodeB);
        return nodeB;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor. As a reminder, rotations can occur after removing
     * the predecessor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data from AVL.");
        }
        AVLNode<T> temp = new AVLNode<>(null);
        root = removeHelper(root, data, temp);
        return temp.getData();
    }

    /**
     * Private helper function that implements remove recursively
     * @param curr current traversal node
     * @param data data to remove
     * @param temp dummy node to store removed data
     * @return data removed
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data, AVLNode<T> temp) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Cannot remove data that is not in AVL.");
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, temp));
        } else if ((data.compareTo(curr.getData()) < 0)) {
            curr.setLeft(removeHelper(curr.getLeft(), data, temp));
        } else {
            size--;
            temp.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                AVLNode<T> successorData = new AVLNode<T>(null);
                curr.setLeft(removeSuccessor(curr.getLeft(), successorData));
                curr.setData(successorData.getData());
            }
        }
        return balance(curr);
    }

    /**
     * Private helper function that finds and removes successor
     * @param curr current traversal node
     * @param temp dummy node to store successor data
     * @return successor node for pointer reinforcement
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> curr, AVLNode<T> temp) {
        if (curr.getRight() == null) {
            temp.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setLeft(removeSuccessor(curr.getRight(), temp));
        }
        return balance(curr);
    }

    /**
     * Private helper function that balances node passed in
     * @param curr node to balance
     * @return balanced node
     */
    private AVLNode<T> balance(AVLNode<T> curr) {
        updateHeightBalanceFactor(curr);
        if (curr.getBalanceFactor() == 2) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(leftRotation(curr.getLeft()));
            }
            return rightRotation(curr);
        } else if (curr.getBalanceFactor() == -2) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rightRotation(curr.getRight()));
            }
            return leftRotation(curr);
        }
        return curr;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data from AVL.");
        }
        return getHelper(root, data);
    }

    /**
     * Private helper function that implements get recursively
     * @param curr current traversal node
     * @param data data to get
     * @return data
     */
    private T getHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data was not found in AVL.");
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(curr.getRight(), data);
        } else if ((data.compareTo(curr.getData()) < 0)) {
            return getHelper(curr.getLeft(), data);
        }
        return curr.getData();
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        try {
            get(data);
        } catch (java.util.NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Finds and retrieves the median data of the passed in AVL. 
     * 
     * This method will not need to traverse the entire tree to
     * function properly, so you should only traverse enough branches of the tree
     * necessary to find the median data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     * 
     * findMedian() should return 40
     *
     * @throws java.util.NoSuchElementException if the tree is empty or contains an even number of data
     * @return the median data of the AVL
     */
    public T findMedian() {
        if (root == null || size % 2 == 0) {
            throw new java.util.NoSuchElementException("Cannot find the median of an empty or even size AVL.");
        }
        AVLNode<Integer> index = new AVLNode<Integer>(0);
        AVLNode<T> temp = new AVLNode<T>(null);
        return medianHelper(root, index, temp).getData();
    }

    /**
     * Private helper function that finds median recursively
     * @param curr current traversal node
     * @param index dummy node to store index of median
     * @param temp dummy node to store median
     * @return node with median data
     */
    private AVLNode<T> medianHelper(AVLNode<T> curr, AVLNode<Integer> index, AVLNode<T> temp) {
        if (curr != null && temp.getData() == null) {
            medianHelper(curr.getLeft(), index, temp);
            index.setData(index.getData() + 1);
            if (index.getData() == (size + 1) / 2) {
                temp.setData(curr.getData());
                return temp;
            }
            medianHelper(curr.getRight(), index, temp);
        }
        return temp;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}