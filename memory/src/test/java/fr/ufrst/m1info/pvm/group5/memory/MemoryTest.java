package fr.ufrst.m1info.pvm.group5.memory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


// TODO : Import the following w/ the pom
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.SymbolTable;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.SymbolTableEntry;
import fr.ufrst.m1info.pvm.group5.memory.heap.Heap;

class MemoryTest {
    private Memory memory;

    @Mock
    private Stack stackMocked;

    @Mock
    private SymbolTable symbolTableMocked;

    @Mock
    private Heap heapMocked;

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
        memory.setHeap(heapMocked);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (mocksCloser != null) mocksCloser.close();
    }

    // Test will fail right now, as it is TDD and the API is not implemented yet

    @Test
    void constructor() {
         // Memory should have non-null fields (we'll replace them with mocks in setUp)
        assertNotNull(memory);
        assertNotNull(memory.stack);
        assertNotNull(memory.symbolTable);
    }

    @Test
    void pushDelegatesToStackInt() {
        // When we push in the Stack we must delegate it to the setVar method
        memory.push("x", 42, DataType.INT, EntryKind.VARIABLE);
        verify(stackMocked, times(1)).setVar("x", 42, DataType.INT);
    }

    @Test
    void pushDelegatesToStackCst() {
        // When we push in the Stack we must delegate it to the setVar method
        memory.push("x", 42, DataType.INT, EntryKind.CONSTANT);
        verify(stackMocked, times(1)).setConst("x", 42, DataType.INT);
    }


    @Test
    void pushWithIllegalArg() {
        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            memory.push("x", 42, DataType.INT, null);
        });

        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            memory.push("x", 42, null, EntryKind.VARIABLE);
        });
    }

    @Test
    void popDelegatesToStack() throws Exception {
        when(stackMocked.pop()).thenReturn(new StackObject("x", 1, 0, EntryKind.VARIABLE, DataType.INT));
        memory.pop();
        verify(stackMocked, times(1)).pop();
    }

    @Test
    void popDelegatesToSymbolTable() throws Exception {
        StackObject obj = new StackObject("x", 1, 0, EntryKind.VARIABLE, DataType.INT);
        when(stackMocked.pop()).thenReturn(obj);

        memory.pop();

        verify(symbolTableMocked, times(1)).removeEntry("x");
    }

    @Test
    void declVarAddsSymbolTableEntry() {
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
    void declCstAddsSymbolTableEntry() {
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
    void withdrawDeclRemovesEntry() {
        memory.withdrawDecl("tmp");
        verify(symbolTableMocked, times(1)).removeEntry("tmp");
    }


    @Test
    void withdrawDeclThrowsException() {
        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            memory.withdrawDecl("");
        });

        assertThrows(Memory.MemoryIllegalArgException.class, () -> {
            memory.withdrawDecl(null);
        });
    }



    /**
    @Test
    void affectValueUpdatesSymbolTableEntry() {
        SymbolTableEntry mockedEntry = mock(SymbolTableEntry.class);

        when(symbolTableMocked.lookup("v")).thenReturn(mockedEntry);
        memory.affectValue("v", 555);

        verify(symbolTableMocked, times(1)).lookup("v");
        verify(mockedEntry, times(1)).setReference(555);
    }

    @Test
    void valReturnsReferenceFromSymbolTable() {
        SymbolTableEntry realEntry = new SymbolTableEntry("y", EntryKind.VARIABLE, DataType.INT);
        realEntry.setReference(777);
        when(symbolTableMocked.lookup("y")).thenReturn(realEntry);

        Object result = memory.val("y");

        assertEquals(777, result);
        verify(symbolTableMocked, times(1)).lookup("y");
    }
    */

    @Test
    void swapDelegatesToStack() {
        // Call swap on memory and verify it calls the stack swap method
        memory.swap();
        verify(stackMocked, times(1)).swap();
    }

    @Test
    void swapPropagatesException() {
        // Calling swap should throw error
        doThrow(new RuntimeException("swap error")).when(stackMocked).swap();
        assertThrows(RuntimeException.class, () -> memory.swap());
        verify(stackMocked, times(1)).swap();
    }

    @Test
    void declMethodAddsSymbolTableEntry() {
        // On déclare une méthode "foo" avec type de retour INT et params null (ASTNode par ex)
        memory.declMethod("foo", DataType.INT, null);

        ArgumentCaptor<SymbolTableEntry> captor = ArgumentCaptor.forClass(SymbolTableEntry.class);
        verify(symbolTableMocked, times(1)).addEntry(captor.capture());

        SymbolTableEntry entry = captor.getValue();
        assertEquals("foo", entry.getName());
        assertEquals(EntryKind.METHOD, entry.getKind());
        assertEquals(DataType.INT, entry.getDataType());
        assertNull(entry.getReference());
    }

    @Test
    void getMethodReturnsMethodEntry() {
        SymbolTableEntry methodEntry = mock(SymbolTableEntry.class);
        when(methodEntry.getKind()).thenReturn(EntryKind.METHOD);
        when(symbolTableMocked.lookup("foo")).thenReturn(methodEntry);

        SymbolTableEntry result = memory.getMethod("foo");

        assertEquals(methodEntry, result);
        verify(symbolTableMocked, times(1)).lookup("foo");
    }

    @Test
    void withdrawMethodRemovesMethodEntryFromSymbolTable() {
        // Arrange
        SymbolTableEntry methodEntry = mock(SymbolTableEntry.class);
        when(methodEntry.getKind()).thenReturn(EntryKind.METHOD);
        when(symbolTableMocked.lookup("foo")).thenReturn(methodEntry);

        // Act
        memory.withdrawMethod("foo");

        // Assert
        verify(symbolTableMocked, times(1)).lookup("foo");
        verify(symbolTableMocked, times(1)).removeEntry("foo");
    }

    @Test
    void withdrawMethodThrowsExceptionForInvalidIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> memory.withdrawMethod(""));
        assertThrows(IllegalArgumentException.class, () -> memory.withdrawMethod(null));
    }

    @Test
    void withdrawMethodThrowsIfEntryNotFound() {
        when(symbolTableMocked.lookup("foo")).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> memory.withdrawMethod("foo"));
    }

    @Test
    void withdrawMethodThrowsIfNotAMethod() {
        SymbolTableEntry variableEntry = mock(SymbolTableEntry.class);
        when(variableEntry.getKind()).thenReturn(EntryKind.VARIABLE);
        when(symbolTableMocked.lookup("foo")).thenReturn(variableEntry);

        assertThrows(IllegalArgumentException.class, () -> memory.withdrawMethod("foo"));
    }

    @Test
    void declTabAllocatesHeapAndRegistersArray() {
        when(heapMocked.allocate(5, DataType.INT)).thenReturn(123);

        memory.declTab("arr", 5, DataType.INT);

        verify(heapMocked, times(1)).allocate(5, DataType.INT);
        verify(symbolTableMocked, times(1)).addEntry("arr", EntryKind.ARRAY, DataType.INT);
        verify(stackMocked, times(1)).setVar("arr", 123, DataType.INT);
    }

    @Test
    void affectValueOnArrayUpdatesHeapReferences() {
        SymbolTableEntry arrayEntry = new SymbolTableEntry("arr", EntryKind.ARRAY, DataType.INT);
        when(symbolTableMocked.lookup("arr")).thenReturn(arrayEntry);

        int oldRef = 50;
        StackObject obj = new StackObject("arr", oldRef, 0, EntryKind.VARIABLE, DataType.INT);
        when(stackMocked.searchObject("arr")).thenReturn(obj);

        memory.affectValue("arr", 200);

        verify(symbolTableMocked, times(1)).lookup("arr");
        verify(stackMocked, times(1)).searchObject("arr");
        verify(heapMocked, times(1)).removeReference(oldRef);
        verify(heapMocked, times(1)).addReference(200);
        // stack object's value should be updated
        assertEquals(200, obj.getValue());
    }

    @Test
    void affectValTDelegatesToHeap() {
        int addr = 77;
        StackObject addrObj = new StackObject("arr", addr, 0, EntryKind.VARIABLE, DataType.INT);
        when(stackMocked.getObject("arr")).thenReturn(addrObj);

        Value val = new Value(999);

        memory.affectValT("arr", 3, val);

        verify(stackMocked, times(1)).getObject("arr");
        verify(heapMocked, times(1)).setValue(addr, 3, val);
    }

    @Test
    void valTReturnsValueFromHeap() {
        int addr = 88;
        StackObject addrObj = new StackObject("arr", addr, 0, EntryKind.VARIABLE, DataType.INT);
        when(stackMocked.getObject("arr")).thenReturn(addrObj);

        Value expected = new Value(42);
        when(heapMocked.getValue(addr, 1)).thenReturn(expected);

        Value result = memory.valT("arr", 1);

        verify(stackMocked, times(1)).getObject("arr");
        verify(heapMocked, times(1)).getValue(addr, 1);
        assertNotNull(result);
        assertEquals(ValueType.INT, result.type);
        assertEquals(42, result.valueInt);
    }

    @Test
    void tabLengthDelegatesToHeapSizeOf() {
        int addr = 99;
        StackObject addrObj = new StackObject("arr", addr, 0, EntryKind.VARIABLE, DataType.INT);
        when(stackMocked.getObject("arr")).thenReturn(addrObj);

        when(heapMocked.sizeOf(addr)).thenReturn(5);

        int len = memory.tabLength("arr");

        verify(stackMocked, times(1)).getObject("arr");
        verify(heapMocked, times(1)).sizeOf(addr);
        assertEquals(5, len);
    }

    @Test
    void affectValTDoesNothingWhenBackingNotInt() {
        StackObject wrongObj = new StackObject("arr", "not-an-address", 0, EntryKind.VARIABLE, DataType.DOUBLE);
        when(stackMocked.getObject("arr")).thenReturn(wrongObj);

        Value val = new Value(1);

        memory.affectValT("arr", 0, val);

        verify(heapMocked, never()).setValue(anyInt(), anyInt(), any(Value.class));
    }

    @Test
    void affectValTThrowsWhenNoStackObject() {
        when(stackMocked.getObject("arr")).thenReturn(null);
        assertThrows(NullPointerException.class, () -> memory.affectValT("arr", 0, new Value(1)));
    }

    @Test
    void valTReturnsNullWhenBackingNotInt() {
        StackObject wrongObj = new StackObject("arr", "not-an-address", 0, EntryKind.VARIABLE, DataType.DOUBLE);
        when(stackMocked.getObject("arr")).thenReturn(wrongObj);

        Value result = memory.valT("arr", 2);
        assertNull(result);
        verify(heapMocked, never()).getValue(anyInt(), anyInt());
    }

    @Test
    void valTThrowsWhenNoStackObject() {
        when(stackMocked.getObject("arr")).thenReturn(null);
        assertThrows(NullPointerException.class, () -> memory.valT("arr", 0));
    }

    @Test
    void tabLengthReturnsMinusOneWhenBackingNotInt() {
        StackObject wrongObj = new StackObject("arr", "not-an-address", 0, EntryKind.VARIABLE, DataType.DOUBLE);
        when(stackMocked.getObject("arr")).thenReturn(wrongObj);

        int len = memory.tabLength("arr");
        assertEquals(-1, len);
        verify(heapMocked, never()).sizeOf(anyInt());
    }

    @Test
    void tabLengthThrowsWhenNoStackObject() {
        when(stackMocked.getObject("arr")).thenReturn(null);
        assertThrows(NullPointerException.class, () -> memory.tabLength("arr"));
    }
}
