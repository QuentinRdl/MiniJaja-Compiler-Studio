package fr.ufrst.m1info.pvm.group5.ast.Nodes;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;


import java.util.ArrayList;
import java.util.List;

/**
 * Absract class created to make the management of binary operators easier (I hope)
 */
public abstract class BinaryOperator extends ASTNode implements EvaluableNode {
    ASTNode left;
    ASTNode right;

    /**
     * Constructs a binary operator from it's two operands
     *
     * @param left  left operand of the operator
     * @param right right operand of the operator
     * @throws ASTBuildException throws an exception if one of the operator is null or not evaluable
     */
    public BinaryOperator(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
        if (this.left == null || this.right == null) {
            throw new ASTBuildException("Binary operator cannot have a null operand");
        }
        if (!(left instanceof EvaluableNode) || !(right instanceof EvaluableNode)) {
            throw new ASTBuildException("Binary operator cannot have a non-evaluable operand");
        }
    }

    public void interpret(Memory m) {
        throw new ASTInvalidOperationException("Cannot interpret BinaryOperator");
    }

    public Value eval(Memory m) throws ASTInvalidMemoryException, ASTInvalidOperationException {
        Value l = ((EvaluableNode) left).eval(m);
        Value r = ((EvaluableNode) right).eval(m);
        return mainOperation(l, r);
    }

    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(left.compile(address));
        JJCodes.addAll(right.compile(address + JJCodes.size()));
        JJCodes.add(getCompileName());
        return JJCodes;
    }

    /**
     * Get the name of the JaJaCode operation of the node
     *
     * @return Name of the JaJaCode operation corresponding to the node
     */
    protected abstract String getCompileName();

    /**
     * Operation performed by the node when evaluated
     *
     * @param leftOperand  Value of the left operand
     * @param rightOperand Value of the right operand
     * @return Result of the operations performed on the 2 operands
     * @throws Exception
     */
    protected abstract Value mainOperation(Value leftOperand, Value rightOperand) throws ASTInvalidMemoryException, ASTInvalidOperationException;

    @Override
    protected List<ASTNode> getChildren() {
        return List.of(left,right);
    }


    //TODO : redo this properly
    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        String leftType = left.checkType(m);
        String rightType = right.checkType(m);

        String className = getClass().getSimpleName();
        if (className.contains("Equal") || className.contains("NotEqual")) {

            if (!(leftType.equals(rightType) &&
                    (leftType.equals("int") || leftType.equals("bool")))) {
                throw new ASTInvalidDynamicTypeException(
                        "Invalid operand types for comparison in " + className
                );
            }
            return "bool";
        }
        if (className.contains("And") || className.contains("Or")) {
            if (!leftType.equals("bool") || !rightType.equals("bool")) {
                throw new ASTInvalidDynamicTypeException(
                        "Boolean operator " + className + " requires boolean operands"
                );
            }
            return "bool";
        }
        if (!leftType.equals("int") || !rightType.equals("int")) {
            throw new ASTInvalidDynamicTypeException(
                    "Arithmetic operator " + className + " requires integer operands"
            );
        }
        if(className.contains("Sup"))
            return "bool";
        return "int";
    }
}
