package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.InterpretationInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.ast.LocatedElement;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;

import java.util.List;

public abstract class Instruction implements LocatedElement {
    private int line;

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    /**
     *
      * @param address The address of the current instruction
     * @param m Memory used for the interpretation
     * @return The address of the next instruction to be executed
     */
    public abstract int execute(int address, Memory m);

    /**
     * Compares two types
     * @param expected expected type
     * @param actual actual type
     * @throws InterpretationInvalidTypeException throws an exception if the check fails
     */
    public void compatibleType(ValueType expected, ValueType actual) {
        if(actual != expected){
            throw new InterpretationInvalidTypeException(this, expected.toString(), actual.name());
        }
    }

    /**
     * Asserts a type is present in a list of compatible types
     * @param expected List of expected types
     * @param actual actual type
     * @throws InterpretationInvalidTypeException throws an exception if the check fails
     */
    public void compatibleType(List<ValueType> expected, ValueType actual) {
        if(!expected.contains(actual)){
            throw new InterpretationInvalidTypeException(this, expected.toString(), actual.name());
        }
    }
}