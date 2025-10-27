package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.Value;

public class BinMinusNode extends BinaryOperator {
    public BinMinusNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "minus";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand){
        return new Value(leftOperand.valueInt - rightOperand.valueInt);
    }
}
