package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;


public class IncInstruction extends Instruction{
    String ident;

    public IncInstruction(String ident){
        this.ident = ident;
    }

    public String getIdent() {
        return ident;
    }

    @Override
    public int execute(int address, Memory m) {
        Value vAdd = (Value) MemoryCallUtil.safeCall(() -> m.val(ident), this);
        Value vPop = (Value) MemoryCallUtil.safeCall(m::pop, this);
        if(vAdd.type == ValueType.EMPTY)
            throw ASTInvalidMemoryException.UndefinedVariable("x", this);
        compatibleType(ValueType.INT, vAdd.type);
        compatibleType(ValueType.INT, vPop.type);
        int res = vPop.valueInt + vAdd.valueInt;
        Value vres = new Value(res);
        MemoryCallUtil.safeCall(() -> m.affectValue(this.ident, vres), this);
        return address+1;
    }

    public String toString(){return "inc";}
}
