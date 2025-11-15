package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Stack;

public class PopInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) throws Exception {
        m.pop();
        return address + 1;
    }
}
