package fr.ufrst.m1info.pvm.group5.driver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CodeLine class
 */
public class CodeLineTest {
    private CodeLine codeLine;
    private int lineNumber = 0;

    @BeforeEach
    public void setup(){
        codeLine = new CodeLine(++lineNumber, "var x = 10;");
    }

    @Test
    public void getLineNumber(){
        assertEquals(codeLine.getLineNumber(), 1);
    }

    @Test
    public void getCode(){
        assertEquals(codeLine.getCode(), "var x = 10;");
    }

    @Test
    public void testIsBreakpointWithNoBreakpoint(){
        assertFalse(codeLine.isBreakpoint());
    }

    @Test
    public void testIsBreakpointWithBreakpoint(){
        assertFalse(codeLine.isBreakpoint());
        codeLine.setBreakpoint(true);
        assertTrue(codeLine.isBreakpoint());
    }

    @Test
    public void setCode(){
        assertEquals(codeLine.getCode(), "var x = 10;");
        codeLine.setCode("String hello = 'Hello';");
        assertEquals(codeLine.getCode(), "String hello = 'Hello';");
    }

    @Test
    public void setLineNumber(){
        assertEquals(1, codeLine.getLineNumber());
        codeLine.setLineNumber(2);
        assertEquals(2, codeLine.getLineNumber());
    }
}
