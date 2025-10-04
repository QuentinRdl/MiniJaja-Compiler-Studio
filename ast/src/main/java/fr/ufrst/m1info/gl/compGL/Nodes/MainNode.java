package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory;
import fr.ufrst.m1info.gl.compGL.WithradawableNode;

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
    public List<String> compile() {
        List<String> JJCodes = new ArrayList<String>();
        JJCodes.addAll(vars.compile());
        JJCodes.addAll(instrs.compile());
        JJCodes.add("push(0)");
        JJCodes.addAll(((WithradawableNode)vars).WithdrawCompile());
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        vars.interpret(m);
        instrs.interpret(m);
        ((WithradawableNode)vars).WithradawInterpret(m);
    }
}
