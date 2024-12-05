package com.ProcessScheduling;

public interface Queue <E> {
    int size();
    boolean isEmpty();
    void enqueue(E e);
    E first();
    E dequeue();
}
