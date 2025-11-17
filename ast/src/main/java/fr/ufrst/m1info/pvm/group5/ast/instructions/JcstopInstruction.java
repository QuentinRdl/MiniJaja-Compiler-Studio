package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;

public class JcstopInstruction extends Instruction{

    @Override
    public int execute(int address, Memory m) {
        return -1;
    }
}
