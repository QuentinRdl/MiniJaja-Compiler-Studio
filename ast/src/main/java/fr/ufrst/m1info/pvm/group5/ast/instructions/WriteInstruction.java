package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

public class WriteInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        Value top = (Value) MemoryCallUtil.safeCall(m::pop, this);
        MemoryCallUtil.safeCall(() -> m.write(top.toString()), this);
        return address + 1;
    }

    public String toString() { return "write"; }
}
