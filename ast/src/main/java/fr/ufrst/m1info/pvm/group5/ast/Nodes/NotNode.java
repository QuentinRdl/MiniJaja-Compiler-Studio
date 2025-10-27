package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.*;

import java.util.ArrayList;
import java.util.List;

public class NotNode extends ASTNode implements EvaluableNode {
    ASTNode expr;

    public NotNode(ASTNode expr) {

        this.expr = expr;
        if(expr==null){
            throw new ASTBuildException("Not operator must have an operand");
        }
    }

    @Override
    public Value eval(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        Value v = ((EvaluableNode)expr).eval(m);
        return new Value(!v.valueBool);
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(expr.compile(address));
        JJCodes.add("not");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException {
        throw new ASTInvalidOperationException("Not node cannot be interpreted");
    }
}
