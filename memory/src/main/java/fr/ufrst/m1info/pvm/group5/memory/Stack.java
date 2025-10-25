package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.Objects;

public class Stack {
    private Deque<Stack_Object> stack_content;
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
     * Exception thrown when attempting to create or set a variable with an invalid (null/empty) name.
     * This is unchecked so existing callers don't need to change their signatures.
     */
    public static class InvalidNameException extends IllegalArgumentException {
        public InvalidNameException(String msg) {
            super(msg);
        }
    }

    /**
     * Constructor
     */
    public Stack() {
        this.stack_content = new ArrayDeque<>();
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
        while(!stack_content.isEmpty() && stack_content.peek().getScope() == scopeDepth) {
            stack_content.pop();
        }

        scopeDepth--;
    }

    /**
     * Pushes a new var in the stack, w/ the current scope as suffix
     * @param name name of the var
     * @param value value of the var
     */
    public void setVar(String name, Object value, DataType type) {
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException("Variable name cannot be null or empty");
        }

        // Allow declarations without initial value: only validate when value is non-null
        if (value != null) {
            validateType(value, type);
        }

        Stack_Object var = new Stack_Object(name, value, scopeDepth, EntryKind.VARIABLE, type);
        stack_content.push(var);
    }


    /**
     * Pushes a new const in the stack, w/ the current scope as suffix
     * @param name name of the const
     * @param value value of the const
     */
    public void setConst(String name, Object value, DataType type) {
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException("Constant name cannot be null or empty");
        }

        // Constants may be declared without an initial value (value can be assigned later),
        // so only validate when a non-null value is provided
        if (value != null) {
            validateType(value, type);
        }

        Stack_Object constant = new Stack_Object(name, value, scopeDepth, EntryKind.CONSTANT, type);
        stack_content.push(constant);
    }




    /**
     * Returns the top object from the stack
     * @return Object the top object
     * @throws EmptyStackException if stack empty
     */
    public Stack_Object top() {
        if (stack_content.isEmpty()) throw new EmptyStackException();
        return stack_content.peek();
    }


    /**
     * Removes and return the top Object from the stack
     * @return Stack_Object the object on top of the stack
     * @throws StackIsEmptyException if the stack is empty
     */
    public Stack_Object pop() throws StackIsEmptyException {
        if(stack_content.isEmpty()) throw new StackIsEmptyException("The stack is empty, cannot pop");
        return stack_content.pop();
    }


    /**
     * @param name the name of the var we are looking for
     * @return Object, the Object if found, null otherwise
     */
     public Object getObject(String name) {
         for(Stack_Object obj : stack_content) {
             if(obj.getName().equals(name) && obj.getScope() == scopeDepth) {
                 return obj;
             }
         }
         return null;
     }


    /**
     * @param name the name of the var we are looking for
     * @return Object, the value if found, null otherwise
     */
    public Object getObjectValue(String name) {
        for(Stack_Object obj : stack_content) {
            if(obj.getName().equals(name) && obj.getScope() == scopeDepth) {
                return obj.getValue();
            }
        }
        return null;
    }

    /**
     * Updates the top var that matches the given name (in current scope)
     * @param name name of the var
     * @param value new value for the var
     * @return true if var updated, false otherwise
     */
    public boolean updateVar(String name, Object value) {
        for(Stack_Object var : stack_content) {
            if(var.getEntryKind() != EntryKind.VARIABLE) {
                // TODO : Should we throw an error ? this way if false is returned then the var is not found
                // And if exception, we now that it is not because the object is not a var
                return false;
            }
            if(var.getName().equals(name) && var.getScope() == scopeDepth) {
                // validate value against the variable's declared type
                validateType(value, var.getDataType());
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
        if (stack_content.isEmpty()) {
            return false;
        }

        Stack_Object topVar = stack_content.peek();

        if(topVar.getEntryKind() != EntryKind.VARIABLE) {
            return false; // Not a var
        }

        validateType(value, topVar.getDataType());
        topVar.setValue(value);
        return true;
    }

    /**
     * Checks if a var exists in the current scope
     * @param name name of the var
     * @return true if the var exists, false otherwise
     */
    public boolean hasObj(String name) {
        for(Stack_Object obj : stack_content) {
            if(obj.getName().equals(name) && obj.getScope() == scopeDepth) {
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
        return stack_content.size();
    }

    /**
     * Check if the vars stack is empty
     * @return true if no vars, false otherwise
     */
    public boolean isEmpty() {
        return stack_content.isEmpty();
    }

    /**
     * Clears all vars and reset the scope
     */
    public void clear() {
        stack_content.clear();
        scopeDepth = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stack{scopeDepth=").append(scopeDepth).append(", contents=");
        sb.append('[');
        boolean first = true;
        for (Stack_Object obj : stack_content) {
            if (!first) sb.append(", ");
            sb.append(obj.toString());
            first = false;
        }
        sb.append(']');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Searches for the given Object in the stack // TODO : Unit tests !!!
     * @param identifier the name of the Object we are looking for
     * @return Object if found, null otherwise
     */
    public Stack_Object searchObject(String identifier) {
        for(Stack_Object obj : stack_content) {
            if(Objects.equals(obj.getName(), identifier)) {
                return obj; // Object found
            }
        }
        return null; // Object not found
    }

    /**
     * Will remove the given Stack_Object given, MUST BE SURE IT EXISTS
     * @param object the Stack_Object to remove
     */
    private void removeObject(Stack_Object object) {
        stack_content.remove(object);
    }

    /**
     * Will look for an Object with the name given as an argument, remove it and put it back on top of the stack
     * @param identifier name of the Object we want to put on top
     * @return true if successful, false otherwise
     */
    public boolean putOnTop(String identifier) {
        Stack_Object obj = searchObject(identifier);
        if(obj == null) return false; // Object does not exist in the stack
        // We remove the Stack_Object from the stack
        removeObject(obj);

        // We put the object back on top of the stack
        stack_content.push(obj);
        return true;
    }

    /**
     * Func to validate that a given value matches the declared DataType
     * @param value we want to check the type of this value
     * @param type we check that the object has this type
     */
    private void validateType(Object value, DataType type) {
        if (value == null) {
            throw new IllegalArgumentException("Called with null value for type " + type);
        }

        switch (type) {
            case BOOL:
                if (!(value instanceof Boolean)) {
                    throw new IllegalArgumentException("Called with BOOL DataType, but object is of type " + value.getClass());
                }
                break;
            case INT:
                if (!(value instanceof Integer)) {
                    throw new IllegalArgumentException("Called with INT DataType, but object is of type " + value.getClass());
                }
                break;
            case FLOAT:
                if (!(value instanceof Float)) {
                    throw new IllegalArgumentException("Called with FLOAT DataType, but object is of type " + value.getClass());
                }
                break;
            case DOUBLE:
                if (!(value instanceof Double)) {
                    throw new IllegalArgumentException("Called with DOUBLE DataType, but object is of type " + value.getClass());
                }
                break;
            case STRING:
                if (!(value instanceof String)) {
                    throw new IllegalArgumentException("Called with STRING DataType, but object is of type " + value.getClass());
                }
                break;
            case VOID:
            case UNKNOWN:
            default:
                throw new IllegalArgumentException("Called with unvalid argument : " + value.getClass());
        }
    }
}
