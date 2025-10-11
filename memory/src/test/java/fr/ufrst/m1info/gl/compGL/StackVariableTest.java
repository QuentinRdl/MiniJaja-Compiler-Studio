package fr.ufrst.m1info.gl.compGL;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StackVariableTest {
    private Stack_Variable variable;

    @Before
    public void setUp() {
        variable = new Stack_Variable("x", 42, 0);
    }

    @Test
    public void testConstructor() {
        assertEquals("x", variable.getName());
        assertEquals(42, variable.getValue());
        assertEquals(0, variable.getScope());
    }

    @Test
    public void getName() {
        assertEquals("x", variable.getName());
        Stack_Variable newVar = new Stack_Variable("y", 2, 12);
        assertEquals("y", newVar.getName());
    }

    @Test
    public void getValue() {
        assertEquals(42, variable.getValue());
        Stack_Variable newVar = new Stack_Variable("y", 2, 12);
        assertEquals(2, newVar.getValue());
    }

    @Test
    public void getScope() {
        assertEquals(0, variable.getScope());
        Stack_Variable newVar = new Stack_Variable("y", 2, 12);
        assertEquals(12, newVar.getScope());
    }

    @Test
    public void setValue() {
        variable.setValue(100);
        assertEquals(100, variable.getValue());
    }

    @Test
    public void setValueWithNull() {
        variable.setValue(null);
        assertNull(variable.getValue());
    }

    @Test
    public void setValueWithDifferentTypes() {
        variable.setValue("string");
        assertEquals("string", variable.getValue());

        variable.setValue(3.14);
        assertEquals(3.14, variable.getValue());

        variable.setValue(true);
        assertEquals(true, variable.getValue());

    }

    @Test
    public void nameImmutable() {
        String originalName = variable.getName();
        assertEquals("x", originalName);
        // Name cannot be changed, so we verify it stays the same
        assertEquals("x", variable.getName());
    }

    @Test
    public void testScopeImmutable() {
        int originalScope = variable.getScope();
        assertEquals(0, originalScope);
        // Scope cannot be changed, so we verify it stays the same
        assertEquals(0, variable.getScope());
    }

    @Test
    public void testToString() {
        assertEquals("x_0=42", variable.toString());
    }
}
