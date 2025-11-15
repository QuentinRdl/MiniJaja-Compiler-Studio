package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Stack_Object;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class WriteInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) throws Exception {
        Value top = (Value) m.pop();
        m.write(top.toString());
        return address + 1;
    }
}
