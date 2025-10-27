package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.ast.WithradawableNode;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

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
        if(ident == null){
            throw new ASTBuildException("Class node cannot have null identifier");
        }
        if(main == null){
            throw new ASTBuildException("Class must contain a main method");
        }
        if(decls != null && !(decls instanceof WithradawableNode)){
            throw new ASTBuildException("Class node declaration must be withradawable");
        }
    }

    @Override
    public List<String> compile(int  address) {
        List<String> JJCodes = new ArrayList<String>();
        JJCodes.add("init");
        if(decls!=null)
            JJCodes.addAll(decls.compile(address + JJCodes.size()));
        JJCodes.addAll(main.compile(address + JJCodes.size()));
        if(decls!=null)
            JJCodes.addAll(((WithradawableNode)decls).withdrawCompile(address + JJCodes.size()));
        JJCodes.add("pop");
        JJCodes.add("jcstop");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException, ASTInvalidOperationException {
        m.declVarClass(ident.identifier);
        if(decls!=null)
            decls.interpret(m);
        main.interpret(m);
        if(decls != null){
            ((WithradawableNode) decls).withradawInterpret(m);
        }
        m.withdrawDecl(ident.identifier);
    }
}
