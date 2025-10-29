package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class TypeNode {
    public ValueType valueType;

    public TypeNode(ValueType valueType){
        this.valueType = valueType;
    }

    public String toString(){
        return valueType.toString();
    }
}
