package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Value;

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

    @Override
    protected String controlType(String leftType, String rightType) throws ASTInvalidDynamicTypeException {
        if (!leftType.equals("int") || !rightType.equals("int")){
            throw new ASTInvalidDynamicTypeException(
                    "Sup operator must be used with 2 operand of type int"
            );
        }
        return "bool";
    }
}
