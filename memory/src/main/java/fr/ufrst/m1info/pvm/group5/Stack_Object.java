package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.SymbolTable.DataType; // TODO : We should not have to import
import fr.ufrst.m1info.pvm.group5.SymbolTable.EntryKind;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * Represents an entry of a variable in the stack, with name that contains the actual
 * name of the variable and scope the scope it has as a suffix
 */
public class Stack_Object {
    private final String name;
    private Object value;
    private final EntryKind entryKind;
    private final DataType dataType;
    private final int scope;


    /**
     * Generic constructor, will cover all cases
     * @param name name of the object
     * @param value value of the object
     * @param scope current scope of the object
     * @param kind kind of the object
     * @param dataType dataType of the object
     */
    public Stack_Object(String name, Object value, int scope, EntryKind kind, DataType dataType) {
        if (kind == EntryKind.VARIABLE || kind == EntryKind.CONSTANT) {
            if (dataType == null || dataType == DataType.UNKNOWN) {
                throw new InvalidStackObjectConstructionException(
                        "VARIABLE or CONSTANT must specify a valid DataType"
                );
            }
        } else {
            if (dataType != null && dataType != DataType.UNKNOWN) {
                throw new InvalidStackObjectConstructionException(
                        "Non-variable/non-constant objects should not specify DataType"
                );
            }
        }
        this.name = name;
        this.value = value;
        this.scope = scope;
        this.entryKind = kind;
        this.dataType = (dataType != null) ? dataType : DataType.UNKNOWN;
    }

    /**
     * Second constructor, used for objects that do not need to specify a DataType
     * @param name name of the object
     * @param value value of the object
     * @param scope current scope of the object
     * @param kind kind of the object
     */
    public Stack_Object(String name, Object value, int scope, EntryKind kind) {
        this(name, value, scope, kind, DataType.UNKNOWN);
    }

    /**
     * Returns the name of the var
     * @return String name of the var
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of the var
     * @return Object value of the var
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns the scope of the var
     * @return int Scope of the var
     */
    public int getScope() {
        return scope;
    }

    public EntryKind getEntryKind() {
        return this.entryKind;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    /**
     * Sets the value of the var
     * @param value new value for the var
     */
    public void setValue(Object value) {
        if (this.entryKind == EntryKind.CONSTANT) {
            throw new ConstantModificationException(
                "Cannot modify value of constant Stack_Object '" + this.name + "' (scope=" + this.scope + ")"
            );
        }
        this.value = value;
    }

    /**
     * Tests if the given object matches this one
     * @param obj the reference object with which to compare.
     * @return true if matched, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Stack_Object actualObj)) {
            return false;
        }

        if (!Objects.equals(this.name, actualObj.name)) {
            return false;
        }

        if (!Objects.equals(this.value, actualObj.value)) {
            return false;
        }

        if (this.entryKind != actualObj.entryKind) {
            return false;
        }

        if (this.dataType != actualObj.dataType) {
            return false;
        }

        return this.scope == actualObj.scope;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, entryKind, dataType, scope);
    }

    /**
     * Prints the var
     * @return String the var
     */
    @Override
    public String toString() {
        return name + "_" + scope + "=" + value;
    }
}
