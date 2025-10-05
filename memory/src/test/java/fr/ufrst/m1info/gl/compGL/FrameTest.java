package fr.ufrst.m1info.gl.compGL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the Frame class
 */
public class FrameTest {
    private Frame globalFrame;
    private Frame childFrame;

    @BeforeEach
    public void setUp() {
       globalFrame = new Frame("global", 0, null);
       childFrame = new Frame("func", 10, globalFrame);
    }

    @Test
    public void setAndGetVar() {
        globalFrame.setVariable("x", 1234);
        assertEquals(1234, globalFrame.getVariable("x"));
    }

    @Test
    public void getVarNullNotExist() {
        globalFrame.setVariable("x", 1234);

        assertNotNull(globalFrame.getVariable("x"));
        assertNull(globalFrame.getVariable("y"));
    }

    @Test
    public void getVarSearchParent() {
       globalFrame.setVariable("x", 1234);
       childFrame.setVariable("y", 4321);

       // Child var must access its own var
       assertEquals(4321, childFrame.getVariable("y"));

       // Child var can access their parent var
       assertEquals(1234, childFrame.getVariable("x"));
    }

    @Test
    public void hasVar() {
        globalFrame.setVariable("x", 1234);
        childFrame.setVariable("y", 4321);

        assertTrue(globalFrame.hasLocalVariable("x"));
        assertFalse(globalFrame.hasLocalVariable("y"));
        assertFalse(childFrame.hasLocalVariable("x"));
        assertTrue(childFrame.hasLocalVariable("y"));
    }

    @Test
    public void hasLocalVar() {
        globalFrame.setVariable("x", 1234);
        childFrame.setVariable("y", 4321);

        assertTrue(globalFrame.hasVariable("x"));
        assertFalse(globalFrame.hasVariable("y"));
        assertTrue(childFrame.hasVariable("x"));
        assertTrue(childFrame.hasVariable("y"));
    }

    @Test
    public void varShadowing() {
        globalFrame.setVariable("x", 1234);
        childFrame.setVariable("x", 4321);

        assertEquals(1234, globalFrame.getVariable( "x"));
        assertEquals(4321, childFrame.getVariable( "x"));
    }

    @Test
    public void updateVarOK() {
        globalFrame.setVariable("x", 1234);

        assertEquals(1234, globalFrame.getVariable("x"));
        assertTrue(globalFrame.updateVariable("x", 4321));
        assertEquals(4321, globalFrame.getVariable("x"));
    }

    @Test
    public void updateVarKO() {
        globalFrame.setVariable("x", 1234);

        assertFalse(globalFrame.updateVariable("y", 4321));
    }

    @Test
    public void updateVarFromChildOK() {
        globalFrame.setVariable("x", 1234);

        assertEquals(1234, globalFrame.getVariable("x"));
        assertTrue(childFrame.updateVariable("x", 4321));
        assertEquals(4321, childFrame.getVariable("x"));
    }

    @Test
    public void getParent() {
        assertNull(globalFrame.getParent());
        assertNotNull(childFrame.getParent());
        assertEquals(globalFrame, childFrame.getParent());
    }

    @Test
    public void getFuncName() {
        assertEquals("global", globalFrame.getFuncName());
        assertEquals("func", childFrame.getFuncName());
    }

    @Test
    public void getCallLine() {
        assertEquals(0, globalFrame.getCallLine());
        assertEquals(10, childFrame.getCallLine());
    }

}
