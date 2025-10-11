package fr.ufrst.m1info.gl.compGL;

import java.util.ArrayDeque;
import java.util.Deque;

// TODO : Write the JavaDoc
// TODO : Do the integration tests w/ the Stack class

/**
 * Represents an entry of a variable in the stack, with name that contains the actual
 * name of the variable and scope the scope it has as a suffix
 */
public class Stack_Variable {
    private final String name;
    private Object value;
    private final int scope;

    public Stack_Variable(String name, Object value, int scope) {
        this.name = name;
        this.value = value;
        this.scope = scope;
    }

    public String getName() {
       return name;
    }

    public Object getValue() {
        return value;
    }

    public int getScope() {
        return scope;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + "_" + scope + "=" + value;
    }
}
