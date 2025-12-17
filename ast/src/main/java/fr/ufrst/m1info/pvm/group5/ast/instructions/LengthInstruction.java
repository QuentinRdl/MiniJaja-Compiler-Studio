package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;

public class LengthInstruction extends Instruction {
    String ident;

    public LengthInstruction(String ident) {
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        if (!MemoryCallUtil.safeCall(()->m.isArray(ident), this)){
            throw new InterpretationInvalidTypeException("Expected " + ident + " to be an array", this);
        }
        int l = MemoryCallUtil.safeCall(()->m.tabLength(ident), this);
        Value vl = new Value(l);
        MemoryCallUtil.safeCall(()->m.push(".", vl, DataType.INT, EntryKind.CONSTANT), this);
        return address + 1;
    }

    public String toString(){
        return "length";
    }
}
