package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.StackObject;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;

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
        }catch (Exception e){
            throw new ASTInvalidMemoryException("new line ("+(address+1)+") : "+e.getMessage());
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
        for (int i=scope-1; i>-1; i--){
            StackObject s=listSO.get(i);
            m.push(s.getName(),s.getValue(),s.getDataType(),s.getEntryKind());
        }
        if (type!=DataType.INT && type!=DataType.BOOL){
            throw new ASTInvalidDynamicTypeException("new line ("+(address+1)+") : Invalid type.");
        }
        if (kind==EntryKind.VARIABLE){
            if (v.type!=ValueType.EMPTY && ValueType.toDataType(v.type)!=type){
                throw new ASTInvalidDynamicTypeException("new line ("+(address+1)+") : "+type+" variable cannot be declared with "+ValueType.toDataType(v.type)+" value.");
            }
            m.declVar(identifier,v,type);
        }
        else if (kind==EntryKind.CONSTANT){
            if (v.type!=ValueType.EMPTY && ValueType.toDataType(v.type)!=type){
                throw new ASTInvalidDynamicTypeException("new line ("+(address+1)+") : "+type+" constant cannot be declared with "+ValueType.toDataType(v.type)+" value.");
            }
            m.declCst(identifier,v,type);
        }else if (kind==EntryKind.METHOD){
            if (v.type!=ValueType.INT){
                throw new ASTInvalidDynamicTypeException("new line ("+(address+1)+") : Value must be of type int.");
            }
            m.push(identifier,v,type,EntryKind.METHOD);
        }
        else {
            throw new ASTBuildException("new line ("+(address+1)+") : Entry kind must be var or const");
        }
        return address+1;
    }
}
