package fr.ufrst.m1info.pvm.group5.memory.Heap;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        currentElement = new HeapElement(0, newExtAddress(), totalSize);
        addresses.put(0, currentElement);
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
        if(size > availableSize)
            throw new InsufficientMemoryException(availableSize, size);
        // Finding a suitable space for the allocation
        HeapElement first = currentElement;
        while(currentElement.size() < size){
            currentElement = currentElement.getNext();
            if(currentElement == first){ // External fragmentation (oh no :c)
                defragment();
                return allocate(size, type);
            }
        }
        // Realising the allocation
        HeapElement allocation = currentElement.split(size);
        allocation.externalAddress = newExtAddress();
        addresses.put(allocation.externalAddress, allocation);
        allocation.allocate(type);

        return allocation.externalAddress;
    }

    private HeapElement checkAddress(int externalAddress) throws InvalidMemoryAddressException{
        HeapElement target = addresses.get(externalAddress);
        if(target == null)
            throw new UnmappedMemoryAddressException(externalAddress);
        return target;
    }

    /**
     * Remove a memory space from the heap
     * @param address address to the element in the heap
     * @throws InvalidMemoryAddressException throws an exception if the address is not referenced in the heap
     */
    public void free(int address) throws InvalidMemoryAddressException{
        HeapElement target = checkAddress(address);
        target.free();
        addresses.remove(address);
        currentElement = target; //ensures "currentElements" points to a valid element
    }                            //(could be one of the merged blocks)

    /**
     * Removed a memory space from the heap
     * @param target memory space to remove
     */
    private void free(HeapElement target){
        target.free();
        addresses.remove(target.externalAddress);
        currentElement = target;
    }

    /**
     * Notify the garbage collector a new reference has been added to a memory space
     * @param address address of an element in the heap
     * @throws InvalidMemoryAddressException throws an exception if the address is not referenced in the heap
     */
    public void addReference(int address) throws InvalidMemoryAddressException{
        HeapElement target = checkAddress(address);
        target.references++;
    }

    /**
     * Notify the garbage collector a reference has been removed from a memory space
     * @param address address of an element in the heap
     * @throws InvalidMemoryAddressException throws an exception if the address is not referenced in the heap
     */
    public void removeReference(int address) throws InvalidMemoryAddressException{
        HeapElement target = checkAddress(address);
        target.references--;
        if(target.references == 0)
            free(target);
    }

    /**
     * Get a value of an element given by the starting address of an object and an offset
     * @param address address of a block of the heap
     * @param offset offset of the element
     * @return value stored at the given address
     * @throws InvalidMemoryAddressException throws an exception if the address is not referenced in the heap
     */
    public Value getValue(int address, int offset) throws InvalidMemoryAddressException{
        HeapElement target = checkAddress(address);
        if(!target.contains(address + offset))
            throw new UnmappedMemoryAddressException("Offset " + offset + " is beyond the allocated block for address " + address, address + offset);
        byte val = storage[target.internalAddress + offset];
        return switch (target.getStorageType()){
            case DataType.INT -> new Value(val);
            case DataType.BOOL -> new Value(val == 1);
            default -> new Value();
        };
    }

    /**
     * Sets the value of an element given by the starting address of an objet and an offset
     * @param address address of a block of the heap
     * @param offset offset of the element
     * @param value value to store at the given address
     * @throws InvalidMemoryAddressException throws an exception if the address is not allocated in the heap
     */
    public void setValue(int address, int offset, Value value) throws InvalidMemoryAddressException{
        HeapElement target = checkAddress(address);
        if(!target.contains(address + offset))
            throw new UnmappedMemoryAddressException("Offset " + offset + " is beyond the allocated block for address " + address, address + offset);
        storage[target.internalAddress + offset] = switch (target.getStorageType()){
            case INT -> (byte) value.valueInt;
            case BOOL -> (byte)((value.valueBool)? 1 : 0);
            default -> 0;
        };
    }

    /**
     * Copies the content of the memory from an address to another
     * No check is done to ensure the address is free, valid, and does not create a new block to store the information of the moved content
     * @param origin memory block to move
     * @param target address to move the block to
     */
    private void copy(HeapElement origin, int target){
        int originAddress = origin.internalAddress;
        for(int i = 0; i < origin.size(); i++){
            storage[originAddress + i] =  storage[target + i];
        }
    }

    /**
     * Relocate every allocated element of the heap at the start of the array, and merge the remaining ones at the end.
     * This allows to minimize the internal fragmentation of the heap.
     * This operation is extremely expensive, and should only be called if necessary
     */
    private void defragment(){
        List<Integer> allBlocks = new ArrayList<>(addresses.keySet());
        allBlocks.sort(Integer::compareTo);
        int lastAvailableAddress = 0;
        for(int i : allBlocks){
            HeapElement current = addresses.get(i);
            if(current.internalAddress == lastAvailableAddress) {
                lastAvailableAddress += current.size();
                continue;
            }
            copy(current, lastAvailableAddress);
            current.internalAddress += lastAvailableAddress;
            lastAvailableAddress += current.size();
        }
    }

    /**
     * Check if any element were left in the heap, and not de-referenced
     * @return list of errors containing the elements that were not de-referenced
     */
    public List<String> sanitize(){
        List<String> errors = new ArrayList<>();
        HeapElement current = currentElement;
        HeapElement start = current;
        do{
            if(!current.isFree())
                errors.add(current.size() + " bytes still allocated at address " + current.externalAddress);
            current = current.getNext();
        }while(current != start);
        return errors;
    }
}
