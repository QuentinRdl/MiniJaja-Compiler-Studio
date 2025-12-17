package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.nodes.ASTNode;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class InterpretationInvalidTypeException extends RuntimeException {
    public InterpretationInvalidTypeException(int line, ValueType expected, ValueType actual, ASTNode operation) {
        super(String.format("Invalid type, type %s given when %s is expected for %s (at line %d)", actual.name(), expected.name(), operation.toString(), line));
    }

    public InterpretationInvalidTypeException(String message, int line) {
        super("Line " + line + ": " + message);
    }
}