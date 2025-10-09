package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Value;

public class DivNode extends BinaryOperator{
    public DivNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "div";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand) throws Exception{
        if (rightOperand.valueInt != 0){
            return new Value(leftOperand.valueInt / rightOperand.valueInt);
        }
        throw new Exception("Can not divide by zero");
    }
}
