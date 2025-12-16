package fr.ufrst.m1info.pvm.group5.memory;

/**
 * Class defining a boolean or integer value within the AST
 */
public class Value {
    public int valueInt;
    public boolean valueBool;
    public String valueString;
    public ValueType type;

    /**
     * Constructor to make a boolean value
     * @param valueBool bool value
     */
    public Value(boolean valueBool){
        this.valueBool = valueBool;
        this.type = ValueType.BOOL;
    }

    /**
     * Constructor to make an int value
     * @param valueInt int value
     */
    public Value(int valueInt){
        this.valueInt = valueInt;
        this.type = ValueType.INT;
    }

    /**
     * Constructor to make an String value
     * @param valueString str value
     */
    public Value(String valueString){
        this.valueString = valueString;
        this.type = ValueType.STRING;
    }

    /**
     * Constructor to make an empty value
     */
    public Value(){
        this.type =ValueType.EMPTY;
    }

    @Override
    public String toString(){
        return switch (type) {
            case INT -> valueInt + "";
            case BOOL -> valueBool + "";
            case STRING -> valueString;
            default -> "";
        };
    }
}
