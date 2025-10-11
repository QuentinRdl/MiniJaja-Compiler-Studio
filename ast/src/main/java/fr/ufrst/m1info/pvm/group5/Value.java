package fr.ufrst.m1info.pvm.group5;

public class Value {
    public int valueInt;
    public boolean valueBool;
    public ValueType Type;

    public Value(boolean valueBool){
        this.valueBool = valueBool;
        this.Type = ValueType.BOOL;
    }

    public Value(int valueInt){
        this.valueInt = valueInt;
        this.Type = ValueType.INT;
    }

    /*
    TODO : Add a constructor when we have access to the memory
     */
}
