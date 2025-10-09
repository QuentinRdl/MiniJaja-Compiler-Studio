package fr.ufrst.m1info.gl.compGL.Memory;


/**
 * Copy of the memory API class from the memory class
 * Change the imports and remove this class once 
 */
public class Memory {

    /**
     * Operations directly related to the stack
     */

    /**
     * Put a value at the top of the stack
     * @param identifier identifier of the value, can be null for a "w" value
     * @param value value to store
     * @param type type of the value
     * @param kind kind of value (method, var, ...)
     */
    public void push(String identifier, Object value, DataType type, EntryKind kind) {
        // TODO
    }

    /**
     * Removes the top of the stack
     */
    public void pop(){
        // TODO
    }

    /**
     * Swap the two elements at the top of the stack
     */
    public void swap(){
        //TODO
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
    public void declVar(String identifier, Object value, DataType type){
        //TODO
    }

    /**
     * Declares a named constant
     * @param identifer identifier of the constant, can't be null
     * @param value value of the constant. If it's null, the value of the constant will be assignable later
     * @param type tupe of the constant
     */
    public void declCst(String identifer, Object value, DataType type){
        // TODO
    }

    // DeclTab and DeclMeth will be done when we'll do methods and arrays

    /**
     * Remove a declaration
     * @param identifier identifier of the declaration to remove
     */
    public void withdrawDecl(String identifier){
        // TODO
    }

    /**
     * Give a value to a given identifier.
     * If the identifier is the one of a constant type (method, affected constant, ...)
     * @param identifier identifier of the value to edit (cannot be null)
     * @param value value to affect (cannot be null)
     */
    public void affectValue(String identifier, Object value){
        // TODO
    }

    // Method related methods (context, etc..) will have to be added later
}
