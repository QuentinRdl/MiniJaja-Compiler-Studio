package fr.ufrst.m1info.pvm.group5.ast.instructions;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class AndInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        Value v2 = (Value) MemoryCallUtil.safeCall(m::pop, this); //first pop => right operand
        Value v1 = (Value) MemoryCallUtil.safeCall(m::pop, this); //second pop => left operand
        compatibleType(ValueType.BOOL, v1.type);
        compatibleType(ValueType.BOOL, v2.type);
        boolean res = v1.valueBool && v2.valueBool;
        Value vres = new Value(res);
        MemoryCallUtil.safeCall(() -> m.push(".", vres, DataType.BOOL, EntryKind.CONSTANT), this);
        return address+1;
    }

    public String toString(){ return "and"; }
}