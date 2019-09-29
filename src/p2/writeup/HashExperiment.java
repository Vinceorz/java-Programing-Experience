package p2.writeup;

import java.util.Random;
import java.util.Scanner;

import datastructures.dictionaries.MoveToFrontList;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import tests.gitlab.ckpt1.CircularArrayComparatorTests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.function.Supplier;

import cse332.interfaces.worklists.LIFOWorkList;
import cse332.misc.WordReader;
import cse332.types.AlphabeticString;
import cse332.types.NGram;
import datastructures.worklists.ArrayStack;

public class HashExperiment {

  public static final int size = 1000000;

  public static void main(String[] args) throws IOException {
    File f = new File("C:\\Users\\yfzhou\\Documents\\Courses\\CSE 332\\p2-zhouyf98_chenn24\\dictionary2.txt");
    Scanner s = new Scanner(f);
    Dictionary<AlphabeticString, Integer> ht = new ChainingHashTable<>(() -> new MoveToFrontList<>());

    int i = 0;

    long startTime = System.currentTimeMillis();
    while (s.hasNext() && i < size) {
      ht.insert(new AlphabeticString(s.next()), 1);
      i++;
    }
    long endTime = System.currentTimeMillis();

    System.out.println("inserted " + i + " words using " + (endTime - startTime) + " ms.");

    s.close();
  }

}