package fr.ufrst.m1info.pvm.group5;



/**
 * Interface implemented by nodes that can be interpreted in the "eval" mode.
 */
public interface EvaluableNode {
    /**
     * Interprets node in the "eval" mode using the memory and returns the result
     * @param m memory used for the evaluation
     * @return value resulting of the evaluation
     * @throws Exception throws an exception when performing an evaluation on wrong operators,
     *                   or when an attempt to read the memory fails
     */
    public Value eval(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException;
}
