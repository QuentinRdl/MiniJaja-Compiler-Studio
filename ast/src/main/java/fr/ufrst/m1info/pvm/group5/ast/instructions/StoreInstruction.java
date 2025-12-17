package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.List;

public class StoreInstruction extends Instruction{
    String ident;

    public StoreInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        Value v = (Value) MemoryCallUtil.safeCall(m::pop, this);
        ValueType varType = MemoryCallUtil.safeCall(() -> m.valueTypeOf(ident), this);
        compatibleType(List.of(ValueType.BOOL, ValueType.INT), v.type);
        if(varType!=ValueType.EMPTY) {
            compatibleType(varType, v.type);
        }
        MemoryCallUtil.safeCall(() -> m.affectValue(this.ident, v), this);
        return address+1;
    }

    public String toString(){
        return "store";
    }
}
