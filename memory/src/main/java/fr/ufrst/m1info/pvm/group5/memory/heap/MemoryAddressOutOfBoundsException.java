package fr.ufrst.m1info.pvm.group5.memory.heap;

/**
 * Exception called when trying to access a memory address before or after the allowed addresses
 */
public class MemoryAddressOutOfBoundsException extends InvalidMemoryAddressException {

    public MemoryAddressOutOfBoundsException(String message, int address) {
    super(message, address);
  }

    public MemoryAddressOutOfBoundsException(int address, int lowerBound, int upperBound) {
        super("Trying to access address "+address+", when the address of the object are mapped between " + lowerBound + " and " + upperBound, address);
    }
}
