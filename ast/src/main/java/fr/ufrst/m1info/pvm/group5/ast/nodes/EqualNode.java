package fr.ufrst.m1info.pvm.group5.ast.nodes;


import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class EqualNode extends BinaryOperator{
    public EqualNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    @Override
    protected String getCompileName() {
        return "cmp";
    }

    @Override
    protected Value mainOperation(Value leftOperand, Value rightOperand) {
        if (leftOperand.type == ValueType.BOOL){
            return new Value(leftOperand.valueBool == rightOperand.valueBool);
        }
        return new Value(leftOperand.valueInt == rightOperand.valueInt);
    }

    @Override
    protected String controlType(String leftType, String rightType) throws InterpretationInvalidTypeException {
        if (!leftType.equals(rightType)){
            throw new InterpretationInvalidTypeException(this, rightType, leftType);
        }
        if (!leftType.equals("int") && !leftType.equals("bool")){
            throw new InterpretationInvalidTypeException(this, "[int, bool]", leftType);
        }
        return "bool";
    }

    public String toString(){return "==";}
}
