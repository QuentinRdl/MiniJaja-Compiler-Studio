package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.EvaluableNode;
import fr.ufrst.m1info.pvm.group5.Memory;
import fr.ufrst.m1info.pvm.group5.Value;

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
        return jajacodes;
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
