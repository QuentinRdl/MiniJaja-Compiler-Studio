package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
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
        if(kind != EntryKind.METHOD && kind != EntryKind.CONSTANT && kind != EntryKind.VARIABLE){
            throw new ASTBuildException("new", "kind", "variable kind cannot be "+kind);
        }
    }

    @Override
    public int execute(int address, Memory m) {
        Value v;
        ArrayList<StackObject> listSO= new ArrayList<>();
        try{
            for (int i=0; i<scope; i++){
                listSO.add(MemoryCallUtil.safeCall(m::top, this));
                m.pop();
            }
        }catch (Memory.MemoryIllegalOperationException e){
            throw ASTInvalidMemoryException.EmptyStack(this);
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
                v=null;
            }
        }catch (Exception e){
            v=null;
        }
        if(kind == EntryKind.METHOD)
            compatibleType(List.of(ValueType.INT, ValueType.BOOL, ValueType.VOID), DataType.toValueType(type));
        else
            compatibleType(List.of(ValueType.INT, ValueType.BOOL), DataType.toValueType(type));
        if (kind==EntryKind.VARIABLE){
            if (v != null && v.type!=ValueType.EMPTY){
                compatibleType(DataType.toValueType(type), v.type);
            }
            final var u = v;
            MemoryCallUtil.safeCall(() -> m.declVar(identifier,(u==null)?new Value():u,type), this);
        }
        else if (kind==EntryKind.CONSTANT){
            if (v!=null && v.type!=ValueType.EMPTY){
                compatibleType(DataType.toValueType(type), v.type);
            }
            final var u = v;
            MemoryCallUtil.safeCall(() -> m.declCst(identifier,u,type), this);
        }else{
            if(v==null)
                throw new InterpretationInvalidTypeException(this, "int", "null");
            compatibleType(ValueType.INT , v.type);
            final var u = v;
            MemoryCallUtil.safeCall(() -> m.push(identifier,u,type,EntryKind.METHOD), this);
        }
        for (int i=scope-1; i>-1; i--){
            StackObject s=listSO.get(i);
            MemoryCallUtil.safeCall(() -> m.push(s.getName(),s.getValue(),s.getDataType(),s.getEntryKind()), this);
        }
        return address+1;
    }

    public String toString(){return "new";}
}
