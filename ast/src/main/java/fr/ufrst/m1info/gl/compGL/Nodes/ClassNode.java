package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory.DataType;
import fr.ufrst.m1info.gl.compGL.Memory.Memory;
import fr.ufrst.m1info.gl.compGL.WithradawableNode;

import java.util.ArrayList;
import java.util.List;

public class ClassNode extends ASTNode {
    IdentNode ident;
    ASTNode decls;
    ASTNode main;

    public ClassNode(IdentNode ident, ASTNode decls, ASTNode main){
        this.ident = ident;
        this.decls = decls;
        this.main = main;
    }

    @Override
    public List<String> compile(int  address) {
        List<String> JJCodes = new ArrayList<String>();
        JJCodes.add("init");
        if(decls!=null)
            JJCodes.addAll(decls.compile(address + JJCodes.size()));
        JJCodes.addAll(main.compile(address + JJCodes.size()));
        if(decls!=null)
            JJCodes.addAll(((WithradawableNode)decls).WithdrawCompile(address + JJCodes.size()));
        JJCodes.add("pop");
        JJCodes.add("jcstop");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        m.declVarClass(ident.identifier);
        if(decls!=null)
            decls.interpret(m);
        main.interpret(m);
        if(decls != null && decls instanceof WithradawableNode){
            ((WithradawableNode) decls).WithradawInterpret(m);
        }
    }
}
