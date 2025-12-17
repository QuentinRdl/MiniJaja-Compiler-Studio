package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.instructions.Instruction;
import fr.ufrst.m1info.pvm.group5.ast.nodes.ASTNode;

/**
 * Exception thrown when an operation is performed on a node, when it shouldn't be.
 * This exception is also thrown for divisions by 0.
 *
 * This exception mostly exists for debugging purposes and shouldn't occur during production, besides for the division by 0.
 */
public class ASTInvalidOperationException extends RuntimeException {
    public ASTInvalidOperationException(String operation, LocatedElement element) {
        super(String.format("Trying to use invalid operation %s (at line %d in %s)", operation, element.getLine(), element));
    }

}
