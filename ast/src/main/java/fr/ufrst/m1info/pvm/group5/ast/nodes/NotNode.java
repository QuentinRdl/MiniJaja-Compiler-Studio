package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;

public class NotNode extends ASTNode implements EvaluableNode {
    ASTNode expr;

    public NotNode(ASTNode expr) {

        this.expr = expr;
        if(expr==null){
            throw new ASTBuildException("Not operator must have an operand");
        }
        else if(!(expr instanceof EvaluableNode))
            throw new ASTBuildException("Not operator must have an evaluable operand");
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

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        String exprType = expr.checkType(m);
        if (!exprType.equals("bool")) {
            throw new ASTInvalidDynamicTypeException(
                    "'not' operator applied to a non-bool type : " + exprType
            );
        }
        return "bool";
    }

    @Override
    protected List<ASTNode> getChildren() {
        return List.of(expr);
    }

}
