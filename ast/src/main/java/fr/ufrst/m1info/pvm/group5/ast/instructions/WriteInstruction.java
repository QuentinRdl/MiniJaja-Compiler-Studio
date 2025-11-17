package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

public class WriteInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        Value top = (Value) m.pop();
        m.write(top.toString());
        return address + 1;
    }
}
