package fr.ufrst.m1info.pvm.group5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

import fr.ufrst.m1info.pvm.group5.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.SymbolTable.EntryKind;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


// TODO : Import the following w/ the pom
import fr.ufrst.m1info.pvm.group5.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.SymbolTable.SymbolTable;
import fr.ufrst.m1info.pvm.group5.SymbolTable.SymbolTableEntry;

public class MemoryTest {
    private Memory memory;

    @Mock
    private Stack stackMocked;

    @Mock
    private SymbolTable symbolTableMocked;

    private AutoCloseable mocksCloser; // To close mocks in @After

    /**
     * Before each test, we set up an empty memory
     */
    @BeforeEach
    public void setUp() {
        mocksCloser = MockitoAnnotations.openMocks(this);
        memory = new Memory();
        // We put the mocks inside the fields of the memory class
        memory.stack = stackMocked;
        memory.symbolTable = symbolTableMocked;
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (mocksCloser != null) mocksCloser.close();
    }

    // Test will fail right now, as it is TDD and the API is not implemented yet

    @Test
    public void constructor() {
         // Memory should have non-null fields (we'll replace them with mocks in setUp)
        assertNotNull(memory);
        assertNotNull(memory.stack);
        assertNotNull(memory.symbolTable);
    }

    @Test
    public void pushDelegatesToStackInt() {
        // When we push in the Stack we must delegate it to the setVar method
        memory.push("x", 42, DataType.INT, EntryKind.VARIABLE);
        verify(stackMocked, times(1)).setVar("x", 42, DataType.INT);
    }

    @Test
    public void pushDelegatesToStackCst() {
        // When we push in the Stack we must delegate it to the setVar method
        memory.push("x", 42, DataType.INT, EntryKind.CONSTANT);
        verify(stackMocked, times(1)).setConst("x", 42, DataType.INT);
    }


    @Test
    public void pushWithIllegalArg() {
        assertThrows(IllegalArgumentException.class, () -> {
            memory.push("x", 42, DataType.INT, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            memory.push("x", 42, null, EntryKind.VARIABLE);
        });
    }

    @Test
    public void popDelegatesToStack() throws Exception {
        when(stackMocked.pop()).thenReturn(new Stack_Object("x", 1, 0, EntryKind.VARIABLE, DataType.INT));
        memory.pop();
        verify(stackMocked, times(1)).pop();
    }

    @Test
    public void declVarAddsSymbolTableEntry() {
        memory.declVar("a", 123, DataType.INT);
        ArgumentCaptor<SymbolTableEntry> captor = ArgumentCaptor.forClass(SymbolTableEntry.class);
        verify(symbolTableMocked, times(1)).addEntry(captor.capture());

        SymbolTableEntry entry = captor.getValue();
        assertEquals("a", entry.getName());
        assertEquals(EntryKind.VARIABLE, entry.getKind());
        assertEquals(DataType.INT, entry.getDataType());
        assertNull(entry.getReference()); // Because the reference is not set
    }

    @Test
    public void declCstAddsSymbolTableEntry() {
        memory.declCst("PI", 3.14, DataType.DOUBLE);

        ArgumentCaptor<SymbolTableEntry> captor = ArgumentCaptor.forClass(SymbolTableEntry.class);
        verify(symbolTableMocked, times(1)).addEntry(captor.capture());

        SymbolTableEntry entry = captor.getValue();
        assertEquals("PI", entry.getName());
        assertEquals(EntryKind.CONSTANT, entry.getKind());
        assertEquals(DataType.DOUBLE, entry.getDataType());
        assertNull(entry.getReference()); // Because the ref is not set
    }


    @Test
    public void withdrawDeclRemovesEntry() {
        memory.withdrawDecl("tmp");
        verify(symbolTableMocked, times(1)).removeEntry("tmp");
    }


    @Test
    public void withdrawDeclThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            memory.withdrawDecl("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            memory.withdrawDecl(null);
        });
    }


    /**

    @Test
    public void affectValueUpdatesSymbolTableEntry() {
        SymbolTableEntry mockedEntry = mock(SymbolTableEntry.class);

        when(symbolTableMocked.lookup("v")).thenReturn(mockedEntry);
        memory.affectValue("v", 555);

        verify(symbolTableMocked, times(1)).lookup("v");
        verify(mockedEntry, times(1)).setReference(555);
    }

    @Test
    public void valReturnsReferenceFromSymbolTable() {
        SymbolTableEntry realEntry = new SymbolTableEntry("y", EntryKind.VARIABLE, DataType.INT);
        realEntry.setReference(777);
        when(symbolTableMocked.lookup("y")).thenReturn(realEntry);

        Object result = memory.val("y");

        assertEquals(777, result);
        verify(symbolTableMocked, times(1)).lookup("y");
    }
    */
}
