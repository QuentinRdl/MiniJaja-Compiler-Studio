package fr.ufrst.m1info.pvm.group5.memory.Heap;

public class UnmappedMemoryAddressException extends InvalidMemoryAddressException{
    public UnmappedMemoryAddressException(String message, int address) {
        super(message, address);
    }
    public UnmappedMemoryAddressException(int address) {
        super("Trying to access address in unmapped region : "+address, address);
    }
}
