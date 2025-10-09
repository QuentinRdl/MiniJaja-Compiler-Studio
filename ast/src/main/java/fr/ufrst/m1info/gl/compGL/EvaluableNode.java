package fr.ufrst.m1info.gl.compGL;

import fr.ufrst.m1info.gl.compGL.Memory.Memory;

public interface EvaluableNode {
    public Value eval(Memory m);
}
