package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;

public class StoreInstruction extends Instruction{
    String ident;

    public StoreInstruction(String ident){
        this.ident = ident;
    }

    @Override
    public int execute(int address, Memory m) {
        Value v = (Value) m.pop();
        DataType varType = m.dataTypeOf(ident);
        if (v.type == ValueType.STRING){
            throw new ASTInvalidTypeException("store line ("+(address+1)+") : Type error: operation does not accept STRING type, but received " + v.type + ".");
        }
        if (varType!= DataType.VOID && ValueType.toDataType(v.type) != varType){
            throw new ASTInvalidDynamicTypeException("store line ("+(address+1)+") : Type error: "+varType+" variable cannot store "+v.type+" value.");
        }
        m.affectValue(this.ident, v);
        return address+1;
    }
}
