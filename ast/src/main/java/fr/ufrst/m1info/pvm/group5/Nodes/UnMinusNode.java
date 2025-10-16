package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.*;
import fr.ufrst.m1info.pvm.group5.Memory;

import java.util.ArrayList;
import java.util.List;

public class UnMinusNode extends ASTNode implements EvaluableNode {
    ASTNode exp ;

    public UnMinusNode(ASTNode exp){

        this.exp = exp;
        if(exp == null){
            throw new ASTBuildException("Unary minus must hava an operand");
        }
        if(!(exp instanceof EvaluableNode)){
            throw new ASTBuildException("Unary minus must have an evaluable operand");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.addAll(exp.compile(address));
        jajacodes.add("neg");
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException {
        throw new ASTInvalidOperationException("Cannot interpret unary minus operator");
    }

    @Override
    public Value eval(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        Value v = ((EvaluableNode)exp).eval(m);
        return new Value(-v.valueInt);
    }
}
