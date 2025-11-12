package fr.ufrst.m1info.pvm.group5.ast.Instructions;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;

public class NewInstruction extends Instruction{
    String identifier;
    DataType type;
    String v;
    int scope;

    public NewInstruction(String identifier, DataType type, String v, int scope){
        this.identifier=identifier;
        this.type=type;
        this.v = v;
        this.scope = scope;
    }

    @Override
    public int execute(int address, Memory m) {
        if (v.equals("var")){
            m.declVar(identifier,new Value(),type);
        }
        else if (v.equals("cst")){
            m.declCst(identifier,new Value(),type);
        }
        return address+1;
    }
}
