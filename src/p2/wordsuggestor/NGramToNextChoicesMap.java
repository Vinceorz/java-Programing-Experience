package p2.wordsuggestor;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.Dictionary;
import cse332.misc.LargeValueFirstItemComparator;
import cse332.sorts.InsertionSort;
import cse332.types.AlphabeticString;
import cse332.types.NGram;

public class NGramToNextChoicesMap {
    private final Dictionary<NGram, Dictionary<AlphabeticString, Integer>> map;
    private final Supplier<Dictionary<AlphabeticString, Integer>> newInner;

    public NGramToNextChoicesMap(
            Supplier<Dictionary<NGram, Dictionary<AlphabeticString, Integer>>> newOuter,
            Supplier<Dictionary<AlphabeticString, Integer>> newInner) {
        this.map = newOuter.get();
        this.newInner = newInner;
    }

    /**
     * Increments the count of word after the particular NGram ngram.
     */
    public void seenWordAfterNGram(NGram ngram, String word) {
        if (ngram == null || word == null) {
            throw new IllegalArgumentException();
        }
        AlphabeticString given_word = new AlphabeticString(word);
        if (this.map.find(ngram) == null) {
            Dictionary<AlphabeticString, Integer> new_word = newInner.get();
            new_word.insert(given_word, 0);
            this.map.insert(ngram, new_word);
        }
        Dictionary<AlphabeticString, Integer> words_after = this.map.find(ngram);      
        if (words_after.find(given_word) == null) {
            words_after.insert(given_word, 0);
        }
        Integer count = this.map.find(ngram).find(given_word);
        words_after.insert(given_word, count + 1);
    }

    /**
     * Returns an array of the DataCounts for this particular ngram. Order is
     * not specified.
     *
     * @param ngram
     *            the ngram we want the counts for
     * 
     * @return An array of all the Items for the requested ngram.
     */
    public Item<String, Integer>[] getCountsAfter(NGram ngram) {
        if (ngram == null) {
            throw new IllegalArgumentException();
        }
        Item<String, Integer>[] result;
        Dictionary<AlphabeticString, Integer> words_after = this.map.find(ngram);
        if (words_after == null) {
            return (Item<String, Integer>[]) new Item[0];
        } else {
            result = (Item<String, Integer>[]) new Item[words_after.size()];
        }
        Iterator<Item<AlphabeticString, Integer>>  it  = words_after.iterator();
        int i = 0;
        while (it.hasNext()) {
            Item<AlphabeticString,Integer> word = it.next();
            result[i] = new Item<String, Integer>(word.key.toString(), word.value);
            i++;
        }
        return result;
    }

    public String[] getWordsAfter(NGram ngram, int k) {
        Item<String, Integer>[] afterNGrams = getCountsAfter(ngram);

        Comparator<Item<String, Integer>> comp = new LargeValueFirstItemComparator<String, Integer>();
        if (k < 0) {
            InsertionSort.sort(afterNGrams, comp);
        }
        else {
            // You must fix this line toward the end of the project
            throw new NotYetImplementedException();
        }

        String[] nextWords = new String[k < 0 ? afterNGrams.length : k];
        for (int l = 0; l < afterNGrams.length && l < nextWords.length
                && afterNGrams[l] != null; l++) {
            nextWords[l] = afterNGrams[l].key;
        }
        return nextWords;
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
