package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory;
import fr.ufrst.m1info.gl.compGL.Value;

import java.util.List;

public class AndNode extends BinaryOperator{
    public AndNode(ASTNode left, ASTNode right) {
        super(left, right);
    }

    public static AndNode create(ASTNode left, ASTNode right) {
        return new AndNode(left, right);
    }

    @Override
    public Value eval(Memory m) {
        return null;
    }

    @Override
    public List<String> compile(int address) {
        return List.of();
    }
}
