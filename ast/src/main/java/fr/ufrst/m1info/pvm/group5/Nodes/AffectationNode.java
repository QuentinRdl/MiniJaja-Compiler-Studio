package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.EvaluableNode;
import fr.ufrst.m1info.pvm.group5.Memory.Memory;
import fr.ufrst.m1info.pvm.group5.Value;

import java.util.ArrayList;
import java.util.List;

public class AffectationNode extends ASTNode{
    IdentNode identifier;
    ASTNode expression;

    public AffectationNode(IdentNode identifier, ASTNode expression) {
        this.identifier = identifier;
        this.expression = expression;
        if(identifier == null || expression == null){
            throw new NullPointerException("AffectationNode cannot have null nodes");
        }
        if(!(expression instanceof EvaluableNode)){
            throw new NullPointerException("AffectationNode cannot have non-evaluable nodes");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(expression.compile(address));
        JJCodes.add("store("+identifier.identifier+")");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException, ASTInvalidOperationException {
        Value v = ((EvaluableNode)expression).eval(m);
        m.affectValue(identifier.identifier, v);
    }
}
