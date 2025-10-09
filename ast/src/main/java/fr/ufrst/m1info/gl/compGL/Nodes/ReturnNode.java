package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Memory.Memory;
import fr.ufrst.m1info.gl.compGL.Value;

import java.util.ArrayList;
import java.util.List;

public class ReturnNode extends ASTNode{
    ASTNode expr;

    public ReturnNode(ASTNode expr){
        this.expr = expr;
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(expr.compile(address));
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        Value v = ((EvaluableNode)expr).eval(m);
        // TODO : Add memory primitive to this
    }
}
