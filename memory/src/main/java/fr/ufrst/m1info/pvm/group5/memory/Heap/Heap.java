package fr.ufrst.m1info.pvm.group5.memory.Heap;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the heap
 */
public class Heap {
    final int totalSize;
    int availableSize;
    HeapElement currentElement;
    byte[] storage;
    Map<Integer, HeapElement> addresses;

    /**
     * Creates a heap of the given size
     * @param totalSize total size of the heap
     */
    public Heap(int totalSize) {
        this.totalSize = totalSize;
        this.availableSize = totalSize;
        this.storage = new byte[totalSize];
        addresses = new HashMap<>();
    }

    /**
     * Creates a heap. The default size of the heap is 2Ko.
     */
    public Heap(){
        this(2048);
    }

    private int lastExternalAddress = -1;
    /**
     * Returns an int to create a new external address
     * @return new external address created
     */
    private int newExtAddress(){
        lastExternalAddress++;
        return lastExternalAddress;
    }

    /**
     * Allocate a space in the heap
     * @param size size of the space to allocate
     * @param type type of value stored in the memory space
     * @return address of the created memory space
     * @throws InsufficientMemoryException throws an exception if the given size is superior to the available one
     */
    public int allocate(int size, DataType type) throws InsufficientMemoryException{
        return 0;
    }

    /**
     * Remove a memory space from the heap
     * @param address address to the element in the heap
     * @throws InsufficientMemoryException throws an exception if the address is not referenced in the heap
     */
    public void free(int address) throws InsufficientMemoryException{

    }

    /**
     * Notify the garbage collector a new reference has been added to a memory space
     * @param address address of an element in the heap
     * @throws InvalidMemoryAddressException throws an exception if the address is not referenced in the heap
     */
    public void addReference(int address) throws InvalidMemoryAddressException{
    }

    /**
     * Notify the garbage collector a reference has been removed from a memory space
     * @param address address of an element in the heap
     * @throws InvalidMemoryAddressException throws an exception if the address is not referenced in the heap
     */
    public void removeReference(int address) throws InvalidMemoryAddressException{
    }

    /**
     * Get the value of an element at a given address
     * @param address address located within the heap
     * @return value stored at the given address
     * @throws InvalidMemoryAddressException throws an exception if the address is not referenced in the heap
     */
    public Value getValue(int address) throws InvalidMemoryAddressException{
        return null;
    }

    /**
     * Get a value of an element given by the starting address of an object and an offset
     * @param address address of a block of the heap
     * @param offset offset of the element
     * @return value stored at the given address
     * @throws InvalidMemoryAddressException throws an exception if the address is not referenced in the heap
     */
    public Value getValue(int address, int offset) throws InvalidMemoryAddressException{
        return null;
    }

    /**
     * Relocate every allocated element of the heap at the start of the array, and merge the remaining ones at the end.
     * This allows to minimize the internal fragmentation of the heap.
     * This operation is extremely expensive, and should only be called if necessary
     */
    private void defragment(){

    }
}
