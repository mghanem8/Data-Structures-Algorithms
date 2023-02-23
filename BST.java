import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Mohamed Ghanem
 * @version 1.0
 * @userid mghanem8
 * @GTID 903533880
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot initialize BST with null collection.");
        }
        root = null;
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to BST.");
        }
        root = helperAdd(root, data);
    }

    /**
     * Private helper function for add that helps with recursive add implementation.
     *
     * @param curr current traversal node
     * @param data data to add
     * @return node to reinforce
     */
    private BSTNode<T> helperAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(helperAdd(curr.getRight(), data));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(helperAdd(curr.getLeft(), data));
        }
        return curr;
    }


    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data from BST.");
        }
        BSTNode<T> temp = new BSTNode<T>(null);
        root = helperRemove(root, data, temp);
        return temp.getData();
        
    }

    /**
     * Private helper function for remove that helps with recursive remove implementation.
     *
     * @param curr current traversal node
     * @param data data to remove
     * @param temp dummy node that is used to store the data removed
     * @return node to reinforce
     */
    private BSTNode<T>  helperRemove(BSTNode<T> curr, T data, BSTNode<T> temp) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data is not in the BST.");
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(helperRemove(curr.getRight(), data, temp));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(helperRemove(curr.getLeft(), data, temp));
        } else {
            temp.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                return curr.getRight();
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else {
                BSTNode<T> temp2 = new BSTNode<T>(null);
                curr.setRight(removeSuccessor(curr.getRight(), temp2));
                curr.setData(temp2.getData());
            }
        }
        return curr;
    }

    /**
     * Private helper method that helps find the successor of the node that is being removed.
     *
     * @param curr current traversal node
     * @param temp dummy node that is used to store successor data
     * @return successor of the node removed
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> temp) {
        if (curr.getLeft() == null) {
            temp.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), temp));
        }
        return curr;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data from BST.");
        }
        BSTNode<T> temp = new BSTNode<T>(null);
        root = helperGet(root, data, temp);
        return temp.getData();
    }

    /**
     * Private helper function for get used for recursive get implementation.
     *
     * @param curr current traversal node
     * @param data data to get
     * @param temp dummy node used to store data we need to get
     * @return node to reinforce
     */
    private BSTNode<T>  helperGet(BSTNode<T> curr, T data, BSTNode<T> temp) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data is not in the BST.");
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(helperGet(curr.getRight(), data, temp));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(helperGet(curr.getLeft(), data, temp));
        } else {
            temp.setData(curr.getData());
        }
        return curr;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("BST cannot contain null data.");
        }
        return helperContains(root, data);
    }

    /**
     * Private helper function for contains used for recursive implementation of contains.
     *
     * @param curr current traversal node
     * @param data the data to search for
     * @return rue if the parameter is contained within the tree, false otherwise
     *
     */
    private boolean helperContains(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (data.compareTo(curr.getData()) > 0) {
            return helperContains(curr.getRight(), data);
        } else if (data.compareTo(curr.getData()) < 0) {
            return helperContains(curr.getLeft(), data);
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorder = new ArrayList<T>();
        helperPreorder(root, preorder);
        return preorder;
    }

    /**
     * Private helper function used to implement preorder recursively.
     *
     * @param curr current traversal node
     * @param preorder the preorder traversal of the tree
     */
    private void helperPreorder(BSTNode<T> curr, List<T> preorder) {
        if (curr != null) {
            preorder.add(curr.getData());
            helperPreorder(curr.getLeft(), preorder);
            helperPreorder(curr.getRight(), preorder);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorder = new ArrayList<T>();
        helperInorder(root, inorder);
        return inorder;
    }
    /**
     * Private helper function used to implement inorder recursively.
     *
     * @param curr current traversal node
     * @param inorder the inorder traversal of the tree
     */
    private void helperInorder(BSTNode<T> curr, List<T> inorder) {
        if (curr != null) {
            helperInorder(curr.getLeft(), inorder);
            inorder.add(curr.getData());
            helperInorder(curr.getRight(), inorder);
        }
    }
    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorder = new ArrayList<T>();
        helperPostorder(root, postorder);
        return postorder;
    }

    /**
     * Private helper function used to implement postorder recursively.
     *
     * @param curr current traversal node
     * @param postorder the postorder traversal of the tree
     */
    private void helperPostorder(BSTNode<T> curr, List<T> postorder) {
        if (curr != null) {
            helperPostorder(curr.getLeft(), postorder);
            helperPostorder(curr.getRight(), postorder);
            postorder.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
        queue.add(root);
        List<T> levelorder = new ArrayList<T>();
        helperLevelorder(queue, levelorder);
        return levelorder;
    }

    /**
     * Private helper function used to implement levelorder recursively.
     *
     * @param queue queue used to determine next traversal nose
     * @param levelorder the levelorder traversal of the tree
     */
    private void helperLevelorder(Queue<BSTNode<T>> queue, List<T> levelorder) {
        if (!queue.isEmpty()) {
            BSTNode<T> curr = queue.remove();
            if (curr != null) {
                levelorder.add(curr.getData());
                queue.add(curr.getLeft());
                queue.add(curr.getRight());
            }
            helperLevelorder(queue, levelorder);
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return helperHeight(root);
    }

    /**
     * Private helper function used to implement height recursively
     *
     * @param curr current traversal node
     * @return the height of the root of the tree
     */
    private int helperHeight(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        int leftHeight = helperHeight(curr.getLeft());
        int rightHeight = helperHeight(curr.getRight());
        if (leftHeight > rightHeight) {
            return ++leftHeight;
        } else {
            return ++rightHeight;
        }
    }
    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Removes all elements strictly greater than the passed in data.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * pruneGreaterThan(27) should remove 37, 40, 50, 75. Below is the resulting BST
     *             25
     *            /
     *          12
     *         /  \
     *        10  15
     *           /
     *          13
     *
     * Should have a running time of O(log(n)) for balanced tree. O(n) for a degenerated tree.
     *
     * @throws java.lang.IllegalArgumentException if data is null
     * @param data the threshold data. Elements greater than data should be removed
     * @param tree the root of the tree to prune nodes from
     * @param <T> the generic typing of the data in the BST
     * @return the root of the tree with all elements greater than data removed
     */
    public static <T extends Comparable<? super T>> BSTNode<T> pruneGreaterThan(BSTNode<T> tree, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot prune null data from BST.");
        }
        if (tree == null) {
            return null;
        } else if (tree.getData().compareTo(data) > 0) {
            return pruneGreaterThan(tree.getLeft(), data);
        } else {
            tree.setRight(pruneGreaterThan(tree.getRight(), data));
            return tree;
        }
    }
    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
