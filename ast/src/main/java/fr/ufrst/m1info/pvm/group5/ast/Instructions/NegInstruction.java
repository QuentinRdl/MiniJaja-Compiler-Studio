package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class NegInstruction extends Instruction{

    @Override
    public int execute(int address, Memory m) {
        Value v = (Value) m.pop();
        if (v.Type != ValueType.INT){
            throw new ASTInvalidTypeException("Type error: neg operator expects one INT operands, but received " + v.Type + ".");
        }
        int res = -v.valueInt ;
        Value vres = new Value(res);
        m.push(".", vres, DataType.INT, EntryKind.CONSTANT);
        return address+1;
    }
}
