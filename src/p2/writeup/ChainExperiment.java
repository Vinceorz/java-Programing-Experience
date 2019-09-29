package p2.writeup;

import java.util.Random;

import datastructures.dictionaries.MoveToFrontList;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;

public class ChainExperiment {

  public static final int size = 1000000;
  public static final int numFind = 1000000;

  public static void main(String[] args) {
    
    // test hash table using MTFList
    Dictionary<Integer, Integer> ht = new ChainingHashTable<>(() -> new MoveToFrontList<>());
    long mtfTime = measureRandomInserts(ht, size);
    System.out.println("Inserting 10000 random keys into hash table using MTFList took " + mtfTime + " ms");
    mtfTime = measureRandomFinds(ht, size, numFind);
    System.out.println("Finding 100000 random keys in hash table using MTFList took " + mtfTime + " ms");
    System.out.println();


    ht = new ChainingHashTable<Integer, Integer>(() -> new BinarySearchTree<>());
    long bstTime = measureRandomInserts(ht, size);
    System.out.println("Inserting 10000 random keys into hash table using BST took " + bstTime + " ms");
    bstTime = measureRandomFinds(ht, size, numFind);
    System.out.println("Finding 100000 random keys in hash table using BST took " + bstTime + " ms");
    System.out.println();

    ht = new ChainingHashTable<Integer, Integer>(() -> new AVLTree<>());
    long avlTime = measureRandomInserts(ht, size);
    System.out.println("Inserting 10000 random keys into hash table using AVL tree took " + avlTime + " ms");
    avlTime = measureRandomFinds(ht, size, numFind);
    System.out.println("Finding 100000 random keys in hash table using AVL tree took " + avlTime + " ms");
    System.out.println();
  }

  // perform a number of inserts using randomly generated keys
  public static long measureRandomInserts(Dictionary<Integer, Integer> dict, int size) {
    Random rand = new Random();
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < size; i++) {
      dict.insert(rand.nextInt(size), rand.nextInt(size));
    }
    long endTime = System.currentTimeMillis();
    return (endTime - startTime);
  }

  // performs a number of random finds
  public static long measureRandomFinds(Dictionary<Integer, Integer> dict, int size, int numFind) {
    Random rand = new Random();
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < numFind; i++) {
      dict.find(rand.nextInt(size));
    }
    long endTime = System.currentTimeMillis();
    return (endTime - startTime);
  }
}