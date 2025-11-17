package fr.ufrst.m1info.pvm.group5.memory;

/**
 * Class defining a boolean or integer value within the AST
 */
public class Value {
    public int valueInt;
    public boolean valueBool;
    public String valueString;
    public ValueType type;

    public Value(boolean valueBool){
        this.valueBool = valueBool;
        this.type = ValueType.BOOL;
    }

    public Value(int valueInt){
        this.valueInt = valueInt;
        this.type = ValueType.INT;
    }

    public Value(String valueString){
        this.valueString = valueString;
        this.type = ValueType.STRING;
    }

    public Value(){
        this.type =ValueType.EMPTY;
    }
    
    public String toString(){
        return switch (type) {
            case INT -> valueInt + "";
            case BOOL -> valueBool + "";
            case STRING -> valueString;
            default -> "";
        };
    }
}
