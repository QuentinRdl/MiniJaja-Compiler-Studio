package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

public class SwapInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        MemoryCallUtil.safeCall(m::swap, this);
        return address + 1;
    }

    public String toString() {
        return "swap";
    }
}
