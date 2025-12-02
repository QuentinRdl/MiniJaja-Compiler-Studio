package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.StackObject;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;

import java.util.Objects;

public class NewarrayInstruction extends Instruction {
    String identifier;
    DataType type;

    public NewarrayInstruction(String identifier, DataType type){
        this.identifier=identifier;
        this.type=type;
    }

    @Override
    public int execute(int address, Memory m) {
        StackObject top = m.top();
        if (!Objects.equals(top.getName(), ".")){
            throw new ASTInvalidMemoryException("newarray line ("+(address+1)+") : Stack has no value");
        }
        Value v = ((Value) m.pop());
        if (ValueType.toDataType(v.type)!=DataType.INT){
            throw new ASTInvalidDynamicTypeException("newarray line ("+(address+1)+") : Value must be of type int");
        }
        if (type!=DataType.INT && type!=DataType.BOOL){
            throw new ASTInvalidDynamicTypeException("newarray line ("+(address+1)+") : Array must be of type int or bool");
        }
        int size=v.valueInt;
        if (size<1){
            throw new ASTInvalidOperationException("newarray line ("+(address+1)+") : Value must be positive");
        }
        m.declTab(identifier,size,type);
        return address+1;
    }
}
