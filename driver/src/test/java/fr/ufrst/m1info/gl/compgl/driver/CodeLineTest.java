package fr.ufrst.m1info.gl.compgl.driver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test void setCode(){
        assertEquals(codeLine.getCode(), "var x = 10;");
        codeLine.setCode("String hello = 'Hello';");
        assertEquals(codeLine.getCode(), "String hello = 'Hello';");
    }
}
