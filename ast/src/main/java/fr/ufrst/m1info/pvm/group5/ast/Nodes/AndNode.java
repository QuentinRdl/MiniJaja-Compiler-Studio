package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.Value;

public class AndNode extends BinaryOperator{

    public AndNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "and";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand){
        return new Value(leftOperand.valueBool && rightOperand.valueBool);
    }
}
