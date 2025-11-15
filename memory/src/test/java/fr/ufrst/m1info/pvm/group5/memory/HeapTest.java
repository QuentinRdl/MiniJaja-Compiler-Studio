package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.Heap.Heap;
import fr.ufrst.m1info.pvm.group5.memory.Heap.InsufficientMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.Heap.InvalidMemoryAddressException;
import fr.ufrst.m1info.pvm.group5.memory.Heap.UnmappedMemoryAddressException;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HeapTest {

    @Test
    @DisplayName("Base constructor")
    public void constructorTest() {
        Heap heap = new Heap(512);
        assertEquals(512, heap.getTotalSize());
        assertEquals(512, heap.getAvailableSize());
        assertEquals(1, heap.getBlocksSnapshot().size());
        assertFalse(heap.getBlocksSnapshot().getFirst().isAllocated());
    }

    // Allocation

    @Test
    @DisplayName("Allocate")
    public void allocateTest() {
        Heap heap = new Heap(512);
        assertEquals(1, heap.allocate(12, DataType.INT));
        assertEquals(512, heap.getTotalSize());
        assertEquals(500, heap.getAvailableSize());
        List<Heap.ElementRecord> result = List.of(
          new Heap.ElementRecord(0, true, 12, false),
          new Heap.ElementRecord(12, false, 500, true)
        );
        assertEquals(result, heap.getBlocksSnapshot());
    }

    @Test
    @DisplayName("Allocate - Not enough memory")
    public void allocateNotEnoughMemoryTest() {
        Heap heap = new Heap(512);
        assertThrows(InsufficientMemoryException.class, () -> heap.allocate(1000, DataType.INT));
    }

    @Test
    @DisplayName("Allocate - Not enough memory after allocation")
    public void allocateNotEnoughMemoryAfterAllocationTest() {
        Heap heap = new Heap(512);
        heap.allocate(500, DataType.INT);
        assertThrows(InsufficientMemoryException.class, () -> heap.allocate(500, DataType.INT));
    }

    @Test
    @DisplayName("Allocate - Invalid size")
    public void allocateInvalidSizeTest() {
        Heap heap = new Heap(512);
        assertThrows(IllegalArgumentException.class, () -> heap.allocate(-1, DataType.INT));
    }

    @Test
    @DisplayName("Allocate - Multiple")
    public void allocateMultipleTest() {
        Heap heap = new Heap(512);
        List<Heap.ElementRecord> result = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            heap.allocate(50,  DataType.INT);
            result.add(new Heap.ElementRecord(i * 50,true,50,false));
        }
        result.add(new Heap.ElementRecord(500,false,12,true));
        assertEquals(result,heap.getBlocksSnapshot());
    }

    // Free
    @Test
    @DisplayName("Free - Single")
    public void freeTest() {
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.INT);
        heap.free(address);
        assertEquals(512, heap.getTotalSize());
        assertEquals(512, heap.getAvailableSize());
        assertEquals(new Heap.ElementRecord(0, false, 512, true), heap.getBlocksSnapshot().getFirst());
    }

    @Test
    @DisplayName("Free - InvalidAddress")
    public void freeInvalidAddressTest() {
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.INT);
        assertThrows(InvalidMemoryAddressException.class, () -> heap.free(address+1));
    }


    @Test
    @DisplayName("Free - In the middle")
    public void freeInTheMiddleTest() {
        Heap heap = new Heap(512);
        int b1 = heap.allocate(50,DataType.INT);
        int b2 = heap.allocate(50,DataType.INT);
        heap.free(b1);
        assertEquals(3, heap.getBlocksSnapshot().size());
        heap.free(b2);
        assertEquals(1, heap.getBlocksSnapshot().size());
    }

    @Test
    @DisplayName("Free - Multiple")
    public void freeMultipleTest(){
        Heap heap = new Heap(512);
        List<Integer> addresses =  new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            addresses.add(heap.allocate(50, DataType.INT));
        }
        assertEquals(12, heap.getAvailableSize());
        for(int i = 1; i <= 10; i++){
            heap.free(addresses.get(i-1));
            assertEquals(12 + 50 * i, heap.getAvailableSize());
            if(i<10) // Each block only does 1 merge except the last
                assertEquals(12-i, heap.getBlocksSnapshot().size());
        }
        assertEquals(1, heap.getBlocksSnapshot().size());
    }

    // References
    @Test
    @DisplayName("Reference - Add then remove")
    public void ReferenceAddAndRemoveTest(){
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.INT);
        heap.addReference(address);
        heap.removeReference(address);
        assertEquals(512, heap.getAvailableSize());
        assertEquals(1, heap.getBlocksSnapshot().size());
    }

    @Test
    @DisplayName("Reference - Add to invalid address")
    public void ReferenceInvalidAddressTest(){
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.INT);
        assertThrows(InvalidMemoryAddressException.class, () -> heap.addReference(address+1));
    }

    @Test
    @DisplayName("Reference - Remove to invalid address")
    public void ReferenceInvalidAddressRemoveTest(){
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.INT);
        assertThrows(InvalidMemoryAddressException.class, () -> heap.removeReference(address+1));
    }

    @Test
    @DisplayName("Reference - Two add one remove")
    public void ReferenceTwoRemoveOneTest(){
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.INT);
        heap.addReference(address);
        heap.addReference(address);
        heap.removeReference(address);
        assertEquals(500, heap.getAvailableSize());
        List<Heap.ElementRecord> result = List.of(
                new Heap.ElementRecord(0, true, 12, false),
                new Heap.ElementRecord(12, false, 500, true)
        );
        assertEquals(result, heap.getBlocksSnapshot());
    }

    @Test
    @DisplayName("Reference - One add two remove")
    public void ReferenceOneRemoveTwoTest(){
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.INT);
        heap.addReference(address);
        heap.removeReference(address);
        assertThrows(InvalidMemoryAddressException.class, ()->heap.removeReference(address));
    }

    // setValue
    @Test
    @DisplayName("SetValue")
    public void SetValueTest(){
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.INT);
        heap.setValue(address, 2, new Value(5));
        assertEquals(5, heap.getStorageSnapshot()[2]);
    }

    @Test
    @DisplayName("SetValue - boolean")
    public void SetValueBooleanTest(){
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.BOOL);
        heap.setValue(address, 2, new Value(true));
        assertEquals(1, heap.getStorageSnapshot()[2]);
        heap.setValue(address, 3, new Value(false));
        assertEquals(0, heap.getStorageSnapshot()[3]);
    }

    @Test
    @DisplayName("SetValue - Middle of the array")
    public void SetValueMiddleTest(){
        Heap heap = new Heap(512);
        heap.allocate(250, DataType.INT);
        var address = heap.allocate(12, DataType.INT);
        heap.allocate(249,DataType.INT);
        heap.setValue(address, 7, new Value(5));
        assertEquals(5, heap.getStorageSnapshot()[257]);
    }

    @Test
    @DisplayName("SetValue - Set in UnAllocated block")
    public void SetValueSetInUnAllocatedBlockTest(){
        Heap heap = new Heap(512);
        assertThrows(InvalidMemoryAddressException.class, ()->heap.setValue(0,0, new Value(5)));
    }

    @Test
    @DisplayName("SetValue - InvalidAddress")
    public void SetValueInvalidAddressTest(){
        Heap heap = new Heap(512);
        assertThrows(UnmappedMemoryAddressException.class, ()->heap.setValue(1,0, new Value(5)));
    }

    @Test
    @DisplayName("SetValue - Offset OOB")
    public void SetValueOffsetOOBTest(){
        Heap heap = new Heap(512);
        var address = heap.allocate(12,DataType.INT);
        heap.setValue(address, 11, new Value(5)); // 11 is still in bounds
        assertThrows(UnmappedMemoryAddressException.class, ()->heap.setValue(address,12, new Value(5)));
        assertThrows(UnmappedMemoryAddressException.class, ()->heap.setValue(address,13, new Value(5)));
    }

    @Test
    @DisplayName("SetValue - AfterFree")
    public void SetValueAfterFreeTest(){
        Heap heap = new Heap(512);
        var address = heap.allocate(12,DataType.INT);
        heap.free(address);
        assertThrows(UnmappedMemoryAddressException.class, ()->heap.setValue(address, 11, new Value(5)));
    }
}
