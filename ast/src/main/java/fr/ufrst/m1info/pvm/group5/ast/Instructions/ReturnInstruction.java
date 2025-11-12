package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

public class ReturnInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) throws Exception {
        var top = m.pop();
        return ((Value) top).valueInt;
    }
}
