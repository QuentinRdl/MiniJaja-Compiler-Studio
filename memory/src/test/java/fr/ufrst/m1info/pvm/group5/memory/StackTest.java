package fr.ufrst.m1info.pvm.group5.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;

import java.lang.reflect.Field;
import java.util.Deque;


/**
 * Unit test for the Stack class
 */
class StackTest {
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
    void testConstructor() {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void pushScope() {
        stack.pushScope();
        assertTrue(stack.isEmpty());
        // Even though we pushed a scope, as there are nothing in the scope it should still be empty
    }

    @Test
    void popScope() throws Stack.NoScopeException {
        stack.pushScope();
        stack.popScope();
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPopScopeWhenNoScopes() {
        assertThrows(Stack.NoScopeException.class, () -> {
            stack.popScope();
        });
    }

    @Test
    void setVar() {
        stack.pushScope();
        assertEquals(0, stack.size());
        stack.setVar("x", 1234, DataType.INT);
        assertEquals(1, stack.size());
        assertEquals(1234, stack.getObjectValue("x"));
    }

    @Test
    void setConst() {
        stack.pushScope();
        assertEquals(0, stack.size());
        stack.setConst("x", 1234, DataType.INT);
        assertEquals(1, stack.size());
        assertEquals(1234, stack.getObjectValue("x"));
    }

    @Test
    void getObject() {
        stack.pushScope();
        StackObject constant = new StackObject("x", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        assertEquals(0, stack.size());
        stack.setConst("x", 1234, DataType.INT);
        assertEquals(1, stack.size());
        assertEquals(1234, stack.getObjectValue("x"));

        Object constant2 = stack.getObject("x");
        assertTrue(constant.equals(constant2));
    }

    @Test
    void isEqual() {
        StackObject constant = new StackObject("x", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        StackObject constantTrue = new StackObject("x", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        assertTrue(constant.equals(constantTrue));

        StackObject cstNotSameName = new StackObject("y", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        assertFalse(constant.equals(cstNotSameName));

        StackObject cstNotSameVal = new StackObject("x", 4321, 1, EntryKind.CONSTANT, DataType.INT);
        assertFalse(constant.equals(cstNotSameVal));

        StackObject cstNotSameScope = new StackObject("x", 1234, 2, EntryKind.CONSTANT, DataType.INT);
        assertFalse(constant.equals(cstNotSameScope));

        StackObject cstNotSameType = new StackObject("x", 1234, 1, EntryKind.VARIABLE, DataType.INT);
        assertFalse(constant.equals(cstNotSameType));

        StackObject cstNotSameKind = new StackObject("x", 1234, 1, EntryKind.CONSTANT, DataType.DOUBLE);
        assertFalse(constant.equals(cstNotSameKind));

        int notSameObject = 3;
        assertFalse(constant.equals(notSameObject));
    }

    @Test
    void hashCode_differsWhenFieldsDiffer() {
        StackObject base = new StackObject("x", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        StackObject diffName = new StackObject("y", 1234, 1, EntryKind.CONSTANT, DataType.INT);
        StackObject diffVal = new StackObject("x", 4321, 1, EntryKind.CONSTANT, DataType.INT);
        StackObject diffScope = new StackObject("x", 1234, 2, EntryKind.CONSTANT, DataType.INT);
        StackObject diffKind = new StackObject("x", 1234, 1, EntryKind.VARIABLE, DataType.INT);
        StackObject diffType = new StackObject("x", 1234, 1, EntryKind.CONSTANT, DataType.DOUBLE);

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
    void setVarMultipleVariables() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        stack.setVar("y", 2345, integ);
        stack.setVar("z", 3456, integ);
        assertEquals(3, stack.size());
    }

    @Test
    void testTop() {
        stack.pushScope();
        stack.setVar("a", 100, integ);
        StackObject top = stack.top();
        assertEquals("a", top.getName());
        assertEquals(100, top.getValue());
    }

    @Test
    void testTopWhenEmpty() {
        assertThrows(java.util.EmptyStackException.class, () -> {
            stack.top();
        });
    }

    @Test
    void testPop() throws Stack.StackIsEmptyException {
        stack.pushScope();
        stack.setVar("x", 50, integ);
        StackObject popped = stack.pop();
        assertEquals("x", popped.getName());
        assertEquals(50, popped.getValue());
        assertTrue(stack.isEmpty());
    }

    @Test
    //(expected = Stack.StackIsEmptyException.class)
    void popWhenEmpty() throws Stack.StackIsEmptyException {
        assertThrows(Stack.StackIsEmptyException.class, () -> {
            stack.pop();
        });
    }

    @Test
    void getVar() {
        stack.pushScope();
        stack.setVar("x", 1234, DataType.INT);
        Object value = stack.getObjectValue("x");
        assertEquals(1234, value);
    }

    @Test
    void getVarNotFound() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        Object value = stack.getObject("y");
        assertNull(value);
    }

    @Test
    void getVarDifferentScopes() {
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
    void updateVar() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        boolean upd = stack.updateVar("x", 100);
        assertTrue(upd);
        assertEquals(100, stack.getObjectValue("x"));
    }

    @Test
    void updateVarNotFound() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        boolean updated = stack.updateVar("y", 100);
        assertFalse(updated);
    }

    @Test
    void updateTopVar() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        boolean updated = stack.updateTopVar(99);
        assertTrue(updated);
        assertEquals(99, stack.top().getValue());
    }

    @Test
    void updateTopVarWhenEmpty() {
        boolean updated = stack.updateTopVar(50);
        assertFalse(updated);
    }

    @Test
    void hasObj() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);
        assertTrue(stack.hasObj("x"));
        assertFalse(stack.hasObj("y"));
    }

    @Test
    void hasObjDifferentScopes() {
        stack.pushScope();
        stack.setVar("x", 1234, integ);

        stack.pushScope();
        // x exists in previous scope but not in current
        assertFalse(stack.hasObj("x"));
    }

    @Test
    void testSize() {
        assertEquals(0, stack.size());
        stack.pushScope();
        stack.setVar("x", 10, integ);
        assertEquals(1, stack.size());
        stack.setVar("y", 20, integ);
        assertEquals(2, stack.size());
    }

    @Test
    void isEmpty() {
        assertTrue(stack.isEmpty());
        stack.pushScope();
        stack.setVar("x", 11234, integ);
        assertFalse(stack.isEmpty());
    }

    @Test
    void testClear() {
        stack.pushScope();
        stack.setVar("x", 10, integ);
        stack.setVar("y", 20, integ);
        assertEquals(2, stack.size());

        stack.clear();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void actualScenario() throws Stack.StackIsEmptyException, Stack.NoScopeException {
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
    void multipleScopesWithSameVarName() throws Stack.NoScopeException {
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
    void popRemovesOnlyCurrentScopeVars() throws Stack.NoScopeException {
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
    void emptyStack_toString() {
        Stack s = new Stack();
        assertEquals("Stack{scopeDepth=0, contents=[]}", s.toString());
    }

    @Test
    void nonEmptyStack_toString() throws Exception {
        Stack s = new Stack();

        // Construct Stack_Object instances (use constructor that accepts DataType)
        StackObject a = new StackObject("x", 1, 0, EntryKind.VARIABLE, DataType.INT);
        StackObject b = new StackObject("y", 2, 0, EntryKind.VARIABLE, DataType.INT);

        // Use reflection to access the private deque and add elements in insertion order
        Field f = Stack.class.getDeclaredField("stackContent");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        Deque<StackObject> dq = (Deque<StackObject>) f.get(s);

        dq.addLast(a);
        dq.addLast(b);

        String expected = "Stack{scopeDepth=0, contents=[x_0=1, y_0=2]}";
        assertEquals(expected, s.toString());
    }

    @Test
    void constructorExceptionTest() {
        String msg = "Invalid variable name";
        Stack.InvalidNameException ex = new Stack.InvalidNameException(msg);

        assertEquals(msg, ex.getMessage());
    }


    @Test
    void testSwap_sameIdentifier_noop_returnsTrue() {
        Stack s = new Stack();

        StackObject a = mock(StackObject.class);
        when(a.getName()).thenReturn("a");
        when(a.getScope()).thenReturn(0);

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            dq.addLast(a);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        boolean res = s.swap(a, a);
        assertTrue(res);
    }


    @Test
    void testSwap_successful() {
        Stack s = new Stack();

        StackObject a = mock(StackObject.class);
        StackObject b = mock(StackObject.class);
        StackObject c = mock(StackObject.class);

        when(a.getName()).thenReturn("a");
        when(b.getName()).thenReturn("b");
        when(c.getName()).thenReturn("c");

        when(a.getScope()).thenReturn(0);
        when(b.getScope()).thenReturn(0);
        when(c.getScope()).thenReturn(0);

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
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
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            Object[] arr = dq.toArray();
            assertEquals(b, arr[0]);
            assertEquals(a, arr[1]);
            assertEquals(c, arr[2]);
        } catch (Exception ex) {
            fail("Reflection verification failed: " + ex.getMessage());
        }
    }

    @Test
    void testSwap_missingObject_returnsFalse() {
        Stack s = new Stack();

        StackObject a = mock(StackObject.class);
        when(a.getName()).thenReturn("a");
        when(a.getScope()).thenReturn(0);

        StackObject b = mock(StackObject.class);
        when(b.getName()).thenReturn("b");
        when(b.getScope()).thenReturn(0);

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            dq.addLast(a);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        boolean res = s.swap(a, b);
        assertFalse(res);
    }

    @Test
    void swapTop_successful() {
        Stack s = new Stack();

        // Create mocked stack objects to avoid depending on other classes/constructors
        StackObject a = mock(StackObject.class);
        StackObject b = mock(StackObject.class);

        when(a.getName()).thenReturn("a");
        when(b.getName()).thenReturn("b");
        when(a.getScope()).thenReturn(0);
        when(b.getScope()).thenReturn(0);
        when(a.getValue()).thenReturn(1);
        when(b.getValue()).thenReturn(2);

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);

            dq.addLast(b);
            dq.addLast(a);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        // Check before swap func call
        assertEquals("b", s.top().getName());
        assertEquals(2, s.top().getValue());

        s.swap();

        StackObject top = s.top();
        assertEquals("a", top.getName());
        assertEquals(1, top.getValue());

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            Object[] arr = dq.toArray();
            assertEquals(a, arr[0]);
            assertEquals(b, arr[1]);
        } catch (Exception ex) {
            fail("Reflection verification failed: " + ex.getMessage());
        }
    }

    @Test
    void swapTop_notEnoughElements() {
        Stack s = new Stack();
        assertThrows(Memory.MemoryIllegalArgException.class, s::swap);

        // Calling w/ 1 element should throw exception
        StackObject only = mock(StackObject.class);
        when(only.getName()).thenReturn("only");
        when(only.getScope()).thenReturn(0);
        when(only.getValue()).thenReturn(10);
        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            dq.addLast(only);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        assertThrows(Memory.MemoryIllegalArgException.class, s::swap);
    }

    @Test
    void updateTopValue_emptyStack_returnsFalse() {
        Stack s = new Stack();
        boolean res = s.updateTopValue(42);
        assertFalse(res);
    }

    @Test
    void updateTopValue_variable_success() {
        stack.pushScope();
        stack.setVar("x", 1, DataType.INT);
        boolean res = stack.updateTopValue(2);
        assertTrue(res);
        assertEquals(2, stack.top().getValue());
    }

    @Test
    void updateTopValue_typeMismatch_returnsFalse() {
        stack.pushScope();
        stack.setVar("x", 1, DataType.INT);
        // Attempt to update with a Double while variable is INT
        boolean res = stack.updateTopValue(2.5);
        assertFalse(res);
        // value should remain unchanged
        assertEquals(1, stack.top().getValue());
    }

    @Test
    void updateTopValue_constantAlreadyAssigned_returnsFalse() {
        stack.pushScope();
        stack.setConst("c", 10, DataType.INT);
        // constant already has a value -> updateTopValue should refuse
        boolean res = stack.updateTopValue(20);
        assertFalse(res);
        assertEquals(10, stack.top().getValue());
    }

    @Test
    void updateTopValue_constantUninitialized_canInitialize() {
        stack.pushScope();
        // declare constant without initial value
        stack.setConst("c", null, DataType.INT);
        // now initialize it using updateTopValue
        boolean res = stack.updateTopValue(99);
        assertTrue(res);
        assertEquals(99, stack.top().getValue());
    }

    @Test
    void updateTopValue_constantUninitialized_typeMismatch_returnsFalse() {
        stack.pushScope();
        stack.setConst("c", null, DataType.INT);
        // attempt to initialize with wrong type
        boolean res = stack.updateTopValue(3.14);
        assertFalse(res);
        assertNull(stack.top().getValue());
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

    @Test
    void searchObject_returnsObjectWhenPresent() {
        Stack s = new Stack();

        StackObject a = mock(StackObject.class);
        StackObject b = mock(StackObject.class);
        StackObject c = mock(StackObject.class);

        when(a.getName()).thenReturn("a");
        when(b.getName()).thenReturn("b");
        when(c.getName()).thenReturn("c");

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            dq.addLast(a);
            dq.addLast(b);
            dq.addLast(c);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        StackObject found = s.searchObject("b");
        assertSame(b, found);
    }

    @Test
    void searchObject_notFound_returnsNull() {
        Stack s = new Stack();

        StackObject a = mock(StackObject.class);
        when(a.getName()).thenReturn("a");

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            dq.addLast(a);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        StackObject found = s.searchObject("missing");
        assertNull(found);
    }

    @Test
    void searchObject_multipleMatches_returnsFirstEncountered() {
        Stack s = new Stack();

        StackObject first = mock(StackObject.class);
        StackObject second = mock(StackObject.class);

        when(first.getName()).thenReturn("dup");
        when(second.getName()).thenReturn("dup");

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            dq.addLast(first);
            dq.addLast(second);
        } catch (Exception ex) {
            fail("Reflection setup failed: " + ex.getMessage());
        }

        StackObject found = s.searchObject("dup");
        assertSame(first, found);
    }

    @Test
    void removeObject_removesGivenObject() {
        Stack s = new Stack();

        StackObject a = mock(StackObject.class);
        StackObject b = mock(StackObject.class);
        StackObject c = mock(StackObject.class);

        when(a.getName()).thenReturn("a");
        when(b.getName()).thenReturn("b");
        when(c.getName()).thenReturn("c");

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            dq.addLast(a);
            dq.addLast(b);
            dq.addLast(c);

            java.lang.reflect.Method m = Stack.class.getDeclaredMethod("removeObject", StackObject.class);
            m.setAccessible(true);
            m.invoke(s, b);

            Object[] arr = dq.toArray();
            assertEquals(2, arr.length);
            assertEquals(a, arr[0]);
            assertEquals(c, arr[1]);
        } catch (Exception ex) {
            fail("Reflection invocation failed: " + ex.getMessage());
        }
    }

    @Test
    void removeObject_onlyRemovesSpecifiedInstanceWhenDuplicatesExist() {
        Stack s = new Stack();

        StackObject first = mock(StackObject.class);
        StackObject second = mock(StackObject.class);

        when(first.getName()).thenReturn("dup");
        when(second.getName()).thenReturn("dup");

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            dq.addLast(first);
            dq.addLast(second);

            java.lang.reflect.Method m = Stack.class.getDeclaredMethod("removeObject", StackObject.class);
            m.setAccessible(true);
            m.invoke(s, second);

            Object[] arr = dq.toArray();
            assertEquals(1, arr.length);
            assertEquals(first, arr[0]);
        } catch (Exception ex) {
            fail("Reflection invocation failed: " + ex.getMessage());
        }
    }

    @Test
    void removeObject_noopWhenObjectNotPresent() {
        Stack s = new Stack();

        StackObject a = mock(StackObject.class);
        StackObject notPresent = mock(StackObject.class);

        when(a.getName()).thenReturn("a");
        when(notPresent.getName()).thenReturn("x");

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            dq.addLast(a);

            java.lang.reflect.Method m = Stack.class.getDeclaredMethod("removeObject", StackObject.class);
            m.setAccessible(true);
            m.invoke(s, notPresent);

            // Stack should be unchanged
            Object[] arr = dq.toArray();
            assertEquals(1, arr.length);
            assertEquals(a, arr[0]);
        } catch (Exception ex) {
            fail("Reflection invocation failed: " + ex.getMessage());
        }
    }

