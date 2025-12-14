package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class CmpInstruction extends Instruction{
    @Override
    public int execute(int address, Memory m) {
        Value v2 = (Value) m.pop(); //first pop => right operand
        Value v1 = (Value) m.pop(); //second pop => left operand

        compatibleType(v1.type, v2.type);
        if (v1.type == ValueType.INT){
            boolean res = v1.valueInt == v2.valueInt;
            Value vres = new Value(res);
            m.push(".", vres, DataType.BOOL, EntryKind.CONSTANT);
        }else{
            boolean res = v1.valueBool == v2.valueBool;
            Value vres = new Value(res);
            m.push(".", vres, DataType.BOOL, EntryKind.CONSTANT);
        }

        return address+1;
    }

    public String toString(){ return "cmp"; }
}