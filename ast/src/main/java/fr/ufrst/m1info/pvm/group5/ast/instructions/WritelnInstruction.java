package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

public class WritelnInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        Value top = (Value) m.pop();
        m.writeLine(top.toString());
        return address + 1;
    }
}
