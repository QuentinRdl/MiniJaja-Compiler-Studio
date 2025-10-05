package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory;

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
    public List<String> compile() {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.addAll(declaration.compile());
        if (declarations != null){
            jajacodes.addAll(declarations.compile());
        }
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        declaration.interpret(m);
        declarations.interpret(m);
    }
}
