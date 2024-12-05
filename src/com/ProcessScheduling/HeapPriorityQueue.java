package com.ProcessScheduling;

import java.util.ArrayList;
import java.util.Comparator;

//implementation of a priority queue using an array-based heap
public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K,V> {
    //primary collection of priority queue entries
    protected ArrayList<Entry<K,V>> heap = new ArrayList<>();
    //Creates an empty priority queue based on the natural ordering of its keys.
    public HeapPriorityQueue() { super(); }
    //Creates an empty priority queue using the given comparator to order keys.
    //comp comparator defining the order of keys in the priority queue
    public HeapPriorityQueue(Comparator<K> comp) { super(comp); }
    //Creates a priority queue initialized with the respective key-value pairs.
    public HeapPriorityQueue(K[] keys, V[] values) {
        super();
        for (int j=0; j < Math.min(keys.length, values.length); j++)
            heap.add(new PQEntry<>(keys[j], values[j]));
        heapify();
    }
    protected int parent(int j) { return (j-1) / 2; }     // truncating division
    protected int left(int j) { return 2*j + 1; }
    protected int right(int j) { return 2*j + 2; }
    protected boolean hasLeft(int j) { return left(j) < heap.size(); }
    protected boolean hasRight(int j) { return right(j) < heap.size(); }

    //Exchanges the entries at indices i and j of the array list.
    protected void swap(int i, int j) {
        Entry<K,V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    protected void upheap(int j) {
        while (j > 0) {  // continue until reaching root (or break statement)
            int p = parent(j);
            if (compare(heap.get(j), heap.get(p)) >= 0) break;
            swap(j, p);
            j = p;
        }
    }
    protected void downheap(int j) {
        while (hasLeft(j)) { // continue to bottom (or break statement)
            int leftIndex = left(j);
            int smallChildIndex = leftIndex;
            if (hasRight(j)) {
                int rightIndex = right(j);
                if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0)
                    smallChildIndex = rightIndex;
            }
            if (compare(heap.get(smallChildIndex), heap.get(j)) >= 0)
                break;
            swap(j, smallChildIndex);
            j = smallChildIndex;
        }
    }
    //Performs a bottom-up construction of the heap in linear time.
    protected void heapify() {
        int startIndex = parent(size()-1);    // start at PARENT of last entry
        for (int j=startIndex; j >= 0; j--)   // loop until processing the root
            downheap(j);
    }
    //Returns the number of items in the priority queue.
    @Override
    public int size() { return heap.size(); }

    @Override
    public Entry<K,V> min() {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }
    @Override
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K,V> newest = new PQEntry<>(key, value);
        heap.add(newest);
        upheap(heap.size() - 1);
        return newest;
    }
    @Override
    public Entry<K,V> removeMin() {
        if (heap.isEmpty()) return null;
        Entry<K,V> answer = heap.get(0);
        swap(0, heap.size() - 1);        // put minimum item at the end
        heap.remove(heap.size() - 1);  // and remove it from the list;
        downheap(0);                     // then fix new root
        return answer;
    }
}
