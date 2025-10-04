package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory;

import java.util.ArrayList;
import java.util.List;

public class InstructionsNode extends ASTNode{
    ASTNode instruction;
    ASTNode otherInstructions;

    public InstructionsNode(ASTNode instruction, ASTNode otherInstructions) {
        this.instruction = instruction;
        this.otherInstructions = otherInstructions;
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(instruction.compile(address));
        if(otherInstructions!=null)
            JJCodes.addAll(otherInstructions.compile(address));
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        instruction.interpret(m);
        if(otherInstructions!=null)
            otherInstructions.interpret(m);
    }
}
