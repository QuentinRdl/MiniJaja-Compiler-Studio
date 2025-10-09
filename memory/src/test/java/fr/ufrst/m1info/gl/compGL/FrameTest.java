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


    /**
     * Before each test, we set up 2 basic frames
     */
    @BeforeEach
    public void setUp() {
       globalFrame = new Frame("global", 0, null);
       childFrame = new Frame("func", 10, globalFrame);
    }

    /**
     * Checks that the setVar and getVar method works as intended
     */
    @Test
    public void setAndGetVar() {
        globalFrame.setVar("x", 1234);
        assertEquals(1234, globalFrame.getVar("x"));
    }

    /**
     * Checks that the getVar method returns null when var doesn't exist
     */
    @Test
    public void getVarNullNotExist() {
        globalFrame.setVar("x", 1234);

        assertNotNull(globalFrame.getVar("x"));
        assertNull(globalFrame.getVar("y"));
    }


    /**
     * Checks that the getVar method will search the parent frame (if it exists)
     */
    @Test
    public void getVarSearchParentHasParent() {
       globalFrame.setVar("x", 1234);
       childFrame.setVar("y", 4321);

       // Child var must access its own var
       assertEquals(4321, childFrame.getVar("y"));

       // Child var can access their parent var
       assertEquals(1234, childFrame.getVar("x"));
    }

    /**
     * Checks that the getVar method will search the parent frame (does not exist)
     */
    @Test
    public void getVarSearchParentDoesntHaveParent() {
        globalFrame.setVar("x", 1234);
        Frame copyChildFrame = new Frame("func", 10, null);
        copyChildFrame.setVar("y", 4321);

        // Child var must access its own var
        assertEquals(4321, copyChildFrame.getVar("y"));

        // Child var cannot access variable that is not in their scope
        assertNull(copyChildFrame.getVar("x"));
    }

    /**
     * Checks that the hasLocalVar method works as intended
     */
    @Test
    public void hasLocalVar() {
        globalFrame.setVar("x", 1234);
        childFrame.setVar("y", 4321);

        assertTrue(globalFrame.hasLocalVar("x"));
        assertFalse(globalFrame.hasLocalVar("y"));
        assertFalse(childFrame.hasLocalVar("x"));
        assertTrue(childFrame.hasLocalVar("y"));
    }

    /**
     * Checks that the hasVar method works as intended
     */
    @Test
    public void hasVar() {
        globalFrame.setVar("x", 1234);
        childFrame.setVar("y", 4321);

        assertTrue(globalFrame.hasVar("x"));
        assertFalse(globalFrame.hasVar("y"));
        assertTrue(childFrame.hasVar("x"));
        assertTrue(childFrame.hasVar("y"));
    }

    /**
     * Checks that the variable shadowing works as intended
     * If a parent frame has an x value, and their children has another one w/ the same name
     * They need to be treated as 2 separate values
     */
    @Test
    public void varShadowing() {
        globalFrame.setVar("x", 1234);
        childFrame.setVar("x", 4321);

        assertEquals(1234, globalFrame.getVar( "x"));
        assertEquals(4321, childFrame.getVar( "x"));
    }

    /**
     * Checks that the updateVar method works as intended
     */
    @Test
    public void updateVarOK() {
        globalFrame.setVar("x", 1234);

        assertEquals(1234, globalFrame.getVar("x"));
        assertTrue(globalFrame.updateVar("x", 4321));
        assertEquals(4321, globalFrame.getVar("x"));
    }

    /**
     * Checks that the updateVar method fails as intended
     */
    @Test
    public void updateVarKO() {
        globalFrame.setVar("x", 1234);

        assertFalse(globalFrame.updateVar("y", 4321));
    }

    /**
     * Checks that the updateVar method works as intended
     * And can modify values of parent frame
     */
    @Test
    public void updateVarFromChildOK() {
        globalFrame.setVar("x", 1234);

        assertEquals(1234, globalFrame.getVar("x"));
        assertTrue(childFrame.updateVar("x", 4321));
        assertEquals(4321, childFrame.getVar("x"));
    }

    /**
     * Checks that the getParent method works as intended
     */
    @Test
    public void getParent() {
        assertNull(globalFrame.getParent());
        assertNotNull(childFrame.getParent());
        assertEquals(globalFrame, childFrame.getParent());
    }

    /**
     * Checks that the getFuncName method works as intended
     */
    @Test
    public void getFuncName() {
        assertEquals("global", globalFrame.getFuncName());
        assertEquals("func", childFrame.getFuncName());
    }


    /**
     * Checks that the getCallLine method works as intended
     */
    @Test
    public void getCallLine() {
        assertEquals(0, globalFrame.getCallLine());
        assertEquals(10, childFrame.getCallLine());
    }
}
