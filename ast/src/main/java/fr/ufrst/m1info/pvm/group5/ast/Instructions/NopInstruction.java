package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;

public class NopInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) throws Exception {
        return address + 1;
    }
}
