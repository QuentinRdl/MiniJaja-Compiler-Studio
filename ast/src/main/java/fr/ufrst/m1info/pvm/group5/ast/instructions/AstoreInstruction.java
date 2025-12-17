package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;

public class AstoreInstruction extends Instruction{
    String ident;

    public AstoreInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        if (!MemoryCallUtil.safeCall(() -> m.isArray(ident), this)){
            throw new InterpretationInvalidTypeException("Expected " + ident + " to be an array", this);
        }
        Value v = (Value) MemoryCallUtil.safeCall(m::pop, this);
        Value index = (Value) MemoryCallUtil.safeCall(m::pop, this);
        DataType arrayType = MemoryCallUtil.safeCall(()->m.tabType(ident), this);
        compatibleType(DataType.toValueType(arrayType), v.type);
        compatibleType(ValueType.INT, index.type);
        MemoryCallUtil.safeCall(()->m.affectValT(ident, index.valueInt, v), this);
        return address+1;
    }

    public String toString(){
        return "astore";
    }
}
