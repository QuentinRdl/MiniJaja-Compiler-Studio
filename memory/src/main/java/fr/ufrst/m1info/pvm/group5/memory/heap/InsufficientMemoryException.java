package fr.ufrst.m1info.pvm.group5.memory.heap;

/**
 * Exception thrown when trying to allocate more memory than available
 */
public class InsufficientMemoryException extends RuntimeException {

    public InsufficientMemoryException(int availableSize, int allocationSize) {
        super(String.format("Heap : Cannot allocate %s, only %s available", availableSize, allocationSize));
    }
}
