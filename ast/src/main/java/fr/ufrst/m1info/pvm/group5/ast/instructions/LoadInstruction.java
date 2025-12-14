package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.List;

public class LoadInstruction extends Instruction{
    String ident;

    public LoadInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        Value v = (Value) m.val(ident);
        compatibleType(List.of(ValueType.INT, ValueType.BOOL), v.type);
        m.push(".", v, ValueType.toDataType(v.type), EntryKind.CONSTANT);
        return address+1;
    }

    public String toString(){
        return "load";
    }
}
