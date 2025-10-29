package fr.ufrst.m1info.pvm.group5.ast.Nodes;

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
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        String exprType = exp.checkType(m);
        if (!exprType.equals("int")) {
            throw new ASTInvalidDynamicTypeException(
                    "Minus operator applied to a non-int type : " + exprType
            );
        }
        return "int";
    }


    @Override
    public Value eval(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        Value v = ((EvaluableNode)exp).eval(m);
        return new Value(-v.valueInt);
    }
}
