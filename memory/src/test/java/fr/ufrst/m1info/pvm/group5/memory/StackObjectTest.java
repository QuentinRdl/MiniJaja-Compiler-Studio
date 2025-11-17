package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackObjectTest {

    @Test
    void constructNonVarNonConst_ok() {
        StackObject obj = new StackObject("m", "val", 1, EntryKind.METHOD);
        assertEquals("m", obj.getName());
        assertEquals("val", obj.getValue());
        assertEquals(1, obj.getScope());
        assertEquals(EntryKind.METHOD, obj.getEntryKind());
        assertEquals(DataType.UNKNOWN, obj.getDataType());
    }

    @Test
    void constructVariable_withDataType_ok() {
        StackObject var = new StackObject("x", 10, 0, EntryKind.VARIABLE, DataType.INT);
        assertEquals("x", var.getName());
        assertEquals(10, var.getValue());
        assertEquals(0, var.getScope());
        assertEquals(EntryKind.VARIABLE, var.getEntryKind());
        assertEquals(DataType.INT, var.getDataType());
    }

    /*
    Should not pass with the modifications we did to the memory
    @Test
    void constructVariable_withoutDataType_throws() {
        assertThrows(Stack.InvalidStackObjectConstructionException.class, () ->
            new Stack_Object("x", 10, 0, EntryKind.VARIABLE)
        );
    }
     */

    @Test
    void setValue_onVariable_updates() {
        StackObject var = new StackObject("x", 1, 0, EntryKind.VARIABLE, DataType.INT);
        assertEquals(1, var.getValue());
        var.setValue(2);
        assertEquals(2, var.getValue());
    }

    @Test
    void setValue_onConstant_throws() {
        StackObject cst = new StackObject("c", 1, 0, EntryKind.CONSTANT, DataType.INT);
        assertThrows(Stack.ConstantModificationException.class, () -> cst.setValue(2));
    }

    @Test
    void setValue_onNonConstant_allowed() {
        StackObject obj = new StackObject("o", "old", 0, EntryKind.METHOD);
        obj.setValue("new");
        assertEquals("new", obj.getValue());
    }

    @Test
    void toString_formatsAsExpected() {
        StackObject obj = new StackObject("x", 42, 2, EntryKind.VARIABLE, DataType.INT);
        assertEquals("x_2=42", obj.toString());
    }

    @Test
    void constructNonVarWithDataType_throws() {
        assertThrows(Stack.InvalidStackObjectConstructionException.class, () ->
            new StackObject("m", "val", 1, EntryKind.METHOD, DataType.INT)
        );
    }
}
