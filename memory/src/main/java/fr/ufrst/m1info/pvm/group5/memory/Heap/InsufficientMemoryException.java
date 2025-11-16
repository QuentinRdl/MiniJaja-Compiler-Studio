package fr.ufrst.m1info.pvm.group5.memory.Heap;

/**
 * Exception thrown when trying to allocate more memory than available
 */
public class InsufficientMemoryException extends RuntimeException {

    public InsufficientMemoryException(int availableSize, int allocationSize) {
        super("Trying to allocate "+allocationSize+" bytes with only "+availableSize+" bytes available");
    }

    public InsufficientMemoryException(String message) {
        super(message);
    }
}
