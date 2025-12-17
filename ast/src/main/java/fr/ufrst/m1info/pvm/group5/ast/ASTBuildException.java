package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.nodes.ASTNode;

/**
 * Exception thrown when an error occured when building the tree (usually, when a node is null when it shouldn't be).
 * This exception exists solely for debugging purposes and shouldn't occur during production
 */
public class ASTBuildException extends RuntimeException {
    private ASTBuildException(String message) {
        super("[WARNING] This is a critical internal error that shouldn't be displayed ! - " + message);
    }

    public ASTBuildException(String builderNode, String paramNode, String reason){
        this(String.format("Failed to build an Abstract Syntax Tree from the source : Tried to create node %s with %s, %s",builderNode, paramNode, reason));
    }
}
