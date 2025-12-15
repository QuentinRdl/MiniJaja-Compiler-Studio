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
        if (expr instanceof IdentNode && m.isArray(((IdentNode) expr).identifier)){
            throw new ASTInvalidOperationException("Line "+ getLine() +" : Not operator cannot be used with an array.");
        }
        Value v = ((EvaluableNode)expr).eval(m);
        return new Value(!v.valueBool);
    }

    @Override
    public List<String> compile(int address) {
        List<String> jjcodes = new ArrayList<>();
        jjcodes.addAll(expr.compile(address));
        jjcodes.add("not");
        return jjcodes;
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
    public List<ASTNode> getChildren() {
        return List.of(expr);
    }

}
