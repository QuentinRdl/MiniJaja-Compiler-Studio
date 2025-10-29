package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.memory.Value;

public class OrNode extends BinaryOperator {

    public OrNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "or";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand) {
        return new Value(leftOperand.valueBool || rightOperand.valueBool);
    }
}
