package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.ast.EvaluableNode;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NumberNode extends ASTNode implements EvaluableNode {
    int number;

    public NumberNode(int number){
        this.number = number;
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.add("push(" + this.number + ")");
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException{
        throw new ASTInvalidOperationException("Cannot interpret number");
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        return "int";
    }

    @Override
    protected Map<String,String> getProperties(){
        return Map.ofEntries(Map.entry("value", String.valueOf(this.number)));
    }

    @Override
    protected List<ASTNode> getChildren() {
        return List.of();
    }

    @Override
    public Value eval(Memory m) {
        return new Value(this.number);
    }
}
