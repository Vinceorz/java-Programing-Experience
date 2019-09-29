package p2.writeup;

import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import datastructures.dictionaries.MoveToFrontList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Supplier;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;
import cse332.misc.WordReader;
import cse332.types.AlphabeticString;
import cse332.types.NGram;
import p2.wordsuggestor.*;

public class testDictionary {
    // BST, AVLTree, ChainingHashTable, and HashTrieMap
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //Dictionary<String, Integer> test = new ChainingHashTable<String, Integer>(() -> new MoveToFrontList<>());
        //Dictionary<String, Integer> test = new BinarySearchTree();
        //Dictionary<String, Integer> test = new AVLTree();
        Dictionary<AlphabeticString, Integer> test = new HashTrieMap(AlphabeticString.class);
        WordReader reader = new WordReader(new FileReader("alice.txt"));
        int i = 0;
        int N = 50000;
        long startTime = System.currentTimeMillis();
        while (reader.hasNext() && i < N) {
            test.insert(new AlphabeticString(reader.next()), 1);
            i++;
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
