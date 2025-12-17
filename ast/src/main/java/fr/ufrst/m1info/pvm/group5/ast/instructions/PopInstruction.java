package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

public class PopInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        MemoryCallUtil.safeCall(() -> MemoryCallUtil.safeCall(m::pop, this), this);
        return address + 1;
    }

    public String toString() {return "pop";}
}
