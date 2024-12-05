package com.ProcessScheduling;

import java.util.Comparator;

//An abstract base class to ease the implementation of the PriorityQueue interface.
public abstract class AbstractPriorityQueue <K,V> implements PriorityQueue<K,V> {
    //concrete implementation of the Entry interface to be used within a PriorityQueue implementation.
    protected static class PQEntry<K,V> implements Entry<K,V> {
        private K k;  // key
        private V v;  // value

        public PQEntry(K key, V value) {
            k = key;
            v = value;
        }

        // methods of the Entry interface
        public K getKey() { return k; }
        public V getValue() { return v; }

        // utilities not exposed as part of the Entry interface
        protected void setKey(K key) { k = key; }
        protected void setValue(V value) { v = value; }
    }

    // instance variable for an AbstractPriorityQueue
    private Comparator<K> comp;

    protected AbstractPriorityQueue(Comparator<K> c) { comp = c; }

    //Creates an empty priority queue based on the natural ordering of its keys
    protected AbstractPriorityQueue() { this(new Comparator<K>() {
        @Override
        public int compare(K o1, K o2) {
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }); }

    //Method for comparing two entries according to key
    protected int compare(Entry<K,V> a, Entry<K,V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    //check whether a key is valid.
    protected boolean checkKey(K key) throws IllegalArgumentException {
        try {
            return (comp.compare(key,key) == 0);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Incompatible key");
        }
    }
    //Tests whether the priority queue is empty.
    //@return true if the priority queue is empty, false otherwise
    @Override
    public boolean isEmpty() { return size() == 0; }
}
