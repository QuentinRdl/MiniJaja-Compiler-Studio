package fr.ufrst.m1info.pvm.group5.memory;

import org.junit.jupiter.api.Test;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;

import static org.junit.jupiter.api.Assertions.*;

class ValueTest {

    @Test
    void intConstructor_shouldSetTypeAndValue() {
        Value v = new Value(42);
        assertEquals(ValueType.INT, v.type);
        assertEquals(42, v.valueInt);
        assertEquals("42", v.toString());
    }

    @Test
    void boolConstructor_shouldSetTypeAndValue() {
        Value v = new Value(true);
        assertEquals(ValueType.BOOL, v.type);
        assertTrue(v.valueBool);
        assertEquals("true", v.toString());
    }

    @Test
    void defaultConstructor_shouldBeEmpty() {
        Value v = new Value();
        assertEquals(ValueType.EMPTY, v.type);
        assertEquals("", v.toString());
    }

    @Test
    void toString_shouldFollowType() {
        Value v = new Value();

        v.valueInt = -7;
        v.type = ValueType.INT;
        assertEquals("-7", v.toString());

        v.valueBool = false;
        v.type = ValueType.BOOL;
        assertEquals("false", v.toString());

        v.type = ValueType.VOID;
        assertEquals("", v.toString());

        v.type = ValueType.EMPTY;
        assertEquals("", v.toString());
    }

    @Test
    void toString_withNullType() {
        Value v = new Value();
        v.type = null;
        assertThrows(NullPointerException.class, v::toString);
    }

    /*
     * ValueTest tests
     */

    @Test
    void valueType_toDataType_shouldMapAllTypes() {
        assertEquals(DataType.INT, ValueType.toDataType(ValueType.INT));
        assertEquals(DataType.BOOL, ValueType.toDataType(ValueType.BOOL));
        assertEquals(DataType.VOID, ValueType.toDataType(ValueType.VOID));
        assertEquals(DataType.UNKNOWN, ValueType.toDataType(ValueType.EMPTY));
    }

    @Test
    void valueType_toString_shouldReturnExpectedStrings() {
        assertEquals("INT", ValueType.INT.toString());
        assertEquals("BOOL", ValueType.BOOL.toString());
        assertEquals("VOID", ValueType.VOID.toString());
        assertEquals("UNKNOWN", ValueType.EMPTY.toString());
    }

    @Test
    void valueType_toDataType_withNull_shouldThrowNPE() {
        assertThrows(NullPointerException.class, () -> ValueType.toDataType(null));
    }
}
