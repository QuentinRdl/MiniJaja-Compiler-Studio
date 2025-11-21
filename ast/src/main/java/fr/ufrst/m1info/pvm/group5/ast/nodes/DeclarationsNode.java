package fr.ufrst.m1info.pvm.group5.ast.nodes;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.ArrayList;
import java.util.List;

public class DeclarationsNode extends ASTNode implements WithdrawalNode {
    ASTNode declaration;
    ASTNode declarations;

    public DeclarationsNode(ASTNode declaration, ASTNode declarations){
        this.declaration=declaration;
        this.declarations=declarations;
        if(declaration == null){
            throw new ASTBuildException("Invalid declaration");
        }
        if(!(declaration instanceof WithdrawalNode)){
            throw new ASTBuildException("Declarations must be withdrawable");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        jajacodes.addAll(declaration.compile(address));
        if (declarations != null) {
            jajacodes.addAll(declarations.compile(address + jajacodes.size()));
        }
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        declaration.interpret(m);
        if(declarations != null)
            declarations.interpret(m);
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        declaration.checkType(m);

        if (declarations != null) {
            declarations.checkType(m);
        }
        return "void";
    }

    @Override
    protected List<ASTNode> getChildren() {
        List<ASTNode>  children = new ArrayList<>();
        children.add(declaration);
        if(declarations != null)
            children.add(declarations);
        return children;
    }

    @Override
    public void withdrawInterpret(Memory m) {
        if(declarations != null)
            ((WithdrawalNode)declarations).withdrawInterpret(m);
        ((WithdrawalNode)declaration).withdrawInterpret(m);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        if(declarations != null)
            jajacodes.addAll(((WithdrawalNode)declarations).withdrawCompile(address));
        jajacodes.addAll(((WithdrawalNode)declaration).withdrawCompile(address + jajacodes.size()));
        return jajacodes;
    }
}
