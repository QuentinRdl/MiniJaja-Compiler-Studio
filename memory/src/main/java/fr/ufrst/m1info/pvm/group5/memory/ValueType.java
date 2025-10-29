package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;

/**
 * Types of values found during the interpretation of the AST.
 */
public enum ValueType {
    INT,
    BOOL,
    EMPTY,
    VOID;

    /**
     * Turns a ValueType into a Datatype, used by the memory
     * @param vt valuetype to convert
     * @return converted valuetype
     */
    public static DataType toDataType(ValueType vt){
        switch(vt){
            case INT:
                return DataType.INT;
            case BOOL:
                return DataType.BOOL;
            case VOID:
                return DataType.VOID;
        }
        return DataType.UNKNOWN;
    }

    public String toString(){
        switch(this){
            case INT:
                return "INT";
            case BOOL:
                return "BOOL";
            case VOID:
                return "VOID";
            default:
                return "UNKNOWN";
        }
    }
}
