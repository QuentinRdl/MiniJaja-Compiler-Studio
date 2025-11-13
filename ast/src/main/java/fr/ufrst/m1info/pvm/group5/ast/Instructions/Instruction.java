package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Stack;

public abstract class Instruction {
    /**
     *
      * @param address The address of the current instruction
     * @param m Memory used for the interpretation
     * @return The address of the next instruction to be executed
     */
    public abstract int execute(int address, Memory m) throws Exception;

}