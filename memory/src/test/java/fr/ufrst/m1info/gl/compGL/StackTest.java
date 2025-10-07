package fr.ufrst.m1info.gl.compGL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the Stack class
 */

public class StackTest {
    private Stack stack;

    /**
     * Before each test, we set up an empty stack
     */
    @BeforeEach
    public void setUp() {
        stack = new Stack();
    }

    /**
     * Checks that the push and top method work as intended
     */
    @Test
    public void pushAndTop() {
       stack.pushFrame("main", 1) ;
       Frame top = stack.top();

       assertEquals("main", top.getFuncName());
       assertEquals(1, top.getCallLine());
       assertEquals(1, stack.size());
    }


    /**
     * Checks that the push and pop method work as intended
     */
    @Test
    public void pushAndPop() {
        stack.pushFrame("main",1);
        stack.pushFrame("func",2);

        assertEquals(2, stack.size());

        Frame popped = stack.pop();
        assertEquals("func", popped.getFuncName());
        assertEquals(1, stack.size());

        popped = stack.pop();
        assertEquals("main", popped.getFuncName());
        assertEquals(0, stack.size());
    }

    /**
     * Checks that calling the pop method on an empty stacks throws the right exception
     */
    @Test
    public void popEmptyStackException() {
        assertThrows(EmptyStackException.class, () -> stack.pop());
    }

    /**
     * Checks that calling the top method on an empty stacks throws the right exception
     */
    @Test
    public void topEmptyStackException() {
        assertThrows(EmptyStackException.class, () -> stack.top());
    }

    /**
     * Checks that the set and get Variable methods are working as intended
     */
    @Test
    public void setAndGetVariable() {
       stack.pushFrame("main", 1) ;
       stack.setVariable("x", 1234);

       assertEquals(1234, stack.getVariable("x"));
    }

    /**
     * Checks that we can access variables across frames, that we are not limited to one on top of the stack
     * TODO : Should this be working ? Or like only for parent frame ?
     */
    @Test
    public void variableAccessAcrossFrames() {
        stack.pushFrame("main", 0);
        stack.setVariable("x", 1234);

        stack.pushFrame("function", 5);
        stack.setVariable("y", 4321);

        assertEquals(1234, stack.getVariable("x"));
        assertEquals(4321, stack.getVariable("y"));
    }

    /**
     * Checks that the updateVar method works as intended
     */
    @Test
    public void updateVar() {
        stack.pushFrame("main", 0);
        stack.setVariable("x", 1234);
        assertEquals(1234, stack.getVariable("x"));

        stack.pushFrame("func", 5);
        assertTrue(stack.updateVariable("x", 4321));

        assertEquals(4321, stack.getVariable("x"));
    }

    /**
     * When updating a var that does not exist, false should be returned
     */
    @Test
    public void updateVarNotFound() {
        stack.pushFrame("main", 1);
        stack.setVariable("x", 1234);
        assertTrue(stack.updateVariable("x", 4321));
        assertFalse(stack.updateVariable("nope", 42));
    }

    /**
     * Swapping the first and the second // TODO : Change the test to head and end of queue ?
     */
    @Test
    public void testSwap() {
        stack.pushFrame("firstFrame", 1);
        stack.pushFrame("secondFrame", 2);

        assertEquals("secondFrame", stack.top().getFuncName());

        stack.swap();

        assertEquals("firstFrame", stack.top().getFuncName());
    }


    /**
     * Checks that calling the swap method with 0 frame throws the right exception
     */
    @Test
    public void swapWith0Frame() {
        assertThrows(IllegalStateException.class, () -> stack.swap());
    }

    /**
     * Checks that calling the swap method with 0 frame throws the right exception
     */
    @Test
    public void swapWith1Frame() {
        stack.pushFrame("main", 1);
        assertThrows(IllegalStateException.class, () -> stack.swap());
    }

    /**
     * Checks that calling the swap method with >= 2 frame works as intended
     */
    @Test
    public void swapWith2Frame() {
        stack.pushFrame("main", 1);
        stack.pushFrame("second", 2);
        stack.swap(); // TODO : Is there a way to test that nothing is thrown ?
        // TODO : Actually check that they were swapped
    }

    /**
     * Checks that calling the swap method with >= 2 frame works as intended
     */
    /*
    @Test
    public void swapWithMoreThan2Frame()
    TODO : Implement
     */


    /**
     * Checks that the isEmpty method works as intended
     */
    @Test
    public void isEmpty() {
        assertTrue(stack.isEmpty());

        stack.pushFrame("1st", 1);
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());
    }

    /**
     * Checks that the size method works as intended
     */
    @Test
    public void testSize() {
        assertEquals(0, stack.size());

        stack.pushFrame("1st", 1);
        assertEquals(1, stack.size());

        stack.pushFrame("2nd", 2);
        assertEquals(2, stack.size());

        stack.pop();
        assertEquals(1, stack.size());
    }


    /**
     * Checks that the clear method works as intended
     */
    @Test
    public void testClear() {
        assertTrue(stack.isEmpty());
        stack.pushFrame("1st", 1);

        assertFalse(stack.isEmpty());
        stack.pushFrame("2nd", 2);

        assertFalse(stack.isEmpty());
        stack.pushFrame("3rd", 3);

        assertFalse(stack.isEmpty());
        assertEquals(3, stack.size());

        stack.clear();

        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }
}
