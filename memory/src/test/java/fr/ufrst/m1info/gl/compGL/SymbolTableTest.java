package fr.ufrst.m1info.gl.compGL;

import fr.ufrst.m1info.gl.compGL.TableSymbole.DataType;
import fr.ufrst.m1info.gl.compGL.TableSymbole.EntryKind;
import fr.ufrst.m1info.gl.compGL.TableSymbole.SymbolTable;
import fr.ufrst.m1info.gl.compGL.TableSymbole.SymbolTableEntry;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import  org.junit.jupiter.api.Test;
import  org.junit.jupiter.api.BeforeEach;

/**
 * Unit tests for {@link SymbolTable}.
 */
public class SymbolTableTest extends TestCase {

    private SymbolTable table;

    /**
     * Initializes a new SymbolTable before each test.
     */
    @BeforeEach
    protected void setUp() {
        table = new SymbolTable();
    }

    /**
     * Tests adding and looking up a symbol in the table.
     */
    @Test
    public void testAddAndLookupEntry() {
        SymbolTableEntry entry = new SymbolTableEntry("x", EntryKind.VARIABLE, DataType.INT);
        table.addEntry(entry);

        SymbolTableEntry result = table.lookup("x");
        assertNotNull(result);
        assertEquals("x", result.getName());
        assertEquals(EntryKind.VARIABLE, result.getKind());
        assertEquals(DataType.INT, result.getDataType());
    }

    /**
     * Tests removing an entry from the table.
     */
    @Test
    public void testRemoveEntry() {
        SymbolTableEntry entry = new SymbolTableEntry("y", EntryKind.CONSTANTE, DataType.FLOAT);
        table.addEntry(entry);
        assertTrue(table.contains("y"));

        table.removeEntry("y");
        assertFalse(table.contains("y"));
    }

    /**
     * Tests that adding a duplicate symbol throws an exception.
     */
    @Test
    public void testAddDuplicateSymbolThrowsException() {
        SymbolTableEntry entry = new SymbolTableEntry("z", EntryKind.VARIABLE, DataType.INT);
        table.addEntry(entry);

        try {
            table.addEntry(entry); // same name
            fail("Expected IllegalArgumentException for duplicate symbol");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Symbol already declared"));
        }
    }

    /**
     * Tests that looking up a non-existing symbol throws an exception.
     */
    @Test
    public void testLookupNonExistingSymbolThrowsException() {
        try {
            table.lookup("notFound");
            fail("Expected IllegalArgumentException for missing symbol");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Symbol not found"));
        }
    }
    /**
     * Tests removing a non-existing entry throws an exception.
     */
    @Test
    public void testRemoveNonExistingEntryThrowsException() {
        try {
            table.removeEntry("missing");
            fail("Expected IllegalArgumentException for missing symbol");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Symbol not found"));
        }
    }

    /**
     * Tests the contains() method for existing and non-existing entries.
     */
    @Test
    public void testContainsMethod() {
        SymbolTableEntry a = new SymbolTableEntry("a", EntryKind.VARIABLE, DataType.INT);
        table.addEntry(a);
        assertTrue(table.contains("a"));
        assertFalse(table.contains("b"));
    }

    /**
     * Tests the size() method.
     */
    @Test
    public void testSizeMethod() {
        assertEquals(0, table.size());
        table.addEntry(new SymbolTableEntry("a", EntryKind.VARIABLE, DataType.INT));
        table.addEntry(new SymbolTableEntry("b", EntryKind.VARIABLE, DataType.BOOL));
        assertEquals(2, table.size());
    }

    /**
     * Tests printTable() when the table is empty.
     */
    @Test
    public void testPrintTableEmpty() {
        PrintStream originalOut = System.out; // save original
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        try {
            // table is empty
            table.printTable();
            ps.flush(); // ensure all printed content is pushed to baos

            String output = baos.toString().trim();
            assertEquals("Symbol table is empty.", output);
        } finally {
            // restore original System.out even if assertion failed
            System.setOut(originalOut);
            ps.close();
        }
    }

    /**
     * Tests printTable() when the table contains entries.
     */
    @Test
    public void testPrintTableWithEntries() {
        SymbolTableEntry x = new SymbolTableEntry("x", EntryKind.VARIABLE, DataType.INT);
        SymbolTableEntry y = new SymbolTableEntry("y", EntryKind.CONSTANTE, DataType.FLOAT);
        table.addEntry(x);
        table.addEntry(y);

        PrintStream originalOut = System.out; // save original
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        try {
            table.printTable();
            ps.flush();

            String output = baos.toString().trim();
            // We don't assume order (HashMap), so we only check that both names appear
            assertTrue("output should contain x", output.contains("x"));
            assertTrue("output should contain y", output.contains("y"));
            assertTrue("output should contain SymbolTableEntry", output.contains("SymbolTableEntry"));
        } finally {
            System.setOut(originalOut);
            ps.close();
        }
    }
    /**
     * Tests getReference() and setReference() in SymbolTableEntry.
     */
    @Test
    public void testReferenceGetterSetter() {
        SymbolTableEntry entry = new SymbolTableEntry("a", EntryKind.VARIABLE, DataType.INT);

        // Initially null
        assertNull(entry.getReference());

        // Set a reference (simulating a memory address or value)
        entry.setReference(42);
        assertEquals(42, entry.getReference());
    }
}
