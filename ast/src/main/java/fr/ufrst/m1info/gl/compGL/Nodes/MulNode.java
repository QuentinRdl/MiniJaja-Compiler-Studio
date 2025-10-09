package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Value;

public class MulNode extends BinaryOperator{
    public MulNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "mul";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand) throws Exception {
        return new Value(leftOperand.valueInt * rightOperand.valueInt);
    }
}
