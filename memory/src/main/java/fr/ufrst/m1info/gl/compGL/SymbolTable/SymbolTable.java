package fr.ufrst.m1info.gl.compGL.SymbolTable;


import fr.ufrst.m1info.gl.compGL.HashMap;

/**
 * Represents a simple symbol table without scope management.
 * This table stores entries (symbols) in a {@link HashMap}, where
 * the key is the symbol name and the value is a {@link SymbolTableEntry}.
 */
public class SymbolTable {

    /** The underlying map storing symbol names and their corresponding entries. */
    private final HashMap<String, SymbolTableEntry> table;
    /** The parent scope (null if this is the global scope). */
    private final SymbolTable parentScope;

    /** The depth level of this scope (0 = global). */
    private final int scopeLevel;

    /**
     * Constructs a new global symbol table (no parent).
     */
    public SymbolTable() {
        this(null);
    }

    /**
     * Constructs a new symbol table with the specified parent scope.
     *
     * @param parentScope the parent scope (can be null for the global scope)
     */
    public SymbolTable(SymbolTable parentScope) {
        this.table = new HashMap<>();
        this.parentScope = parentScope;
        this.scopeLevel = (parentScope == null) ? 0 : parentScope.scopeLevel + 1;
    }

    /**
     * Adds a new entry to the symbol table.
     * If a symbol with the same name already exists, an exception is thrown.
     *
     * @param entry the symbol table entry to add
     * @throws IllegalArgumentException if the symbol name already exists in the table
     */
    public void addEntry(SymbolTableEntry entry) {
        String name = entry.getName();
        if (table.containsKey(name)) {
            throw new IllegalArgumentException("Symbol already declared: " + name);
        }
        table.put(name, entry);
    }

    /**
     * Looks up a symbol by name in the current scope and recursively in parent scopes.
     *
     * @param name the symbol name
     * @return the found symbol table entry
     * @throws IllegalArgumentException if the symbol is not found in any scope
     */
    public SymbolTableEntry lookup(String name) {
        SymbolTableEntry entry = table.get(name);
        if (entry != null) {
            return entry;
        }
        if (parentScope != null) {
            return parentScope.lookup(name);
        }
        throw new IllegalArgumentException("Symbol not found: " + name);
    }


    /**
     * Removes a symbol from the current scope only.
     *
     * @param name the symbol name to remove
     * @throws IllegalArgumentException if the symbol does not exist in this scope
     */
    public void removeEntry(String name) {
        if (!table.containsKey(name)) {
            throw new IllegalArgumentException("Symbol not found in current scope: " + name);
        }
        table.remove(name);
    }
    /**
     * Creates and returns a new child scope that references this table as its parent.
     *
     * @return a new child symbol table
     */
    public SymbolTable createChildScope() {
        return new SymbolTable(this);
    }

    /**
     * Returns the parent scope of this symbol table.
     *
     * @return the parent symbol table, or null if this is the global scope
     */
    public SymbolTable getParentScope() {
        return parentScope;
    }

    /**
     * Returns the depth level of this scope.
     * Global scope = 0, child scope = 1, etc.
     *
     * @return the scope level
     */
    public int getScopeLevel() {
        return scopeLevel;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---- Scope Level ").append(scopeLevel).append(" ----\n");

        if (table.isEmpty()) {
            sb.append("No symbols in this scope.\n");
        } else {
            for (SymbolTableEntry entry : table.values()) {
                sb.append(entry).append("\n");
            }
        }

        if (parentScope != null) {
            sb.append(parentScope.toString());
        }

        return sb.toString();
    }

    /**
     * Returns the number of entries currently in the symbol table.
     *
     * @return the number of entries in the table
     */
    public int size() {
        return table.size();
    }

    /**
     * Checks whether a symbol with the given name exists in the table.
     *
     * @param name the name of the symbol to check
     * @return {@code true} if the symbol exists, {@code false} otherwise
     */
    public boolean contains(String name) {
        return table.containsKey(name);
    }

}
