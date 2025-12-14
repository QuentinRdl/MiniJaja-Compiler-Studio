package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.List;

public class LengthNode extends ASTNode implements EvaluableNode {
    IdentNode ident;

    public LengthNode(IdentNode ident) {

        this.ident = ident;
        if(ident==null){
            throw new ASTBuildException("Length method must have an identifier");
        }
    }

    @Override
    public Value eval(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        int size = m.tabLength(ident.identifier);
        if (size==-1){
            throw new ASTInvalidOperationException("Line "+getLine()+" : "+ident.identifier+" is not a array");
        }
        return new Value(size);
    }

    @Override
    public List<String> compile(int address) {
        return List.of("length("+ident.identifier+")");
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        throw new ASTInvalidOperationException("Length node cannot be interpreted");
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        return "int";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(ident);
    }
}
