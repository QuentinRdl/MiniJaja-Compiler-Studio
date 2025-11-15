package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Stack_Object;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class IfInstruction extends Instruction{
    int addressIf;

    public IfInstruction(int addressIf){
        this.addressIf = addressIf;
    }


    @Override
    public int execute(int address, Memory m) {
        boolean b;
        Value v;
        try {
            v = ((Value) m.pop());
        }catch (Exception e){
            throw new ASTInvalidMemoryException(e.getMessage());
        }
        if (v.Type!=ValueType.BOOL){
            throw new ASTInvalidDynamicTypeException("if line ("+(address+1)+") : Value in top of the stack must be boolean");
        }
        b = v.valueBool;
        if (b){
            return addressIf;
        }
        return address+1;
    }
}
