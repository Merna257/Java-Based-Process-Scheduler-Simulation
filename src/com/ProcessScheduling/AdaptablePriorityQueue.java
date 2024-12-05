package com.ProcessScheduling;

public interface AdaptablePriorityQueue<K,V> extends PriorityQueue<K,V> {
    //Removes the given entry from the priority queue
    //throws IllegalArgumentException if e is not a valid entry for the priority queue.
    void remove(Entry<K, V> entry) throws IllegalArgumentException;

    //Replaces the key of an entry
    void replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException;

    //Replaces the key of an entry
    void replaceValue(Entry<K, V> entry, V value) throws IllegalArgumentException;
}
