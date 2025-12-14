package fr.ufrst.m1info.pvm.group5.ast.nodes;

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
    ASTNode ident;

    public WriteLineNode(String text){
        this.text = text;
        this.ident = null;;
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
            if(v == null)
                throw new ASTInvalidMemoryException("Unknown variable : " + iNode.identifier);
            m.writeLine(v.toString());
        }
        else if(ident instanceof TabNode tab){
            Value v = tab.eval(m);
            if(v == null)
                throw new ASTInvalidMemoryException("Unknown array : " + tab.ident.identifier);
            m.writeLine(v.toString());
        }
        else{
            m.writeLine(text);
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        if(ident instanceof IdentNode iNode){
            try {
                if (m.isArray(iNode.identifier)){
                    throw new ASTInvalidOperationException("Line "+ getLine() +" : Writeln operation cannot be used with an array.");
                }
                Value v = (Value) m.val(iNode.identifier);
                if(v == null)
                    throw new ASTInvalidMemoryException("Variable " + iNode.identifier +" is not defined");
            }
            catch (Exception e) {
                throw new ASTInvalidMemoryException("Variable " + iNode.identifier +" is not defined");
            }
        }
        else if(ident instanceof TabNode tab){
            try {
                Value v = (Value) m.val(tab.ident.identifier);
                if(v == null)
                    throw new ASTInvalidMemoryException("Array " + tab.ident.identifier +" is not defined");
            }
            catch (Exception e) {
                throw new ASTInvalidMemoryException("Array " + tab.ident.identifier +" is not defined");
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
    public List<ASTNode> getChildren() {
        if(ident != null)
            return List.of(ident);
        return List.of();
    }
}
