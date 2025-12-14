package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.StackObject;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;

public class NewInstruction extends Instruction{
    String identifier;
    DataType type;
    EntryKind kind;
    int scope;

    public NewInstruction(String identifier, DataType type, EntryKind kind, int scope){
        this.identifier=identifier;
        this.type=type;
        this.kind = kind;
        this.scope = scope;
    }

    @Override
    public int execute(int address, Memory m) {
        Value v;
        ArrayList<StackObject> listSO= new ArrayList<StackObject>();
        try{
            for (int i=0; i<scope; i++){
                listSO.add(m.top());
                m.pop();
            }
        }catch (Memory.MemoryIllegalOperationException e){
            throw ASTInvalidMemoryException.EmptyStack(this.getLine());
        }
        try{
            StackObject top= m.top();
            String name= ".";
            if (top!=null){
                name=top.getName();
            }
            if(name.equals(".")) {
                v = ((Value) m.pop());
            }else{
                v=new Value();
            }
        }catch (Exception e){
            v=new Value();
        }
        if(kind == EntryKind.METHOD)
            compatibleType(ValueType.INT, DataType.toValueType(type));
        else
            compatibleType(List.of(ValueType.INT, ValueType.BOOL), DataType.toValueType(type));
        compatibleType(DataType.toValueType(type), v.type);
        if (kind==EntryKind.VARIABLE){
            m.declVar(identifier,v,type);
        }
        else if (kind==EntryKind.CONSTANT){
            m.declCst(identifier,v,type);
        }else if (kind==EntryKind.METHOD){
            m.push(identifier,v,type,EntryKind.METHOD);
        }
        for (int i=scope-1; i>-1; i--){
            StackObject s=listSO.get(i);
            m.push(s.getName(),s.getValue(),s.getDataType(),s.getEntryKind());
        }
        return address+1;
    }

    public String toString(){return "new";}
}
