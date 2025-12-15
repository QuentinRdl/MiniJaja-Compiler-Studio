package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class AddInstruction extends Instruction{
    @Override
    public int execute(int address, Memory m) {
        Value v2 = (Value) MemoryCallUtil.safeCall(m::pop, this); //first pop => right operand
        Value v1 = (Value) MemoryCallUtil.safeCall(m::pop, this); //second pop => left operand
        compatibleType(ValueType.INT, v1.type);
        compatibleType(ValueType.INT, v2.type);
        int res = v1.valueInt + v2.valueInt;
        Value vres = new Value(res);
        MemoryCallUtil.safeCall(() -> m.push(".", vres, DataType.INT, EntryKind.CONSTANT), this);
        return address+1;
    }

    public String toString(){
        return "add";
    }
}
