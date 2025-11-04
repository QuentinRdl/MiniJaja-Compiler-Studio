package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
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

    @Override
    protected String controlType(String leftType, String rightType) throws ASTInvalidDynamicTypeException {
        if (!leftType.equals("bool") || !rightType.equals("bool")){
            throw new ASTInvalidDynamicTypeException(
                    "Or operator must be used with 2 operand of type bool"
            );
        }
        return "bool";
    }
}
