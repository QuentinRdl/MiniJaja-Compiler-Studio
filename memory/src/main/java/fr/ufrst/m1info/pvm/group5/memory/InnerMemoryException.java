package fr.ufrst.m1info.pvm.group5.memory;

public class InnerMemoryException extends RuntimeException {
    public InnerMemoryException(String message) {
        super("[WARNING] This is a critical internal error that shouldn't be displayed ! - " + message);
    }
}
