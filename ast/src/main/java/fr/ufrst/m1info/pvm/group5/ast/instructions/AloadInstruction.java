package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
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
        Value index = (Value) m.pop();
        if (index.type != ValueType.INT){
            throw new ASTInvalidTypeException("Type error: aload instruction expects INT, but received " + index.type );
        }
        if (!m.isArray(ident)){
            throw new ASTInvalidTypeException("Type error: aload instruction expects array" );
        }
        Value res = m.valT(ident, index.valueInt);
        m.push(".", res, DataType.INT, EntryKind.CONSTANT);
        return address+1;
    }
}
