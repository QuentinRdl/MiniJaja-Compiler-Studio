package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class IncInstruction extends Instruction{
    String ident;

    public IncInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        Value v_add = (Value) m.val(ident);
        Value v_pop = (Value) m.pop();
        if (v_add.Type != ValueType.INT || v_pop.Type != ValueType.INT){
            throw new ASTInvalidTypeException("Type error: inc operator expects INT operands, but received " + v_add.Type + " and " + v_pop.Type + ".");
        }
        int res = v_pop.valueInt + v_add.valueInt;
        Value vres = new Value(res);
        m.affectValue(this.ident, vres);
        return address+1;
    }
}
