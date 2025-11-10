package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WriteLineNode extends ASTNode{
    String text;
    IdentNode ident;

    public WriteLineNode(String text){
        this.text = text;
        this.ident = null;
    }

    public WriteLineNode(IdentNode ident){
        this.ident = ident;
        this.text = null;
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        if(text == null){
            JJCodes.addAll(ident.compile(address));
        }
        else {
            JJCodes.add("push(\""+text+"\")");
        }
        JJCodes.add("writeln");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        if(text == null){
            Value v = ident.eval(m);
            if(v == null)
                throw new ASTInvalidMemoryException("Unknown variable : " + ident.identifier);
            m.writeLine(v.toString());
        }
        else{
            m.writeLine(text);
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        if(text == null){
            try {
                Value v = (Value) m.val(ident.identifier);
                if(v == null)
                    throw new ASTInvalidDynamicTypeException("Variable " + ident.identifier +" is not defined");
            }
            catch (Exception e) {
                throw new ASTInvalidDynamicTypeException("Variable " + ident.identifier +" is not defined");
            }
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
    protected List<ASTNode> getChildren() {
        if(ident != null)
            return List.of(ident);
        return null;
    }
}
