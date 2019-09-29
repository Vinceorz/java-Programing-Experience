package p2.writeup;

import datastructures.dictionaries.AVLTree;

import java.util.Random;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;

public class TreeExperiment {

  public static final int size = 10000;
  public static final int numFind = 10000;

  public static void main(String[] args) {
    Dictionary<Integer, Integer> avl = new AVLTree<>();
    Dictionary<Integer, Integer> bst = new BinarySearchTree<>();

    // insert into avl tree
    long avlTime = measureInsertSorted(avl, size);
    System.out.println(size + " inserts into AVL tree took " + avlTime + " milliseconds.");

    // find avl tree
    avlTime = measureRandomFind(avl, size, size);
    System.out.println(numFind + " random finds in AVL tree took " + avlTime + " milliseconds.");

    System.out.println();

    // insert into bst tree
    long bstTime = measureInsertSorted(bst, size);
    System.out.println(size + " inserts into BST took " + bstTime + " milliseconds.");

    // find bst tree
    bstTime = measureRandomFind(bst, size, size);
    System.out.println(numFind + " random finds in BST took " + bstTime + " milliseconds.");

    System.out.println();

  }

  // inserts into the tree a number of times and return the time taken in milliseconds
  public static long measureInsertSorted(Dictionary<Integer, Integer> tree, int insertSize) {
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < insertSize; i++) {
      tree.insert(i, i);
    }
    long endTime = System.currentTimeMillis();
    return (endTime - startTime);
  }

  // perform a number of finds using random integer keys in a specified range
  public static long measureRandomFind(Dictionary<Integer, Integer> tree, int keyMax, int findNumber) {
    Random rand = new Random();
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < findNumber; i++) {
      tree.find(rand.nextInt(keyMax));
    }
    long endTime = System.currentTimeMillis();
    return (endTime - startTime);
  }
}