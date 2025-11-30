package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;

public class InvokeInstruction extends Instruction {
    String ident;

    public InvokeInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        Value vMeth = (Value) m.val(ident);
        if (vMeth==null) {
            throw new ASTInvalidMemoryException("invoke line ("+(address+1)+") : Method "+ident+" is undefined");
        }
        try {
            m.getMethod(ident);
        } catch (Exception e) {
            throw new ASTInvalidMemoryException("invoke line ("+(address+1)+") : "+ident+" is not a method");
        }
        int newAdr=vMeth.valueInt;
        if (newAdr<1){
            throw new IndexOutOfBoundsException("invoke line ("+(address+1)+" : Value must be positive.");
        }
        m.push(".", new Value(address+1), DataType.INT, EntryKind.CONSTANT);
        return newAdr;
    }
}
