package fr.ufrst.m1info.pvm.group5;

import java.util.List;

public interface WithradawableNode {
    public void WithradawInterpret(Memory m);
    public List<String> WithdrawCompile(int address);
}
