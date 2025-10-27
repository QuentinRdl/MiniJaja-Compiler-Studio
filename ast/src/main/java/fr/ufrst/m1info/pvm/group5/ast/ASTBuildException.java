package fr.ufrst.m1info.pvm.group5.ast;

/**
 * Exception thrown when an error occured when building the tree (usually, when a node is null when it shouldn't be).
 * This exception exists solely for debugging purposes and shouldn't occur during production
 */
public class ASTBuildException extends RuntimeException {
    public ASTBuildException(String message) {
        super(message);
    }
}
