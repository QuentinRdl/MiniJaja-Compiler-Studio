package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Memory;
import fr.ufrst.m1info.gl.compGL.Value;

import java.util.ArrayList;
import java.util.List;

public class AffectationNode extends ASTNode{
    ASTNode identifier;
    ASTNode expression;

    public AffectationNode(ASTNode identifier, ASTNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(expression.compile(address));
        JJCodes.add("store("+identifier+")");
        return List.of();
    }

    @Override
    public void interpret(Memory m) throws Exception {
        Value v = ((EvaluableNode)expression).eval(m);
        // TODO : do this when we can use memory
    }
}