    @Test
    void putOnTop_existing_movesToTop_andReturnsTrue() {
        Stack s = new Stack();

        StackObject a = mock(StackObject.class);
        StackObject b = mock(StackObject.class);
        StackObject c = mock(StackObject.class);

        when(a.getName()).thenReturn("a");
        when(b.getName()).thenReturn("b");
        when(c.getName()).thenReturn("c");

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            // Initial order : [a, b, c]
            dq.addLast(a);
            dq.addLast(b);
            dq.addLast(c);

            boolean res = s.putOnTop("b");
            assertTrue(res);

            Object[] arr = dq.toArray();
            // We want : [b, a, c]
            assertEquals(3, arr.length);
            assertEquals(b, arr[0]);
            assertEquals(a, arr[1]);
            assertEquals(c, arr[2]);
        } catch (Exception ex) {
            fail("Reflection/setup failed: " + ex.getMessage());
        }
    }

    @Test
    void putOnTop_notFound_returnsFalse_andLeavesStackUnchanged() {
        Stack s = new Stack();

        StackObject a = mock(StackObject.class);
        when(a.getName()).thenReturn("a");

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            dq.addLast(a);

            boolean res = s.putOnTop("missing");
            assertFalse(res);

            Object[] arr = dq.toArray();
            assertEquals(1, arr.length);
            assertEquals(a, arr[0]);
        } catch (Exception ex) {
            fail("Reflection/setup failed: " + ex.getMessage());
        }
    }

    @Test
    void putOnTop_withDuplicates_movesFirstEncountered() {
        Stack s = new Stack();

        StackObject other = mock(StackObject.class);
        StackObject firstDup = mock(StackObject.class);
        StackObject secondDup = mock(StackObject.class);

        when(other.getName()).thenReturn("x");
        when(firstDup.getName()).thenReturn("dup");
        when(secondDup.getName()).thenReturn("dup");

        try {
            Field f = Stack.class.getDeclaredField("stackContent");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Deque<StackObject> dq = (Deque<StackObject>) f.get(s);
            // Initial order : [x, firstDup, secondDup]
            dq.addLast(other);
            dq.addLast(firstDup);
            dq.addLast(secondDup);

            boolean res = s.putOnTop("dup");
            assertTrue(res);

            Object[] arr = dq.toArray();
            // We want : [firstDup, x, secondDup]
            assertEquals(3, arr.length);
            assertEquals(firstDup, arr[0]);
            assertEquals(other, arr[1]);
            assertEquals(secondDup, arr[2]);
        } catch (Exception ex) {
            fail("Reflection/setup failed: " + ex.getMessage());
        }
    }

    @Disabled
    @Test
    void validateType_nullValue_throwsIllegalArgumentException() throws Exception {
        Stack s = new Stack();
        java.lang.reflect.Method m = Stack.class.getDeclaredMethod("validateType", Object.class, fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType.class);
        m.setAccessible(true);

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                m.invoke(s, null, DataType.INT);
            } catch (java.lang.reflect.InvocationTargetException ite) {
                throw (RuntimeException) ite.getCause();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void validateType_validTypes_doNotThrow() throws Exception {
        Stack s = new Stack();
        java.lang.reflect.Method m = Stack.class.getDeclaredMethod("validateType", Object.class, DataType.class);
        m.setAccessible(true);

        // BOOL
        try {
            m.invoke(s, Boolean.TRUE, DataType.BOOL);
        } catch (java.lang.reflect.InvocationTargetException ite) {
            fail("validateType threw for BOOL: " + ite.getCause());
        }

        // INT
        try {
            m.invoke(s, Integer.valueOf(42), DataType.INT);
        } catch (java.lang.reflect.InvocationTargetException ite) {
            fail("validateType threw for INT: " + ite.getCause());
        }

        // FLOAT
        try {
            m.invoke(s, Float.valueOf(3.14f), DataType.FLOAT);
        } catch (java.lang.reflect.InvocationTargetException ite) {
            fail("validateType threw for FLOAT: " + ite.getCause());
        }

        // DOUBLE
        try {
            m.invoke(s, Double.valueOf(2.718), DataType.DOUBLE);
        } catch (java.lang.reflect.InvocationTargetException ite) {
            fail("validateType threw for DOUBLE: " + ite.getCause());
        }

        // STRING
        try {
            m.invoke(s, "hello", DataType.STRING);
        } catch (java.lang.reflect.InvocationTargetException ite) {
            fail("validateType threw for STRING: " + ite.getCause());
        }
    }

    @Disabled
    @Test
    void validateType_mismatchedType_throwsIllegalArgumentException() throws Exception {
        Stack s = new Stack();
        java.lang.reflect.Method m = Stack.class.getDeclaredMethod("validateType", Object.class, fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType.class);
        m.setAccessible(true);

        // BOOL expected, but provide Integer
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                m.invoke(s, Integer.valueOf(1), DataType.BOOL);
            } catch (java.lang.reflect.InvocationTargetException ite) {
                throw (RuntimeException) ite.getCause();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // INT expected, but provide Double
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                m.invoke(s, Double.valueOf(1.2), DataType.INT);
            } catch (java.lang.reflect.InvocationTargetException ite) {
                throw (RuntimeException) ite.getCause();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // DOUBLE expected, but provide Float
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                m.invoke(s, Float.valueOf(1.2f), DataType.DOUBLE);
            } catch (java.lang.reflect.InvocationTargetException ite) {
                throw (RuntimeException) ite.getCause();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Disabled
    @Test
    void validateType_voidOrUnknown_throwsIllegalArgumentException() throws Exception {
        Stack s = new Stack();
        java.lang.reflect.Method m = Stack.class.getDeclaredMethod("validateType", Object.class, fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType.class);
        m.setAccessible(true);

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                m.invoke(s, Integer.valueOf(5), DataType.VOID);
            } catch (java.lang.reflect.InvocationTargetException ite) {
                throw (RuntimeException) ite.getCause();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        assertThrows(IllegalArgumentException.class, () -> {
            try {
                m.invoke(s, "something", DataType.UNKNOWN);
            } catch (java.lang.reflect.InvocationTargetException ite) {
                throw (RuntimeException) ite.getCause();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void getDataTypeFromGenericObjectTest() {
        DataType res;
        // INT
        int integer = 2;
        res = stack.getDataTypeFromGenericObject(integer);
        assertEquals(DataType.INT, res);

        // BOOL
        boolean bouleEtBill = true;
        res = stack.getDataTypeFromGenericObject(bouleEtBill);
        assertEquals(DataType.BOOL, res);

        // STRING
        String str = "chaine";
        res = stack.getDataTypeFromGenericObject(str);
        assertEquals(DataType.STRING, res);

        // FLOAT
        float flottant = 1.2F;
        res = stack.getDataTypeFromGenericObject(flottant);
        assertEquals(DataType.FLOAT, res);

        // DOUBLE
        double deux = 1.2;
        res = stack.getDataTypeFromGenericObject(deux);
        assertEquals(DataType.DOUBLE, res);

        // UNKNOWN
        res = stack.getDataTypeFromGenericObject(null);
        assertEquals(DataType.UNKNOWN, res);
    }
}