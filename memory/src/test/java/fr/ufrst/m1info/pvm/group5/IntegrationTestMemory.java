package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.SymbolTable.EntryKind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class IntegrationTestMemory {

    Memory mem;

    /*
    import org.junit.jupiter.params.ParameterizedTest;
    import org.junit.jupiter.params.provider.MethodSource;

    @BeforeEach
    public void setUp() {
        mem = new Memory();
    }

    @AfterEach
    void tearDown() {
        mem = null;
    }


    static Stream<org.junit.jupiter.params.provider.Arguments> dataProvider() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("id_int", 2, DataType.INT),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_true", true, DataType.BOOL),
                org.junit.jupiter.params.provider.Arguments.of("id_bool_false", false, DataType.BOOL),
                org.junit.jupiter.params.provider.Arguments.of("id_float", 3.14, DataType.FLOAT)
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void popWorks(String id, Object value, DataType type) throws Exception {
        mem.push(id, value, type, EntryKind.VARIABLE);
        Stack_Object ret = mem.pop();
        assertNotNull(ret);
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
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
    @MethodSource("dataProvider")
    public void popEmptySymbolTable(String id, Object value, DataType type) {
        mem.stack.setVar("var", value, type);
        assertThrows(IllegalArgumentException.class, () -> mem.pop());
    }

     */


}
