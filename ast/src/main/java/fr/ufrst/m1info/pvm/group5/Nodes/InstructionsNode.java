package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.Memory;

import java.util.ArrayList;
import java.util.List;

public class InstructionsNode extends ASTNode{
    ASTNode instruction;
    ASTNode otherInstructions;

    public InstructionsNode(ASTNode instruction, ASTNode otherInstructions) {
        this.instruction = instruction;
        this.otherInstructions = otherInstructions;
        if(instruction == null){
            throw new ASTBuildException("Invalid instruction definition");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(instruction.compile(address));
        if(otherInstructions!=null)
            JJCodes.addAll(otherInstructions.compile(address + JJCodes.size()));
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        instruction.interpret(m);
        if(otherInstructions!=null)
            otherInstructions.interpret(m);
    }
}
