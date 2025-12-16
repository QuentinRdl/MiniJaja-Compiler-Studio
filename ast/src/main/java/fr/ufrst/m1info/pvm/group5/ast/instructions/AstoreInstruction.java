package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;

public class AstoreInstruction extends Instruction{
    String ident;

    public AstoreInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        if (!m.isArray(ident)){
            throw new ASTInvalidTypeException("Type error: astore instruction expects array" );
        }
        Value v = (Value) m.pop();
        Value index = (Value) m.pop();
        DataType arrayType = m.tabType(ident);
        if (ValueType.toDataType(v.type) != arrayType ){
            throw new ASTInvalidTypeException("Type error: The type of the value to be added is different from the type of the array.");
        }
        if (index.type != ValueType.INT ){
            throw new ASTInvalidTypeException("Type error: The type of the index must be a int.");
        }
        m.affectValT(ident, index.valueInt, v);
        return address+1;
    }
}
