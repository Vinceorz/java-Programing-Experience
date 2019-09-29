package datastructures.dictionaries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cse332.interfaces.misc.BString;
import cse332.interfaces.trie.TrieMap;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {

  public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {



    public HashTrieNode() {
        this.pointers = new HashMap<A, HashTrieNode>();
        this.value = null;
    }

    public HashTrieNode(V value) {
      this.pointers = new HashMap<A, HashTrieNode>();
      this.value = value;
    }

    @Override
    public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
      return pointers.entrySet().iterator();
    }
  }

  public HashTrieMap(Class<K> KClass) {
    super(KClass);
    root = new HashTrieNode();
    size = 0;
  }

  @Override
  public V insert(K key, V value) {
    if (key == null || value == null) {
      throw new IllegalArgumentException();
    }

    Iterator<A> it = key.iterator();
    A currentChar;
    HashTrieNode lastNode = (HashTrieNode) root;
    HashTrieNode currentNode;
    V returnValue = null;

    // follow pointers to the correct node
    while (it.hasNext()) {
      currentChar = it.next();
      currentNode = lastNode.pointers.get(currentChar);
      if (currentNode == null) {
        // key doesn't exist yet, put in new node
        lastNode.pointers.put(currentChar, new HashTrieNode());
        currentNode = lastNode.pointers.get(currentChar);
      }

      // current is now guaranteed to exist as a child to lastNode
      lastNode = currentNode;
    }
    // we're at the correct node
    if (lastNode.value != null) {
      // return and fix size if there is a value
      size--;
      returnValue = lastNode.value;
    }
    lastNode.value = value;
    size++;
    return returnValue;
  }

  @Override
  public V find(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }

    A currentA;
    HashTrieNode lastNode = (HashTrieNode) root;
    HashTrieNode current;
    V returnValue = null;

    Iterator<A> it = key.iterator();
    while (it.hasNext()) {
      currentA = it.next();
      current = lastNode.pointers.get(currentA);

      if (current == null) {
        // we're at the end of the trie, key doesn't exist
        return null;
      }

      // not there yet, keep looking
      lastNode = current;
    }

    // we're at the correct node - return
    // value could be null, but returning directly is fine
    return lastNode.value;

  }

  @Override
  public boolean findPrefix(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }

    A currentA;
    HashTrieNode lastNode = (HashTrieNode) root;
    HashTrieNode current;

    Iterator<A> it = key.iterator();
    while (it.hasNext()) {
      currentA = it.next();
      current = lastNode.pointers.get(currentA);

      if (current == null) {
        // key doesn't exist, return
        return false;
      }

      // keep going
      lastNode = current;
    }

    return true;
  }

  @Override
  public void delete(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    Iterator<A> it = key.iterator();
    deleteUselessChildren( (HashTrieNode) root, it);
  }

  // delete any children from its pointers if they can be deleted
  // return if the node itself can be deleted
  private boolean deleteUselessChildren(HashTrieNode node, Iterator<A> it) {
    if (it.hasNext()) {
      // keep going down, or return if child doesn't exist
      A currentChar = it.next();
      HashTrieNode next = node.pointers.get(currentChar);
      if (next == null) {
        // can't find the node to delete
        // this node is removable if it's empty and has no value
        return node.pointers.isEmpty() && node.value == null;
      }
      boolean childIsRemovable = deleteUselessChildren(next, it);
      if (childIsRemovable) {
        // remove it from mapping
        node.pointers.remove(currentChar);
      }
    } else {
      // at the node to delete
      if (node.value != null) {
        // change size only if the node has value
        size--;
      }
      node.value = null;
    }
    // this node is removable if it's empty and has no value
    return node.pointers.isEmpty() && node.value == null;
  }

  @Override
  public void clear() {
    root = new HashTrieNode();
    size = 0;
  }

  @Override
  public int size() {
    return size;
  }
}
