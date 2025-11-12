package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;

/**
 * Types of values found during the interpretation of the AST.
 */
public enum ValueType {
    INT,
    BOOL,
    STRING,
    EMPTY,
    VOID;

    /**
     * Turns a ValueType into a Datatype, used by the memory
     * @param vt valuetype to convert
     * @return converted valuetype
     */
    public static DataType toDataType(ValueType vt){
        return switch (vt) {
            case INT -> DataType.INT;
            case BOOL -> DataType.BOOL;
            case STRING -> DataType.STRING;
            case VOID -> DataType.VOID;
            default -> DataType.UNKNOWN;
        };
    }

    public String toString(){
        return switch (this) {
            case INT -> "INT";
            case BOOL -> "BOOL";
            case STRING -> "STRING";
            case VOID -> "VOID";
            default -> "UNKNOWN";
        };
    }
}
