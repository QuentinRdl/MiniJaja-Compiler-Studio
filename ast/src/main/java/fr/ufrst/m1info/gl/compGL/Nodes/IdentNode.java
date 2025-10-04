package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Memory;
import fr.ufrst.m1info.gl.compGL.Value;

import java.util.ArrayList;
import java.util.List;

public class IdentNode extends ASTNode implements EvaluableNode {
    String identifier;

    public IdentNode(String identifier){
        this.identifier = identifier;
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.add("load(" + this.identifier + ")");
        return List.of();
    }

    @Override
    public void interpret(Memory m) throws Exception{
        throw new Exception("Can not interpret ident node");
    }

    // TODO : do this when we have access to memory
    @Override
    public Value eval(Memory m) {
        return null;
    }
}
