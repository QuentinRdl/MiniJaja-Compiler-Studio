package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class StoreInstruction extends Instruction{
    String ident;

    public StoreInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        Value v = (Value) m.pop();
        if (v.Type == ValueType.STRING){
            throw new ASTInvalidTypeException("Type error: operation does not accept STRING type, but received " + v.Type + ".");
        }
        m.affectValue(this.ident, v);
        return address+1;
    }
}
