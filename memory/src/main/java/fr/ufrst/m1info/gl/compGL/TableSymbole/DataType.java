package fr.ufrst.m1info.gl.compGL.TableSymbole;

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
    UNKNOWN
}
