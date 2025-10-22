package fr.ufrst.m1info.pvm.group5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fr.ufrst.m1info.pvm.group5.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.SymbolTable.EntryKind;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.util.Deque;


/**
 * Unit test for the Stack class
 */
public class StackTest {
    private Stack stack;
    private DataType integ;

    @Mock
    private Stack_Variable mockVariable;

    /**
     * Before each test, we set up an empty stack
     */
    @BeforeEach
    public void setUp() {
        stack = new Stack();
        integ = DataType.INT;
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

    @Test
    public void testPopScopeWhenNoScopes() {
        assertThrows(Stack.NoScopeException.class, () -> {
            stack.popScope();
        });
    }

    @Test
    public void setVar() {
        stack.pushScope();
        assertEquals(0, stack.size());
        stack.setVar("x", 1234, integ);
        assertEquals(1, stack.size());
        assertEquals(1234, stack.getObject("x"));
    }

    @Test
    public void setVarMultipleVariables() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        stack.setVar("y", 2345, integ);
        stack.setVar("z", 3456, integ);
        assertEquals(3, stack.size());
    }

    @Test
    public void testTop() {
        stack.pushScope();
        stack.setVar("a", 100, integ);
        Stack_Object top = stack.top();
        assertEquals("a", top.getName());
        assertEquals(100, top.getValue());
    }

    @Test
    public void testTopWhenEmpty() {
        assertThrows(java.util.EmptyStackException.class, () -> {
            stack.top();
        });
    }

    @Test
    public void testPop() throws Stack.StackIsEmptyException {
        stack.pushScope();
        stack.setVar("x", 50, integ);
        Stack_Object popped = stack.pop();
        assertEquals("x", popped.getName());
        assertEquals(50, popped.getValue());
        assertTrue(stack.isEmpty());
    }

    @Test
    //(expected = Stack.StackIsEmptyException.class)
    public void popWhenEmpty() throws Stack.StackIsEmptyException {
        assertThrows(Stack.StackIsEmptyException.class, () -> {
            stack.pop();
        });
    }

    @Test
    public void getVar() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        Object value = stack.getObject("x");
        assertEquals(1234, value);
    }

    @Test
    public void getVarNotFound() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        Object value = stack.getObject("y");
        assertNull(value);
    }

    @Test
    public void getVarDifferentScopes() {
        stack.pushScope();
        stack.setVar("x", 1, integ);

        stack.pushScope();
        stack.setVar("x", 2, integ);
        // Should only get the x from current scope (2)
        assertEquals(2, stack.getObject("x"));

        try {
            stack.popScope();
        } catch (Stack.NoScopeException e) {
            fail("Should not throw exception");
        }
        // Now should get the x from previous scope (1)
        assertEquals(1, stack.getObject("x"));
    }

    @Test
    public void updateVar() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        boolean upd = stack.updateVar("x", 100);
        assertTrue(upd);
        assertEquals(100, stack.getObject("x"));
    }

    @Test
    public void updateVarNotFound() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        boolean updated = stack.updateVar("y", 100);
        assertFalse(updated);
    }

    @Test
    public void updateTopVar() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
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
    public void hasObj() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        assertTrue(stack.hasObj("x"));
        assertFalse(stack.hasObj("y"));
    }

    @Test
    public void hasObjDifferentScopes() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);

        stack.pushScope();
        // x exists in previous scope but not in current
        assertFalse(stack.hasObj("x"));
    }

    @Test
    public void testSize() {
        assertEquals(0, stack.size());
        stack.pushScope();
        stack.setVar("x", 10, integ);
        assertEquals(1, stack.size());
        stack.setVar("y", 20, integ);
        assertEquals(2, stack.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(stack.isEmpty());
        stack.pushScope();
        stack.setVar("x", 11234, integ);
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testClear() {
        stack.pushScope();
        stack.setVar("x", 10, integ);
        stack.setVar("y", 20, integ);
        assertEquals(2, stack.size());

        stack.clear();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    public void actualScenario() throws Stack.StackIsEmptyException, Stack.NoScopeException {
        // Global scope
        stack.pushScope();
        stack.setVar("x", 5, integ);
        stack.setVar("y", 10, integ);

        // Enter function scope
        stack.pushScope();
        stack.setVar("x", 50, integ);
        stack.setVar("z", 100, integ);

        assertEquals(50, stack.getObject("x"));
        assertEquals(100, stack.getObject("z"));
        assertNull(stack.getObject("y"));
        assertEquals(4, stack.size());

        // Update x in current scope
        stack.updateVar("x", 55);
        assertEquals(55, stack.getObject("x"));

        // Exit function scope
        stack.popScope();
        assertEquals(5, stack.getObject("x"));
        assertEquals(10, stack.getObject("y"));
        assertNull(stack.getObject("z"));
        assertEquals(2, stack.size());
    }

    @Test
    public void multipleScopesWithSameVarName() throws Stack.NoScopeException {
        stack.pushScope();
        stack.setVar("count", 0, integ);
        assertEquals(0, stack.getObject("count"));

        stack.pushScope();
        stack.setVar("count", 1, integ);
        assertEquals(1, stack.getObject("count"));

        stack.pushScope();
        stack.setVar("count", 2, integ);
        assertEquals(2, stack.getObject("count"));

        stack.popScope();
        assertEquals(1, stack.getObject("count"));

        stack.popScope();
        assertEquals(0, stack.getObject("count"));

        stack.popScope();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void popRemovesOnlyCurrentScopeVars() throws Stack.NoScopeException {
        stack.pushScope();
        stack.setVar("global", "value", integ);

        stack.pushScope();
        stack.setVar("local1", "val1", integ);
        stack.setVar("local2", "val2", integ);

        assertEquals(3, stack.size());

        stack.popScope();

        // Only the global variable should remain
        assertEquals(1, stack.size());
        assertEquals("value", stack.getObject("global"));
    }

    @Test
    public void emptyStack_toString() {
        Stack s = new Stack();
        assertEquals("Stack{scopeDepth=0, contents=[]}", s.toString());
    }

    @Test
    public void nonEmptyStack_toString() throws Exception {
        Stack s = new Stack();

        // Construct Stack_Object instances (use constructor that accepts DataType)
        Stack_Object a = new Stack_Object("x", 1, 0, EntryKind.VARIABLE, DataType.INT);
        Stack_Object b = new Stack_Object("y", 2, 0, EntryKind.VARIABLE, DataType.INT);

        // Use reflection to access the private deque and add elements in insertion order
        Field f = Stack.class.getDeclaredField("stack_content");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        Deque<Stack_Object> dq = (Deque<Stack_Object>) f.get(s);

        dq.addLast(a);
        dq.addLast(b);

        String expected = "Stack{scopeDepth=0, contents=[x_0=1, y_0=2]}";
        assertEquals(expected, s.toString());
    }

    @Test
    public void constructor_exception_test() {
        String msg = "Invalid variable name";
        Stack.InvalidNameException ex = new Stack.InvalidNameException(msg);

        assertEquals(msg, ex.getMessage());
    }


    /* TODO : Replace with the correct format
    @Test(expected = Stack.InvalidNameException.class)
    public void setVar_nullName_throwsInvalidNameException() {
        Stack s = new Stack();
        s.pushScope();
        s.setVar(null, 42, DataType.INT);
    }

    @Test(expected = Stack.InvalidNameException.class)
    public void setVar_emptyName_throwsInvalidNameException() {
        Stack s = new Stack();
        s.pushScope();
        s.setVar("", 42, DataType.INT);
    }
    */
}