package fr.ufrst.m1info.pvm.group5.memory.Heap;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the heap.
 * Usage :
 *  - Declare an array (ex : int array;) :
 *      1. Allocate a block using [allocate] with the desired type. The result of the method will give you
 *         an address to access the block
 *      2. Add a reference to the block using [addReference].
 *  - Synonymy (ex : int array2 = array):
 *      1. Add a reference to the block used by the synonymy using [addReference].
 *  - Changing the value of an array (ex : array2 = otherArray) :
 *      1. Remove a reference from the previous value of array2 using [removeReference].
 *      2. Change the address pointed by array2
 *      3. Add a reference to otherArray using [addReference]
 *  - "Freeing" an array :
 *      1. Remove a reference to the array using [removeReference]
 *  - Check the content of the heap :
 *      1. Call [getStorageSnapshot] to see the full content of the memory.
 *      2. Call [getBlocksSnapshot] to see the internal handling of the memory.
 *  - Ensure everything in the heap has been freed at the end of my program :
 *      1. Call [sanitize] to get an array of all errors
 *      2. If the array is empty, no allocated block have not been freed
 *      3. Otherwise, the content will tell you what blocks have been forgotten, and at which address
 */
public class Heap {
    /**
     * Total size of the heap, defined at creation
     */
    final int totalSize;
    /**
     * Memory space currently available in the heap
     */
    private int availableSize;
    /**
     * Element currently pointed.
     * Its purpose is to make looking for an empty block for an allocation easier.
     * It always points to an empty element, unless the heap is full.
     */
    private HeapElement currentElement;
    /**
     * Array used as a storage for the array.
     */
    private final byte[] storage;
    /**
     * Map allowing to transcribe external addresses to blocks.
     */
    private final Map<Integer, HeapElement> externalAddresses;

    // Getters for the content of the heap

    /**
     * Internal function returning the element with the internal address 0
     * @return HeapElement with internal address 0
     */
    private HeapElement getFirstElement(){
        HeapElement result = currentElement;
        while(result.internalAddress != 0) result =  result.next;
        return result;
    }

    public int getTotalSize(){
        return totalSize;
    }

    public int getAvailableSize(){
        return availableSize;
    }

    /**
     * Returns an array containing a snapshot of the storage at the current instant
     * @return Array equal to the current memory of the heap
     */
    public byte[] getStorageSnapshot(){
        return storage.clone();
    }

    /**
     * Class allowing to describe the state of a Heap element
     * @param address internal address of the element
     * @param isAllocated true if the element is currently allocated, false otherwise
     * @param size size of the block
     * @param isPointed true if the heap is currently pointing at the element. Only one is pointed simultaneously
     * @param type type of value stored in the heap at the address corresponding to the block
     */
    public record ElementRecord(int address, boolean isAllocated, int size, boolean isPointed, DataType type) {
        public String toString(){
            return (isPointed? "<" : "[") + "@" + address + ": " + size + " " + (isAllocated ? type + " Allocated" : "Free") + (isPointed? ">" : "]");
        }

        public boolean equals(Object o){
            return o == this || (o instanceof ElementRecord && equals((ElementRecord)o));
        }

        public boolean equals(ElementRecord e){
            return address == e.address && size == e.size && isAllocated == e.isAllocated && isPointed == e.isPointed && type == e.type;
        }
    }

    /**
     * Gets a snapshot of the current internal state of the heap, in the form of a linked list of records
     * @return snapshot of the current internal state of the heap
     */
    public List<ElementRecord> getBlocksSnapshot(){
        List<ElementRecord> elements = new ArrayList<>();
        HeapElement first = getFirstElement();
        HeapElement curr =  first;
        do{
            elements.add(new ElementRecord(curr.internalAddress, !curr.isFree(), curr.size, curr == currentElement, curr.getStorageType()));
            curr = curr.next;
        }while (curr != first);
        return elements;
    }

