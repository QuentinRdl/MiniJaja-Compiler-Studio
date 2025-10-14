package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.Value;

public class MulNode extends BinaryOperator{
    public MulNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "mul";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand) {
        return new Value(leftOperand.valueInt * rightOperand.valueInt);
    }
}
