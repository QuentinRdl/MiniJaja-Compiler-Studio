package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WriteLineNode extends ASTNode{
    String text;
    ASTNode ident;

    public WriteLineNode(String text){
        this.text = text;
        this.ident = null;
    }

    public WriteLineNode(ASTNode ident){
        this.ident = ident;
        this.text = null;
    }

    @Override
    public List<String> compile(int address) {
        List<String> jjcodes = new ArrayList<>();
        if(ident instanceof IdentNode){
            jjcodes.addAll(ident.compile(address));
        }
        else if(ident instanceof TabNode){
            jjcodes.addAll(ident.compile(address));
        }
        else {
            jjcodes.add("push(\""+text+"\")");
        }
        jjcodes.add("writeln");
        return jjcodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        if(ident instanceof IdentNode iNode){
            Value v = iNode.eval(m);
            m.writeLine(v.toString());
        }
        else if(ident instanceof TabNode tab){
            Value v = tab.eval(m);
            m.writeLine(v.toString());
        }
        else{
            m.writeLine(text);
        }
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        if(ident instanceof IdentNode ident) {
            if (MemoryCallUtil.safeCall(() -> m.isArray(ident.identifier), this))
                throw new InterpretationInvalidTypeException("Array type cannot be used with instruction " + "write", this);
            ident.checkType(m);
        }
        return "void";
    }

    @Override
    protected Map<String, String> getProperties(){
        if(text == null)
            return null;
        return Map.ofEntries(Map.entry("text", text));
    }

    @Override
    public List<ASTNode> getChildren() {
        if(ident != null)
            return List.of(ident);
        return List.of();
    }

    public String toString(){return "writeln";}
}
