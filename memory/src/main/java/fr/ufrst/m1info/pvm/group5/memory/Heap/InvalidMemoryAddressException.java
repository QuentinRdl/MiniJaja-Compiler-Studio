package fr.ufrst.m1info.pvm.group5.memory.Heap;

/**
 * Exception thrown when trying to access an invalid segment of the memory
 */
public class InvalidMemoryAddressException extends RuntimeException {
    int address;
    public InvalidMemoryAddressException(String message, int address) {
        super(message);
        this.address = address;
    }
}