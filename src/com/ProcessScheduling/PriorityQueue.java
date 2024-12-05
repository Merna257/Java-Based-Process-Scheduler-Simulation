package com.ProcessScheduling;

//Interface for the priority queue ADT.
public interface PriorityQueue <K, V> {
    //Returns the number of items in the priority queue.
    int size();
    //Tests whether the priority queue is empty.
    boolean isEmpty();
    //Inserts a key-value pair and returns the entry created.
    Entry<K,V> insert(K key, V value) throws IllegalArgumentException;

    Entry<K,V> min();

    //Removes and returns an entry with minimal key
    Entry<K,V> removeMin();

}
