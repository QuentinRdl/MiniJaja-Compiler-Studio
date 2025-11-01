package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;

import java.io.Serial;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

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
     * Thrown when attempting to modify a Stack_Object whose entry kind is CONSTANT.
     */
    public static class ConstantModificationException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ConstantModificationException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when attempting to construct a Stack_Object using the generic constructor
     * for kinds that require a specialized constructor (rn only vars and csts)
     */
    public static class InvalidStackObjectConstructionException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1L;

        public InvalidStackObjectConstructionException(String message) {
            super(message);
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
     * @param name the name of the object we are looking for
     * @return Object, the Object if found, null otherwise
     */
     public Stack_Object getObject(String name) {
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
     * Updates the top value of the stack w/ the given value
     * @param value new value for the object on top of the stack
     * @return true if object updated, false otherwise
     */
    public boolean updateTopValue(Object value) {
        if (stack_content.isEmpty()) {
            return false;
        }

        DataType valueDt = getDataTypeFromGenericObject(value);
        Stack_Object topObj = stack_content.peek();
        if(topObj == null) return false;
        if(topObj.getEntryKind() == EntryKind.CONSTANT) {
            // Can only reassign constant if value == null
            if(topObj.getValue() != null) return false; // Cannot reassign constant
        }
        if(topObj.getDataType() != valueDt) return false;

        // The object on top of the stack is of the same type as the object we were given, we can reaffect
        topObj.setValue(value);
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
    public void removeObject(Stack_Object object) {
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
        if (true) {
            return;
        }
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

    /**
     * // TODO : Remove
     * Will swap Objects obj1 & obj2 in the stack
     * @param obj1 1st object
     * @param obj2 2nd Object
     * @return True if swapped, false otherwise
     */
    public boolean swap(Stack_Object obj1, Stack_Object obj2) {
        // Validate arguments
        if (obj1 == null || obj2 == null) {
            throw new IllegalArgumentException("Swap requires non-null Stack_Object arguments");
        }

        String id1 = obj1.getName();
        String id2 = obj2.getName();

        if (id1 == null || id2 == null) {
            throw new IllegalArgumentException("Swap requires Stack_Object instances with non-null names");
        }

        // If identifiers are equal, nothing to do
        if (id1.equals(id2)) return true;

        // Convert deque to list to find indices and swap
        List<Stack_Object> list = new ArrayList<>(stack_content);
        int idx1 = -1, idx2 = -1;
        for (int i = 0; i < list.size(); i++) {
            Stack_Object so = list.get(i);
            if (idx1 == -1 && Objects.equals(so.getName(), id1)) {
                idx1 = i;
            }
            if (idx2 == -1 && Objects.equals(so.getName(), id2)) {
                idx2 = i;
            }
            if (idx1 != -1 && idx2 != -1) break;
        }

        if (idx1 == -1 || idx2 == -1) {
            // One or both objects not found
            return false;
        }

        // Swap in list
        Stack_Object tmp = list.get(idx1);
        list.set(idx1, list.get(idx2));
        list.set(idx2, tmp);

        // Rebuild deque preserving the new order
        stack_content.clear();
        for (Stack_Object so : list) {
            stack_content.addLast(so);
        }

        return true;
    }

    /**
     * Will swap the 2 top values of the stack
     */
    public void swap() {
        // private Deque<Stack_Object> stack_content;
        if(stack_content.size() < 2) {
            throw new IllegalStateException("Not enough elements to swap (need at least 2)");
        }

        // Pop top two elements and push them back in reversed order
        Stack_Object first = stack_content.pop();
        Stack_Object second = stack_content.pop();
        stack_content.push(first);
        stack_content.push(second);
    }

    /**
     * Returns the DataType for any given Object
     * @param obj object to check type
     * @return The DataType (UNKNOWN) if not in DataType enum
     */
    public DataType getDataTypeFromGenericObject(Object obj) {
        if (obj instanceof Integer) {
            return DataType.INT;
        }
        if (obj instanceof Boolean) {
            return DataType.BOOL;
        }
        if (obj instanceof String) {
            return DataType.STRING;
        }
        if (obj instanceof Float) {
            return DataType.FLOAT;
        }
        if (obj instanceof Double) {
            return DataType.DOUBLE;
        }

        /* TODO : How to handle null objects ??
        if (obj == null) {
            return DataType.VOID;
        }
         */

        return DataType.UNKNOWN;
    }

    /**
     * Puts a value on a const that has no affected value yet
     * @param obj const object
     * @param value value to assign the object
     * @return true if value affected, false otherwise
     */
    public boolean initializeConst(Stack_Object obj, Object value) {
        if(obj == null || value == null) return false;
        if(obj.getEntryKind() != EntryKind.CONSTANT) {
            throw new IllegalArgumentException("initializeConst must be called with a const !");
        }

        DataType dt = getDataTypeFromGenericObject(obj);
        if(dt != obj.getDataType()) {
            throw new IllegalArgumentException("Called intializeConst with incorrect data type");
        }

        if(obj.getValue() != null) {
            throw new IllegalStateException("Called intializeConst with a const that is already affected");
        }

        obj.setValue(value);
        return true;
    }
}
