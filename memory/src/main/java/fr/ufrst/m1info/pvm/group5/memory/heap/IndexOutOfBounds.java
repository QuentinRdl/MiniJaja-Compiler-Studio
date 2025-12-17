package fr.ufrst.m1info.pvm.group5.memory.heap;

public class IndexOutOfBounds extends RuntimeException {
    public IndexOutOfBounds(int address, int index, int size) {
        super(String.format("Heap : Array index %s out of bounds of block of size %d allocated at %d", index, size, address));
    }
}
