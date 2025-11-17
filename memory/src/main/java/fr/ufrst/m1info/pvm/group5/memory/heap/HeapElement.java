package fr.ufrst.m1info.pvm.group5.memory.heap;

import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;

/**
 * Storage block of the heap
 */
public class HeapElement {
    /**
     * Address in the memory of the matching block
     */
    int internalAddress;
    /**
     * External address, given by the structure managing the memory to give it a name
     */
    int externalAddress;
    /**
     * Size of the memory block
     */
    int size;
    /**
     * Data stored in the block
     */
    private DataType storageType;
    /**
     * Indicates if the block is free
     */
    private boolean isFree;
    /**
     * Number of references pointed to the block.
     * Managed by the class managing the memory
     */
    int references;
    /**
     * Previous element
     */
    HeapElement prev;
    /**
     * Next element
     */
    HeapElement next;

    // Getters
    public DataType getStorageType() {
        return storageType;
    }

    public int size(){
        return size;
    }

    public boolean isFree(){
        return isFree;
    }

    public HeapElement getPrev() {
        return prev;
    }

    public HeapElement getNext() {
        return next;
    }

    public int getInternalAddress() {
        return internalAddress;
    }

    public int getExternalAddress() {
        return externalAddress;
    }

    /**
     * Exception thrown when trying to split the current element with a size bigger that the current element's one
     */
    public static class InsufficientSizeException extends RuntimeException {
        InsufficientSizeException(String message) {
            super(message);
        }
    }

    public static class InvalidOperationException extends RuntimeException {
        InvalidOperationException(String message) {super(message);}
    }

    private HeapElement() {}

    /**
     * Creates a new heap element with no adjacent element
     * @param internalAddress internal address of the storage referenced by the element
     * @param externalAddress external address of the element stored
     * @param size size of the block to create
     */
    public HeapElement(int internalAddress, int externalAddress, int size) throws IllegalArgumentException {
        if(size <= 0)
            throw new IllegalArgumentException("Block creation failed, block size must be a positive integer. Given : " + size);
        this.internalAddress = internalAddress;
        this.externalAddress = externalAddress;
        this.size = size;
        this.isFree = true;
        this.references = 0;
        this.prev = this;
        this.next = this;
        this.storageType = DataType.UNKNOWN;
    }

    /**
     * Indicates to the element the block it matches has been allocated
     * @param storageType type of data stored in the block
     * @throws InvalidOperationException throws an exception if the block is already allocated
     */
    public void allocate(DataType storageType) throws InvalidOperationException {
        if(!isFree)
            throw new InvalidOperationException("Invalid allocation, block " + externalAddress + " is already allocated");
        this.storageType = storageType;
        this.isFree = false;
    }

    /**
     * Indicates to the element the block is matched has been freed.
     * The element will attempt to merge with the adjacent blocks
     * @throws InvalidOperationException throws an exception if the block is not allocated
     */
    public void free() throws InvalidOperationException {
        if(isFree)
            throw new InvalidOperationException("Invalid free, block " + externalAddress + " has already been freed");
        this.isFree = true;
        this.storageType = DataType.UNKNOWN;
        tryMerge();
    }

    /**
     * Tries to merge the element with an adjacent one.
     * If one of the elements is not free, the merge will fail.
     * @param other other element to merge with this one.
     * @return True if the merge is a success, false otherwise
     * @throws InvalidOperationException throws an exception when attempting to merge while either of the elements are allocated
     */
    private boolean tryMerge(HeapElement other) throws InvalidOperationException {
        // Check if the merge is possible
        if(!isFree)
            throw new InvalidOperationException("Invalid merge attempt, block " + externalAddress + " is allocated");
        if(!other.isFree || other.equals(this))
            return false;

        // Merge
        size += other.size;
        if(other.internalAddress < this.internalAddress) {
            this.internalAddress = other.internalAddress;
            this.prev = other.prev;
            other.prev.next = this;
        }
        else{
            this.next = other.next;
            other.next.prev = this;
        }
        return true;
    }

    /**
     * Tries to merge the element with every adjacent element.
     * If this element or it's adjacent ones are not free, the merge will fail.
     * @return True if the merge is a success, false otherwise
     * @throws InvalidOperationException throws an exception when attempting to merge while this element is allocated
     */
    public boolean tryMerge() throws InvalidOperationException {
        boolean success = false;
        // The first condition prevents the method to use the loop structure to merge
        while(prev.internalAddress < internalAddress && tryMerge(prev)) success = true;
        while(next.internalAddress > internalAddress && tryMerge(next)) success = true;
        return success;
    }

    /**
     * Creates a new heap element by reducing the size of this one.
     * By default, the new element will be placed before this one.
     * The newly created element will share it's external address with its parent
     * @param size size of the new element to created
     * @return newly created element
     * @throws InsufficientSizeException throws an exception if the requested size is bigger than the size of the element
     * @throws IllegalArgumentException throws an exception if the requested size is invalid
     * @throws InvalidOperationException throws an exception when trying to split a non-free element
     */
    public HeapElement split(int size) throws InsufficientSizeException, IllegalArgumentException, InvalidOperationException {
        return split(size, false);
    }

    /**
     * Creates a new heap element by reducing the size of this one.
     * By default, the new element will be placed before this one.
     * @param size size of the new element to create
     * @param after if true, the new element will be placed after this one instead of before
     * @return newly created element
     * @throws InsufficientSizeException throws an exception if the requested size is bigger than the size of the element
     * @throws IllegalArgumentException throws an exception if the requested size is invalid
     * @throws InvalidOperationException throws an exception when trying to split a non-free element
     */
    public HeapElement split(int size, boolean after) throws InsufficientSizeException, IllegalArgumentException, InvalidOperationException {
        // Invalid split cases
        if(!isFree)
            throw new InvalidOperationException("Invalid split attempt, block " + externalAddress + " is allocated");
        if(size <= 0)
            throw new IllegalArgumentException("Invalid split, size for a split must be a positive integer. Given : " + size);
        if(size >= this.size)
            throw new InsufficientSizeException("Invalid split, block " + externalAddress + " has insufficient size. Given : " + size + " available : " + this.size);

        // Performing split
        HeapElement result = new HeapElement();
        result.externalAddress = this.externalAddress;
        result.isFree = true;
        result.size = size;
        this.size -= size;
        if(after) {
            result.internalAddress = this.internalAddress + this.size;
            result.prev = this;
            result.next = this.next;
            this.next.prev = result;
            this.next = result;
            if(this.prev == this)
                this.prev = result;
        }
        else {
            result.internalAddress = this.internalAddress;
            this.internalAddress = this.internalAddress + size;
            result.next = this;
            result.prev = this.prev;
            this.prev.next = result;
            this.prev = result;
            if(this.next == this)
                this.next = result;
        }
        return result;
    }

    /**
     * Tests if an address is located within the block
     * @param internalAddress address to compare
     * @return true if the address is located within this element, false otherwise
     */
    public boolean contains(int internalAddress){
        return internalAddress >= this.internalAddress && internalAddress < this.internalAddress + size;
    }

    public String toString(){
        return "@" + this.externalAddress + ":" + this.internalAddress;
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof HeapElement)) return false;
        return equals((HeapElement)o);
    }

    public boolean equals(HeapElement other){
        return this.externalAddress == other.externalAddress && this.internalAddress == other.internalAddress;
    }

    public int hashCode(){
        return (internalAddress << 5) | externalAddress;
    }
}
