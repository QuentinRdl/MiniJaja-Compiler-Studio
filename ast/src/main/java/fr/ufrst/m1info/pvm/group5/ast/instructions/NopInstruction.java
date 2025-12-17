package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;

public class NopInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        return address + 1;
    }

    public String toString() {return "nop";}
}
