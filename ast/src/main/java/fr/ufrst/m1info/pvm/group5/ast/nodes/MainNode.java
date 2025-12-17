package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.ArrayList;
import java.util.List;

public class MainNode extends ASTNode {
    ASTNode vars;
    ASTNode instrs;

    public MainNode(ASTNode vars, ASTNode instrs) {
        this.vars = vars;
        this.instrs = instrs;
        if(vars != null && !(vars instanceof WithdrawalNode)){
            throw  new ASTBuildException("Main", "declarations", "Main node declarations must be withdrawable");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jjcodes = new ArrayList<>();
        if(vars != null)
            jjcodes.addAll(vars.compile(address));
        if(instrs != null)
            jjcodes.addAll(instrs.compile(address + jjcodes.size()));
        jjcodes.add("push(0)");
        if(vars != null)
            jjcodes.addAll(((WithdrawalNode)vars).withdrawCompile(address + jjcodes.size()));
        return jjcodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        if(vars != null)
            vars.interpret(m);
        if(instrs != null)
            instrs.interpret(m);
        if(vars != null)
            ((WithdrawalNode)vars).withdrawInterpret(m);
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        if (vars != null) {
            vars.checkType(m);
        }
        if (instrs != null) {
            instrs.checkType(m);
        }
        return "void";
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if(vars != null)
            children.add(vars);
        if(instrs != null)
            children.add(instrs);
        return children;
    }

    public String toString(){return "main";}
}
