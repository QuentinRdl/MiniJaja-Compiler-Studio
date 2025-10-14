package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.Memory.Memory;
import fr.ufrst.m1info.pvm.group5.WithradawableNode;

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
}
