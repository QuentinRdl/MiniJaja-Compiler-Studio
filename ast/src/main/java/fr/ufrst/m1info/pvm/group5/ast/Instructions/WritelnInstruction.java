package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Stack_Object;
import fr.ufrst.m1info.pvm.group5.memory.Value;

public class WritelnInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) throws Exception {
        Value top = (Value) m.pop();
        m.writeLine(top.toString());
        return address + 1;
    }
}
