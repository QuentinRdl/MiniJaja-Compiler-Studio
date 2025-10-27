package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StackObjectTest {

    @Test
    public void constructNonVarNonConst_ok() {
        Stack_Object obj = new Stack_Object("m", "val", 1, EntryKind.METHOD);
        assertEquals("m", obj.getName());
        assertEquals("val", obj.getValue());
        assertEquals(1, obj.getScope());
        assertEquals(EntryKind.METHOD, obj.getEntryKind());
        assertEquals(DataType.UNKNOWN, obj.getDataType());
    }

    @Test
    public void constructVariable_withDataType_ok() {
        Stack_Object var = new Stack_Object("x", 10, 0, EntryKind.VARIABLE, DataType.INT);
        assertEquals("x", var.getName());
        assertEquals(10, var.getValue());
        assertEquals(0, var.getScope());
        assertEquals(EntryKind.VARIABLE, var.getEntryKind());
        assertEquals(DataType.INT, var.getDataType());
    }

    @Test
    public void constructVariable_withoutDataType_throws() {
        assertThrows(InvalidStackObjectConstructionException.class, () ->
            new Stack_Object("x", 10, 0, EntryKind.VARIABLE)
        );
    }

    @Test
    public void setValue_onVariable_updates() {
        Stack_Object var = new Stack_Object("x", 1, 0, EntryKind.VARIABLE, DataType.INT);
        assertEquals(1, var.getValue());
        var.setValue(2);
        assertEquals(2, var.getValue());
    }

    @Test
    public void setValue_onConstant_throws() {
        Stack_Object cst = new Stack_Object("c", 1, 0, EntryKind.CONSTANT, DataType.INT);
        assertThrows(ConstantModificationException.class, () -> cst.setValue(2));
    }

    @Test
    public void setValue_onNonConstant_allowed() {
        Stack_Object obj = new Stack_Object("o", "old", 0, EntryKind.METHOD);
        obj.setValue("new");
        assertEquals("new", obj.getValue());
    }

    @Test
    public void toString_formatsAsExpected() {
        Stack_Object obj = new Stack_Object("x", 42, 2, EntryKind.VARIABLE, DataType.INT);
        assertEquals("x_2=42", obj.toString());
    }

    @Test
    public void constructNonVarWithDataType_throws() {
        assertThrows(InvalidStackObjectConstructionException.class, () ->
            new Stack_Object("m", "val", 1, EntryKind.METHOD, DataType.INT)
        );
    }
}
