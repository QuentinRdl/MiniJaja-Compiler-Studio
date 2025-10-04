package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory;

import java.util.List;

public class InstructionsNode extends ASTNode{
    ASTNode instruction;
    ASTNode otherInstructions;

    public InstructionsNode(ASTNode instruction, ASTNode otherInstructions) {
        this.instruction = instruction;
        this.otherInstructions = otherInstructions;
    }

    @Override
    public List<String> compile() {
        return List.of();
    }

    @Override
    public void interpret(Memory m) throws Exception {

    }
}
