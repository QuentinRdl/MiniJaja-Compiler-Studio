package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Value;
import fr.ufrst.m1info.gl.compGL.Memory.Memory;

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
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        // Compiling sub-instructions
        List<String> pe = condition.compile(address);
        List<String> piss = (iss == null)?List.of() : iss.compile(address + pe.size() + 2);
        // Node compilation
        JJCodes.addAll(pe);
        JJCodes.add("not");
        JJCodes.add("if(" + address + pe.size() + piss.size() + 3 +")");
        JJCodes.addAll(piss);
        JJCodes.add("goto("+address+")");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        Value e = ((EvaluableNode)condition).eval(m);
        if(e.valueBool){ // Rule [tantquevrai]
            if(iss != null)
                iss.interpret(m);
            interpret(m);
        }
    }
}
