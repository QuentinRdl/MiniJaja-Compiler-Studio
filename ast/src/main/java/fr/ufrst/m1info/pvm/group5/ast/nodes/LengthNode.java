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
            throw new ASTBuildException("Length", "identifier", "Length node identifier must not be null");
        }
    }

    @Override
    public Value eval(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        int size = m.tabLength(ident.identifier);
        return new Value(size);
    }

    @Override
    public List<String> compile(int address) {
        return List.of("length("+ident.identifier+")");
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        throw new ASTInvalidOperationException("Length", "Length", this.getLine());
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        return "int";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(ident);
    }
}
