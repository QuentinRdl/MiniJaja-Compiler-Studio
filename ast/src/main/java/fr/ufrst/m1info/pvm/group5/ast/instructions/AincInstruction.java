package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;

public class AincInstruction extends Instruction{
    String ident;

    public AincInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        if (!m.isArray(ident)){
            throw new ASTInvalidTypeException("Type error: ainc instruction expects array" );
        }

        Value vAdd = (Value) m.pop();
        Value index = (Value) m.pop();

        if (vAdd.type != ValueType.INT ){
            throw new ASTInvalidTypeException("Type error: The type of the value to be added must be an int.");
        }
        if (index.type != ValueType.INT ){
            throw new ASTInvalidTypeException("Type error: The type of the index must be an int.");
        }
        Value v = m.valT(ident, index.valueInt);
        int res = v.valueInt + vAdd.valueInt;
        Value vres = new Value(res);
        m.affectValT(ident, index.valueInt, vres);
        return address+1;
    }
}
