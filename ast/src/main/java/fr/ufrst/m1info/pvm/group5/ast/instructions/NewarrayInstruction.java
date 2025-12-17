package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.StackObject;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;

import javax.xml.crypto.Data;
import java.util.List;
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
        StackObject top = MemoryCallUtil.safeCall(m::top, this);
        if (!Objects.equals(top.getName(), ".")){
            throw ASTInvalidMemoryException.EmptyStack(this);
        }
        Value v = ((Value) MemoryCallUtil.safeCall(m::pop, this));
        compatibleType(List.of(ValueType.INT, ValueType.BOOL), DataType.toValueType(type));
        compatibleType(ValueType.INT, v.type);
        int size=v.valueInt;
        MemoryCallUtil.safeCall(() -> m.declTab(identifier,size,type), this);
        return address+1;
    }

    public String toString() { return "newarray";}
}
