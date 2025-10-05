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

    @BeforeEach
    public void setUp() {
        stack = new Stack();
    }

    @Test
    public void pushAndTop() {
       stack.pushFrame("main", 1) ;
       Frame top = stack.top();

       assertEquals("main", top.getFuncName());
       assertEquals(1, top.getCallLine());
        assertEquals(1, stack.size());
    }

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

    @Test
    public void popEmptyStackException() {
        assertThrows(EmptyStackException.class, () -> stack.pop());
    }

    @Test
    public void topEmptyStackException() {
        assertThrows(EmptyStackException.class, () -> stack.top());
    }

    @Test
    public void setAndGetVariable() {
       stack.pushFrame("main", 1) ;
       stack.setVariable("x", 1234);

       assertEquals(1234, stack.getVariable("x"));
    }
}
