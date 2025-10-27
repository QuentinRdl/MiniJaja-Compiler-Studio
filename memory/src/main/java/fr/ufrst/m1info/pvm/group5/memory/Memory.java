package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.SymbolTable;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.SymbolTableEntry;

/**
 * API class for the Memory interface
 * It is meant to serve as an extra interface between the memory and the other modules
 * All methods will be used by the abstract syntax tree for the interpretation/compilation
 */
public class Memory {
    public Stack stack;
    public SymbolTable symbolTable;


    public Memory() {
        stack = new Stack();
        symbolTable = new SymbolTable();
    }
    /* Operations directly related to the stack */

    /**
     * Put a value at the top of the stack
     * @param identifier identifier of the value, can be null for a "w" value
     * @param value value to store
     * @param type type of the value
     * @param kind kind of value (method, var, ...)
     */
    public void push(String identifier, Object value, DataType type, EntryKind kind) {

        if(kind == null || type == null || value == null || identifier == null) {
            throw new IllegalArgumentException("One of the following arguments are not compatible with this function call : identifier = " + identifier + " value = " + value + " type = " + type + " kind = " + kind);
        }
        if(kind != EntryKind.VARIABLE && kind != EntryKind.CONSTANT) {
            // TODO : Implement for different EntryKind
            throw new IllegalArgumentException("Pushing with " + kind + " as en EntryKind is invalid !");
        }

        if(kind == EntryKind.VARIABLE) {
            stack.setVar(identifier, value, type);
            // Create an explicit SymbolTableEntry and add it so tests can capture it
            SymbolTableEntry entry = new SymbolTableEntry(identifier, kind, type);
            symbolTable.addEntry(entry);
        }

        if(kind == EntryKind.CONSTANT) {
            stack.setConst(identifier, value, type);
            SymbolTableEntry entry = new SymbolTableEntry(identifier, kind, type);
            symbolTable.addEntry(entry);
        }

        // TODO :
    }

    /**
     * Removes the top of the stack
     */
    public Stack_Object pop() throws Stack.StackIsEmptyException {
        Stack_Object top = stack.pop();
        if (top != null) {
            symbolTable.removeEntry(top.getName()); // TODO : Check in unit tests
        }
        return top;
    }

    /**
     * Swap the two elements at the top of the stack
     */
    public void swap() {
        stack.swap();
    }

    /* Higher level operations */

    /**
     * Declares a named variable
     * @param identifier identifier of the variable, can't be null
     * @param value initial value of the variable. Can be null
     * @param type type of the variable to declare
     */
    public void declVar(String identifier, Object value, DataType type) {
        // Adds to the table of symbols
        // Use the SymbolTableEntry overload so tests can capture the object
        SymbolTableEntry entry = new SymbolTableEntry(identifier, EntryKind.VARIABLE, type);
        symbolTable.addEntry(entry);
        // Adds to the stack
        stack.setVar(identifier, value, type);
    }

    /**
     * Declares a named constant
     * @param identifier identifier of the constant, can't be null
     * @param value value of the constant. If it's null, the value of the constant will be assignable later
     * @param type type of the constant
     */
    public void declCst(String identifier, Object value, DataType type) {
        // Adds to the table of symbols
        SymbolTableEntry entry = new SymbolTableEntry(identifier, EntryKind.CONSTANT, type);
        symbolTable.addEntry(entry);
        // Adds to the stack
        stack.setConst(identifier, value, type);
    }

    // TODO : DeclTab and DeclMeth will be done when we'll do methods and arrays

    /**
     * Remove a declaration
     * @param identifier identifier of the declaration to remove
     */
    public void withdrawDecl(String identifier) {
        if(identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Cannot call 'withdrawDecl' with an empty/null identifier");
        }
        symbolTable.removeEntry(identifier);

        // Find the object in the stack
        Stack_Object obj = stack.getObject(identifier);
        // If object present in the stack, remove it
        if(obj != null) stack.removeObject(obj);
    }

    /**
     * Give a value to a given identifier.
     * If the identifier is the one of a constant type (method, affected constant, ...)
     * @param identifier identifier of the value to edit (cannot be null)
     * @param value value to affect (cannot be null)
     */
    public void affectValue(String identifier, Object value) {
        if(identifier == null) {
            throw new IllegalArgumentException("affectValue cannot be called with null identifier");
        }
        else if(value == null) {
            throw new IllegalArgumentException("affectValue cannot be called with null value");
        }

        // Check that it exists in the symbol table
        SymbolTableEntry entry = symbolTable.lookup(identifier); // Can throw illegalArgumentException if identifier not found

        // Check that types matches
        DataType givenDataType = stack.getDataTypeFromGenericObject(value);

        // Find the object in the stack
        Stack_Object obj = stack.searchObject(identifier);
        if (obj == null) {
            throw new IllegalArgumentException("Identifier '" + identifier + "' exists in the symbol table but no corresponding object was found in the stack");
        }

        // Ensure declared type matches given value type
        DataType declared = entry.getDataType();
        if (declared != givenDataType) {
            throw new IllegalArgumentException("Type mismatch when affecting value to '" + identifier + "' : declared=" + declared + " given=" + givenDataType);
        }

        // Handle according to the kind
        if (entry.getKind() == EntryKind.VARIABLE) {
            // Variables can always be reassigned
            obj.setValue(value);
            // Update symbol table reference
            entry.setReference(value);
            return;
        }

        if (entry.getKind() == EntryKind.CONSTANT) {
            // Constants can only be initialized once
            if (obj.getValue() != null) {
                throw new IllegalStateException("Cannot modify constant '" + identifier + "' once it has already been declared");
            }
            obj.setValue(value);
            entry.setReference(value);
            return;
        }

        // TODO : For other kinds, we don't support assignment yet
        throw new IllegalArgumentException("affectValue is not supported for EntryKind: " + entry.getKind());
    }

    public void declVarClass(String identifier) {
        // TODO
    }

    /**
     * Returns Object with the given identifier
     * @param identifier identifier of the Object we are looking for
     * @return Object if found, null otherwise
     */
    public Object val(String identifier) {
        if(identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("val cannot be called with an empty/null identifier");
        }
        // Lookup the symbol table entry
        SymbolTableEntry entry = symbolTable.lookup(identifier);
        String ref = entry.getName();

        return stack.getObject(ref);
    }

    public String identVarClass() {
        // TODO : La variable de classe est spécifique, pour dire que ca retourne la variable de classe,
        return null;
    }

    // Method related methods (context, etc...) will have to be added later
}
