package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.heap.Heap;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.SymbolTable;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.SymbolTableEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * API class for the Memory interface
 * It is meant to serve as an extra interface between the memory and the other modules
 * All methods will be used by the abstract syntax tree for the interpretation/compilation
 */
public class Memory {
    Stack stack = new Stack();
    SymbolTable symbolTable = new SymbolTable();
    private Heap heap = new Heap();

    private List<Integer> breakpoints = new ArrayList<>();

    private String identifierVarClass;
    /**
     * Writer used for the "write" and "writeline" methods
     * If left null, the said methods will have no effect
     */
    Writer output = null;

    // This flag is here so we can tell we want to keep the items of the memory, not withdraw them
    // This way we can test stuff on the memory
    private boolean preserveAfterInterpret = false;

    /* Constructors */

    public Memory() {
        stack = new Stack();
        symbolTable = new SymbolTable();
        identifierVarClass = null;
    }

    public Memory(Writer output){
        this();
        this.output = output;
    }

    public Heap getHeap() {
        return heap;
    }

    public void setHeap(Heap heap) {
        this.heap = heap;
    }

    /**
     * Exception thrown when attempting to pop a scope from the stack when no scopes exist.
     */
    public static class MemoryIllegalArgException extends RuntimeException{
        public MemoryIllegalArgException(String component, String operation, String reason) {
            super(String.format("%s : %s was called with invalid arguments, %s, ", component, operation, reason));
        }
    }

    public static class MemoryIllegalOperationException extends RuntimeException{
        public MemoryIllegalOperationException(String component, String operation, String reason) {
            super(String.format("%s : Invalid operation, %s, %s", component, operation, reason));
        }
    }

    public void setPreserveAfterInterpret(boolean preserve) {
        this.preserveAfterInterpret = preserve;
    }

    public boolean isPreserveAfterInterpret() {
        return this.preserveAfterInterpret;
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
            throw new MemoryIllegalArgException("Memory", "push", "pushed value cannot be null, given "+"identifier = " + identifier + " value = " + value + " type = " + type + " kind = " + kind);
        }
        if(kind != EntryKind.VARIABLE && kind != EntryKind.CONSTANT && kind != EntryKind.METHOD) {
            throw new MemoryIllegalArgException("Memory","push","pushed value cannot have the following kind : "+kind);
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

        if(kind == EntryKind.METHOD) {
            stack.setMeth(identifier, value, type);
            SymbolTableEntry entry = new SymbolTableEntry(identifier, kind, type);
            symbolTable.addEntry(entry);
        }
    }

    /**
     * Removes the element at the top of the stack
     */
    public Object pop() throws Stack.StackIsEmptyException {
        StackObject top = stack.pop();
        if(top == null) return null;
        if (!top.getName().startsWith(".")) {
            SymbolTableEntry ste = symbolTable.lookup(top.getName());
            if (ste!=null && ste.getKind()==EntryKind.ARRAY){
                heap.removeReference(((Value) top.getValue()).valueInt);
            }
            symbolTable.removeEntry(top.getName());
        }
        return top.getValue();
    }

    /**
     * Swap the two elements at the top of the stack
     */
    public void swap() {
        stack.swap();
    }

    /**
     * Returns the top object from the stack
     * @return Object the top object
     */
    public StackObject top() {
        return stack.top();
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


    /**
     * Remove a declaration
     * @param identifier identifier of the declaration to remove
     */
    public void withdrawDecl(String identifier) {
        if(identifier == null || identifier.isEmpty()) {
            throw new MemoryIllegalArgException("Memory","withdraw", "withdraw operations requires a non-null identifier");
        }
        if(this.preserveAfterInterpret) {
            // This way we can keep the values, and test them
            return;
        }

        SymbolTableEntry kind = symbolTable.lookup(identifier);
        symbolTable.removeEntry(identifier);

        // Find the object in the stack
        StackObject obj = stack.getObject(identifier);

        if(kind != null && kind.getKind() == EntryKind.ARRAY) {
            // The entry kind is an array, so we must dereference the heap referenced to it.
            // For that, we get the int with the address of the heap reference from the stack
            if(obj.getEntryKind() != EntryKind.VARIABLE && obj.getDataType() != DataType.INT) {
                throw new MemoryIllegalArgException("Memory", "withdraw", "no array address found when trying to withdraw an array, given "+kind);
            }
            int reference_value = ((Value) obj.getValue()).valueInt;
            heap.removeReference(reference_value);
        }

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
            throw new MemoryIllegalArgException("Memory", "value affectation", "value affectation requires a non-null identifier");
        } else if(value == null) {
            throw new MemoryIllegalArgException("Memory", "value affectation", "cannot affect "+identifier+" to null");
        }

        // Check that it exists in the symbol table
        SymbolTableEntry entry = symbolTable.lookup(identifier); // Can throw illegalArgumentException if identifier not found

        // Find the object in the stack
        StackObject obj = stack.searchObject(identifier);
        if (obj == null) {
            throw new MemoryIllegalArgException("Memory", "value affectation", "no object found in the stack for given identifier, "+identifier);
        }

        // Handle according to the kind
        if(entry.getKind() == EntryKind.ARRAY) {
            // Remove the reference from old array
            int oldReference = ((Value) obj.getValue()).valueInt;
            heap.removeReference(oldReference);

            // Add the new reference to the heap & the stack
            heap.addReference(((Value) value).valueInt);
            obj.setValue(value);
            return;
        } else if (entry.getKind() == EntryKind.VARIABLE) {
            // Variables can always be reassigned
            obj.setValue(value);
            // Update symbol table reference
            entry.setReference(value);
            return;
        } else if(entry.getKind() == EntryKind.CONSTANT) {
            // Constants can only be initialized once
            if(obj.getValue() != null) {
                throw new Stack.ConstantModificationException("Memory", identifier);
            }
            obj.setValue(value);
            entry.setReference(value);
            return;
        }

        throw new MemoryIllegalOperationException("Memory", "value affectation", "operation not defined for given kind, "+entry.getKind());
    }

