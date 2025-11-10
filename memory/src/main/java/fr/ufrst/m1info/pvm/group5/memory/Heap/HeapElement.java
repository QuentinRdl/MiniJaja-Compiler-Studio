package fr.ufrst.m1info.pvm.group5.memory.Heap;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;

/**
 * Storage block of the heap
 */
public class HeapElement {
    public int internalAddress;
    public int externalAddress;
    public int size;
    public DataType storageType;
    public boolean isFree;
    public int references;
    HeapElement prev;
    HeapElement next;

    public class InsufficientSizeException extends Exception {
        InsufficientSizeException(String message) {
            super(message);
        }
    }

    /**
     * Creates a new heap element with no adjacent element
     * @param internalAddress internal address of the storage referenced by the element
     * @param externalAddress external address of the element stored
     * @param size size of the block to create
     * @param storageType data stored in the element
     */
    public HeapElement(int internalAddress, int externalAddress, int size, DataType storageType) {}

    /**
     * Tries to merge the element with an adjacent one.
     * If one of the elements is not free, the merge will fail.
     * @param other other element to merge with this one.
     */
    private void TryMerge(HeapElement other){}

    /**
     * Tries to merge the element with every adjacent element.
     * If this element or it's adjacent ones are not free, the merge will fail.
     */
    public void TryMerge(){
        TryMerge(prev);
        TryMerge(next);
    }

    /**
     * Creates a new heap element by reducing the size of this one.
     * By default, the new element will be placed after this one.
     * @param size size of the new element to create
     * @return newly created element
     * @throws InsufficientSizeException throws an exception if the requested size is bigger than the size of the element
     */
    public HeapElement split(int size) throws InsufficientSizeException {
        return null;
    }

    /**
     * Creates a new heap element by reducing the size of this one.
     * By default, the new element will be placed after this one.
     * @param size size of the new element to create
     * @param before if true, the new element will be placed before this one instead of after
     * @return newly created element
     * @throws InsufficientSizeException throws an exception if the requested size is bigger than the size of the element
     */
    public HeapElement split(int size, boolean before) throws InsufficientSizeException {
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
