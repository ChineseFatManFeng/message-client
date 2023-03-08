package com.example.annotaionmessage.entity;

import java.io.Serializable;

public class Entry<K,V> implements Serializable {
    private static final long serialVersionUID = 1L;

    private K key;
    private V value;

    public Entry(){}

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public boolean isEmpty(){
        return this.key == null && this.value == null;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

}
