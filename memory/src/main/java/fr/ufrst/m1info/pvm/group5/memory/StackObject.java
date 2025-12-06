package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;

import java.util.Objects;

/**
 * Represents an entry of a variable in the stack, with name that contains the actual
 * name of the variable and scope the scope it has as a suffix
 */
public class StackObject {
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
    public StackObject(String name, Object value, int scope, EntryKind kind, DataType dataType) {
        if (kind == EntryKind.VARIABLE || kind == EntryKind.CONSTANT || kind == EntryKind.METHOD) {
            if (dataType == null) {
                throw new Stack.InvalidStackObjectConstructionException(
                        "VARIABLE, CONSTANT or METHOD must specify a valid DataType"
                );
            }
        } else if(dataType != DataType.UNKNOWN) {
            if (dataType != null) {
                throw new Stack.InvalidStackObjectConstructionException(
                        "Non-variable/non-constant/non-method objects should not specify DataType"
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
    public StackObject(String name, Object value, int scope, EntryKind kind) {
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
        if (this.entryKind == EntryKind.CONSTANT && this.value != null) {
            throw new Stack.ConstantModificationException(
                "Cannot modify value of constant Stack_Object '" + this.name + "' (scope=" + this.scope + ") if it already has a declared value"
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
        if (!(obj instanceof StackObject actualObj)) {
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

    /**
     * Turns a Stack_Object into a Value
     * Should be use where we would Cast
     * @param obj object to cast into value
     * @return Value value casted from the given Stack_Object
     */
    public static Value stackObjToValue(StackObject obj) {
        if(obj == null) return null;

        Object raw = obj.getValue();
        // If the stored object is already a Value, return it, right now it will not be, but later on it will be
        if (raw instanceof Value) {
            return (Value) raw;
        }

        // If the stored object is null, return an empty Value
        if (raw == null) {
            return new Value();
        }

        // Convert primitives to Value
        if (raw instanceof Integer) {
            return new Value((Integer) raw);
        }
        if (raw instanceof Boolean) {
            return new Value((Boolean) raw);
        }

        return null;
    }

    /**
     * Turns a Value into a Stack_Object
     * Should be use where we would Cast
     * @param val Value to cast into Stack_Object
     * @return Stack_Object casted from the given Stack_Object
     */
    public static StackObject valueToStackObj(Value val) {
        if (val == null) return null;

        final String objName = null;

        if (val.type == null) {
            return new StackObject(null, null, 0, EntryKind.OTHER);
        }

        return switch (val.type) {
            case INT -> new StackObject(null, val.valueInt, 0, EntryKind.OTHER);
            case BOOL -> new StackObject(null, val.valueBool, 0, EntryKind.OTHER);
            case STRING -> new StackObject(null, val.valueString, 0, EntryKind.OTHER);
            case EMPTY, VOID -> new StackObject(null, null, 0, EntryKind.OTHER);
        };
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
