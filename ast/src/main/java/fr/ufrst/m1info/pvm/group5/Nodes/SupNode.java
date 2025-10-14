package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.Value;

public class SupNode extends BinaryOperator{
    public SupNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "sup";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand) {
        return new Value(leftOperand.valueInt > rightOperand.valueInt);
    }
}
