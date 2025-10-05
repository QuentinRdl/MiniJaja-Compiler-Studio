package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory;

import java.util.List;

public class FinalNode extends ASTNode{
    @Override
    public List<String> compile() {
        return List.of();
    }

    @Override
    public void interpret(Memory m) throws Exception {

    }
}
