package fr.ufrst.m1info.pvm.group5.memory.symbol_table;

import fr.ufrst.m1info.pvm.group5.memory.ValueType;

/**
 * Enumeration representing possible data types
 * for variables, constants, and return types in the symbol table.
 */
public enum DataType {
    /** Integer type */
    INT,

    /** Boolean type */
    BOOL,

    /** String type */
    STRING,

    /** Floating point type (single precision) */
    FLOAT,

    /** Double precision floating point type */
    DOUBLE,

    /** Void type (used for methods that do not return a value) */
    VOID,

    /** Unknown type (default or error case) */
    UNKNOWN;

    public static ValueType toValueType(DataType v){
        return switch (v){
            case INT -> ValueType.INT;
            case BOOL -> ValueType.BOOL;
            case STRING -> ValueType.STRING;
            case VOID -> ValueType.VOID;
            default -> ValueType.EMPTY;
        };
    }
}
