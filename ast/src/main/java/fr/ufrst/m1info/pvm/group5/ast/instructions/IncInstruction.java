package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.List;

public class IncInstruction extends Instruction{
    String ident;

    public IncInstruction(String ident){
        this.ident = ident;
    }

    public String getIdent() {
        return ident;
    }

    @Override
    public int execute(int address, Memory m) {
        Value v_add = (Value) m.val(ident);
        Value v_pop = (Value) m.pop();
        compatibleType(ValueType.INT, v_add.type);
        compatibleType(ValueType.INT, v_pop.type);
        int res = v_pop.valueInt + v_add.valueInt;
        Value vres = new Value(res);
        m.affectValue(this.ident, vres);
        return address+1;
    }

    public String toString(){return "inc";}
}
