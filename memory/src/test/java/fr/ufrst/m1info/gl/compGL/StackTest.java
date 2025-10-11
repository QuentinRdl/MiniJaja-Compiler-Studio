package fr.ufrst.m1info.gl.compGL;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.mockito.Mock;

/**
 * Unit test for the Stack class
 */
public class StackTest {
    private Stack stack;

    @Mock
    private Stack_Variable mockVariable;

    /**
     * Before each test, we set up an empty stack
     */
    @Before
    public void setUp() {
        stack = new Stack();
    }

    @Test
    public void testConstructor() {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    public void pushScope() {
        stack.pushScope();
        assertTrue(stack.isEmpty());
        // Even though we pushed a scope, as there are nothing in the scope it should still be empty
    }

    @Test
    public void popScope() throws Stack.NoScopeException {
        stack.pushScope();
        stack.popScope();
        assertTrue(stack.isEmpty());
    }

    @Test(expected = Stack.NoScopeException.class)
    public void testPopScopeWhenNoScopes() throws Stack.NoScopeException {
        stack.popScope();
    }

    @Test
    public void setVar() {
        stack.pushScope();
        assertEquals(0, stack.size());
        stack.setVar("x", 1234);
        assertEquals(1, stack.size());
        assertEquals(1234, stack.getVar("x"));
    }

    @Test
    public void setVarMultipleVariables() {
        stack.pushScope();
        stack.setVar("x", 1234);
        stack.setVar("y", 2345);
        stack.setVar("z", 3456);
        assertEquals(3, stack.size());
    }

    @Test
    public void testTop() {
        stack.pushScope();
        stack.setVar("a", 100);
        Stack_Variable top = stack.top();
        assertEquals("a", top.getName());
        assertEquals(100, top.getValue());
    }

    @Test(expected = java.util.EmptyStackException.class)
    public void testTopWhenEmpty() {
        stack.top();
    }

    @Test
    public void testPop() throws Stack.StackIsEmptyException {
        stack.pushScope();
        stack.setVar("x", 50);
        Stack_Variable popped = stack.pop();
        assertEquals("x", popped.getName());
        assertEquals(50, popped.getValue());
        assertTrue(stack.isEmpty());
    }

    @Test(expected = Stack.StackIsEmptyException.class)
    public void popWhenEmpty() throws Stack.StackIsEmptyException {
        stack.pop();
    }

    @Test
    public void getVar() {
        stack.pushScope();
        stack.setVar("x", 1234);
        Object value = stack.getVar("x");
        assertEquals(1234, value);
    }

    @Test
    public void getVarNotFound() {
        stack.pushScope();
        stack.setVar("x", 1234);
        Object value = stack.getVar("y");
        assertNull(value);
    }

    @Test
    public void getVarDifferentScopes() {
        stack.pushScope();
        stack.setVar("x", 1);

        stack.pushScope();
        stack.setVar("x", 2);
        // Should only get the x from current scope (2)
        assertEquals(2, stack.getVar("x"));

        try {
            stack.popScope();
        } catch (Stack.NoScopeException e) {
            fail("Should not throw exception");
        }
        // Now should get the x from previous scope (1)
        assertEquals(1, stack.getVar("x"));
    }

    @Test
    public void updateVar() {
        stack.pushScope();
        stack.setVar("x", 1234);
        boolean upd = stack.updateVar("x", 100);
        assertTrue(upd);
        assertEquals(100, stack.getVar("x"));
    }

    @Test
    public void updateVarNotFound() {
        stack.pushScope();
        stack.setVar("x", 1234);
        boolean updated = stack.updateVar("y", 100);
        assertFalse(updated);
    }

    @Test
    public void updateTopVar() {
        stack.pushScope();
        stack.setVar("x", 1234);
        boolean updated = stack.updateTopVar(99);
        assertTrue(updated);
        assertEquals(99, stack.top().getValue());
    }

    @Test
    public void updateTopVarWhenEmpty() {
        boolean updated = stack.updateTopVar(50);
        assertFalse(updated);
    }

    @Test
    public void hasVar() {
        stack.pushScope();
        stack.setVar("x", 1234);
        assertTrue(stack.hasVar("x"));
        assertFalse(stack.hasVar("y"));
    }

    @Test
    public void hasVarDifferentScopes() {
        stack.pushScope();
        stack.setVar("x", 1234);

        stack.pushScope();
        // x exists in previous scope but not in current
        assertFalse(stack.hasVar("x"));
    }

    @Test
    public void testSize() {
        assertEquals(0, stack.size());
        stack.pushScope();
        stack.setVar("x", 10);
        assertEquals(1, stack.size());
        stack.setVar("y", 20);
        assertEquals(2, stack.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(stack.isEmpty());
        stack.pushScope();
        stack.setVar("x", 11234);
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testClear() {
        stack.pushScope();
        stack.setVar("x", 10);
        stack.setVar("y", 20);
        assertEquals(2, stack.size());

        stack.clear();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    public void actualScenario() throws Stack.StackIsEmptyException, Stack.NoScopeException {
        // Global scope
        stack.pushScope();
        stack.setVar("x", 5);
        stack.setVar("y", 10);

        // Enter function scope
        stack.pushScope();
        stack.setVar("x", 50);
        stack.setVar("z", 100);

        assertEquals(50, stack.getVar("x"));
        assertEquals(100, stack.getVar("z"));
        assertNull(stack.getVar("y"));
        assertEquals(4, stack.size());

        // Update x in current scope
        stack.updateVar("x", 55);
        assertEquals(55, stack.getVar("x"));

        // Exit function scope
        stack.popScope();
        assertEquals(5, stack.getVar("x"));
        assertEquals(10, stack.getVar("y"));
        assertNull(stack.getVar("z"));
        assertEquals(2, stack.size());
    }

    @Test
    public void multipleScopesWithSameVarName() throws Stack.NoScopeException {
        stack.pushScope();
        stack.setVar("count", 0);
        assertEquals(0, stack.getVar("count"));

        stack.pushScope();
        stack.setVar("count", 1);
        assertEquals(1, stack.getVar("count"));

        stack.pushScope();
        stack.setVar("count", 2);
        assertEquals(2, stack.getVar("count"));

        stack.popScope();
        assertEquals(1, stack.getVar("count"));

        stack.popScope();
        assertEquals(0, stack.getVar("count"));

        stack.popScope();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void popRemovesOnlyCurrentScopeVars() throws Stack.NoScopeException {
        stack.pushScope();
        stack.setVar("global", "value");

        stack.pushScope();
        stack.setVar("local1", "val1");
        stack.setVar("local2", "val2");

        assertEquals(3, stack.size());

        stack.popScope();

        // Only the global variable should remain
        assertEquals(1, stack.size());
        assertEquals("value", stack.getVar("global"));
    }
}
