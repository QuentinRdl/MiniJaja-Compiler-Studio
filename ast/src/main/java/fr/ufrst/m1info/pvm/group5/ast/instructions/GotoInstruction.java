package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;

public class GotoInstruction extends Instruction {
    int address;

    public GotoInstruction(int address){
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public int execute(int address, Memory m) {
        return this.address;
    }

    public String toString() { return "goto";}
}
