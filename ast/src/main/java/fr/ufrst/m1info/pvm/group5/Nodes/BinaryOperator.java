package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.EvaluableNode;
import fr.ufrst.m1info.pvm.group5.Memory;
import fr.ufrst.m1info.pvm.group5.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Absract class created to make the management of binary operators easier (I hope)
 */
public abstract class BinaryOperator extends ASTNode implements EvaluableNode {
    ASTNode left;
    ASTNode right;

    public BinaryOperator(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public void interpret(Memory m) throws Exception{
        throw new Exception("Binary operators cannot be interpreted");
    }

    public Value eval(Memory m){
        Value l = ((EvaluableNode)left).eval(m);
        Value r = ((EvaluableNode)right).eval(m);
        return mainOperation(l,r);
    }

    public List<String> compile(int address){
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(left.compile(address));
        JJCodes.addAll(right.compile(address + JJCodes.size()));
        JJCodes.add(getCompileName());
        return JJCodes;
    }

    protected abstract String getCompileName();
    protected abstract Value mainOperation(Value leftOperand, Value rightOperand);
}
