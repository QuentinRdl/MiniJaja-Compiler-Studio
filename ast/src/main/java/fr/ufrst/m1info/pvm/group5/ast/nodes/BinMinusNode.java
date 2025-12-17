package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
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
    protected String controlType(String leftType, String rightType) throws InterpretationInvalidTypeException {
        if (!leftType.equals("int") || !rightType.equals("int")){
            throw new InterpretationInvalidTypeException(this, "int", (!leftType.equals("int"))?leftType:rightType);
        }
        return "int";
    }

    public String toString(){return "-";}
}
