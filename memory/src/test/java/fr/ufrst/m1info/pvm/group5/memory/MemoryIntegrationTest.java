package fr.ufrst.m1info.pvm.group5.memory;


import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.SymbolTable;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.SymbolTableEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
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
        assertThrows(IllegalArgumentException.class, () -> mem.pop());
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    public void affectValueWorks(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Stack_Object.class, ret);
        Stack_Object stack_ret = (Stack_Object) ret;
        assertEquals(value, stack_ret.getValue());
        mem.affectValue(id, newValue);
        assertEquals(newValue, stack_ret.getValue());
    }


    @ParameterizedTest
    @MethodSource("affectValueMultiple")
    public void affectValueMultiple(String id, Object value, DataType type, Object newValue, Object newValue1, Object newValue2) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Stack_Object.class, ret);
        Stack_Object stack_ret = (Stack_Object) ret;
        assertEquals(value, stack_ret.getValue());
        mem.affectValue(id, newValue);
        assertEquals(newValue, stack_ret.getValue());

        mem.affectValue(id, newValue1);
        assertEquals(newValue1, stack_ret.getValue());

        mem.affectValue(id, newValue2);
        assertEquals(newValue2, stack_ret.getValue());
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsNull2")
    public void affectValueNullGiven(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Stack_Object.class, ret);
        Stack_Object stack_ret = (Stack_Object) ret;
        assertEquals(value, stack_ret.getValue());
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            mem.affectValue(id, newValue);
        });
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    public void affectValueIdNull(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declVar(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Stack_Object.class, ret);
        Stack_Object stack_ret = (Stack_Object) ret;
        assertEquals(value, stack_ret.getValue());
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
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

        assertInstanceOf(Stack_Object.class, ret);
        Stack_Object stack_ret = (Stack_Object) ret;
        assertEquals(value, stack_ret.getValue());
        mem.affectValue(id, newValue);
        assertEquals(newValue, stack_ret.getValue());
    }

    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    public void affectValueConst(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declCst(id, null, type);
        Object ret = mem.val(id);

        assertInstanceOf(Stack_Object.class, ret);
        Stack_Object stack_ret = (Stack_Object) ret;
        assertNull(stack_ret.getValue());
        mem.affectValue(id, newValue);
        assertEquals(newValue, stack_ret.getValue());
    }


    @ParameterizedTest
    @MethodSource("typeOfVarsAffect")
    public void affectValueConstCannotModify(String id, Object value, DataType type, Object newValue) {
        // We declare, then affect
        mem.declCst(id, value, type);
        Object ret = mem.val(id);

        assertInstanceOf(Stack_Object.class, ret);
        Stack_Object stack_ret = (Stack_Object) ret;
        assertEquals(value, stack_ret.getValue());
        assertThrows(java.lang.IllegalStateException.class, () -> {
            mem.affectValue(id, newValue);
        });
    }

}

