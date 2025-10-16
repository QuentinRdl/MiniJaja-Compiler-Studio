package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.Memory;

import java.util.List;

/**
 * Interface defining nodes or declaration that can be withdrawn from the memory
 */
public interface WithradawableNode {
    /**
     * Make the interpretation to withdraw the declaration from the given memory
     * @param m memory to withdraw the declaration from
     */
    public void withradawInterpret(Memory m);

    /**
     * Generate the instructions to withdraw the declaration from the stack
     * @param address starting address of the instructions
     * @return generated instructions
     */
    public List<String> withdrawCompile(int address);
}