    /**
     * Declares the class variable
     * @param identifier identifier of the var class
     */
    public void declVarClass(String identifier) {
        // We check that nothing on the Symbol Table & the stack is defined w/ this name
        if(identifierVarClass != null || symbolTable.contains(identifier) || stack.hasObj(identifier)) {
            throw new MemoryIllegalOperationException("Memory", "class variable definition", "a class variable has already been defined");
        }
        // Everything checks out, we create the var, with null type, and null value
        declVar(identifier, null, DataType.UNKNOWN);
        identifierVarClass = identifier;
    }

    /**
     * Returns Object with the given identifier
     * @param identifier identifier of the Object we are looking for
     * @return Object if found, null otherwise
     */
    public Object val(String identifier) {
        if(identifier == null || identifier.isEmpty()) {
            throw new MemoryIllegalArgException("Memory", "read","memory reading requires a non-null identifier");
        }
        // Lookup the symbol table entry
        SymbolTableEntry entry = symbolTable.lookup(identifier);
        String ref = entry.getName();

        StackObject stackobj = stack.getObject(ref);
        // We convert the stack object into a Value
        if (stackobj == null) {
            throw new MemoryIllegalArgException("Memory", "read", "no object found in the stack for given identifier, "+identifier);
        }

        Object raw = stackobj.getValue();
        if (raw instanceof Value) {
            return raw;
        }

        return StackObject.stackObjToValue(stackobj);
    }

    /**
     * Returns the value type of the given identifier
     * @param identifier identifier of the Object we are looking for
     * @return ValueType
     */
    public ValueType valueTypeOf(String identifier){
        return DataType.toValueType(dataTypeOf(identifier));
    }

    /**
     * Returns the data type of the given identifier
     * @param identifier identifier of the Object we are looking for
     * @return DataType
     */
    public DataType dataTypeOf(String identifier){
        if(identifier == null || identifier.isEmpty()) {
            throw new MemoryIllegalArgException("Memory", "read","memory reading requires a non-null identifier");
        }
        // Lookup the symbol table entry
        SymbolTableEntry entry = symbolTable.lookup(identifier);
        if (entry == null) {
            return null;
        }
        return entry.getDataType();
    }

    public String identVarClass() {
        return identifierVarClass;
    }

    public void affVarClass(Object value) {
        if(identifierVarClass == null) {
            throw new MemoryIllegalArgException("Memory", "class variable affectation", "no class variable has been defined");
        }
        if(value == null) {
            throw new MemoryIllegalArgException("Memory", "class variable affectation", "cannot affect class variable to null");
        }
        affectValue(identifierVarClass, value);
    }

    /**
     * Writes text to the output
     * @param text text to write
     */
    public void write(String text){
        if(output != null){
            output.write(text);
        }
    }

    /**
     * Writes a new line of text to the output
     * @param text new line of text to write
     */
    public void writeLine(String text){
        if(output != null){
            output.writeLine(text);
        }
    }

    /**
     * Declares a method in memory
     * @param identifier method name
     * @param returnType <type of return of the method (DataType.INT, DataType.BOOL, DataType.VOID)>
     * @param params object representing the method parameters
     */
    public void declMethod(String identifier, DataType returnType, Object params) {// tab des Ast
        if(identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Cannot declare a method with null or empty identifier");
        }

        if(symbolTable.contains(identifier)) {
            throw new IllegalStateException("Method '" + identifier + "' already exists in the symbol table");
        }
        SymbolTableEntry entry = new SymbolTableEntry(identifier, EntryKind.METHOD, returnType);
        entry.setReference(params);
        symbolTable.addEntry(entry);
        stack.setMethod(identifier, params, returnType);
    }

    /**
     * Retrieve an existing method
     * @param identifier method name
     * @return the method entry in the SymbolTable
     */
    public SymbolTableEntry getMethod(String identifier) {
        SymbolTableEntry entry = symbolTable.lookup(identifier);
        if(entry.getKind() != EntryKind.METHOD) {
            throw new IllegalArgumentException(identifier + " is not a method");
        }
        return entry;
    }

