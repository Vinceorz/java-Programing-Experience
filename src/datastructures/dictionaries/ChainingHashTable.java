package datastructures.dictionaries;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import cse332.datastructures.containers.*;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You must implement a generic chaining hashtable. You may not
 *    restrict the size of the input domain (i.e., it must accept
 *    any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 *    shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 *    than 200,000 elements. After more than 200,000 elements, it should
 *    continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 *    list: http://primes.utm.edu/lists/small/100000.txt
 *    NOTE: Do NOT copy the whole list!
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] table;
    private int tableSize;
    // selected prime numbers for resizing up to ~200,000 items
    private static final int[] primeNumbers
        = {11, 131, 1049, 11071, 100003, 200003};
    private int resizeCount;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.resizeCount = 0;
        this.tableSize = primeNumbers[resizeCount];
        this.size = 0;
        // initialize table: create array and get a new chain as elements
        this.table = (Dictionary<K, V>[]) 
            Array.newInstance(Dictionary.class, this.tableSize);
        for (int i = 0; i < this.tableSize; i++) {
            this.table[i] = this.newChain.get();
        }
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        // resize and rehash when appropriate
        if (this.size >= this.tableSize * 3) {
            resize();
        }
        int bucket = key.hashCode() % this.tableSize;
        // V temp = this.table[bucket].insert(key, value);
        // if (temp != null) {
        //     // previous mapping
        //     return temp;
        // }
        // size++;
        // return temp;
        if (bucket < 0) {
            bucket *= -1;
        }
        if (this.table[bucket].find(key) == null) {
            // no previous mapping
            this.size++;
        }
        return this.table[bucket].insert(key, value);
    }

    /**
     * resize to a bigger prime number (if size is smaller than 200,000)
     * use a different function if bigger than 200,000
     */
    private void resize() {
        this.resizeCount++;
        int oldSize = this.tableSize;
        if (this.resizeCount < primeNumbers.length) {
            // haven't exhausted the prime list yet, use the next prime
            this.tableSize = primeNumbers[this.resizeCount];
        } else {
            // use an alternative function
            this.tableSize = calculateNewSize(this.tableSize);
        }
        // get a new table and reinsert everything in the old table
        Dictionary<K, V>[] oldTable = this.table;
        this.table = (Dictionary<K, V>[]) 
            Array.newInstance(Dictionary.class, this.tableSize);
        for (int i = 0; i < this.tableSize; i++) {
            this.table[i] = newChain.get();
        }
        for (int i = 0; i < oldSize; i++) {
            Iterator<Item<K, V>> it = oldTable[i].iterator();
            while (it.hasNext()) {
                Item<K, V> temp = it.next();
                int bucket = temp.key.hashCode() % this.tableSize;
                if (bucket < 0) {
                    bucket *= -1;
                }
                this.table[bucket].insert(temp.key, temp.value);
            }
        }
    }

    // simple function to calculate a prime-like number for use as table size
    private int calculateNewSize(int oldSize) {
        return oldSize * 19 + 7;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int bucket = key.hashCode() % tableSize;
        if (bucket < 0) {
            bucket *= -1;
        }
        V val = this.table[bucket].find(key);
        return val;
    }

    @Override
    public int hashCode() {
        Iterator<Item<K, V>> it = this.iterator();
        int result = 0;
        int i = 0;
        while (it.hasNext()) {
            Item<K, V> curr = it.next();
            result += curr.hashCode() * 13 * i;
            i++;
        }
        return result;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ChainingHashTableIterator();
    }

    private class ChainingHashTableIterator extends SimpleIterator<Item<K, V>> {
        private int currentBucket;
        private Iterator<Item<K, V>> it;

        public ChainingHashTableIterator() {
            this.currentBucket = 0;
            // find the first nonempty chain with a usable iterator
            // record the index we're at in the table
            // use the last iterator if all chains are empty
            Iterator<Item<K, V>> it = table[this.currentBucket].iterator();
            while (this.currentBucket < (tableSize - 1) && !it.hasNext()) {
                this.currentBucket++;
                it = table[this.currentBucket].iterator();
            }
            // we're at the last chain, or we have a usable iterator
            this.it = it;
        }

        /**
         * move to the next nonempty bucket (if any) if the current iterator is 
         * exhausted.
         */
        @Override
        public boolean hasNext() {
            if (this.it.hasNext()) {
                return true;
            } else if (this.currentBucket < tableSize - 1) {
                // current bucket exhausted but not yet at the last bucket
                while (this.currentBucket < (tableSize - 1) && !this.it.hasNext()) {
                    this.currentBucket++;
                    this.it = table[this.currentBucket].iterator();
                }
                // either reach the end or we have a usable iterator
                return it.hasNext();
            } else {
                // we're at the last bucket and the iterator doesn't have next
                return false;
            }
        }

        @Override
        public Item<K, V> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            Item<K, V> ret = it.next();
            // call the hasNext() function to move to the correct bucket if 
            // current one is exhausted
            this.hasNext();
            return ret;
        }
    }
}
