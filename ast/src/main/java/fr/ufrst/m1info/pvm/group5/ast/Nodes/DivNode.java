package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.memory.Value;

public class DivNode extends BinaryOperator{
    public DivNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "div";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand) throws ASTInvalidOperationException {
        if (rightOperand.valueInt != 0){
            return new Value(leftOperand.valueInt / rightOperand.valueInt);
        }
        throw new ASTInvalidOperationException("Cannot divide by zero");
    }
}
