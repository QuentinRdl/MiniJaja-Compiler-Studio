package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class NotInstruction extends Instruction{

    @Override
    public int execute(int address, Memory m) {
        Value v = (Value) MemoryCallUtil.safeCall(m::pop, this);
        compatibleType(ValueType.BOOL, v.type);
        boolean res = !v.valueBool ;
        Value vres = new Value(res);
        MemoryCallUtil.safeCall(() -> m.push(".", vres, DataType.BOOL, EntryKind.CONSTANT), this);
        return address+1;
    }

    public String toString() {
        return "not";
    }
}
