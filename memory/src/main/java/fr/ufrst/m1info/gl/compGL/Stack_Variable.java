package fr.ufrst.m1info.gl.compGL;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Represents an entry of a variable in the stack, with name that contains the actual
 * name of the variable and scope the scope it has as a suffix
 */
public class Stack_Variable {
    private final String name;
    private Object value;
    private final int scope;

    /**
     * Constructor
     * @param name name of the var
     * @param value value of the var
     * @param scope scope of the var
     */
    public Stack_Variable(String name, Object value, int scope) {
        this.name = name;
        this.value = value;
        this.scope = scope;
    }

    /**
     * Returns the name of the var
     * @return String name of the var
     */
    public String getName() {
       return name;
    }

    /**
     * Returns the value of the var
     * @return Object value of the var
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns the scope of the var
     * @return int Scope of the var
     */
    public int getScope() {
        return scope;
    }

    /**
     * Sets the value of the var
     * @param value new value for the var
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Prints the var
     * @return String the var
     */
    @Override
    public String toString() {
        return name + "_" + scope + "=" + value;
    }
}
