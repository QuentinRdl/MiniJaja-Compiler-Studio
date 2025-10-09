package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory.Memory;

import java.util.ArrayList;
import java.util.List;

public class SommeNode extends ASTNode{
    ASTNode identifier;
    ASTNode expression;

    SommeNode(ASTNode identifier, ASTNode expression){
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(expression.compile(address));
        JJCodes.add("inc(" + identifier + ")");
        return List.of();
    }

    @Override
    public void interpret(Memory m) throws Exception {
        // TODO : do this when whe have access to the memory class
    }
}
