package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class IfInstruction extends Instruction{
    int addressIf;

    public IfInstruction(int addressIf){
        this.addressIf = addressIf;
    }

    public int getAddressIf() {
        return addressIf;
    }

    @Override
    public int execute(int address, Memory m) {
        boolean b;
        Value v;
        try {
            v = ((Value) m.pop());
        }catch (Memory.MemoryIllegalOperationException e){
            throw ASTInvalidMemoryException.EmptyStack(this.getLine());
        }
        compatibleType(ValueType.BOOL, v.type);
        b = v.valueBool;
        if (b){
            return addressIf;
        }
        return address+1;
    }

    public String toString(){
        return "if";
    }
}
