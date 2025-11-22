package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;

public abstract class Instruction {
    private int line;

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    /**
     *
      * @param address The address of the current instruction
     * @param m Memory used for the interpretation
     * @return The address of the next instruction to be executed
     */
    public abstract int execute(int address, Memory m);

}