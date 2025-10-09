package fr.ufrst.m1info.gl.compGL;

import fr.ufrst.m1info.gl.compGL.Memory.DataType;

public enum ValueType {
    INT,
    BOOL,
    VOID;

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
}
