package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class ReturnInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        Value top = (Value) m.pop();
        if(top.Type != ValueType.INT){
            throw new IllegalStateException("Return address must be int");
        }
        return top.valueInt;
    }
}
