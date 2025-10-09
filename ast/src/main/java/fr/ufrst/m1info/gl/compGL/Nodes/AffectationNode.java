package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Memory.Memory;
import fr.ufrst.m1info.gl.compGL.Value;

import java.util.ArrayList;
import java.util.List;

public class AffectationNode extends ASTNode{
    IdentNode identifier;
    ASTNode expression;

    public AffectationNode(IdentNode identifier, ASTNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(expression.compile(address));
        JJCodes.add("store("+identifier+")");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        Value v = ((EvaluableNode)expression).eval(m);
        m.affectValue(identifier.identifier, v);
    }
}
