package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.SymbolTable.SymbolTable;
import fr.ufrst.m1info.pvm.group5.SymbolTable.SymbolTableEntry;

/**
 * API class for the Memory interface
 * It is meant to serve as an extra interface between the memory and the other modules
 * All methods will be used by the abstract syntax tree for the interpretation/compilation
 */
public class Memory {
    Stack stack = new Stack();
    SymbolTable symbolTable = new SymbolTable();

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
    }

    /**
     * Removes the top of the stack
     */
    public void pop() throws Stack.StackIsEmptyException {
        stack.pop();
    }

    /**
     * Swap the two elements at the top of the stack
     */
    public void swap() {
        // TODO
    }

    /**
     * Higher level operations
     */

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
    }

    /**
     * Give a value to a given identifier.
     * If the identifier is the one of a constant type (method, affected constant, ...)
     * @param identifier identifier of the value to edit (cannot be null)
     * @param value value to affect (cannot be null)
     */
    public void affectValue(String identifier, Object value) {
        // TODO
    }

    public void declVarClass(String identifier) {
        // TODO
    }

    public Object val(String identifier) {
        // TODO
        return null;
    }

    public String identVarClass() {
        // TODO
        return null;
    }

    // Method related methods (context, etc...) will have to be added later
}
