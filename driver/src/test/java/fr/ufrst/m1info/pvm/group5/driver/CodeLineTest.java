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
        assertEquals(codeLine.getLineNumber(), 1);
    }

    @Test
    void getCode(){
        assertEquals(codeLine.getCode(), "var x = 10;");
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
        assertEquals(codeLine.getCode(), "var x = 10;");
        codeLine.setCode("String hello = 'Hello';");
        assertEquals(codeLine.getCode(), "String hello = 'Hello';");
    }

    @Test
    void setLineNumber(){
        assertEquals(1, codeLine.getLineNumber());
        codeLine.setLineNumber(2);
        assertEquals(2, codeLine.getLineNumber());
    }
}
