package fr.ufrst.m1info.gl.compGL;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;

public class NewStack {
    private Deque<HashMap<String, Object>> scopes;
    private int size; // Size of the stack

    public static class StackIsEmptyException extends Exception {
        public StackIsEmptyException(String msg) {
            super(msg);
        }
    }

    public NewStack() {
        this.scopes = new ArrayDeque<>();
        this.size = 0;
    }

    public void pushScope() {
        scopes.push(new HashMap<>());
        size++;
    }


    public void popScope() throws StackIsEmptyException {
        if(scopes.isEmpty()) {
            throw new StackIsEmptyException("Cannot pop current scope, as the stack is empty");
        }
        // If scopes is empty, an exception has already been thrown, so we can continue without problems
        scopes.pop();
        size--;
    }

    public void setVariable(String name, Object value) {
        if(scopes.isEmpty()) throw new EmptyStackException();
        String key = name + "_" + size;
        scopes.peek().put(key, value);
    }
}
