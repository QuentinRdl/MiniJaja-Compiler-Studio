package fr.ufrst.m1info.pvm.group5.memory;
import fr.ufrst.m1info.pvm.group5.memory.heap.HeapElement;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class HeapElementTest {

    // Constructor
    @Test
    @DisplayName("Constructor") // yes this line is very necessary
    public void Constructor(){
        HeapElement e = new HeapElement(0, 50, 1);
        assertNotNull(e);
        assertEquals(1,e.size());
        assertEquals(50,e.getExternalAddress());
        assertEquals(0, e.getInternalAddress());
        assertEquals(DataType.UNKNOWN, e.getStorageType());
        assertTrue(e.isFree());
    }

    @Test
    @DisplayName("Constructor - null size()")
    public void ConstructorInvalidSize(){
        assertThrows(IllegalArgumentException.class, () -> new HeapElement(0, 50, 0));
    }

    @Test
    @DisplayName("Constructor - Negative size()")
    public void ConstructorNegativeSize(){
        assertThrows(IllegalArgumentException.class, () -> new HeapElement(0, 50, -1));
    }

    // Allocate

    @Test
    @DisplayName("Allocate")
    public void Allocate(){
        HeapElement e = new HeapElement(0, 50, 1);
        e.allocate(DataType.INT);
        assertEquals(DataType.INT, e.getStorageType());
        assertFalse(e.isFree());
    }

    @Test
    @DisplayName("Allocate - Already allocated")
    public void AlreadyAllocated(){
        HeapElement e = new HeapElement(0, 50, 1);
        e.allocate(DataType.INT);
        assertThrows(HeapElement.InvalidOperationException.class,()->e.allocate(DataType.INT));
    }

    // Free

    @Test
    @DisplayName("Free - NotAllocated")
    public void NotAllocated(){
        HeapElement e = new HeapElement(0, 50, 1);
        assertThrows(HeapElement.InvalidOperationException.class, e::free);
    }

    @Test
    @DisplayName("Free")
    public void Free(){
        HeapElement e = new HeapElement(0, 50, 1);
        e.allocate(DataType.INT);
        e.free();
        assertTrue(e.isFree());
        assertEquals(DataType.UNKNOWN, e.getStorageType());
    }

    // Split

    @Test
    @DisplayName("Split")
    public void Split(){
        HeapElement e = new HeapElement(0, 50, 10);
        HeapElement f = e.split(6);
        assertEquals(6, f.size());
        assertEquals(4, e.size());
        assertEquals(f.getExternalAddress(), e.getExternalAddress());
        assertEquals(0, f.getInternalAddress());
        assertEquals(6, e.getInternalAddress());
        assertEquals(f, e.getPrev());
        assertEquals(f, e.getNext());
        assertEquals(e, f.getPrev());
        assertEquals(e, f.getNext());
    }

    @Test
    @DisplayName("Split - Multiple")
    public void SplitMultiple(){
        HeapElement e = new HeapElement(0, 50, 100);
        var f = e.split(40);
        var g = e.split(20);
        assertEquals(40, e.size());

        // Checking if the chaining was done correctly
        assertEquals(f, e.getNext());
        assertEquals(g, f.getNext());
        assertEquals(e, g.getNext());
        assertEquals(g, e.getPrev());
        assertEquals(f, g.getPrev());
        assertEquals(e, f.getPrev());
    }

    @Test
    @DisplayName("Split - Insufficient size")
    public void SplitInsufficientSize(){
        HeapElement e = new HeapElement(0, 50, 10);
        assertThrows(HeapElement.InsufficientSizeException.class,()->e.split(11));
    }

    @Test
    @DisplayName("Split - Same size") // A heap element cannot have a size() of 0
    public void SplitSameSize(){
        HeapElement e = new HeapElement(0, 50, 10);
        assertThrows(HeapElement.InsufficientSizeException.class,()->e.split(10));
    }

    @Test
    @DisplayName("Split - Null size")
    public void SplitNotSameSize(){
            HeapElement e = new HeapElement(0, 50, 10);
            assertThrows(IllegalArgumentException.class,()->e.split(0));
    }

    @Test
    @DisplayName("Split - Negative size")
    public void SplitNegativeSameSize(){
            HeapElement e = new HeapElement(0, 50, 10);
            assertThrows(IllegalArgumentException.class,()->e.split(-1));
    }

    @Test
    @DisplayName("Split - Already allocated")
    public void SplitAlreadyAllocated(){
        HeapElement e = new HeapElement(0, 50, 10);
        e.allocate(DataType.INT);
        assertThrows(HeapElement.InvalidOperationException.class,()->e.split(6));
    }

    @Test
    @DisplayName("Split - After")
    public void SplitAfter(){
        HeapElement e = new HeapElement(0, 50, 10);
        HeapElement f = e.split(6, true);
        assertEquals(6, f.size());
        assertEquals(4, e.size());
        assertEquals(f.getExternalAddress(), e.getExternalAddress());
        assertEquals(4, f.getInternalAddress());
        assertEquals(0, e.getInternalAddress());
    }

    // TryMerge

    @Test
    @DisplayName("TryMerge - Single Merge")
    public void TryMerge(){
        HeapElement e = new HeapElement(0, 50, 10);
        e.split(6);
        assertTrue(e.tryMerge());
        assertEquals(10, e.size());
        assertEquals(0, e.getInternalAddress());
    }

    @Test
    @DisplayName("TryMerge - Multiple merge")
    public void TryMergeMultiple(){
        HeapElement e = new HeapElement(0, 50, 100);
        e.split(40);
        e.split(20);
        e.split(30,true);
        assertTrue(e.tryMerge());
        assertEquals(100, e.size());
        assertEquals(0, e.getInternalAddress());
    }

    @Test
    @DisplayName("TryMerge - Already allocated")
    public void TryMergeAllocated(){
        HeapElement e = new HeapElement(0, 50, 10);
        e.allocate(DataType.INT);
        assertThrows(HeapElement.InvalidOperationException.class,()->e.split(6));
    }

    @Test
    @DisplayName("TryMerge - Allocated Neighbor")
    public void TryMergeAllocatedNeighbor(){
        HeapElement e = new HeapElement(0, 50, 10);
        HeapElement f = e.split(6);
        f.allocate(DataType.INT);
        assertFalse(e.tryMerge());
        assertEquals(4, e.size());
    }

    @Test
    @DisplayName("TryMerge - Multiple merge, one element allocated")
    public void TryMergeMultipleWithAllocation(){
        HeapElement e = new HeapElement(0, 50, 100);
        e.split(40);
        var f = e.split(20);
        e.split(30,true);
        f.allocate(DataType.INT);
        assertTrue(e.tryMerge());
        assertEquals(40, e.size());
        assertEquals(60, e.getInternalAddress());
    }

    @Test
    @DisplayName("TryMerge - After free")
    public void TryMergeAfterFree(){
        HeapElement e = new HeapElement(0, 50, 100);
        e.split(40);
        e.split(20);
        e.split(30,true);
        e.allocate(DataType.INT);
        e.free();
        assertEquals(100, e.size());
        assertEquals(0, e.getInternalAddress());
    }

    // Belongs to
    @Test
    @DisplayName("Belongs to - Within it")
    public void BelongsToWithinIt(){
        HeapElement e = new HeapElement(20, 50, 10);
        assertTrue(e.contains(20));
        assertTrue(e.contains(21));
        assertTrue(e.contains(29));
    }

    @Test
    @DisplayName("Belongs to - Outside it")
    public void BelongsToOusideIt(){
        HeapElement e = new HeapElement(20, 50, 10);
        assertFalse(e.contains(19));
        assertFalse(e.contains(30));
        assertFalse(e.contains(31));
    }
}