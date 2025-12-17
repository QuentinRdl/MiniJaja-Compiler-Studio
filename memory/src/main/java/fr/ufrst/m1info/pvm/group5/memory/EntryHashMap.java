package fr.ufrst.m1info.pvm.group5.memory;

public class EntryHashMap<K,V> {
    private K key;
    private V value;

    public EntryHashMap(K key, V value){
        this.key=key;
        this.value=value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
