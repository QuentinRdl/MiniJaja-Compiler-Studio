package fr.ufrst.m1info.gl.compGL;

import fr.ufrst.m1info.gl.compGL.Memory.Memory;

import java.util.List;

public interface WithradawableNode {
    public void WithradawInterpret(Memory m);
    public List<String> WithdrawCompile(int address);
}
