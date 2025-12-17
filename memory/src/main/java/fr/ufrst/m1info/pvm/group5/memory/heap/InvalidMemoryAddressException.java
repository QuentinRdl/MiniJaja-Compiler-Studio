package fr.ufrst.m1info.pvm.group5.memory.heap;

/**
 * Exception thrown when trying to access an invalid segment of the memory
 */
public class InvalidMemoryAddressException extends RuntimeException {
    int address;
    public InvalidMemoryAddressException(int address) {
        super(String.format("Heap : No array allocated at given address %d", address));
        this.address = address;
    }
}