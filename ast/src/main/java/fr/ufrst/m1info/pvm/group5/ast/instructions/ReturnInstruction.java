package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class ReturnInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        Value top = (Value) MemoryCallUtil.safeCall(m::pop, this);
        compatibleType(ValueType.INT, top.type);
        MemoryCallUtil.safeCall(m::decrementScope, this);
        return top.valueInt;
    }

    public String toString() {
        return "return";
    }
}
