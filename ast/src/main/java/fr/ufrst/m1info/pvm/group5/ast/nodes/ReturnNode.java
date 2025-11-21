package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;

public class ReturnNode extends ASTNode{
    ASTNode expr;

    public ReturnNode(ASTNode expr){
        this.expr = expr;
        if(expr == null)
            throw new ASTBuildException("Return node must have a expression");
        else if (!(expr instanceof EvaluableNode))
            throw new ASTBuildException("Return node must have an evaluable expression");
    }

    @Override
    public List<String> compile(int address) {
        List<String> jjcodes = new ArrayList<>();
        jjcodes.addAll(expr.compile(address));
        return jjcodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        Value v = ((EvaluableNode)expr).eval(m);
        m.affectValue(m.identVarClass(), v);
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        String exprType = expr.checkType(m);
        return exprType;
    }

    @Override
    protected List<ASTNode> getChildren() {
        return List.of(expr);
    }

}
