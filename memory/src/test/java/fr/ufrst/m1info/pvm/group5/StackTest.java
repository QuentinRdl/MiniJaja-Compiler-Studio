package fr.ufrst.m1info.pvm.group5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fr.ufrst.m1info.pvm.group5.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.SymbolTable.EntryKind;

import java.lang.reflect.Field;
import java.util.Deque;


/**
 * Unit test for the Stack class
 */
public class StackTest {
    private Stack stack;
    private DataType integ;

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
        stack.setVar("x", 1234, DataType.INT);
        assertEquals(1, stack.size());
        assertEquals(1234, stack.getObjectValue("x"));
    }

    @Test
    public void setConst() {
        stack.pushScope();
        assertEquals(0, stack.size());
        stack.setConst("x", 1234, DataType.INT);
        assertEquals(1, stack.size());
        assertEquals(1234, stack.getObjectValue("x"));
    }

    @Test
    public void getObject() {
        stack.pushScope();
        Stack_Object constant = new Stack_Object("x", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        assertEquals(0, stack.size());
        stack.setConst("x", 1234, DataType.INT);
        assertEquals(1, stack.size());
        assertEquals(1234, stack.getObjectValue("x"));

        Object constant2 = stack.getObject("x");
        assertTrue(constant.equals(constant2));
    }

    @Test
    public void isEqual() {
        Stack_Object constant = new Stack_Object("x", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        Stack_Object constantTrue = new Stack_Object("x", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        assertTrue(constant.equals(constantTrue));

        Stack_Object cstNotSameName = new Stack_Object("y", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        assertFalse(constant.equals(cstNotSameName));

        Stack_Object cstNotSameVal = new Stack_Object("x", 4321, 1, EntryKind.CONSTANT, DataType.INT);
        assertFalse(constant.equals(cstNotSameVal));

        Stack_Object cstNotSameScope = new Stack_Object("x", 1234, 2, EntryKind.CONSTANT, DataType.INT);
        assertFalse(constant.equals(cstNotSameScope));

        Stack_Object cstNotSameType = new Stack_Object("x", 1234, 1, EntryKind.VARIABLE, DataType.INT);
        assertFalse(constant.equals(cstNotSameType));

        Stack_Object cstNotSameKind = new Stack_Object("x", 1234, 1, EntryKind.CONSTANT, DataType.DOUBLE);
        assertFalse(constant.equals(cstNotSameKind));

        int notSameObject = 3;
        assertFalse(constant.equals(notSameObject));
    }

    @Test
    public void hashCode_differsWhenFieldsDiffer() {
        Stack_Object base = new Stack_Object("x", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        Stack_Object diffName = new Stack_Object("y", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        Stack_Object diffVal = new Stack_Object("x", 4321, 1, EntryKind.CONSTANT, DataType.INT);
        Stack_Object diffScope = new Stack_Object("x", 1234, 2, EntryKind.CONSTANT, DataType.INT);
        Stack_Object diffKind = new Stack_Object("x", 1234, 1, EntryKind.VARIABLE, DataType.INT);
        Stack_Object diffType = new Stack_Object("x", 1234, 1, EntryKind.CONSTANT, DataType.DOUBLE);

        // Ensure objects are not equal
        assertNotEquals(base, diffName);
        assertNotEquals(base, diffVal);
        assertNotEquals(base, diffScope);
        assertNotEquals(base, diffKind);
        assertNotEquals(base, diffType);

        // It's extremely unlikely these different objects will have colliding hash codes
        assertNotEquals(base.hashCode(), diffName.hashCode());
        assertNotEquals(base.hashCode(), diffVal.hashCode());
        assertNotEquals(base.hashCode(), diffScope.hashCode());
        assertNotEquals(base.hashCode(), diffKind.hashCode());
        assertNotEquals(base.hashCode(), diffType.hashCode());
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
        stack.setVar("x", 1234, DataType.INT);
        Object value = stack.getObjectValue("x");
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
        assertEquals(2, stack.getObjectValue("x"));

        try {
            stack.popScope();
        } catch (Stack.NoScopeException e) {
            fail("Should not throw exception");
        }
        // Now should get the x from previous scope (1)
        assertEquals(1, stack.getObjectValue("x"));
    }

    @Test
    public void updateVar() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        boolean upd = stack.updateVar("x", 100);
        assertTrue(upd);
        assertEquals(100, stack.getObjectValue("x"));
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

        assertEquals(50, stack.getObjectValue("x"));
        assertEquals(100, stack.getObjectValue("z"));
        assertNull(stack.getObject("y"));
        assertEquals(4, stack.size());

        // Update x in current scope
        stack.updateVar("x", 55);
        assertEquals(55, stack.getObjectValue("x"));

        // Exit function scope
        stack.popScope();
        assertEquals(5, stack.getObjectValue("x"));
        assertEquals(10, stack.getObjectValue("y"));
        assertNull(stack.getObjectValue("z"));
        assertNull(stack.getObject("z"));
        assertEquals(2, stack.size());
    }

    @Test
    public void multipleScopesWithSameVarName() throws Stack.NoScopeException {
        stack.pushScope();
        stack.setVar("count", 0, integ);
        assertEquals(0, stack.getObjectValue("count"));

        stack.pushScope();
        stack.setVar("count", 1, integ);
        assertEquals(1, stack.getObjectValue("count"));

        stack.pushScope();
        stack.setVar("count", 2, integ);
        assertEquals(2, stack.getObjectValue("count"));

        stack.popScope();
        assertEquals(1, stack.getObjectValue("count"));

        stack.popScope();
        assertEquals(0, stack.getObjectValue("count"));

        stack.popScope();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void popRemovesOnlyCurrentScopeVars() throws Stack.NoScopeException {
        stack.pushScope();
        stack.setVar("global", 1, DataType.INT);

        stack.pushScope();
        stack.setVar("local1", 2, DataType.INT);
        stack.setVar("local2", 3, DataType.INT);

        assertEquals(3, stack.size());

        stack.popScope();

        // Only the global variable should remain
        assertEquals(1, stack.size());
        assertEquals(1, stack.getObjectValue("global"));
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
    public void constructorExceptionTest() {
        String msg = "Invalid variable name";
        Stack.InvalidNameException ex = new Stack.InvalidNameException(msg);

        assertEquals(msg, ex.getMessage());
    }


    @Test
    public void testSwap_sameIdentifier_noop_returnsTrue() {
        Stack s = new Stack();

        Stack_Object a = mock(Stack_Object.class);
        when(a.getName()).thenReturn("a");
        when(a.getScope()).thenReturn(0);

        try {
            Field f = Stack.class.getDeclaredField("stack_content");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<Stack_Object> dq = (Deque<Stack_Object>) f.get(s);
            dq.addLast(a);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        boolean res = s.swap(a, a);
        assertTrue(res);
    }


    @Test
    public void testSwap_successful() {
        Stack s = new Stack();

        Stack_Object a = mock(Stack_Object.class);
        Stack_Object b = mock(Stack_Object.class);
        Stack_Object c = mock(Stack_Object.class);

        when(a.getName()).thenReturn("a");
        when(b.getName()).thenReturn("b");
        when(c.getName()).thenReturn("c");

        when(a.getScope()).thenReturn(0);
        when(b.getScope()).thenReturn(0);
        when(c.getScope()).thenReturn(0);

        try {
            Field f = Stack.class.getDeclaredField("stack_content");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<Stack_Object> dq = (Deque<Stack_Object>) f.get(s);
            dq.addLast(a);
            dq.addLast(b);
            dq.addLast(c);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        boolean res = s.swap(a, b);
        assertTrue(res);

        // Verify order is now [b, a, c]
        try {
            Field f = Stack.class.getDeclaredField("stack_content");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<Stack_Object> dq = (Deque<Stack_Object>) f.get(s);
            Object[] arr = dq.toArray();
            assertEquals(b, arr[0]);
            assertEquals(a, arr[1]);
            assertEquals(c, arr[2]);
        } catch (Exception ex) {
            fail("Reflection verification failed: " + ex.getMessage());
        }
    }

    @Test
    public void testSwap_missingObject_returnsFalse() {
        Stack s = new Stack();

        Stack_Object a = mock(Stack_Object.class);
        when(a.getName()).thenReturn("a");
        when(a.getScope()).thenReturn(0);

        Stack_Object b = mock(Stack_Object.class);
        when(b.getName()).thenReturn("b");
        when(b.getScope()).thenReturn(0);

        try {
            Field f = Stack.class.getDeclaredField("stack_content");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<Stack_Object> dq = (Deque<Stack_Object>) f.get(s);
            dq.addLast(a);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        boolean res = s.swap(a, b);
        assertFalse(res);
    }

    @Test
    public void swapTop_successful() {
        Stack s = new Stack();

        // Create mocked stack objects to avoid depending on other classes/constructors
        Stack_Object a = mock(Stack_Object.class);
        Stack_Object b = mock(Stack_Object.class);

        when(a.getName()).thenReturn("a");
        when(b.getName()).thenReturn("b");
        when(a.getScope()).thenReturn(0);
        when(b.getScope()).thenReturn(0);
        when(a.getValue()).thenReturn(1);
        when(b.getValue()).thenReturn(2);

        try {
            Field f = Stack.class.getDeclaredField("stack_content");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<Stack_Object> dq = (Deque<Stack_Object>) f.get(s);

            dq.addLast(b);
            dq.addLast(a);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        // Check before swap func call
        assertEquals("b", s.top().getName());
        assertEquals(2, s.top().getValue());

        s.swap();

        Stack_Object top = s.top();
        assertEquals("a", top.getName());
        assertEquals(1, top.getValue());

        try {
            Field f = Stack.class.getDeclaredField("stack_content");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<Stack_Object> dq = (Deque<Stack_Object>) f.get(s);
            Object[] arr = dq.toArray();
            assertEquals(a, arr[0]);
            assertEquals(b, arr[1]);
        } catch (Exception ex) {
            fail("Reflection verification failed: " + ex.getMessage());
        }
    }

    @Test
    public void swapTop_notEnoughElements() {
        Stack s = new Stack();
        assertThrows(IllegalStateException.class, s::swap);

        // Calling w/ 1 element should throw exception
        Stack_Object only = mock(Stack_Object.class);
        when(only.getName()).thenReturn("only");
        when(only.getScope()).thenReturn(0);
        when(only.getValue()).thenReturn(10);
        try {
            Field f = Stack.class.getDeclaredField("stack_content");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<Stack_Object> dq = (Deque<Stack_Object>) f.get(s);
            dq.addLast(only);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        assertThrows(IllegalStateException.class, s::swap);
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