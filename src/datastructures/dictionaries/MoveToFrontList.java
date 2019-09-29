package datastructures.dictionaries;

import java.util.Iterator;

import cse332.datastructures.containers.*;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;


/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private class Node {
        public Item<K, V> Item;
        public Node next;

        // post: constructs a node with given data and null link
        public Node(K key, V value) {
            this(key, value, null);
        }

        // post: constructs a node with given data and given link
        public Node(K key, V value, Node next) {
            this.Item = new Item<K, V>(key, value);
            this.next = next;
        }

    }
    
    private Node head;
    
    public MoveToFrontList() {
        size = 0;
        this.head = null;
    }
      
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (this.head == null) {
            this.head = new Node(key, value);
            size++;
        } else {
            V key_exist = find(key);
            if (key_exist == null) {
                this.head = new Node(key, value, this.head); 
                size++;
            } else {
                V result = head.Item.value;
                head.Item.value = value;
                return result;
            }
        }  
        return null;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (this.head == null) {
            return null;
        } else if (this.head != null && head.Item.key.equals(key)) { 
            return head.Item.value; 
        } else {
            Node previous= this.head;
            Node current= previous.next;
            while(current != null) {
                if(current.Item.key.equals(key)){
                    previous.next = current.next;
                    current.next = head;
                    head = current;
                    return head.Item.value;
                }
                previous = current;
                current = current.next;
            }
            return null;
        }
    }
    
    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontIterator();
    }
    
    private class MoveToFrontIterator extends SimpleIterator<Item<K, V>> {
        private Node front;

        public MoveToFrontIterator() {
            front = head;
        }

        @Override
        public Item<K, V> next() {
            Item<K, V> result = front.Item;
            front = front.next;
            return result;
        }

        @Override
        public boolean hasNext() {
            return front != null;
        }
    }
}
