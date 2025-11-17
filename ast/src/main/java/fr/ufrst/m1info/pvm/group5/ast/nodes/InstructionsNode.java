package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

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

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        instruction.checkType(m);

        if (otherInstructions != null) {
            otherInstructions.checkType(m);
        }
        return "void";
    }

    @Override
    protected List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(instruction);
        if(otherInstructions!=null)
            children.add(otherInstructions);
        return children;
    }

}
