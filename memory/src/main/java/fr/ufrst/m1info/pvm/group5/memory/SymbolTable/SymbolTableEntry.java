package fr.ufrst.m1info.pvm.group5.memory.SymbolTable;

/**
 * Represents a single entry (row) in the symbol table.
 * Each entry contains the symbol name, its kind (variable, method, constant, ...),
 * its data type, and an optional reference (e.g., memory address or value).
 */
public class SymbolTableEntry {

    /** The name of the symbol (identifier). */
    private String name;

    /** The kind of the entry (e.g., variable, method, constant, class, ...). */
    private EntryKind kind;

    /** The data type of the entry (e.g., int, bool, string, ...). */
    private DataType dataType;

    /**
     * A reference associated with the entry.
     * This can represent a memory address, a value, or be left null if not yet assigned.
     */
    private Object reference;

    /**
     * Constructs a new symbol table entry with the specified name, kind, and data type.
     * The reference is initialized to {@code null}.
     *
     * @param name the name of the symbol (identifier)
     * @param kind the kind of the entry (variable, method, constant, ...)
     * @param dataType the data type of the entry (int, bool, string, ...)
     */
    public SymbolTableEntry(String name, EntryKind kind, DataType dataType) {
        this.name = name;
        this.kind = kind;
        this.dataType = dataType;
        this.reference = null; // initially left empty
    }

    /**
     * Gets the name of the symbol.
     *
     * @return the symbol name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the kind of this entry.
     *
     * @return the entry kind
     */
    public EntryKind getKind() {
        return kind;
    }

    /**
     * Gets the data type of this entry.
     *
     * @return the data type
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Gets the reference associated with this entry.
     *
     * @return the reference object, or {@code null} if not set
     */
    public Object getReference() {
        return reference;
    }

    /**
     * Sets the reference for this entry.
     *
     * @param reference the reference to associate (e.g., memory address or value)
     */
    public void setReference(Object reference) {
        this.reference = reference;
    }


    /**
     * Sets the DataType for this entry.
     * Should only be used, when the DataType is UNKNOWN
     * @param dataType the DataType to set
     */
    public void setDataType(DataType dataType) {
        if(this.dataType != DataType.UNKNOWN) {
            throw new IllegalStateException("Should not try to reaffect on types different than DataType.UNKNOWN");
        }
        this.dataType = dataType;
    }


    /**
     * Returns a string representation of this entry for debugging purposes.
     *
     * @return a string representation of the entry
     */
    @Override
    public String toString() {
        return "SymbolTableEntry{" +
                "name='" + name + '\'' +
                ", kind=" + kind +
                ", dataType=" + dataType +
                ", reference=" + reference +
                '}';
    }
}
