package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.datastructures.trees.BinarySearchTree.BSTNode;

/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 *    children array or left and right fields in AVLNode.  This will 
 *    instead mask the super-class fields (i.e., the resulting node 
 *    would actually have multiple copies of the node fields, with 
 *    code accessing one pair or the other depending on the type of 
 *    the references used to access the instance).  Such masking will 
 *    lead to highly perplexing and erroneous behavior. Instead, 
 *    continue using the existing BSTNode children array.
 * 4. If this class has redundant methods, your score will be heavily
 *    penalized.
 * 5. Cast children array to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so we recommend you make
 *    private methods that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V>  {
    private class AVLNode extends BSTNode{
        public int height;
        
        // post: constructs a node with given data and null link
        public AVLNode(K key, V value) {
            super(key, value);
            height = 0;
        }
        
        public AVLNode left() {
            return (AVLNode) children[LEFT];
        }
        
        public AVLNode right() {
            return (AVLNode) children[RIGHT];
        }
        
        public void updateHeight() {
            int leftHeight = -1;
            int rightHeight = -1;
            if (left() != null) {
                leftHeight = left().height;
            }
            if (right() != null) {
                rightHeight = right().height;
            }
            int h = Math.max(leftHeight, rightHeight);
            height = h + 1;
        }
    }
    
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    
    private AVLNode castNode(BSTNode node) {
        return (AVLNode) node;
    }
      
    private V result;
    
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            root = new AVLNode(key, value);
            size++;
            return null;
        }     
        this.result = null;
        root = insertAVLTree(key, value, castNode(root));
        return result;
    }
    
    private int getHeightDifference(AVLNode current) {
        int leftHeight = -1;
        int rightHeight = -1;
        if (current.left() != null) {
            leftHeight = current.left().height;
        }
        if (current.right() != null) {
            rightHeight = current.right().height;
        }
        return leftHeight - rightHeight;
    }
    
    private AVLNode insertAVLTree(K key, V value, AVLNode current) {       
        if (current == null) {
            // cannot find this key, insert a new node
            size++;
            return new AVLNode(key, value);
        }
        int direction = Integer.signum(key.compareTo(current.key));
        if (direction == 0) {
            this.result = current.value;
            current.value = value;  
            return current;
        } else {
            // go to its child
            int child = Integer.signum(direction + 1);
            current.children[child] = insertAVLTree(key, value, castNode(current.children[child]));
            current.updateHeight();
            // check if the AVL property keeps
            int check_height = getHeightDifference(current);
            if (check_height > 1) {
                AVLNode leftChild = current.left();
                if (getHeightDifference(leftChild) > 0) {
                    // single right rotation ---> left left
                    current = rotate(current, RIGHT); // 
                } else if (getHeightDifference(leftChild) < 0) {
                    // left right rotation ---> left right 
                    current.children[LEFT] = rotate(leftChild, LEFT);
                    current = rotate(current, RIGHT);
                }   
            } else if (check_height < -1) {
                AVLNode rightChild = current.right();
                if (getHeightDifference(rightChild) < 0) {
                 // single left rotation ---> right right
                    current = rotate(current, LEFT);
                } else if (getHeightDifference(rightChild) > 0) {
                 // right left rotation ---> right left
                    current.children[RIGHT] = rotate(rightChild, RIGHT);
                    current = rotate(current, LEFT);
                }
            }
        }
        return current;   
    }
    
    public AVLNode rotate(AVLNode current, int direction) {
        AVLNode target;
        if (direction == LEFT) {
            // left rotation
            target = current.right();
            AVLNode target_left_child = target.left();
            target.children[LEFT] = current;
            current.children[RIGHT] = target_left_child;
        } else {
            // right rotation
            target = current.left();
            AVLNode target_right_child = target.right();
            target.children[RIGHT] = current;
            current.children[LEFT] = target_right_child;
        }
        current.updateHeight();
        target.updateHeight();     
        return target;
    }
}
