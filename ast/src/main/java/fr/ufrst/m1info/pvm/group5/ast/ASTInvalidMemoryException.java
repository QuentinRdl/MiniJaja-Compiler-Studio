package fr.ufrst.m1info.pvm.group5.ast;

/**
 * Exception thrown when a node tries to access a value in the memory and fails
 */
public class ASTInvalidMemoryException extends RuntimeException {
    /*
    - Pour les accès à une variable : Variable {identificateur} is not defined
    - Pour les accès à une variable invalide (ex : Méthode au lieu de variable) : Symbol {identificateur} is a {Sorte}, expected {Sorte}
    - Pour les accès à une valeur indéfinie : Variable {identificateur} has no assigned value
    - Tentative de modification d'une constante : Attempted to change constant value {identificateur}
     */

    private ASTInvalidMemoryException(String message) {
        super(message);
    }

    public static ASTInvalidMemoryException UndefinedVariable(String identifier, int line) {
        return new ASTInvalidMemoryException(String.format("Line %d : Symbol %s is not defined", line, identifier));
    }

    public static ASTInvalidMemoryException InvalidVariable(String identifier, int line, String expected, String actual) {
        return new ASTInvalidMemoryException(String.format("Line %d : Symbol %s is a %s, expected %s", line, identifier, expected, actual));
    }

    public static ASTInvalidMemoryException NoValueAvailable(String identifier, int line) {
        return new ASTInvalidMemoryException(String.format("Line %d : Variable %s has no defined value", line, identifier));
    }

    public static ASTInvalidMemoryException ConstantChange(String identifier, int line) {
        return new ASTInvalidMemoryException(String.format("Line %d : Attempted to change constant value %s", line, identifier));
    }

    public static ASTInvalidMemoryException EmptyStack(int line) {
        return new ASTInvalidMemoryException(String.format("Line %d : Attempted to pop a value on an empty stack", line));
    }
}
