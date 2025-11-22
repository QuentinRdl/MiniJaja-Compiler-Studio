package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class LoadInstruction extends Instruction{
    String ident;

    public LoadInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        Value v = (Value) m.val(ident);
        if (v.type == ValueType.STRING){
            throw new ASTInvalidTypeException("load line ("+(address+1)+" : Type error: operation does not accept STRING type, but received " + v.type + ".");

        }
        if (v.type == ValueType.EMPTY){
            throw new ASTInvalidMemoryException("load line ("+(address+1)+" : Variable "+ident+" has no value");

        }
        m.push(".", v, ValueType.toDataType(v.type), EntryKind.CONSTANT);
        return address+1;
    }
}
