package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
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
        if(decls != null && !(decls instanceof WithdrawalNode)){
            throw new ASTBuildException("Class node declaration must be withradawable");
        }
    }

    @Override
    public List<String> compile(int  address) {
        List<String> jjcodes = new ArrayList<String>();
        jjcodes.add("init");
        if(decls!=null)
            jjcodes.addAll(decls.compile(address + jjcodes.size()));
        jjcodes.addAll(main.compile(address + jjcodes.size()));
        if(decls!=null)
            jjcodes.addAll(((WithdrawalNode)decls).withdrawCompile(address + jjcodes.size()));
        jjcodes.add("pop");
        jjcodes.add("jcstop");
        return jjcodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException, ASTInvalidOperationException {
        m.declVarClass(ident.identifier);
        if(decls!=null)
            decls.interpret(m);
        main.interpret(m);
        if(decls != null){
            ((WithdrawalNode) decls).withdrawInterpret(m);
        }
        // Only withdraw the class declaration if we don't want to preserve the items of memory
        // This is so we can test
        if (!m.isPreserveAfterInterpret()) {
            m.withdrawDecl(ident.identifier);
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        if (decls != null) {
            decls.checkType(m);
        }
        main.checkType(m);

        return "void";
    }

    @Override
    protected List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(ident);
        if(decls!=null)
            children.add(decls);
        children.add(main);
        return children;
    }


}
