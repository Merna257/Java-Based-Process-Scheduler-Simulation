package com.ProcessScheduling;


//Interface for a key-value pair
public interface Entry<K,V> {
    //Returns the key stored in this entry.
    K getKey();
    //Returns the value stored in this entry.
    V getValue();
}
