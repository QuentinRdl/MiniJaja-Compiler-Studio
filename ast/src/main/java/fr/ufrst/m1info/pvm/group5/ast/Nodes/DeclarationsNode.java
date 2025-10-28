package fr.ufrst.m1info.pvm.group5.ast.Nodes;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.ArrayList;
import java.util.List;

public class DeclarationsNode extends ASTNode implements WithradawableNode{
    ASTNode declaration;
    ASTNode declarations;

    public DeclarationsNode(ASTNode declaration, ASTNode declarations){
        this.declaration=declaration;
        this.declarations=declarations;
        if(declaration == null){
            throw new ASTBuildException("Invalid declaration");
        }
        if(!(declaration instanceof WithradawableNode)){
            throw new ASTBuildException("Declarations must be withdrawable");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
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
    public void withradawInterpret(Memory m) {
        if(declarations != null)
            ((WithradawableNode)declarations).withradawInterpret(m);
        ((WithradawableNode)declaration).withradawInterpret(m);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        if(declarations != null)
            jajacodes.addAll(((WithradawableNode)declarations).withdrawCompile(address));
        jajacodes.addAll(((WithradawableNode)declaration).withdrawCompile(address + jajacodes.size()));
        return jajacodes;
    }
}
