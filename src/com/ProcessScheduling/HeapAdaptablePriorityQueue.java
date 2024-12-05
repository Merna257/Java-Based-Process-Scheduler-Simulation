package com.ProcessScheduling;

import java.util.Comparator;

//implementation of an adaptable priority queue using an array-based heap
public class HeapAdaptablePriorityQueue<K, V> extends HeapPriorityQueue<K, V>
        implements AdaptablePriorityQueue<K, V> {

    //Extension of the PQEntry to include location information.
    protected static class AdaptablePQEntry<K, V> extends AbstractPriorityQueue.PQEntry<K, V> {
        private int index;  // entry's current index within the heap

        public AdaptablePQEntry(K key, V value, int j) {
            super(key, value);   // this sets the key and value
            index = j;           // this sets the new field
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int j) {
            index = j;
        }
    }
    //Creates an empty adaptable priority queue using natural ordering of keys.
    public HeapAdaptablePriorityQueue() {
        super();
    }
    //Creates an empty adaptable priority queue using the given comparator to order keys.
    //comp comparator defining the order of keys in the priority queue.
    public HeapAdaptablePriorityQueue(Comparator<K> comp) {
        super(comp);
    }
    //return the entry cast as an AdaptablePQEntry instance
    //throws IllegalArgumentException if the given entry was not valid
    protected AdaptablePQEntry<K, V> validate(Entry<K, V> entry)
            throws IllegalArgumentException {
        if (!(entry instanceof AdaptablePQEntry))
            throw new IllegalArgumentException("Invalid entry");
        AdaptablePQEntry<K, V> locator = (AdaptablePQEntry<K, V>) entry;   // safe
        int j = locator.getIndex();
        if (j >= heap.size() || heap.get(j) != locator)
            throw new IllegalArgumentException("Invalid entry");
        return locator;
    }
     //Exchanges the entries at indices i and j of the array list.
    @Override
    protected void swap(int i, int j) {
        super.swap(i, j);
        ((AdaptablePQEntry<K, V>) heap.get(i)).setIndex(i);
        ((AdaptablePQEntry<K, V>) heap.get(j)).setIndex(j);
    }
     //Restores the heap property by moving the entry at index j upward/downward.
    protected void bubble(int j) {
        if (j > 0 && compare(heap.get(j), heap.get(parent(j))) < 0)
            upheap(j);
        else
            downheap(j);
    }


     //Inserts a key-value pair and return the entry created.
    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> newest = new AdaptablePQEntry<>(key, value, heap.size());
        heap.add(newest);
        // up-heap newly added entry
        upheap(heap.size() - 1);
        return newest;
    }

     //Removes the given entry from the priority queue.
    @Override
    public void remove(Entry<K, V> entry) throws IllegalArgumentException {
        AdaptablePQEntry<K, V> locator = validate(entry);
        int j = locator.getIndex();
        if (j == heap.size() - 1)
            heap.remove(heap.size() - 1);
        else {
            swap(j, heap.size() - 1); // swap entry to last position
            heap.remove(heap.size() - 1);
            bubble(j);
        }
    }
    //Replaces the key of an entry.
    @Override
    public void replaceKey(Entry<K, V> entry, K key)
            throws IllegalArgumentException {
        AdaptablePQEntry<K, V> locator = validate(entry);
        checkKey(key);
        locator.setKey(key);
        bubble(locator.getIndex());
    }
    //Replaces the value of an entry.
    @Override
    public void replaceValue(Entry<K, V> entry, V value)
            throws IllegalArgumentException {
        AdaptablePQEntry<K, V> locator = validate(entry);
        locator.setValue(value);
    }
}
