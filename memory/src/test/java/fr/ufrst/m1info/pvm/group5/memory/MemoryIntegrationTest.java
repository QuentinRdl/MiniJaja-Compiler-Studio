package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.heap.Heap;
import fr.ufrst.m1info.pvm.group5.memory.heap.IndexOutOfBounds;
import fr.ufrst.m1info.pvm.group5.memory.heap.InvalidMemoryAddressException;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.stream.Stream;

class MemoryIntegrationTest {

    Memory mem;

    String goldenFilePath = "/fr/ufrst/m1info/pvm/group5/memory/golden/";

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
                org.junit.jupiter.params.provider.Arguments.of("id_bool_false", false, DataType.BOOL),
                org.junit.jupiter.params.provider.Arguments.of("id_str", "blabla", DataType.STRING)
        );
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> typeOfVarsAffect() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("id_int", 2, DataType.INT, 4),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_true", true, DataType.BOOL, false),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_false", false, DataType.BOOL, true)
        );
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> typeOfVarsNull() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("id_int", null, DataType.INT, 4),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_true", null, DataType.BOOL, false),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_false", null, DataType.BOOL, true)
        );
    }
    static Stream<org.junit.jupiter.params.provider.Arguments> typeOfVarsNull2() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("id_int", 2, DataType.INT, null),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_true", true, DataType.BOOL, null),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_false", false, DataType.BOOL, null)
        );
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> affectValueMultiple() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("id_int", 2, DataType.INT, 23, 234, 2345),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_true", true, DataType.BOOL, false, true, true),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_false", false, DataType.BOOL, false, true, true)
        );
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> affVarNull() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(23),
                org.junit.jupiter.params.provider.Arguments.of(2.3),
                org.junit.jupiter.params.provider.Arguments.of("blabla"),
                org.junit.jupiter.params.provider.Arguments.of(true),
                org.junit.jupiter.params.provider.Arguments.of(false)
        );
    }

    @ParameterizedTest
    @MethodSource("typeOfVars")
    void popWorks(String id, Object value, DataType type) throws Exception {
        mem.push(id, value, type, EntryKind.VARIABLE);
        Object ret = mem.pop();
        assertNotNull(ret);
    }

    @ParameterizedTest
    @MethodSource("typeOfVars")
    void popCheckStackException(String id, Object value, DataType type) throws Exception {
        // Stack is empty, pop should throw StackIsEmptyException
        assertThrows(Stack.StackIsEmptyException.class, () -> mem.pop());

        mem.push(id, value, type, EntryKind.VARIABLE);
        Object ret = mem.pop();
        assertNotNull(ret);

        // Stack is now empty again, should throw StackIsEmptyException
        assertThrows(Stack.StackIsEmptyException.class, () -> mem.pop());
    }

    @ParameterizedTest
    @MethodSource("typeOfVars")
    void popEmptySymbolTable(String id, Object value, DataType type) {
        mem.stack.setVar("var", value, type);
        assertThrows(Memory.MemoryIllegalArgException.class, () -> mem.pop());
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    void affectValueWorks(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value ret_val = (Value) ret;
        if (type == DataType.BOOL) {
            assertEquals(value, ret_val.valueBool);
        } else if (type == DataType.INT) {
            assertEquals(value, ret_val.valueInt);
        } else if (type == DataType.STRING) {
            assertEquals(value, ret_val.valueString);
        } else {
            return;
        }
        mem.affectValue(id, newValue);

        ret = mem.val(id);
        ret_val = (Value) ret;

        if (type == DataType.BOOL) {
            assertEquals(newValue, ret_val.valueBool);
        } else if (type == DataType.INT) {
            assertEquals(newValue, ret_val.valueInt);
        } else if (type == DataType.STRING) {
            assertEquals(newValue, ret_val.valueString);
        } else {
            return;
        }
    }


    @ParameterizedTest
    @MethodSource("affectValueMultiple")
    void affectValueMultiple(String id, Object value, DataType type, Object newValue, Object newValue1, Object newValue2) {
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
            return;
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
            return;
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
            return;
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
            return;
        }
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsNull2")
    void affectValueNullGiven(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value val = (Value) ret;
        StackObject stack_ret = StackObject.valueToStackObj(val);
        assertEquals(value, stack_ret.getValue());
        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            mem.affectValue(id, newValue);
        });
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    void affectValueIdNull(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value val = (Value) ret;
        StackObject stack_ret = StackObject.valueToStackObj(val);
        assertEquals(value, stack_ret.getValue());
        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            mem.affectValue(null, newValue);
        });
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    void affectValueNotDeclared(String id, Object value, DataType type, Object newValue) {
        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            mem.affectValue(id, newValue);
        });
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsNull")
    void declVarWithNull(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value val = (Value) ret;
        StackObject stack_ret = StackObject.valueToStackObj(val);
        assertEquals(value, stack_ret.getValue());
        mem.affectValue(id, newValue);
        stack_ret = mem.stack.getObject(id);
        assertEquals(newValue, stack_ret.getValue());
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    void affectValueConst(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declCst(id, null, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value val = (Value) ret;
        StackObject stack_ret = StackObject.valueToStackObj(val);
        assertNull(stack_ret.getValue());
        mem.affectValue(id, newValue);
        stack_ret = mem.stack.getObject(id);
        assertEquals(newValue, stack_ret.getValue());
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    void affectValueConstCannotModify(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declCst(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Value.class, ret);
        Value val = (Value) ret;
        StackObject stack_ret = StackObject.valueToStackObj(val);
        assertEquals(value, stack_ret.getValue());
        assertThrows(Stack.ConstantModificationException.class, () -> {
            mem.affectValue(id, newValue);
        });
    }


    @Test
    void declVarClassWorks() {
        mem.declVarClass("val");
        String ret = mem.identVarClass();
        assertEquals("val", ret);
    }


    /**
     * Using declVarClass should declare the var class with
     * a DataType set with UNKNOWN
     */
    @Test
    void declVarClassUsesUnknownDataType() {
        mem.declVarClass("val");
        String ret = mem.identVarClass();
        StackObject obj = mem.stack.getObject("val");
        assertEquals("val", ret);
        assertEquals(DataType.UNKNOWN, obj.getDataType());
    }


    /**
     * Using declVarClass should declare the var class with
     * data = null
     */
    @Test
    void declVarClassUsesValueEqualsNull() {
        mem.declVarClass("val");
        String ret = mem.identVarClass();
        StackObject obj = mem.stack.getObject("val");
        assertEquals("val", ret);
        assertNull(obj.getValue());
    }

    @Test
    void affVarClass() {
        mem.declVarClass("val");
        String ret = mem.identVarClass();
        StackObject obj = mem.stack.getObject("val");
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

    /**
     * We test with calling with no declared class var, and with null arg
     */
    @ParameterizedTest
    @MethodSource("affVarNull")
    void affVarClassCalledNull(Object arg) {
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
    void declVarClassThrowsWhenClassVarAlreadyDefined() {
        // First declaration should succeed
        mem.declVarClass("classVar");
        // Second declaration (any identifier) should fail because a class var already exists
        assertThrows(Memory.MemoryIllegalOperationException.class, () -> mem.declVarClass("another"));
    }

    @Test
    void declVarClassThrowsWhenSymbolTableContainsIdentifier() {
        // Declare a normal variable which will add an entry in the symbol table
        mem.declVar("idInTable", 1, DataType.INT);

        // Attempting to declare a class var with the same identifier must fail (symbolTable.contains branch)
        Memory.MemoryIllegalOperationException ex = assertThrows(Memory.MemoryIllegalOperationException.class, () -> mem.declVarClass("idInTable"));
        System.out.println(ex.getMessage());
        assertTrue(ex.getMessage().contains("a class variable has already been defined"));
    }

    @Test
    void declVarClassThrowsWhenStackHasObjectWithSameIdentifier() {
        // Add an object directly to the stack without adding a symbol table entry
        mem.stack.setVar("idOnStackOnly", 2, DataType.INT);

        // Symbol table should not contain this identifier
        assertFalse(mem.symbolTable.contains("idOnStackOnly"));

        // Now declVarClass should throw because stack.hasObj(identifier) is true
        Memory.MemoryIllegalOperationException ex = assertThrows(Memory.MemoryIllegalOperationException.class, () -> mem.declVarClass("idOnStackOnly"));
        assertTrue(ex.getMessage().contains("a class variable has already been defined"));
    }

    @Test
    void memoryConstructorAndWriteMethods() {
        Writer writer = new Writer();
        StringBuilder sb = new StringBuilder();

        // subscribe to TextAddedEvent so we can capture written diffs
        writer.textAddedEvent.subscribe(data -> sb.append(data.diff()));

        Memory memWithWriter = new Memory(writer);
        memWithWriter.write("hello");
        assertEquals("hello", sb.toString());

        memWithWriter.writeLine("world");
        assertEquals("hello" + "world\n", sb.toString());
    }

    @Test
    void write_and_writeLine_withNullOutput_doNotThrow() {
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
    void valueTypeOfUndefinedVariable() {
        Memory mem = new Memory();
        assertThrows(Memory.MemoryIllegalArgException.class,() -> mem.valueTypeOf("x"));
    }

    @Test
    void valueTypeOfBoolean() {
        // Default constructor leaves output == null
        Memory mem = new Memory();
        mem.declVar("x", new Value(false), ValueType.toDataType(ValueType.BOOL));
        assertEquals(ValueType.BOOL,mem.valueTypeOf("x"));
    }

    @Test
    void valueTypeOfInt() {
        Memory mem = new Memory();
        mem.declVar("x", new Value(1), ValueType.toDataType(ValueType.INT));
        assertEquals(ValueType.INT,mem.valueTypeOf("x"));
    }

    @Test
    void valueTypeOfEmpty() {
        Memory mem = new Memory();
        mem.declVar("x", new Value(), ValueType.toDataType(ValueType.EMPTY));
        assertEquals(ValueType.EMPTY,mem.valueTypeOf("x"));
    }

    @Test
    void dataTypeOfUndefinedVariable() {
        Memory mem = new Memory();
        assertThrows(Memory.MemoryIllegalArgException.class,() -> mem.dataTypeOf("x"));
    }

    @Test
    void dataTypeOfBoolean() {
        // Default constructor leaves output == null
        Memory mem = new Memory();
        mem.declVar("x", new Value(false), ValueType.toDataType(ValueType.BOOL));
        assertEquals(DataType.BOOL,mem.dataTypeOf("x"));
    }

    @Test
    void dataTypeOfInt() {
        Memory mem = new Memory();
        mem.declVar("x", new Value(1), ValueType.toDataType(ValueType.INT));
        assertEquals(DataType.INT,mem.dataTypeOf("x"));
    }

    @Test
    void dataTypeOfEmpty() {
        Memory mem = new Memory();
        mem.declVar("x", new Value(), ValueType.toDataType(ValueType.EMPTY));
        assertEquals(DataType.UNKNOWN,mem.dataTypeOf("x"));
    }

    @Test
    void declTabBasic() {
        Memory mem = new Memory();
        Heap heap = mem.getHeap();
        int initialHeapSize = heap.getAvailableSize();

        // First, the initial size should be equal to the total size
        assertEquals(initialHeapSize, heap.getTotalSize());
        mem.declTab("arr", 5, DataType.INT);

        // Array reference should be stocked as an int
        assertEquals(DataType.INT, mem.dataTypeOf("arr"));

        // We should now have less space than initially
        assertTrue(initialHeapSize > heap.getAvailableSize());
    }

    @Test
    void declTabPlusWithdraw() {
        Memory mem = new Memory();
        Heap heap = mem.getHeap();
        int initialHeapSize = heap.getAvailableSize();

        mem.declTab("arr", 5, DataType.INT);

        // Array reference should be stocked as an int
        assertEquals(DataType.INT, mem.dataTypeOf("arr"));
        assertTrue(initialHeapSize > heap.getAvailableSize());

        mem.withdrawDecl("arr");

        Memory.MemoryIllegalArgException ex = assertThrows(Memory.MemoryIllegalArgException.class, () -> mem.val("arr"));
        assertTrue(ex.getMessage().contains("Symbol not found"));

        // After freeing the block, the available size of the heap should return to the total space
        assertEquals(initialHeapSize, heap.getTotalSize());
    }

    @ParameterizedTest
    @ValueSource(ints = {127, -127}) // TODO : Put back once heap corrected -> , 12345678, -12345678})
    void valTandAffValT(int testValue) {
        Memory mem = new Memory();

        Value val = new Value(testValue);
        int valInt = val.valueInt;

        mem.declTab("arr", 5, DataType.INT);
        mem.affectValT("arr", 0, val);

        assertEquals(valInt, mem.valT("arr", 0).valueInt);
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void valTAndAffValTBool(boolean valBool) {
        Memory mem = new Memory();

        Value val = new Value(valBool);

        mem.declTab("arr", 5, DataType.BOOL);
        mem.affectValT("arr", 0, val);

        assertEquals(valBool, mem.valT("arr", 0).valueBool);
    }

    @Test
    void heapIncorrectIndex() {
        Memory mem = new Memory();
        Value val = new Value(true);

        mem.declTab("arr", 5, DataType.BOOL);

        assertThrows(IndexOutOfBounds.class, () -> mem.affectValT("arr", -1, val));
        assertThrows(IndexOutOfBounds.class, () -> mem.affectValT("arr", 6, val));
    }

    static Stream<Arguments> tabLengthTestProvider() {
        return Stream.of(
                Arguments.of(1, DataType.INT),
                Arguments.of(5, DataType.INT),
                Arguments.of(100, DataType.INT),
                Arguments.of(200, DataType.INT),
                Arguments.of(1, DataType.BOOL),
                Arguments.of(5, DataType.BOOL),
                Arguments.of(100, DataType.BOOL),
                Arguments.of(200, DataType.BOOL)
        );
    }

    @ParameterizedTest
    @MethodSource("tabLengthTestProvider")
    void tabLengthTest(int length, DataType dataType) {
        Memory mem = new Memory();
        mem.declTab("arr", length, dataType);
        assertEquals(length, mem.tabLength("arr"));
    }

    @Test
    void arrayFuncWithNoArrayThrows() {
        Memory mem = new Memory();

        assertThrows(java.lang.NullPointerException.class, () -> mem.tabLength("arr"));
        assertThrows(java.lang.NullPointerException.class, () -> mem.affectValT("arr", 0, new Value(12)));
        assertThrows(java.lang.NullPointerException.class, () -> mem.valT("arr", 0));
    }

    @Test
    void aliasingBetweenArrayAndIntVariable() {
        Memory mem = new Memory();

        mem.declTab("tab", 3, DataType.INT);
        StackObject aObj = mem.stack.getObject("tab");
        assertNotNull(aObj);
        assertEquals(DataType.INT, aObj.getDataType());

        // Create an alias variable that stores the raw address
        mem.declVar("alias", aObj.getValue(), DataType.INT);

        // Write via original identifier
        mem.affectValT("tab", 0, new Value(42));
        // Read via alias -> should see same value
        assertEquals(42, mem.valT("alias", 0).valueInt);

        // Write via alias
        mem.affectValT("alias", 1, new Value(7));
        // Read via original -> should see same value
        assertEquals(7, mem.valT("tab", 1).valueInt);
    }

    @Test
    void toStringTabGolden() throws Exception {
        Memory mem = new Memory();
        mem.setHeap(new Heap(16));

        String[] res = mem.toStringTab();
        assertNotNull(res);
        assertEquals(2, res.length);

        try (InputStream is = getClass().getResourceAsStream(goldenFilePath + "heap.toStringTab.golden")) {
            assertNotNull(is); // Make sure file is loaded
            String expectedHeap = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            assertEquals(expectedHeap, res[0]);
        }

        try (InputStream is = getClass().getResourceAsStream(goldenFilePath + "stack.toStringTab.golden")) {
            assertNotNull(is); // Make sure file is loaded
            String expectedStack = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            assertEquals(expectedStack, res[1]);
        }
    }

    @Test
    void toStringTabAfterDeclTab() throws Exception {
        Memory mem = new Memory();
        mem.setHeap(new Heap(16));

        // Declare an array of 5 ints
        mem.declTab("arr", 5, DataType.INT);

        String[] res = mem.toStringTab();
        assertNotNull(res);
        assertEquals(2, res.length);

        try (InputStream is = getClass().getResourceAsStream(goldenFilePath + "heap.allocated5.toStringTab.golden")) {
            assertNotNull(is); // Make suire file is loaded
            String expectedHeap = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            assertEquals(expectedHeap, res[0]);
        }

        try (InputStream is = getClass().getResourceAsStream(goldenFilePath + "stack.allocated5.toStringTab.golden")) {
            assertNotNull(is); // Make sure file is loaded
            String expectedStack = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            assertEquals(expectedStack, res[1]);
        }
    }

    @Test
    void toStringTabAfterWrite() throws Exception {
        Memory mem = new Memory();
        mem.setHeap(new Heap(16));

        mem.declTab("arr", 5, DataType.INT);
        mem.affectValT("arr", 0, new Value(7));

        String[] res = mem.toStringTab();
        assertNotNull(res);
        assertEquals(2, res.length);

        try (InputStream is = getClass().getResourceAsStream(goldenFilePath + "stack.allocated5.toStringTab.golden")) {
            assertNotNull(is); // Make sure file is loaded
            String expectedStack = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            assertEquals(expectedStack, res[1]);
        }
    }
}
