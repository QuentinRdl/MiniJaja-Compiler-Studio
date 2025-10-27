package fr.ufrst.m1info.pvm.group5.ast;

/**
 * Exception thrown when an operation is performed on a node, when it shouldn't be.
 * This exception is also thrown for divisions by 0.
 *
 * This exception mostly exists for debugging purposes and shouldn't occur during production, besides for the division by 0.
 */
public class ASTInvalidOperationException extends RuntimeException {
    public ASTInvalidOperationException(String message) {
        super(message);
    }
}
