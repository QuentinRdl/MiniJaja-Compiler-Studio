package fr.ufrst.m1info.pvm.group5.ast.nodes;

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
        throw new RuntimeException(String.format("Line %d : Cannot divide by 0", this.getLine()));
    }

    @Override
    protected String controlType(String leftType, String rightType) throws ASTInvalidDynamicTypeException {
        if (!leftType.equals("int") || !rightType.equals("int")){
            throw new ASTInvalidDynamicTypeException(this.getLine(), "int", (!leftType.equals("int"))?leftType:rightType, "division");
        }
        return "int";
    }
}
