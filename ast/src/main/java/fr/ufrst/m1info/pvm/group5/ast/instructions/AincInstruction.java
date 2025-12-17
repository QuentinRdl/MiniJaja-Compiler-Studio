package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class AincInstruction extends Instruction{
    String ident;

    public AincInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        if (!MemoryCallUtil.safeCall(() -> m.isArray(ident), this)){
            throw new InterpretationInvalidTypeException("Expected "+ident+" to be an array", this);
        }

        Value vAdd = (Value) MemoryCallUtil.safeCall(m::pop, this);
        Value index = (Value) MemoryCallUtil.safeCall(m::pop, this);
        compatibleType(ValueType.INT, vAdd.type);
        compatibleType(ValueType.INT, index.type);
        Value v = MemoryCallUtil.safeCall(() -> m.valT(ident, index.valueInt), this);
        int res = v.valueInt + vAdd.valueInt;
        Value vres = new Value(res);
        MemoryCallUtil.safeCall(() -> m.affectValT(ident, index.valueInt, vres), this);
        return address+1;
    }

    public String toString(){
        return "ainc";
    }
}
