package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.Memory;
import fr.ufrst.m1info.pvm.group5.Value;

import java.util.ArrayList;
import java.util.List;

public class IncNode extends ASTNode{
    IdentNode ident;

    public IncNode(IdentNode ident){
        this.ident = ident;
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.add("push(1)");
        JJCodes.add("load("+ident+")");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException {
        Value v = (Value)m.val(ident.identifier);
        if(v == null){
            throw new ASTInvalidMemoryException("Variable " + ident + " is undefined");
        }
        Value res = new Value(v.valueInt + 1);
        m.affectValue(ident.identifier, res);
    }
}