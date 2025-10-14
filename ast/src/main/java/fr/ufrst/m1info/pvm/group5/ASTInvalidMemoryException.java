package fr.ufrst.m1info.pvm.group5;

/**
 * Exception thrown when a node tries to access a value in the memory and fails
 */
public class ASTInvalidMemoryException extends RuntimeException {
    public ASTInvalidMemoryException(String message) {
        super(message);
    }
}
