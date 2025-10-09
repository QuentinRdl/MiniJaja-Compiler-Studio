package fr.ufrst.m1info.gl.compGL;

public interface EvaluableNode {
    public Value eval(Memory m) throws Exception;
}
