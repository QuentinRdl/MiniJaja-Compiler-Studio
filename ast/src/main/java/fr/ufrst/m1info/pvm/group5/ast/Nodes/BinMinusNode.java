package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Value;

public class BinMinusNode extends BinaryOperator {
    public BinMinusNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "sub";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand){
        return new Value(leftOperand.valueInt - rightOperand.valueInt);
    }

    @Override
    protected String controlType(String leftType, String rightType) throws ASTInvalidDynamicTypeException {
        if (!leftType.equals("int") || !rightType.equals("int")){
            throw new ASTInvalidDynamicTypeException(
                    "Minus operator must be used with 2 operand of type int"
            );
        }
        return "int";
    }
}
