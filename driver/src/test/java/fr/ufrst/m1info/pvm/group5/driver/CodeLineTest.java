package fr.ufrst.m1info.pvm.group5.driver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CodeLine class
 */
class CodeLineTest {
    private CodeLine codeLine;
    private int lineNumber = 0;

    @BeforeEach
    void setup(){
        codeLine = new CodeLine(++lineNumber, "var x = 10;");
    }

    @Test
    void getLineNumber(){
        assertEquals(1, codeLine.getLineNumber());
    }

    @Test
    void getCode(){
        assertEquals("var x = 10;", codeLine.getCode());
    }

    @Test
    void testIsBreakpointWithNoBreakpoint(){
        assertFalse(codeLine.isBreakpoint());
    }

    @Test
    void testIsBreakpointWithBreakpoint(){
        assertFalse(codeLine.isBreakpoint());
        codeLine.setBreakpoint(true);
        assertTrue(codeLine.isBreakpoint());
    }

    @Test
    void setCode(){
        assertEquals("var x = 10;", codeLine.getCode());
        codeLine.setCode("String hello = 'Hello';");
        assertEquals("String hello = 'Hello';", codeLine.getCode());
    }

    @Test
    void setLineNumber(){
        assertEquals(1, codeLine.getLineNumber());
        codeLine.setLineNumber(2);
        assertEquals(2, codeLine.getLineNumber());
    }
}
