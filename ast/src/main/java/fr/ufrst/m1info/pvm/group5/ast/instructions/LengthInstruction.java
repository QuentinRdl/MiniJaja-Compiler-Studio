package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;

public class LengthInstruction extends Instruction {
    String ident;

    public LengthInstruction(String ident) {
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        if (!m.isArray(ident)){
            throw new ASTInvalidTypeException("Type error: length instruction expects array" );
        }
        int l = m.tabLength(ident);
        Value vl = new Value(l);
        m.push(".", vl, DataType.INT, EntryKind.CONSTANT);
        return address + 1;
    }

}
