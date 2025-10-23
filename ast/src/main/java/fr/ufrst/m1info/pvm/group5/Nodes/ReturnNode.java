package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.*;

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
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        Value v = ((EvaluableNode)expr).eval(m);
        m.affectValue(m.identVarClass(), v);
    }

    @Override
    public String checkType() throws ASTInvalidDynamicTypeException {
        if (expr == null) {
            throw new ASTInvalidDynamicTypeException("Return without expression");
        }
        String exprType = expr.checkType();
        return exprType;
    }

}
