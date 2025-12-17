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
            throw new ASTBuildException("Not", "expr", "Not operator must have a non-null operand");
        }
        else if(!(expr instanceof EvaluableNode))
            throw new ASTBuildException("Not", "expr", "Not operator must have an evaluable operand");
    }

    @Override
    public Value eval(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
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
        throw new ASTInvalidOperationException("interpretation", this);
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        String exprType = expr.checkType(m);
        if (!exprType.equals("bool")) {
            throw new InterpretationInvalidTypeException(this, "bool", exprType);
        }
        if (expr instanceof IdentNode && MemoryCallUtil.safeCall(()->m.isArray(((IdentNode) expr).identifier), this)){
            throw new InterpretationInvalidTypeException(this, "bool", "array");
        }
        return "bool";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(expr);
    }

    public String toString(){return "noy";}
}
