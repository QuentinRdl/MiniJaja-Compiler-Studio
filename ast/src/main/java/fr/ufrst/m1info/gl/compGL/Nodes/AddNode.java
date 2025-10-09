package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Value;

public class AddNode extends BinaryOperator{
    public AddNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "add";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand) throws Exception {
        return new Value(leftOperand.valueInt + rightOperand.valueInt);
    }
}
