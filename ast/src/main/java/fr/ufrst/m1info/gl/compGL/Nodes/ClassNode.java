package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory;
import fr.ufrst.m1info.gl.compGL.WithradawableNode;

import java.util.ArrayList;
import java.util.List;

public class ClassNode extends ASTNode {
    ASTNode ident;
    ASTNode decls;
    ASTNode main;

    public ClassNode(ASTNode ident, ASTNode decls, ASTNode main){
        this.ident = ident;
        this.decls = decls;
        this.main = main;
    }

    @Override
    public List<String> compile(int  address) {
        List<String> JJCodes = new ArrayList<String>();
        JJCodes.add("init");
        JJCodes.addAll(decls.compile(address));
        JJCodes.addAll(main.compile(address));
        JJCodes.addAll(((WithradawableNode)decls).WithdrawCompile());
        JJCodes.add("pop");
        JJCodes.add("jcstop");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        m.DeclVar();
        decls.interpret(m);
        main.interpret(m);
        if(decls instanceof WithradawableNode){
            ((WithradawableNode) decls).WithradawInterpret(m);
        }
    }
}
