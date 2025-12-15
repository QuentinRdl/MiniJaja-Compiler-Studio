package fr.ufrst.m1info.pvm.group5.ast;

/**
 * Exception thrown when a node tries to access a value in the memory and fails
 */
public class ASTInvalidMemoryException extends RuntimeException {
    public ASTInvalidMemoryException(String message) {
        super(message);
    }

    public static ASTInvalidMemoryException UndefinedVariable(String identifier, int line) {
        return new ASTInvalidMemoryException(String.format("Symbol %s is not defined (at line %d)", identifier, line));
    }

    public static ASTInvalidMemoryException InvalidVariable(String identifier, int line, String expected, String actual) {
        return new ASTInvalidMemoryException(String.format("Symbol %s is a %s, expected %s (at line %d)", identifier, expected, actual, line));
    }

    public static ASTInvalidMemoryException NoValueAvailable(String identifier, int line) {
        return new ASTInvalidMemoryException(String.format("Variable %s has no defined value (at line %d)", identifier, line));
    }

    public static ASTInvalidMemoryException ConstantChange(String identifier, int line) {
        return new ASTInvalidMemoryException(String.format("Attempted to change constant value %s (at line %d)", identifier, line));
    }

    public static ASTInvalidMemoryException EmptyStack(int line) {
        return new ASTInvalidMemoryException(String.format("Attempted to pop a value on an empty stack (at line %d)", line));
    }
}
