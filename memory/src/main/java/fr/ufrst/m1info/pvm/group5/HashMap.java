package fr.ufrst.m1info.pvm.group5;


import java.util.*;


/**
 * Hello world!
 *
 */
public class HashMap<K,V>
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
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size(){
        return sizeHashMap;
    }

    /**
     * Returns the index of key
     *
     * @param key
     * @return the index of key
     */
    public int getIndex(Object key){
        if (key==null){
            return 0;
        }
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * Double the capacity of the hashMap
     */
    public void resize(){
        Set<EntryHashMap<K,V>> entries=entrySet();
        capacity*=2;
        buckets=new List[capacity];
        for (int i=0;i<capacity;i++){
            buckets[i]=new ArrayList<>();
        }
        sizeHashMap=0;
        for (EntryHashMap<K,V> e : entries){
            put(e.getKey(),e.getValue());
        }
    }

    /**
     * Returns true if this map contains no key-value mappings.
     *
     * @return true if this map contains no key-value mappings
     */
    public boolean isEmpty(){
        return size()==0;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    public V get(Object key){
        int index=getIndex(key);
        for (EntryHashMap<K,V> e : buckets[index]){
            if (key==null && e.getKey()==null){
                return e.getValue();
            }
            if ((key!=null && e.getKey()!=null) && key.equals(e.getKey())){
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
    public boolean containsKey(Object key){
        int index=getIndex(key);
        for (EntryHashMap<K,V> e : buckets[index]){
            if (key==null && e.getKey()==null){
                return true;
            }
            if ((key!=null && e.getKey()!=null) && key.equals(e.getKey())){
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
    public V put(K key, V value){
        int index=getIndex(key);
        V old_value=null;
        for (EntryHashMap<K,V> e : buckets[index]){
            if (key==null && e.getKey()==null){
                old_value=e.getValue();
                e.setValue(value);
                return old_value;
            }
            if ((key!=null && e.getKey()!=null) && key.equals(e.getKey())){
                old_value=e.getValue();
                e.setValue(value);
                return old_value;
            }
        }
        buckets[index].add(new EntryHashMap<>(key,value));
        sizeHashMap++;
        if (sizeHashMap>capacity*loadFactor){
            resize();
        }
        return old_value;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no mapping for key.
     */
    public V remove(Object key){
        int index=getIndex(key);
        for (int i=0; i<buckets[index].size();i++){
            if (key==null && buckets[index].get(i).getKey()==null){
                V value = buckets[index].get(i).getValue();
                buckets[index].remove(i);
                sizeHashMap--;
                return value;
            }
            if ((key!=null && buckets[index].get(i).getKey()!=null) && key.equals(buckets[index].get(i).getKey())){
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
    public void clear(){
        for (int i=0;i<capacity;i++){
            buckets[i]=new ArrayList<>();
        }
        sizeHashMap=0;
    }

    /**
     * Returns a Set view of the entries contained in this map.
     *
     * @return a set view of the entries contained in this map
     */
    public Set<EntryHashMap<K,V>> entrySet(){
        Set<EntryHashMap<K,V>> res=new HashSet<>();
        for (int i=0;i<capacity;i++){
            for (EntryHashMap<K,V> e : buckets[i]){
                res.add(e);
            }
        }
        return res;
    }

    /**
     * Returns a Collection view of the values contained in this map.
     *
     * @return a view of the values contained in this map
     */
    public Collection<V> values(){
        Collection<V> res = new ArrayList<>();
        for (int i=0;i<capacity;i++){
            for (EntryHashMap<K,V> e : buckets[i]){
                res.add(e.getValue());
            }
        }
        return res;
    }

}
