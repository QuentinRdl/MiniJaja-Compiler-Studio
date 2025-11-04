package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
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

    @Override
    protected String controlType(String leftType, String rightType) throws ASTInvalidDynamicTypeException {
        if (!leftType.equals("int") || !rightType.equals("int")){
            throw new ASTInvalidDynamicTypeException(
                    "Div operator must be used with 2 operand of type int"
            );
        }
        return "int";
    }
}
