package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.memory.Value;

public class AddNode extends BinaryOperator{
    public AddNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "add";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand) {
        return new Value(leftOperand.valueInt + rightOperand.valueInt);
    }
}
