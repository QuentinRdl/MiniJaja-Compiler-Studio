package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;

public class AloadInstruction extends Instruction{
    String ident;

    public AloadInstruction(String ident){
        this.ident = ident;
    }


    @Override
    public int execute(int address, Memory m) {
        Value index = (Value) MemoryCallUtil.safeCall(m::pop, this);
        if (!MemoryCallUtil.safeCall(() -> m.isArray(ident), this)){
            throw new InterpretationInvalidTypeException("Expected "+ident+" to be an array", this);
        }
        compatibleType(ValueType.INT, index.type);
        Value res = MemoryCallUtil.safeCall(()-> m.valT(ident, index.valueInt), this);
        MemoryCallUtil.safeCall(()->m.push(".", res, DataType.INT, EntryKind.CONSTANT), this);
        return address+1;
    }

    public String toString(){
        return "aload";
    }
}
