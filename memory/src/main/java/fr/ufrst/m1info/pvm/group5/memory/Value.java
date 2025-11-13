package fr.ufrst.m1info.pvm.group5.memory;

/**
 * Class defining a boolean or integer value within the AST
 */
public class Value {
    public int valueInt;
    public boolean valueBool;
    public String valueString;
    public ValueType Type;

    public Value(boolean valueBool){
        this.valueBool = valueBool;
        this.Type = ValueType.BOOL;
    }

    public Value(int valueInt){
        this.valueInt = valueInt;
        this.Type = ValueType.INT;
    }

    public Value(String valueString){
        this.valueString = valueString;
        this.Type = ValueType.STRING;
    }

    public Value(){
        this.Type=ValueType.EMPTY;
    }
    
    public String toString(){
        return switch (Type) {
            case INT -> valueInt + "";
            case BOOL -> valueBool + "";
            case STRING -> valueString;
            default -> "";
        };
    }
}
