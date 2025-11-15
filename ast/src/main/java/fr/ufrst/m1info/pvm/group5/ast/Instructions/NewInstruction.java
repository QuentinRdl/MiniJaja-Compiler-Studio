package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
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
            v = ((Value) m.pop());
        }catch (Exception e){
            throw new ASTInvalidMemoryException(e.getMessage());
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
