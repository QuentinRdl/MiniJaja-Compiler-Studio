package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.SymbolTable.SymbolTable;
import fr.ufrst.m1info.pvm.group5.SymbolTable.SymbolTableEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for {@link SymbolTable}.
 */
public class SymbolTableTest {
    private SymbolTable globalTable;

    /**
     * Initializes a new global SymbolTable before each test.
     */
    @BeforeEach
    protected void setUp() {
        globalTable = new SymbolTable();
    }

    /**
     * Tests adding and looking up a symbol in the global scope.
     */
    @Test
    public void testAddAndLookupInGlobalScope() {
        SymbolTableEntry entry = new SymbolTableEntry("x", EntryKind.VARIABLE, DataType.INT);
        globalTable.addEntry(entry);

        SymbolTableEntry result = globalTable.lookup("x");
        assertNotNull(result);
        assertEquals("x", result.getName());
        assertEquals(EntryKind.VARIABLE, result.getKind());
        assertEquals(DataType.INT, result.getDataType());
    }


    /**
     * Tests that adding a duplicate symbol in the same scope throws an exception.
     */
    @Test
    public void testAddDuplicateSymbolInSameScopeThrowsException() {
        SymbolTableEntry entry = new SymbolTableEntry("x", EntryKind.VARIABLE, DataType.INT);
        globalTable.addEntry(entry);

        try {
            globalTable.addEntry(entry);
            fail("Expected IllegalArgumentException for duplicate symbol in the same scope");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Symbol already declared"));
        }
    }

    /**
     * Tests creating a child scope and adding a new symbol to it.
     */
    @Test
    public void testCreateChildScopeAndAddSymbol() {
        SymbolTable childScope = globalTable.createChildScope();

        SymbolTableEntry localVar = new SymbolTableEntry("y", EntryKind.VARIABLE, DataType.BOOL);
        childScope.addEntry(localVar);

        assertNotNull(childScope.lookup("y"));
        assertEquals(DataType.BOOL, childScope.lookup("y").getDataType());
    }

    /**
     * Tests looking up a symbol from a parent scope (global variable visible in child).
     */
    @Test
    public void testLookupSymbolFromParentScope() {
        globalTable.addEntry(new SymbolTableEntry("a", EntryKind.VARIABLE, DataType.INT));
        SymbolTable childScope = globalTable.createChildScope();

        // "a" should be visible in the child scope via parent lookup
        SymbolTableEntry found = childScope.lookup("a");
        assertNotNull(found);
        assertEquals("a", found.getName());
    }

    /**
     * Tests that removing a symbol affects only the current scope.
     */
    @Test
    public void testRemoveEntryAffectsOnlyCurrentScope() {
        globalTable.addEntry(new SymbolTableEntry("g", EntryKind.VARIABLE, DataType.INT));
        SymbolTable childScope = globalTable.createChildScope();

        SymbolTableEntry local = new SymbolTableEntry("l", EntryKind.VARIABLE, DataType.BOOL);
        childScope.addEntry(local);

        // Remove only from child
        childScope.removeEntry("l");
        assertFalse(childScope.contains("l"));

        // Parent still has its symbol
        assertTrue(globalTable.contains("g"));
    }

    /**
     * Tests that removing a non-existing symbol in a scope throws an exception.
     */
    @Test
    public void testRemoveNonExistingSymbolThrowsException() {
        try {
            globalTable.removeEntry("missing");
            fail("Expected IllegalArgumentException for missing symbol");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Symbol not found"));
        }
    }

    /**
     * Tests lookup of a non-existing symbol across all scopes.
     */
    @Test
    public void testLookupNonExistingSymbolInAllScopesThrowsException() {
        SymbolTable childScope = globalTable.createChildScope();
        try {
            childScope.lookup("ghost");
            fail("Expected IllegalArgumentException for missing symbol");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Symbol not found"));
        }
    }

    /**
     * Tests that contains() only checks the current scope, not the parent.
     */
    @Test
    public void testContainsIsLocalOnly() {
        globalTable.addEntry(new SymbolTableEntry("x", EntryKind.VARIABLE, DataType.INT));
        SymbolTable childScope = globalTable.createChildScope();

        assertFalse(childScope.contains("x")); // present in parent, not local
        assertTrue(globalTable.contains("x"));
    }

    /**
     * Tests the size() and getScopeLevel() methods.
     */
    @Test
    public void testSizeAndScopeLevel() {
        assertEquals(0, globalTable.size());
        assertEquals(0, globalTable.getScopeLevel());

        SymbolTable child = globalTable.createChildScope();
        assertEquals(1, child.getScopeLevel());

        child.addEntry(new SymbolTableEntry("t", EntryKind.CONSTANT, DataType.FLOAT));
        assertEquals(1, child.size());
    }

    /**
     * Tests toString() output when the table contains nested scopes.
     */
    @Test
    public void testToStringWithNestedScopes() {
        globalTable.addEntry(new SymbolTableEntry("x", EntryKind.VARIABLE, DataType.INT));
        SymbolTable child = globalTable.createChildScope();
        child.addEntry(new SymbolTableEntry("y", EntryKind.VARIABLE, DataType.BOOL));

        String output = child.toString();

        assertTrue(output.contains("---- Scope Level 1 ----"));
        assertTrue(output.contains("---- Scope Level 0 ----"));
        assertTrue(output.contains("x"));
        assertTrue(output.contains("y"));
    }

    /**
     * Tests toString() output when the scope is empty.
     */
    @Test
    public void testToStringWhenEmpty() {
        String output = globalTable.toString();

        assertTrue(output.contains("---- Scope Level 0 ----"));
        assertTrue(output.contains("No symbols in this scope."));
    }

    /**
     * Tests getReference() and setReference() in SymbolTableEntry.
     */
    @Test
    public void testReferenceGetterSetter() {
        SymbolTableEntry entry = new SymbolTableEntry("r", EntryKind.VARIABLE, DataType.INT);
        assertNull(entry.getReference());

        entry.setReference(99);
        assertEquals(99, entry.getReference());
    }
    /**
     * Tests getParentScope() to ensure the parent link is correctly assigned.
     */
    @Test
    public void testGetParentScope() {
        SymbolTable child = globalTable.createChildScope();
        assertSame(globalTable, child.getParentScope());
    }
    /**
     * Tests getScopeLevel() returns correct values for nested scopes.
     */
    @Test
    public void testGetScopeLevel() {
        // Global scope should have level 0
        assertEquals(0, globalTable.getScopeLevel());

        // Create child and grandchild scopes
        SymbolTable child = globalTable.createChildScope();
        SymbolTable grandChild = child.createChildScope();

        // Verify levels
        assertEquals(1, child.getScopeLevel());
        assertEquals(2, grandChild.getScopeLevel());
    }

}

