package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory;
import fr.ufrst.m1info.gl.compGL.Value;

import java.util.List;

public class AndNode extends BinaryOperator{

    public AndNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "and";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand) {
        return new Value(leftOperand.valueBool && rightOperand.valueBool);
    }
}
