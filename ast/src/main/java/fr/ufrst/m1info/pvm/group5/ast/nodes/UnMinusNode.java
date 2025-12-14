package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;

public class UnMinusNode extends ASTNode implements EvaluableNode {
    ASTNode exp ;

    public UnMinusNode(ASTNode exp){

        this.exp = exp;
        if(exp == null){
            throw new ASTBuildException("Unary Minus", "expression", "Unary minus must have a non-null operand");
        }
        if(!(exp instanceof EvaluableNode)){
            throw new ASTBuildException("Unary Minus", "expression", "Unary minus must have an evaluable operand");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        jajacodes.addAll(exp.compile(address));
        jajacodes.add("neg");
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException {
        throw new ASTInvalidOperationException("interpretation", "Unary minus", this.getLine());
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        String exprType = exp.checkType(m);
        if (!exprType.equals("int")) {
            throw new InterpretationInvalidTypeException(this.getLine(), "int", exprType, "Unary minus");
        }
        return "int";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(exp);
    }


    @Override
    public Value eval(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        Value v = ((EvaluableNode)exp).eval(m);
        return new Value(-v.valueInt);
    }
}
