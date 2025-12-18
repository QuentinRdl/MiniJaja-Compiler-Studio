package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;

public class ReturnNode extends ASTNode{
    ASTNode expr;

    public ReturnNode(ASTNode expr){
        this.expr = expr;
        if(expr == null)
            throw new ASTBuildException("Return", "expression", "Return node must have a return value");
        else if (!(expr instanceof EvaluableNode))
            throw new ASTBuildException("Return", "expression","Return node must have an evaluable return value");
    }

    @Override
    public List<String> compile(int address) {
        List<String> jjcodes = new ArrayList<>();
        jjcodes.addAll(expr.compile(address));
        return jjcodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        Value v = ((EvaluableNode)expr).eval(m);
        MemoryCallUtil.safeCall(() -> m.affectValue(m.identVarClass(), v), this);
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        String expType=expr.checkType(m);
        ASTNode root=getRoot();
        while (root!=null){
            if (root instanceof MethodeNode methNode){
                String methType=methNode.returnType.getValueType().toString().toLowerCase();
                if (!expType.equals(methType)){
                    throw new InterpretationInvalidTypeException(this, methType, expType);
                }
                break;
            }
            root=root.getRoot();
        }
        return "void";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(expr);
    }

    public String toString(){return "return";}
}