    /**
     * Remove a method from memory
     * @param identifier method name
     */
    public void withdrawMethod(String identifier) {
        if (identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Cannot withdraw a method with null or empty identifier");
        }

        SymbolTableEntry entry = symbolTable.lookup(identifier);
        if (entry == null) {
            throw new IllegalArgumentException("Cannot withdraw '" + identifier + "' because it does not exist");
        }

        if (entry.getKind() != EntryKind.METHOD) {
            throw new IllegalArgumentException("Cannot withdraw '" + identifier + "' because it is not a method");
        }
        symbolTable.removeEntry(identifier);
        StackObject obj = stack.getObject(identifier);
        if (obj != null) {
            stack.removeObject(obj);
        }
    }

    /**
     * Adds a new scope
     */
    public void pushScope() {
        stack.pushScope();
        symbolTable=symbolTable.createChildScope();
    }

    /**
     * Pops the current scope
     */
    public void popScope() {
        try {
            stack.popScope();
            symbolTable=symbolTable.getParentScope();
        } catch (Stack.NoScopeException _) {

        }
    }

    /**
     * Decrement the current scope
     */
    public void decrementScope() {
        try {
            stack.decrementScope();
        } catch (Stack.NoScopeException _) {

        }
    }

    /**
     * Checks whether a symbol with the given name exists in the table.
     *
     * @param identifier the name of the symbol to check
     * @return {@code true} if the symbol exists, {@code false} otherwise
     */
    public boolean contains(String identifier) {
        if (identifier == null || identifier.isEmpty()) return false;
        return val(identifier) != null;
    }

    /**
     * Declares a named array
     * @param identifier identifier of the array, can't be null
     * @param size value of the array. Can't be null
     * @param type type of the array
     */
    public void declTab(String identifier, int size, DataType type) {
        int addr = heap.allocate(size, type);
        heap.addReference(addr);
        symbolTable.addEntry(identifier, EntryKind.ARRAY, type);
        stack.setVar(identifier, new Value(addr), DataType.INT);
    }

    /**
     * Modifies the given array at given index with given value
     * identifier[index] = value
     * @param identifier id of the array
     * @param index index of the array we want to affect
     * @param value value we want to affect
     */
    public void affectValT(String identifier, int index, Value value) {
        StackObject addressObj = stack.getObject(identifier);
        if(addressObj.getDataType() != DataType.INT) {
            return; // An array should be stocked as an int that has its own reference
        }

        int address = ((Value) addressObj.getValue()).valueInt;

        heap.setValue(address, index, value);
    }

    /**
     * Returns the value stocked on the given array at the given index
     * will return the value of identifier[index]
     * @param identifier id of the array
     * @param index index of the value
     * @return Value the value stocked at identifier[index]
     */
    public Value valT(String identifier, int index) {
        StackObject addressObj = stack.getObject(identifier);
        if(addressObj.getDataType() != DataType.INT) {
            return null; // An array should be stocked as an int that has its own reference
        }

        int address = ((Value) addressObj.getValue()).valueInt;
        return heap.getValue(address, index);
    }

    /**
     * Returns the length of the given array
     * @param identifier id of the array
     * @return length of the given array -1 if incorrect
     */
    public int tabLength(String identifier) {
        StackObject addressObj = stack.getObject(identifier);
        if(addressObj.getDataType() != DataType.INT) {
            return -1; // An array should be stocked as an int that has its own reference
        }
        if (!isArray(identifier)){
            return -1;
        }

        int address = ((Value) addressObj.getValue()).valueInt;
        return heap.sizeOf(address);
    }

    /**
     * Returns the type of the given array
     * @param identifier id of the array
     * @return type of the given array
     */
    public DataType tabType(String identifier) {
        SymbolTableEntry arr = symbolTable.lookup(identifier);
        if (arr.getKind()!= EntryKind.ARRAY){
            throw new IllegalArgumentException(identifier +" is not a array");
        }
        return arr.getDataType();
    }

    /**
     * Check if the element is an array
     * @param identifier id of the element
     * @return true if the element is an array
     */
    public boolean isArray(String identifier) {
        SymbolTableEntry arr = symbolTable.lookup(identifier);
        return arr != null && arr.getKind() == EntryKind.ARRAY;
    }

    /**
     * Prints the content of the Memory (heap + stack)
     * @return String -> the Memory
     */
    @Override
    public String toString() {
        String res = heap.toString();
        res += stack.toString(symbolTable);
        return res;
    }

    /**
     * Alternate func for toString, returns the Memory (heap + stack)
     * @return tab of str with at index 0 the heap, index 1 the stack
     */
    public String[] toStringTab() {
        String[] res = {null, null};

        res[0] = heap.toString();
        res[1] = stack.toString(symbolTable);

        return res;
    }

    /* Breakpoints related operations */
    public void setBreakpoints(List<Integer> breakpoints) {
        this.breakpoints = breakpoints;
    }

    public List<Integer> getBreakpoints() {
        return breakpoints;
    }

    public boolean isBreakpoint(int address) {
        return breakpoints.contains(address);
    }
}