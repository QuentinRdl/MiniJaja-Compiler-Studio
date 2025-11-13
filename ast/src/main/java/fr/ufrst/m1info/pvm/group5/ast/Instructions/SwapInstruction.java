package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;

public class SwapInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        m.swap();
        return address + 1;
    }
}
