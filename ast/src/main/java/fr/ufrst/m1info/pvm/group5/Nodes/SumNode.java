package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.EvaluableNode;
import fr.ufrst.m1info.pvm.group5.Memory;
import fr.ufrst.m1info.pvm.group5.Value;

import java.util.ArrayList;
import java.util.List;

public class SumNode extends ASTNode{
    IdentNode identifier;
    ASTNode expression;

    public SumNode(IdentNode identifier, ASTNode expression){
        this.identifier = identifier;
        this.expression = expression;
        if(this.identifier == null){
            throw new ASTBuildException("Sum identifier cannot be null");
        }
        if(this.expression == null){
            throw new ASTBuildException("Sum operand cannot be null");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(expression.compile(address));
        JJCodes.add("inc(" + identifier + ")");
        return List.of();
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException {
        Value v = ((EvaluableNode)expression).eval(m);
        Value u = (Value)m.val(identifier.identifier);
        if(u == null){
            throw new ASTInvalidMemoryException("Variable" + identifier.identifier + " is undefined");
        }
        int res = u.valueInt + v.valueInt;
        m.affectValue(identifier.identifier, new Value(res));
    }
}
