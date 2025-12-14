package fr.ufrst.m1info.pvm.group5.ast;

public class InterpretationInvalidTypeException extends RuntimeException {
    public InterpretationInvalidTypeException(int line, String expected, String actual, String operation) {
        super(String.format("Line %d : Invalid type, type %s given when %s is expected for %s", line, expected, actual, operation));
    }
}