    /**
     * Creates a heap of the given size
     * @param totalSize total size of the heap
     */
    public Heap(int totalSize) {
        this.totalSize = totalSize;
        this.availableSize = totalSize;
        this.storage = new byte[totalSize];
        externalAddresses = new HashMap<>();
        currentElement = new HeapElement(0, newExtAddress(), totalSize);
        externalAddresses.put(0, currentElement);
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
     * Allocate a memory block in the heap.
     * If there is not enough memory available, the allocation will fail.
     * Principle : Iterate on the heap from the current pointed element, until a block of the right size is found.
     * If a complete loop is done, it means the heap is too fragmented to make the allocation, despite the space for it being available.
     * In that case, the heap is defragmented, and the process is repeated.
     * @param size size of the space to allocate
     * @param type type of value stored in the memory space
     * @return address of the created memory space
     * @throws InsufficientMemoryException throws an exception if the given size is superior to the available one
     */
    public int allocate(int size, DataType type) throws InsufficientMemoryException, IllegalArgumentException{
        if(size <= 0)
            throw new IllegalArgumentException("Cannot allocate a non-positive size. Given : " + size);
        if(size > availableSize)
            throw new InsufficientMemoryException(availableSize, size);
        // Finding a suitable space for the allocation
        HeapElement first = currentElement;
        while(currentElement.size() < size || ! currentElement.isFree()){
            currentElement = currentElement.getNext();
            if(currentElement == first){ // External fragmentation (oh no :c)
                defragment();
                return allocate(size, type);
            }
        }
        // Realising the allocation
        HeapElement allocation;
        if(size == availableSize){
            allocation = currentElement;
        }
        else {
            allocation = currentElement.split(size);
            allocation.externalAddress = newExtAddress();
            externalAddresses.put(allocation.externalAddress, allocation);
        }
        availableSize -= size;
        allocation.allocate(type);

        return allocation.externalAddress;
    }

    /**
     * Utility function.
     * Check if the given external address is valid, and return the corresponding element
     * @param externalAddress external address of a heap element
     * @return heap element corresponding to the address
     * @throws InvalidMemoryAddressException throws an exception if no element matches the address
     */
    private HeapElement checkAddress(int externalAddress) throws InvalidMemoryAddressException{
        HeapElement target = externalAddresses.get(externalAddress);
        if(target == null)
            throw new UnmappedMemoryAddressException(externalAddress);
        return target;
    }

    /**
     * Internal method to call HeapElement.free, to ensure the merged element are deleted from the existing addresses
     * @param e element to merge
     */
    private void internalFree(HeapElement e){
        if(e.getPrev().isFree())
            externalAddresses.remove(e.getPrev().externalAddress);
        if(e.getNext().isFree())
            externalAddresses.remove(e.getNext().externalAddress, e);
        e.free();
    }

    /**
     * Remove a memory space from the heap
     * /!\ This method should only be called for direct memory management. /!\
     * /!\ Do not use this if you want to use the garbage collector /!\
     * @param address address to the element in the heap
     * @throws InvalidMemoryAddressException throws an exception if the address is not referenced in the heap
     */
    public void free(int address) throws InvalidMemoryAddressException{
        HeapElement target = checkAddress(address);
        availableSize += target.size();
        internalFree(target);
        currentElement = target; //ensures "currentElements" points to a valid element
    }                            //(could be one of the merged blocks)

    /**
     * Removed a memory space from the heap
     * @param target memory space to remove
     */
    private void free(HeapElement target){
        availableSize += target.size();
        internalFree(target);
        currentElement = target;
    }

    /**
     * Notify the garbage collector a new reference has been added to a memory space
     * @param address address of an element in the heap
     * @throws InvalidMemoryAddressException throws an exception if the address is not referenced in the heap
     */
    public void addReference(int address) throws InvalidMemoryAddressException{
        HeapElement target = checkAddress(address);
        if(target.isFree())
            throw new InvalidMemoryAddressException("Invalid free, no block allocated at : " + address, address);
        target.references++;
    }

    /**
     * Notify the garbage collector a reference has been removed from a memory space.
     * If the memory has no reference pointing to it, it will be freed.
     * @param address address of an element in the heap
     * @throws InvalidMemoryAddressException throws an exception if the address is not referenced in the heap
     */
    public void removeReference(int address) throws InvalidMemoryAddressException{
        HeapElement target = checkAddress(address);
        if(target.isFree())
            throw new InvalidMemoryAddressException("Invalid free, no block allocated at : " + address, address);
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
        if(offset >= target.size())
            throw new UnmappedMemoryAddressException("Offset " + offset + " is beyond the allocated block for address " + address, address + offset);
        if(target.isFree())
            throw new InvalidMemoryAddressException("Invalid address, no block allocated at : " + address, address);
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
        if(offset >= target.size())
            throw new UnmappedMemoryAddressException("Offset " + offset + " is beyond the allocated block for address " + address, address + offset);
        if(target.isFree())
            throw new InvalidMemoryAddressException("Invalid address, no block allocated at : " + address, address);
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
            storage[target + i] =  storage[originAddress + i];
        }
    }

    /**
     * Relocate every allocated element of the heap at the start of the array, and merge the remaining ones at the end.
     * This allows to minimize the internal fragmentation of the heap.
     * This operation is extremely expensive, and should only be called if necessary
     */
    private void defragment(){
        // Variables initialisation
        List<HeapElement> allBlocks = new ArrayList<>(externalAddresses.values());
        allBlocks.sort((self, other) -> self.internalAddress - other.internalAddress);
        int lastAvailableAddress = 0; // Last address where it is safe to write
        HeapElement freeElements = null; // Chain containing every free elements found
        HeapElement prev = null; // Last allocated element found, and treated
        HeapElement first = null;
        // Iterating through every element
        for(HeapElement current : allBlocks){
            if(current.isFree()){ // Current element is not allocated
                if(freeElements == null) { // If it's the first, we store it
                    freeElements = current;
                }
                else { // If not, insert it after the first unallocated element
                    freeElements.next.prev = current;
                    current.next = freeElements.next;
                    freeElements.next = current;
                    externalAddresses.remove(current.externalAddress); // The current node will be merged at the end
                }                                                      // so we remove its address now
            }
            else { // Current element is allocated
                if (current.internalAddress == lastAvailableAddress) { // Current is already at a good place
                    lastAvailableAddress += current.size();

                }else {
                    copy(current, lastAvailableAddress);
                    current.internalAddress = lastAvailableAddress;
                    lastAvailableAddress += current.size();
                    if (prev != null) { // Element being treated is not the first element
                        prev.next = current; // Chaining the element to the previous one
                        current.prev = prev;
                    }
                }
                if(prev == null)
                    first = current;
                prev = current; // Updating the last element only if we treated an allocated one
            }
        }
        // If the defragment method was called, at least one allocated and unallocated element were seen
        // Therefor, the following calls are safe
        prev.next = freeElements; // Chaining the last allocated element to the free one
        freeElements.prev = prev;
        freeElements.tryMerge(); // Merging all unallocated elements
        freeElements.internalAddress = lastAvailableAddress;
        first.prev = freeElements;  // Chaining the first allocated element to the free one
        freeElements.next = first;
        currentElement = freeElements;
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
