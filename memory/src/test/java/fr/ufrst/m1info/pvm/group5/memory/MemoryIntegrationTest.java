package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.SymbolTable;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.SymbolTableEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class MemoryIntegrationTest {

    Memory mem;

    @BeforeEach
    public void setUp() {
        mem = new Memory();
    }

    @AfterEach
    void tearDown() {
        mem = null;
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> typeOfVars() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("id_int", 2, DataType.INT),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_true", true, DataType.BOOL),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_false", false, DataType.BOOL)
                // TODO : org.junit.jupiter.params.provider.Arguments.of("id_float", 3.14, DataType.FLOAT)
        );
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> typeOfVarsAffect() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("id_int", 2, DataType.INT, 4),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_true", true, DataType.BOOL, false),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_false", false, DataType.BOOL, true)
                // TODO : org.junit.jupiter.params.provider.Arguments.of("id_float", 3.14, DataType.FLOAT)
        );
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> typeOfVarsNull() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("id_int", null, DataType.INT, 4),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_true", null, DataType.BOOL, false),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_false", null, DataType.BOOL, true)
                // TODO : org.junit.jupiter.params.provider.Arguments.of("id_float", 3.14, DataType.FLOAT)
        );
    }
    static Stream<org.junit.jupiter.params.provider.Arguments> typeOfVarsNull2() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("id_int", 2, DataType.INT, null),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_true", true, DataType.BOOL, null),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_false", false, DataType.BOOL, null)
                // TODO : org.junit.jupiter.params.provider.Arguments.of("id_float", 3.14, DataType.FLOAT)
        );
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> typeOfIdNull() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(null, 2, DataType.INT, null),
                org.junit.jupiter.params.provider.Arguments.of(null, true, DataType.BOOL, null),
                org.junit.jupiter.params.provider.Arguments.of(null, false, DataType.BOOL, null)
                // TODO : org.junit.jupiter.params.provider.Arguments.of("id_float", 3.14, DataType.FLOAT)
        );
    }



    static Stream<org.junit.jupiter.params.provider.Arguments> affectValueMultiple() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("id_int", 2, DataType.INT, 23, 234, 2345),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_true", true, DataType.BOOL, false, true, true),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_false", false, DataType.BOOL, false, true, true)
                // TODO : org.junit.jupiter.params.provider.Arguments.of("id_float", 3.14, DataType.FLOAT)
        );
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> affVarNull() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(23),
                org.junit.jupiter.params.provider.Arguments.of(2.3),
                org.junit.jupiter.params.provider.Arguments.of("blabla"),
                org.junit.jupiter.params.provider.Arguments.of(true),
                org.junit.jupiter.params.provider.Arguments.of(false)
                // TODO : org.junit.jupiter.params.provider.Arguments.of("id_float", 3.14, DataType.FLOAT)
        );
    }


    @ParameterizedTest
    @MethodSource("typeOfVars")
    public void popWorks(String id, Object value, DataType type) throws Exception {
        mem.push(id, value, type, EntryKind.VARIABLE);
        Stack_Object ret = mem.pop();
        assertNotNull(ret);
    }

    @ParameterizedTest
    @MethodSource("typeOfVars")
    public void popCheckStackException(String id, Object value, DataType type) throws Exception {
        // Stack is empty, pop should throw StackIsEmptyException
        assertThrows(Stack.StackIsEmptyException.class, () -> mem.pop());

        mem.push(id, value, type, EntryKind.VARIABLE);
        Stack_Object ret = mem.pop();
        assertNotNull(ret);

        // Stack is now empty again, should throw StackIsEmptyException
        assertThrows(Stack.StackIsEmptyException.class, () -> mem.pop());
    }

    @ParameterizedTest
    @MethodSource("typeOfVars")
    public void popEmptySymbolTable(String id, Object value, DataType type) {
        mem.stack.setVar("var", value, type);
        assertThrows(java.lang.IllegalArgumentException.class, () -> mem.pop());
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    public void affectValueWorks(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value ret_val = (Value) ret;
        if(type == DataType.BOOL) {
            assertEquals(value, ret_val.valueBool);
        }
        else if(type == DataType.INT) {
            assertEquals(value, ret_val.valueInt);
        } else {
            return; // TODO : Do other DataTypes
        }
        mem.affectValue(id, newValue);

        ret = mem.val(id);
        ret_val = (Value) ret;

        if(type == DataType.BOOL) {
            assertEquals(newValue, ret_val.valueBool);
        }
        else if(type == DataType.INT) {
            assertEquals(newValue, ret_val.valueInt);
        } else {
            return; // TODO : Do other DataTypes
        }
    }


    @ParameterizedTest
    @MethodSource("affectValueMultiple")
    public void affectValueMultiple(String id, Object value, DataType type, Object newValue, Object newValue1, Object newValue2) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value ret_val = (Value) ret;
        if(type == DataType.BOOL) {
            assertEquals(value, ret_val.valueBool);
        }
        else if(type == DataType.INT) {
            assertEquals(value, ret_val.valueInt);
        } else {
            return; // TODO : Do other DataTypes
        }

        mem.affectValue(id, newValue);

        ret = mem.val(id);
        ret_val = (Value) ret;

        if(type == DataType.BOOL) {
            assertEquals(newValue, ret_val.valueBool);
        }
        else if(type == DataType.INT) {
            assertEquals(newValue, ret_val.valueInt);
        } else {
            return; // TODO : Do other DataTypes
        }

        mem.affectValue(id, newValue1);

        ret = mem.val(id);
        ret_val = (Value) ret;

        if(type == DataType.BOOL) {
            assertEquals(newValue1, ret_val.valueBool);
        }
        else if(type == DataType.INT) {
            assertEquals(newValue1, ret_val.valueInt);
        } else {
            return; // TODO : Do other DataTypes
        }

        mem.affectValue(id, newValue2);

        ret = mem.val(id);
        ret_val = (Value) ret;

        if(type == DataType.BOOL) {
            assertEquals(newValue2, ret_val.valueBool);
        }
        else if(type == DataType.INT) {
            assertEquals(newValue2, ret_val.valueInt);
        } else {
            return; // TODO : Do other DataTypes
        }
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsNull2")
    public void affectValueNullGiven(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value val = (Value) ret;
        Stack_Object stack_ret = Stack_Object.valueToStackObj(val);
        assertEquals(value, stack_ret.getValue());
        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            mem.affectValue(id, newValue);
        });
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    public void affectValueIdNull(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value val = (Value) ret;
        Stack_Object stack_ret = Stack_Object.valueToStackObj(val);
        assertEquals(value, stack_ret.getValue());
        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            mem.affectValue(null, newValue);
        });
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    public void affectValueNotDeclared(String id, Object value, DataType type, Object newValue) {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            mem.affectValue(id, newValue);
        });
    }




    @ParameterizedTest
    @MethodSource("typeOfVarsNull")
    public void declVarWithNull(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value val = (Value) ret;
        Stack_Object stack_ret = Stack_Object.valueToStackObj(val);
        assertEquals(value, stack_ret.getValue());
        mem.affectValue(id, newValue);
        stack_ret = mem.stack.getObject(id);
        assertEquals(newValue, stack_ret.getValue());
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    public void affectValueConst(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declCst(id, null, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value val = (Value) ret;
        Stack_Object stack_ret = Stack_Object.valueToStackObj(val);
        assertNull(stack_ret.getValue());
        mem.affectValue(id, newValue);
        stack_ret = mem.stack.getObject(id);
        assertEquals(newValue, stack_ret.getValue());
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    public void affectValueConstCannotModify(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declCst(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value val = (Value) ret;
        Stack_Object stack_ret = Stack_Object.valueToStackObj(val);
        assertEquals(value, stack_ret.getValue());
        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            mem.affectValue(id, newValue);
        });
    }


    @Test
    public void declVarClassWorks() {
        mem.declVarClass("val");
        String ret = mem.identVarClass();
        assertEquals("val", ret);
    }


    /**
     * Using declVarClass should declare the var class with
     * a DataType set with UNKNOWN
     */
    @Test
    public void declVarClassUsesUnknownDataType() {
        mem.declVarClass("val");
        String ret = mem.identVarClass();
        Stack_Object obj = mem.stack.getObject("val");
        assertEquals("val", ret);
        assertEquals(DataType.UNKNOWN, obj.getDataType());
    }


    /**
     * Using declVarClass should declare the var class with
     * data = null
     */
    @Test
    public void declVarClassUsesValueEqualsNull() {
        mem.declVarClass("val");
        String ret = mem.identVarClass();
        Stack_Object obj = mem.stack.getObject("val");
        assertEquals("val", ret);
        assertNull(obj.getValue());
    }

    @Test
    public void affVarClass() {
        mem.declVarClass("val");
        String ret = mem.identVarClass();
        Stack_Object obj = mem.stack.getObject("val");
        assertEquals("val", ret);
        assertNull(obj.getValue());
        mem.affVarClass(2); // We affect the var class to 2
        obj = mem.stack.getObject("val");
        assertEquals(2, obj.getValue());

        // Reaffect
        mem.affVarClass(4);
        obj = mem.stack.getObject("val");
        assertEquals(4, obj.getValue());
    }

    /* TODO : Put back later
    @Disabled
    @Test
    public void affVarClassReaffectDiffType() {
        mem.declVarClass("val");
        String ret = mem.identVarClass();
        Stack_Object obj = mem.stack.getObject("val");
        assertEquals("val", ret);
        assertNull(obj.getValue());
        mem.affVarClass(2); // We affect the var class to 2
        obj = mem.stack.getObject("val");
        assertEquals(2, obj.getValue());

        // Reaffect with different type should throw an error

        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            mem.affVarClass("mismatch");
        });
    }

     */

    /**
     * We test with calling with no declared class var, and with null arg
     */
    @ParameterizedTest
    @MethodSource("affVarNull")
    public void affVarClassCalledNull(Object arg) {
        // We test with no declared class var
        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            mem.affVarClass(arg);
        });

        // Now we declare and call it with null
        mem.declVarClass("val");
        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            mem.affVarClass(null);
        });

    }

    @Test
    public void declVarClassThrowsWhenClassVarAlreadyDefined() {
        // First declaration should succeed
        mem.declVarClass("classVar");
        // Second declaration (any identifier) should fail because a class var already exists
        assertThrows(Memory.MemoryIllegalArgException.class, () -> mem.declVarClass("another"));
    }

    @Test
    public void declVarClassThrowsWhenSymbolTableContainsIdentifier() {
        // Declare a normal variable which will add an entry in the symbol table
        mem.declVar("idInTable", 1, DataType.INT);

        // Attempting to declare a class var with the same identifier must fail (symbolTable.contains branch)
        Memory.MemoryIllegalArgException ex = assertThrows(Memory.MemoryIllegalArgException.class, () -> mem.declVarClass("idInTable"));
        assertTrue(ex.getMessage().contains("Symbol Table") || ex.getMessage().contains("Symbol"));
    }

    @Test
    public void declVarClassThrowsWhenStackHasObjectWithSameIdentifier() {
        // Add an object directly to the stack without adding a symbol table entry
        mem.stack.setVar("idOnStackOnly", 2, DataType.INT);

        // Symbol table should not contain this identifier
        assertFalse(mem.symbolTable.contains("idOnStackOnly"));

        // Now declVarClass should throw because stack.hasObj(identifier) is true
        Memory.MemoryIllegalArgException ex = assertThrows(Memory.MemoryIllegalArgException.class, () -> mem.declVarClass("idOnStackOnly"));
        assertTrue(ex.getMessage().contains("Stack") || ex.getMessage().contains("Stack"));
    }

    @Test
    public void memoryConstructorAndWriteMethods() {
        Writer writer = new Writer();
        StringBuilder sb = new StringBuilder();

        // subscribe to TextAddedEvent so we can capture written diffs
        writer.TextAddedEvent.subscribe(data -> sb.append(data.diff()));

        Memory memWithWriter = new Memory(writer);
        memWithWriter.write("hello");
        assertEquals("hello", sb.toString());

        memWithWriter.writeLine("world");
        assertEquals("hello" + "world\n", sb.toString());
    }

    @Test
    public void write_and_writeLine_withNullOutput_doNotThrow() {
        // Default constructor leaves output == null
        Memory mem = new Memory();
        assertNull(mem.output);

        // Should not throw when no output is configured
        mem.write("nope");
        mem.writeLine("still nothing");

        // output must remains null
        assertNull(mem.output);
    }

    @Test
    public void valueTypeOfUndefinedVariable() {
        Memory mem = new Memory();
        assertThrows(IllegalArgumentException.class,() -> mem.valueTypeOf("x"));
    }

    @Test
    public void valueTypeOfBoolean() {
        // Default constructor leaves output == null
        Memory mem = new Memory();
        mem.declVar("x", new Value(false), ValueType.toDataType(ValueType.BOOL));
        assertEquals(ValueType.BOOL,mem.valueTypeOf("x"));
    }

    @Test
    public void valueTypeOfInt() {
        Memory mem = new Memory();
        mem.declVar("x", new Value(1), ValueType.toDataType(ValueType.INT));
        assertEquals(ValueType.INT,mem.valueTypeOf("x"));
    }

    @Test
    public void valueTypeOfEmpty() {
        Memory mem = new Memory();
        mem.declVar("x", new Value(), ValueType.toDataType(ValueType.EMPTY));
        assertEquals(ValueType.EMPTY,mem.valueTypeOf("x"));
    }

    @Test
    public void dataTypeOfUndefinedVariable() {
        Memory mem = new Memory();
        assertThrows(IllegalArgumentException.class,() -> mem.dataTypeOf("x"));
    }

    @Test
    public void dataTypeOfBoolean() {
        // Default constructor leaves output == null
        Memory mem = new Memory();
        mem.declVar("x", new Value(false), ValueType.toDataType(ValueType.BOOL));
        assertEquals(DataType.BOOL,mem.dataTypeOf("x"));
    }

    @Test
    public void dataTypeOfInt() {
        Memory mem = new Memory();
        mem.declVar("x", new Value(1), ValueType.toDataType(ValueType.INT));
        assertEquals(DataType.INT,mem.dataTypeOf("x"));
    }

    @Test
    public void dataTypeOfEmpty() {
        Memory mem = new Memory();
        mem.declVar("x", new Value(), ValueType.toDataType(ValueType.EMPTY));
        assertEquals(DataType.UNKNOWN,mem.dataTypeOf("x"));
    }
}
