package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.ast.Value;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.ArrayList;
import java.util.List;

public class WriteNode extends ASTNode{
    String text;
    IdentNode ident;

    public WriteNode(String text){
        this.text = text;
        this.ident = null;
    }

    public WriteNode(IdentNode ident){
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
        JJCodes.add("write");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        if(text == null){
            Value v = ident.eval(m);
            m.write(v.toString());
        }
        else{
            m.write(text);
        }
    }
}
