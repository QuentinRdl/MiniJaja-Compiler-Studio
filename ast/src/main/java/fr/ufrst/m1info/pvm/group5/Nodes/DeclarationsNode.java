package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.Memory;

import java.util.ArrayList;
import java.util.List;

public class DeclarationsNode extends ASTNode{
    ASTNode declaration;
    ASTNode declarations;

    public DeclarationsNode(ASTNode declaration, ASTNode declarations){
        this.declaration=declaration;
        this.declarations=declarations;
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
    public void interpret(Memory m) throws Exception {
        declaration.interpret(m);
        declarations.interpret(m);
    }
}
