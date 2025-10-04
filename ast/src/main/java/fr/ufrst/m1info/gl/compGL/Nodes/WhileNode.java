package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Value;
import fr.ufrst.m1info.gl.compGL.Memory;
import fr.ufrst.m1info.gl.compGL.ValueType;

import java.util.ArrayList;
import java.util.List;

public class WhileNode extends ASTNode{
    ASTNode condition;
    ASTNode iss;

    public WhileNode(ASTNode condition, ASTNode iss){
        this.condition = condition;
        this.iss = iss;
    }

    @Override
    public List<String> compile() {
        List<String> JJCodes = new ArrayList<>();
        List<String> pe = condition.compile();
        JJCodes.addAll(pe);
        JJCodes.add("not");

        List<String> piss = iss.compile();
        return List.of();
    }

    @Override
    public void interpret(Memory m) throws Exception {
        Value e = ((EvaluableNode)condition).eval(m);
        if(e.valueBool){ // Rule [tantquevrai]
            iss.interpret(m);
            interpret(m);
        }
    }
}
