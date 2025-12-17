package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.nodes.ASTNode;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class InterpretationInvalidTypeException extends RuntimeException {
    public InterpretationInvalidTypeException(LocatedElement element, String expected, String actual) {
        super(String.format("Invalid type, type %s given when %s is expected (at line %d, %s)", actual, expected, element.getLine(), element));
    }

    public InterpretationInvalidTypeException(String message, LocatedElement element) {
        super(message + "(at line " + element.getLine() + ", " + element +")");
    }
}