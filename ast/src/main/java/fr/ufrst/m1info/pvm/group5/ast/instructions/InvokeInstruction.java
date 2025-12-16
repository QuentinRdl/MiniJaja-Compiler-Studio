package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;

public class InvokeInstruction extends Instruction {
    String ident;

    public InvokeInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        Value vMeth = (Value) MemoryCallUtil.safeCall(() -> m.val(ident), this);
        if (vMeth==null) {
            throw ASTInvalidMemoryException.UndefinedVariable(ident, this.getLine());
        }
        try {
            m.getMethod(ident);
        } catch (Exception e) {
            if(m.dataTypeOf(ident) == null)
                throw ASTInvalidMemoryException.UndefinedVariable(ident, this.getLine());
            throw ASTInvalidMemoryException.InvalidVariable(ident, this.getLine(), "method", m.dataTypeOf(ident).name());
        }
        int newAdr=vMeth.valueInt;
        if (newAdr<1){
            throw new IndexOutOfBoundsException("invoke line ("+(address+1)+" : Value must be positive.");
        }
        MemoryCallUtil.safeCall(() -> m.push(".", new Value(address+1), DataType.INT, EntryKind.CONSTANT), this);
        MemoryCallUtil.safeCall(m::pushScope, this);
        return newAdr;
    }

    public String toString() {return "invoke";}
}
