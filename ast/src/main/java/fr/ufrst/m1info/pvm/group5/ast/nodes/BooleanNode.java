package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.ast.EvaluableNode;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BooleanNode extends ASTNode implements EvaluableNode {
    boolean bool;

    public BooleanNode(boolean bool){
        this.bool = bool;
    }
    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        if (this.bool){
            jajacodes.add("push(jcvrai)");
        } else {
            jajacodes.add("push(jcfaux)");
        }
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException {
        throw new ASTInvalidOperationException("interpretation", this);
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        return "bool";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of();
    }

    @Override
    protected Map<String,String> getProperties(){
        return Map.ofEntries(Map.entry("value", String.valueOf(this.bool)));
    }

    @Override
    public Value eval(Memory m) {
        return new Value(this.bool);
    }

    public String toString(){return "bool";}
}
