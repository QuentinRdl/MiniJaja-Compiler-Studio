package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.List;

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
        Value v_add = (Value) MemoryCallUtil.safeCall(() -> m.val(ident), this);
        Value v_pop = (Value) MemoryCallUtil.safeCall(m::pop, this);
        if(v_add.type == ValueType.EMPTY)
            throw ASTInvalidMemoryException.UndefinedVariable("x", this);
        compatibleType(ValueType.INT, v_add.type);
        compatibleType(ValueType.INT, v_pop.type);
        int res = v_pop.valueInt + v_add.valueInt;
        Value vres = new Value(res);
        MemoryCallUtil.safeCall(() -> m.affectValue(this.ident, vres), this);
        return address+1;
    }

    public String toString(){return "inc";}
}
