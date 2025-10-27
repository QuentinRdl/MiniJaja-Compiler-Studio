package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;


import java.util.List;

public abstract class ASTNode {

    /**
     * Compile the node and its descendants
     * @return List of JaJaCodes compiled from it
     */
    public abstract List<String> compile(int address);

    /**
     * Interpret the node and it's descendants using a memory
     * @param m Memory used for the interpretation
     * @throws Exception throws an exception when an error occurs while trying to evaluate the node
     */
    public abstract void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException;

}
