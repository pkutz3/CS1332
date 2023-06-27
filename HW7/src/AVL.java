
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Peter Kutz
 * @userid pkutz3
 * @GTID 903637824
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
            throw new IllegalArgumentException("The data is null.");
        }
        for (T t : data) {
            if (t == null) {
                throw new IllegalArgumentException("A value in the data is null");
            } else {
                add(t);
            }
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
            throw new IllegalArgumentException("The data is null.");
        }

        root = rAdd(root, data);
    }

    /**
     * Private helper that adds the data to the tree recursively using pointer
     * reinforcement. Also updates the nodes and rebalances if necessary.
     *
     * @param curr the current node that is being accessed
     * @param data the data to add
     * @return the node replacing curr if a rotation is necessary; curr if not
     **/
    private AVLNode<T> rAdd(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        update(curr);
        return checkRotations(curr);
    }

    /**
     * Private method that performs a left rotation on the passed in node.
     *
     * @param curr the node that is out of balance and will be rotated
     * @return the node that rotated into curr's place in the tree
     */
    private AVLNode<T> leftRotate(AVLNode<T> curr) {
        AVLNode<T> repl = curr.getRight();
        curr.setRight(repl.getLeft());
        repl.setLeft(curr);
        update(curr);
        update(repl);
        return repl;
    }

    /**
     * Private method that performs a right rotation on the passed in node.
     *
     * @param curr the node that is out of balance and will be rotated
     * @return the node that rotated into curr's place in the tree
     */
    private AVLNode<T> rightRotate(AVLNode<T> curr) {
        AVLNode<T> repl = curr.getLeft();
        curr.setLeft(repl.getRight());
        repl.setRight(curr);
        update(curr);
        update(repl);
        return repl;
    }

    /**
     * Private method that checks the balance factor of curr node and
     * calls rotations for the tree if necessary.
     *
     * @param curr the node being checked
     * @return the current node to facilitate pointer reinforcement
     */
    private AVLNode<T> checkRotations(AVLNode<T> curr) {
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(leftRotate(curr.getLeft()));
            }
            return rightRotate(curr);
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rightRotate(curr.getRight()));
            }
            return leftRotate(curr);
        }
        return curr;
    }

    /**
     * Private method used to update the height and balance factor of curr node.
     *
     * @param curr the node being updated
     */
    private void update(AVLNode<T> curr) {
        int lHeight = -1;
        int rHeight = -1;

        if (curr.getLeft() != null) {
            lHeight = curr.getLeft().getHeight();
        }
        if (curr.getRight() != null) {
            rHeight = curr.getRight().getHeight();
        }
        curr.setHeight(Math.max(lHeight, rHeight) + 1);
        curr.setBalanceFactor(lHeight - rHeight);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
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
            throw new IllegalArgumentException("The data is null.");
        }

        AVLNode<T> dummy = new AVLNode<>(null);
        root = rRemove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Private helper that removes the data from the tree recursively using
     * pointer reinforcement,
     *
     * @param curr the current node that is being accessed
     * @param data the data to be removed
     * @param dummy dummy node used to store the removed data
     * @return the node replacing curr if a rotation is necessary; curr if not
     */
    private AVLNode<T> rRemove(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("The data was not found in the tree.");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            size--;

            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                AVLNode<T> dummy2 = new AVLNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        update(curr);
        return checkRotations(curr);
    }

    /**
     * Private helper method that removes the successor from the tree and
     * rearranges the tree to implement remove by successor.
     *
     * @param curr the current node being accessed
     * @param dummy the dummy node used to store removed data
     * @return the successor node used in remove by successor
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
            update(curr);
            return checkRotations(curr);
        }
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
            throw new IllegalArgumentException("The data is null.");
        }

        return rGet(root, data);
    }

    /**
     * Private helper method that traverses through the tree and finds a node
     * with the given data.
     * @param curr the current node being accessed
     * @param data the data we are trying to get
     * @return the data that was found in the tree
     */
    private T rGet(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree.");
        }
        if (data.compareTo(curr.getData()) < 0) {
            return rGet(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return rGet(curr.getRight(), data);
        } else {
            return curr.getData();
        }
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
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        return rContains(root, data);
    }

    /**
     * Private helper method searches the tree to see if the data is in said tree.
     *
     * @param curr the current node being accessed
     * @param data the data being searched for
     * @return true if data is in tree; false if data is not in tree
     */
    private boolean rContains(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (data.compareTo(curr.getData()) < 0) {
            return rContains(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return rContains(curr.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<>(size);
        rDeep(root, list);
        return list;
    }

    /**
     * Private helper method that executes the deepestBranches method recursively.
     * Compares the heights of child nodes to only traverse the longest branches.
     * @param curr the current node being accessed
     * @param list a list of the nodes on the deepest branches
     */
    private void rDeep(AVLNode<T> curr, List<T> list) {
        int lHeight = -1;
        int rHeight = -1;
        if (curr != null) {
            list.add(curr.getData());

            if (curr.getLeft() != null) {
                lHeight = curr.getLeft().getHeight();
            }
            if (curr.getRight() != null) {
                rHeight = curr.getRight().getHeight();
            }

            if (lHeight > rHeight) {
                rDeep(curr.getLeft(), list);
            } else if (rHeight > lHeight) {
                rDeep(curr.getRight(), list);
            } else {
                rDeep(curr.getLeft(), list);
                rDeep(curr.getRight(), list);
            }
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("data1 cannot be greater than data2.");
        }

        List<T> list = new ArrayList<>(size);
        rSorted(root, list, data1, data2);
        return list;
    }

    /**
     * Private helper method that executes sortedInBetween method recursively.
     * @param curr the current node being accessed
     * @param list the list of sorted nodes in between data1 and data2
     * @param data1 the lower bound
     * @param data2 the upper bound
     */
    private void rSorted(AVLNode<T> curr, List<T> list, T data1, T data2) {
        if (curr != null) {
            if (curr.getData().compareTo(data1) <= 0) {
                rSorted(curr.getRight(), list, data1, data2);
            } else if (curr.getData().compareTo(data2) >= 0) {
                rSorted(curr.getLeft(), list, data1, data2);
            } else {
                rSorted(curr.getLeft(), list, data1, data2);
                list.add(curr.getData());
                rSorted(curr.getRight(), list, data1, data2);
            }
        }
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
        } else {
            return root.getHeight();
        }
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