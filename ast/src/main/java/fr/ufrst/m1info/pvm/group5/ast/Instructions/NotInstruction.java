package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class NotInstruction extends Instruction{

    @Override
    public int execute(int address, Memory m) {
        Value v = (Value) m.pop();
        if (v.Type != ValueType.BOOL){
            throw new ASTInvalidTypeException("Type error: not operator expects one BOOL operands, but received " + v.Type + ".");
        }
        boolean res = !v.valueBool ;
        Value vres = new Value(res);
        m.push(".", vres, DataType.BOOL, EntryKind.CONSTANT);
        return address+1;
    }
}
