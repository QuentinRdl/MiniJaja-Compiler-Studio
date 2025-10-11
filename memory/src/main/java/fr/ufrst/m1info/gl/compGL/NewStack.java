package fr.ufrst.m1info.gl.compGL;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;

// TODO : Write the JavaDoc
// TODO : Do the associated unit tests
// TODO : Do the integration tests w/ the Stack_Variable class

public class NewStack {
    private Deque<Stack_Variable> vars;
    private int scopeDepth;
    private int size;

    public static class NoScopeException extends Exception {
        public NoScopeException(String msg) {
            super(msg);
        }
    }

    public static class StackIsEmptyException extends Exception {
        public StackIsEmptyException(String msg) {
            super(msg);
        }
    }

    /**
     * Constructor
     */
    public NewStack() {
        this.vars = new ArrayDeque<>();
        this.scopeDepth = 0;
        this.size = 0;
    }

    public void pushScope() {
        scopeDepth++;
    }

    public void popScope() throws NoScopeException {
        if(scopeDepth == 0) throw new NoScopeException("There are currently 0 scopes, cannot pop");

        // If we are here then no exception has been thrown, then we can remove all vars from current scope
        while(!vars.isEmpty() && vars.peek().getScope() == scopeDepth) {
            vars.pop();
        }

        scopeDepth--;
    }

    /**
     * Pushes a new var in the stack, w/ the current scope as suffix
     * @param name name of the var
     * @param value value of the var
     */
    public void setVar(String name, Object value) {
        vars.push(new Stack_Variable(name, value, scopeDepth));
    }

    /**
     * Removes and return the top var from the stack
     * @return Stack_Variable the var on top of the stack
     * @throws StackIsEmptyException if the stack is empty
     */
    public Stack_Variable top() throws StackIsEmptyException {
        if(vars.isEmpty()) throw new StackIsEmptyException("The stack is empty, cannot pop");
        return vars.pop();
    }


    /* TODO : Question -> Do we need the following func ? */
    /**
     * @param name the name of the var we are looking for
     * @return Object, the var value if found, null otherwise
     */
     public Object getVar(String name) {
         for(Stack_Variable var : vars) {
             if(var.getName().equals(name) && var.getScope() == scopeDepth) {
                 /* TODO : Do we return the var object of the value ? */
                 /* return var; */
                 return var.getValue();
             }
         }
         return null;
     }


}
