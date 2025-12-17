package fr.ufrst.m1info.pvm.group5.ast;

/**
 * Exception thrown when a node tries to access a value in the memory and fails
 */
public class ASTInvalidMemoryException extends RuntimeException {
    public ASTInvalidMemoryException(String message) {
        super(message);
    }

    public static ASTInvalidMemoryException UndefinedVariable(String identifier, LocatedElement element) {
        return new ASTInvalidMemoryException(String.format("Symbol %s is not defined (at line %d, %s)", identifier, element.getLine(), element));
    }

    public static ASTInvalidMemoryException InvalidVariable(String identifier, LocatedElement element, String expected, String actual) {
        return new ASTInvalidMemoryException(String.format("Symbol %s is a %s, expected %s (at line %d, %s)", identifier, expected, actual, element.getLine(), element));
    }

    public static ASTInvalidMemoryException EmptyStack(LocatedElement element) {
        return new ASTInvalidMemoryException(String.format("Attempted to pop a value on an empty stack (at line %d, %s)", element.getLine(), element));
    }

    public static ASTInvalidMemoryException InvalidMemoryOperation(LocatedElement element, String message) {
        return new ASTInvalidMemoryException(message + " (at line " + element.getLine() + ", " + element +")");
    }
}
