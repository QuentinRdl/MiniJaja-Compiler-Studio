package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.StackObject;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;

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
        if (kind==EntryKind.VARIABLE){
            m.declVar(identifier,v,type);
        }
        else if (kind==EntryKind.CONSTANT){
            m.declCst(identifier,v,type);
            // todo : add entry kind method
        }else {
            throw new ASTBuildException("new line ("+(address+1)+") : Entry kind must be var or const");
        }
        return address+1;
    }
}
