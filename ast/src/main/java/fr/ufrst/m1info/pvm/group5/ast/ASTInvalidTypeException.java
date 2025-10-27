package fr.ufrst.m1info.pvm.group5.ast;

/**
 * Exception thrown during type check, when the type of a node or one of its operand is invalid
 */
public class ASTInvalidTypeException extends RuntimeException {
    public ASTInvalidTypeException(String message) {
        super(message);
    }
}
