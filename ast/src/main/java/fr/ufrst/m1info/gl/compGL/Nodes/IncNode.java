package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory.Memory;
import fr.ufrst.m1info.gl.compGL.Value;

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
    public void interpret(Memory m) throws Exception {
        Value v = (Value)m.val(ident.identifier);
        Value res = new Value(v.valueInt + 1);
        m.affectValue(ident.identifier, res);
    }
}