package fr.ufrst.m1info.pvm.group5;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;

public class Stack {
    private Deque<Stack_Variable> vars;
    private int scopeDepth;
    // private int size;

    /**
     * Exception thrown when attempting to pop a scope from the stack when no scopes exist.
     */
    public static class NoScopeException extends Exception {
        public NoScopeException(String msg) {
            super(msg);
        }
    }

    /**
     * Exception thrown when attempting to pop or access an element from the stack when it is empty.
     */
    public static class StackIsEmptyException extends Exception {
        public StackIsEmptyException(String msg) {
            super(msg);
        }
    }

    /**
     * Constructor
     */
    public Stack() {
        this.vars = new ArrayDeque<>();
        this.scopeDepth = 0;
        // this.size = 0;
    }

    /**
     * Adds a new scope
     */
    public void pushScope() {
        scopeDepth++;
    }

    /**
     * Pops the current scope
     * @throws NoScopeException if no scope are present
     */
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
     * Returns the top variable from the stack
     * @return Variable the top variable
     * @throws EmptyStackException if stack empty
     */
    public Stack_Variable top() {
        if (vars.isEmpty()) throw new EmptyStackException();
        return vars.peek();
    }


    /**
     * Removes and return the top var from the stack
     * @return Stack_Variable the var on top of the stack
     * @throws StackIsEmptyException if the stack is empty
     */
    public Stack_Variable pop() throws StackIsEmptyException {
        if(vars.isEmpty()) throw new StackIsEmptyException("The stack is empty, cannot pop");
        return vars.pop();
    }


    /* TODO : Question -> Do we need the following func ?
    *   Or should we use a generic func to get the top var ?
    */
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

    /* TODO : Question -> Do we need the following func ?
     *   Or should we use a generic func to update the top var ?
     */

    /**
     * Updates the top var that matches the given name (in current scope)
     * @param name name of the var
     * @param value new value for the var
     * @return true if var updated, false otherwise
     */
    public boolean updateVar(String name, Object value) {
        for(Stack_Variable var : vars) {
            if(var.getName().equals(name) && var.getScope() == scopeDepth) {
                var.setValue(value);
                return true;
            }
        }
        return false;
    }


    /**
     * Updates the top var of the stack w/ the given value
     * @param value new value for the var on top of the stack
     * @return true if var updated, false otherwise
     */
    public boolean updateTopVar(Object value) {
        if (vars.isEmpty()) {
            return false;
        }
        Stack_Variable topVar = vars.peek();
        topVar.setValue(value);
        return true;
    }

    /**
     * Checks if a var exists in the current scope
     * @param name name of the var
     * @return true if the var exists, false otherwise
     */
    public boolean hasVar(String name) {
        for(Stack_Variable var : vars) {
            if(var.getName().equals(name) && var.getScope() == scopeDepth) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of vars on the stack
     * @return int nb of vars
     */
    public int size() {
        return vars.size();
    }

    /**
     * Check if the vars stack is empty
     * @return true if no vars, false otherwise
     */
    public boolean isEmpty() {
        return vars.isEmpty();
    }

    /**
     * Clears all vars and reset the scope
     */
    public void clear() {
        vars.clear();
        scopeDepth = 0;
    }

    // TODO : Do a toString method
}
