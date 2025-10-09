package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Memory.Memory;
import fr.ufrst.m1info.gl.compGL.Value;

import java.util.ArrayList;
import java.util.List;

public class SommeNode extends ASTNode{
    IdentNode identifier;
    ASTNode expression;

    public SommeNode(IdentNode identifier, ASTNode expression){
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
        Value v = ((EvaluableNode)expression).eval(m);
        int res = ((Value)m.val(identifier.identifier)).valueInt + v.valueInt;
        m.affectValue(identifier.identifier, res);
    }
}
