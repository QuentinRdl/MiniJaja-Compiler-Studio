package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;

public class PopInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        m.pop();
        return address + 1;
    }
}
