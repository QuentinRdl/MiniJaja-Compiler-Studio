package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Memory;

/**
 * Absract class created to make the management of binary operators easier (I hope)
 */
public abstract class BinaryOperator extends ASTNode implements EvaluableNode {
    ASTNode left;
    ASTNode right;

    public BinaryOperator(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Creates a new binary operator from the two left/right node
     * Should theorically be created as a Factory design pattern, but it should work as it is.
     * @param left : Left child of the node
     * @param right : Right child of the node
     * @return new node created
     */
    public static BinaryOperator create(ASTNode left, ASTNode right) {
        return null;
    }

    /**
     * Merge the current one with another one.
     * The current node will be the left child of the node, and the right child will be the other.
     * @param other : Node to merge the current node with
     * @return New node created
     * @param <G> Type of the new node to create
     */
    public <G extends BinaryOperator> G merge(ASTNode other) {
        return (G) G.create(this, other);
    }

    public void interpret(Memory m) throws Exception{
        throw new Exception("Binary operators cannot be interpreted");
    }
}
