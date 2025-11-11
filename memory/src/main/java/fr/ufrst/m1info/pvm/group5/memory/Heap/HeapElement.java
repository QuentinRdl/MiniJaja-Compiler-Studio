package fr.ufrst.m1info.pvm.group5.memory.Heap;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;

/**
 * Storage block of the heap
 */
public class HeapElement {
    int internalAddress;
    int externalAddress;
    int size;
    private DataType storageType;
    private boolean isFree;
    int references;
    private HeapElement prev;
    private HeapElement next;

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
    public class InsufficientSizeException extends RuntimeException {
        InsufficientSizeException(String message) {
            super(message);
        }
    }

    public class InvalidOperationException extends RuntimeException {
        InvalidOperationException(String message) {super(message);}
    }

    /**
     * Creates a new heap element with no adjacent element
     * @param internalAddress internal address of the storage referenced by the element
     * @param externalAddress external address of the element stored
     * @param size size of the block to create
     */
    public HeapElement(int internalAddress, int externalAddress, int size) throws IllegalArgumentException {

    }

    /**
     * Indicates to the element the block it matches has been allocated
     * @param storageType type of data stored in the block
     * @throws InvalidOperationException throws an exception if the block is already allocated
     */
    public void allocate(DataType storageType) throws InvalidOperationException {}

    /**
     * Indicates to the element the block is matched has been freed.
     * The element will attempt to merge with the adjacent blocks
     * @throws InvalidOperationException throws an exception if the block is not allocated
     */
    public void free() throws InvalidOperationException {}

    /**
     * Tries to merge the element with an adjacent one.
     * If one of the elements is not free, the merge will fail.
     * @param other other element to merge with this one.
     * @return True if the merge is a success, false otherwise
     * @throws InvalidOperationException throws an exception when attempting to merge while either of the elements are allocated
     */
    private boolean tryMerge(HeapElement other) throws InvalidOperationException {
        return false;
    }

    /**
     * Tries to merge the element with every adjacent element.
     * If this element or it's adjacent ones are not free, the merge will fail.
     * @return True if the merge is a success, false otherwise
     * @throws InvalidOperationException throws an exception when attempting to merge while this element is allocated
     */
    public boolean tryMerge() throws InvalidOperationException {
        return false;
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
        return null;
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
        return null;
    }

    /**
     * Tests if an address is located within the block
     * @param internalAddress address to compare
     * @return true if the address is located within this element, false otherwise
     */
    public boolean belongsTo(int internalAddress){
        return false;
    }
}
