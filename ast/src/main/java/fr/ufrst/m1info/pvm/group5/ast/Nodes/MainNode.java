package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.WithradawableNode;

import java.util.ArrayList;
import java.util.List;

public class MainNode extends ASTNode {
    ASTNode vars;
    ASTNode instrs;

    public MainNode(ASTNode vars, ASTNode instrs) {
        this.vars = vars;
        this.instrs = instrs;
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<String>();
        if(vars != null)
            JJCodes.addAll(vars.compile(address));
        if(instrs != null)
            JJCodes.addAll(instrs.compile(address + JJCodes.size()));
        JJCodes.add("push(0)");
        if(vars != null)
            JJCodes.addAll(((WithradawableNode)vars).withdrawCompile(address + JJCodes.size()));
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        if(vars != null)
            vars.interpret(m);
        if(instrs != null)
            instrs.interpret(m);
        if(vars != null)
            ((WithradawableNode)vars).withradawInterpret(m);
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        if (vars != null) {
            vars.checkType(m);
        }
        if (instrs != null) {
            instrs.checkType(m);
        }
        return "void";
    }

}
