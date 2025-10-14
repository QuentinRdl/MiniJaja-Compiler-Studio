package fr.ufrst.m1info.pvm.group5.Nodes;


import fr.ufrst.m1info.pvm.group5.Value;
import fr.ufrst.m1info.pvm.group5.ValueType;

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
        if (leftOperand.Type == ValueType.BOOL){
            return new Value(leftOperand.valueBool == rightOperand.valueBool);
        }
        return new Value(leftOperand.valueInt == rightOperand.valueInt);
    }
}
