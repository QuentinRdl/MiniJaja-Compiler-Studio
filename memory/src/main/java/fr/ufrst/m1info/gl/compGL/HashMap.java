package fr.ufrst.m1info.gl.compGL;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Hello world!
 *
 */
public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable
{
    private int capacity;
    private float loadFactor;
    private List<EntryHashMap<K,V>>[] buckets;
    private int sizeHashMap;

    /**
     * Constructs an empty HashMap with the specified initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     */
    public HashMap(int initialCapacity, float loadFactor){
        if (initialCapacity<0 || loadFactor<=0.0){
            throw new IllegalArgumentException("Arguments cannot be negative");
        }
        capacity=initialCapacity;
        this.loadFactor=loadFactor;
        buckets=new List[capacity];
        for (int i=0;i<capacity;i++){
            buckets[i]=new ArrayList<>();
        }
        sizeHashMap=0;
    }

    /**
     * Constructs an empty HashMap with the specified initial capacity and the default load factor (0.75).
     *
     * @param initialCapacity the initial capacity.
     */
    public HashMap(int initialCapacity){
        this(initialCapacity,0.75F);
    }

    /**
     * Constructs an empty HashMap with the default initial capacity (16) and the default load factor (0.75)
     */
    public HashMap(){
        this(16,0.75F);
    }

    /**
     * Constructs a new HashMap with the same mappings as the specified Map.
     * The HashMap is created with default load factor (0.75) and an initial capacity sufficient to hold the mappings in the specified Map.
     *
     * @param m the map whose mappings are to be placed in this map
     */
    public HashMap(Map<? extends K,? extends V> m){
        if (m==null){
            throw new NullPointerException("Argument cannot be null");
        }
        capacity=16;
        this.loadFactor=0.75F;
        buckets=new List[capacity];
        for (int i=0;i<capacity;i++){
            buckets[i]=new ArrayList<>();
        }
        sizeHashMap=0;
        putAll(m);
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    @Override
    public int size(){
        return sizeHashMap;
    }

    /**
     * Returns true if this map contains no key-value mappings.
     *
     * @return true if this map contains no key-value mappings
     */
    @Override
    public boolean isEmpty(){
        return size()==0;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    @Override
    public V get(Object key){
        int index=key.hashCode() % capacity;
        for (EntryHashMap<K,V> e : buckets[index]){
            if (key.equals(e.getKey())){
                return e.getValue();
            }
        }
        return null;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(Object key){
        int index=key.hashCode() % capacity;
        for (EntryHashMap<K,V> e : buckets[index]){
            if (key.equals(e.getKey())){
                return true;
            }

        }
        return false;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    @Override
    public V put(K key, V value){
        int index=key.hashCode() % capacity;
        V old_value=null;
        for (EntryHashMap<K,V> e : buckets[index]){
            if (key.equals(e.getKey())){
                old_value=e.getValue();
                e.setValue(value);
                return old_value;
            }
        }
        buckets[index].add(new EntryHashMap<>(key,value));
        sizeHashMap++;
        return old_value;
    }

    /**
     * Copies all of the mappings from the specified map to this map. These mappings will replace any mappings that this map had for any of the keys currently in the specified map.
     *
     * @param m mappings to be stored in this map
     */
    @Override
    public void putAll(Map<? extends K,? extends V> m){
        for (K key : m.keySet()){
            put(key,m.get(key));
        }
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no mapping for key.
     */
    @Override
    public V remove(Object key){
        int index=key.hashCode() % capacity;
        for (int i=0; i<buckets[index].size();i++){
            if (key.equals(buckets[index].get(i).getKey())){
                V value = buckets[index].get(i).getValue();
                buckets[index].remove(i);
                sizeHashMap--;
                return value;
            }
        }
        return null;
    }

    /**
     * Removes all of the mappings from this map. The map will be empty after this call returns.
     */
    @Override
    public void clear(){
        for (int i=0;i<capacity;i++){
            buckets[i]=new ArrayList<>();
        }
        sizeHashMap=0;
    }

    /**
     * Returns true if this map maps one or more keys to the specified value.
     *
     * @param value value whose presence in this map is to be tested
     * @return true if this map maps one or more keys to the specified value
     */
    @Override
    public boolean containsValue(Object value){
        for (int i=0;i<capacity;i++){
            for (EntryHashMap<K,V> e : buckets[i]){
                if (value.equals(e.getValue())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * TODO : Returns a Set view of the keys contained in this map.
     *
     * @return a set view of the keys contained in this map
     */
    @Override
    public Set<K> keySet(){
        Set<K> res=new HashSet<>();
        for (int i=0;i<capacity;i++){
            for (EntryHashMap<K,V> e : buckets[i]){
                res.add(e.getKey());
            }
        }
        return res;
    }

    /**
     * TODO : Returns a Collection view of the values contained in this map.
     *
     * @return a view of the values contained in this map
     */
    @Override
    public Collection<V> values(){
        Collection<V> res = new ArrayList<>();
        for (int i=0;i<capacity;i++){
            for (EntryHashMap<K,V> e : buckets[i]){
                res.add(e.getValue());
            }
        }
        return res;
    }

    /**
     * TODO : Returns a Set view of the mappings contained in this map.
     *
     * @return a set view of the mappings contained in this map
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K,V>> res = new HashSet<>();
        return res;
    }

    /**
     * Returns the value to which the specified key is mapped, or defaultValue if this map contains no mapping for the key
     *
     * @param key the key whose associated value is to be returned
     * @param defaultValue the default mapping of the key
     * @return the value to which the specified key is mapped, or defaultValue if this map contains no mapping for the key
     */
    public V getOrDefault(Object key, V defaultValue) {
        V value = get(key);
        if (value==null){
            return defaultValue;
        }
        return value;
    }

    /**
     * If the specified key is not already associated with a value (or is mapped to null) associates it with the given value and returns null,
     * else returns the current value.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key
     */
    public V putIfAbsent(K key, V value) {
        int index=key.hashCode() % capacity;
        V old_value=null;
        for (EntryHashMap<K,V> e : buckets[index]){
            if (key.equals(e.getKey())){
                old_value=e.getValue();
                if (old_value==null){
                    e.setValue(value);
                }
                return old_value;
            }
        }
        buckets[index].add(new EntryHashMap<>(key,value));
        sizeHashMap++;
        return old_value;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to the specified value.
     *
     * @param key key with which the specified value is associated
     * @param value value expected to be associated with the specified key
     * @return true if the value was removed
     */
    public boolean remove(Object key, Object value){
        int index=key.hashCode() % capacity;
        for (int i=0; i<buckets[index].size();i++){
            if (key.equals(buckets[index].get(i).getKey())){
                V old_value = buckets[index].get(i).getValue();
                if (value.equals(old_value)){
                    buckets[index].remove(i);
                    sizeHashMap--;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Replaces the entry for the specified key only if currently mapped to the specified value.
     *
     * @param key key with which the specified value is associated
     * @param oldValue value expected to be associated with the specified key
     * @param newValue value to be associated with the specified key
     * @return true if the value was replaced
     */
    public boolean replace(K key, V oldValue, V newValue){
        int index=key.hashCode() % capacity;
        for (EntryHashMap<K,V> e : buckets[index]){
            if (key.equals(e.getKey())){
                if (oldValue.equals(e.getValue())){
                    e.setValue(newValue);
                    return true;
                }
                return false;
            }
        }
        return false;
    }


    /**
     * Replaces the entry for the specified key only if it is currently mapped to some value.
     *
     * @param key key with which the specified value is associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key.
     */
    public V replace(K key, V value){
        int index=key.hashCode() % capacity;
        V old_value=null;
        for (EntryHashMap<K,V> e : buckets[index]){
            if (key.equals(e.getKey())){
                old_value=e.getValue();
                e.setValue(value);
                return old_value;
            }
        }
        return old_value;
    }

    /**
     * If the specified key is not already associated with a value (or is mapped to null),
     * attempts to compute its value using the given mapping function and enters it into this map unless null.
     * If the function returns null no mapping is recorded.
     *
     * @param key key with which the specified value is to be associated
     * @param mappingFunction the function to compute a value
     * @return the current (existing or computed) value associated with the specified key
     */
    public V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction){
        V value=mappingFunction.apply(key);
        if (value!=null){
            putIfAbsent(key,value);
        }
        return get(key);
    }

    /**
     * If the value for the specified key is present and non-null, attempts to compute a new mapping given the key and its current mapped value
     *
     * @param key key with which the specified value is to be associated
     * @param remappingFunction the function to compute a value
     * @return the new value associated with the specified key, or null if none
     */
    public V computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction){
        int index=key.hashCode() % capacity;
        V old_value=null;
        for (int i=0;i<buckets[index].size();i++){
            EntryHashMap<K,V> e=buckets[index].get(i);
            if (key.equals(e.getKey())){
                old_value=e.getValue();
                if (old_value==null){
                    return null;
                }
                V new_value = remappingFunction.apply(key,old_value);
                if (new_value==null){
                    buckets[index].remove(i);
                    sizeHashMap--;
                    return null;
                }
                buckets[index].get(i).setValue(new_value);
                return new_value;
            }
        }
        return null;
    }

    /**
     * Attempts to compute a mapping for the specified key and its current mapped value (or null if there is no current mapping)
     * If the function returns null, the mapping is removed (or remains absent if initially absent).
     *
     * @param key key with which the specified value is to be associated
     * @param remappingFunction the function to compute a value
     * @return the new value associated with the specified key, or null if none
     */
    public V compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction){
        int index=key.hashCode() % capacity;
        V old_value=null;
        for (int i=0;i<buckets[index].size();i++){
            EntryHashMap<K,V> e=buckets[index].get(i);
            if (key.equals(e.getKey())){
                old_value=e.getValue();
                V new_value = remappingFunction.apply(key,old_value);
                if (new_value==null){
                    buckets[index].remove(i);
                    sizeHashMap--;
                    return null;
                }
                buckets[index].get(i).setValue(new_value);
                return new_value;
            }
        }
        V new_value = remappingFunction.apply(key,old_value);
        if (new_value!=null){
            putIfAbsent(key,new_value);
        }
        return new_value;
    }

    /**
     * If the specified key is not already associated with a value or is associated with null, associates it with the given non-null value.
     * Otherwise, replaces the associated value with the results of the given remapping function, or removes if the result is null
     * If the function returns null the mapping is removed
     *
     * @param key key with which the resulting value is to be associated
     * @param value the non-null value to be merged with the existing value associated with the key or, if no existing value or a null value is associated with the key, to be associated with the key
     * @param remappingFunction the function to recompute a value if present
     * @return the new value associated with the specified key, or null if no value is associated with the key
     */
    public V merge(K key, V value, BiFunction<? super V,? super V,? extends V> remappingFunction){
        int index=key.hashCode() % capacity;
        V old_value=null;
        for (int i=0;i<buckets[index].size();i++){
            EntryHashMap<K,V> e=buckets[index].get(i);
            if (key.equals(e.getKey())){
                old_value=e.getValue();
                if (old_value==null){
                    V new_value = value;
                    putIfAbsent(key,new_value);
                    return new_value;
                }
                V new_value = remappingFunction.apply(old_value,value);
                if (new_value==null){
                    buckets[index].remove(i);
                    sizeHashMap--;
                    return null;
                }
                buckets[index].get(i).setValue(new_value);
                return new_value;
            }
        }
        V new_value = value;
        putIfAbsent(key,new_value);
        return new_value;
    }

    /**
     * TODO : Performs the given action for each entry in this map until all entries have been processed or the action throws an exception.
     * Unless otherwise specified by the implementing class, actions are performed in the order of entry set iteration (if an iteration order is specified.)
     * Exceptions thrown by the action are relayed to the caller.
     *
     * @param action The action to be performed for each entry
     */
    public void forEach(BiConsumer<? super K,? super V> action){

    }

    /**
     * Replaces each entry's value with the result of invoking the given function on that entry until all entries have been processed or the function throws an exception.
     * Exceptions thrown by the function are relayed to the caller.
     *
     * @param function the function to apply to each entry
     */
    public void replaceAll(BiFunction<? super K,? super V,? extends V> function){
        for (int i=0;i<capacity;i++){
            for (EntryHashMap<K,V> e : buckets[i]){
                e.setValue(function.apply(e.getKey(),e.getValue()));
            }
        }
    }

    /**
     * TODO : Returns a shallow copy of this HashMap instance: the keys and values themselves are not cloned.
     *
     * @return a shallow copy of this map
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
