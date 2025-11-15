package fr.ufrst.m1info.pvm.group5.memory;

import fr.ufrst.m1info.pvm.group5.memory.Heap.*;
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
          new Heap.ElementRecord(0, true, 12, false, DataType.INT),
          new Heap.ElementRecord(12, false, 500, true, DataType.UNKNOWN)
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
            result.add(new Heap.ElementRecord(i * 50,true,50,false, DataType.INT));
        }
        result.add(new Heap.ElementRecord(500,false,12,true, DataType.UNKNOWN));
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
        assertEquals(new Heap.ElementRecord(0, false, 512, true, DataType.UNKNOWN), heap.getBlocksSnapshot().getFirst());
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
                new Heap.ElementRecord(0, true, 12, false, DataType.INT),
                new Heap.ElementRecord(12, false, 500, true, DataType.UNKNOWN)
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
        assertThrows(InvalidMemoryAddressException.class, ()->heap.setValue(address, 11, new Value(5)));
    }

    // GetValue
    @Test
    @DisplayName("GetValue")
    public void getValueTest(){
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.INT);
        heap.setValue(address, 2, new Value(5));
        assertEquals(5, heap.getValue(address,2).valueInt);
    }

    @Test
    @DisplayName("GetValue - Bool")
    public void getValueBooleanTest(){
        Heap heap = new Heap(512);
        int address = heap.allocate(12,DataType.BOOL);
        heap.setValue(address, 2, new Value(true));
        heap.setValue(address, 3, new Value(false));
        assertTrue(heap.getValue(address,2).valueBool);
        assertFalse(heap.getValue(address,3).valueBool);
    }

    @Test
    @DisplayName("GetValue - Get in UnAllocated block")
    public void GetValueGetInUnAllocatedBlockTest(){
        Heap heap = new Heap(512);
        assertThrows(InvalidMemoryAddressException.class, ()->heap.getValue(0,0));
    }

    @Test
    @DisplayName("GetValue - InvalidAddress")
    public void GetValueInvalidAddressTest(){
        Heap heap = new Heap(512);
        assertThrows(UnmappedMemoryAddressException.class, ()->heap.getValue(1,0));
    }

    @Test
    @DisplayName("GetValue - Offset OOB")
    public void GetValueOffsetOOBTest(){
        Heap heap = new Heap(512);
        var address = heap.allocate(12,DataType.INT);
        heap.getValue(address, 11); // 11 is still in bounds
        assertThrows(UnmappedMemoryAddressException.class, ()->heap.getValue(address,12));
        assertThrows(UnmappedMemoryAddressException.class, ()->heap.getValue(address,13));
    }

    // Heap Defragmentation
    @Test
    @DisplayName("Defragment")
    public void DefragmentTest(){
        Heap heap = new Heap(512);
        heap.allocate(150,DataType.INT);
        int a2 = heap.allocate(150,DataType.INT);
        heap.allocate(150,DataType.INT);
        heap.free(a2);
        assertEquals(212,heap.getAvailableSize());
        heap.allocate(200,DataType.INT); // This instruction causes a defragmentation
        List<Heap.ElementRecord> result = List.of(
                new Heap.ElementRecord(0, true, 150, false, DataType.INT),
                new Heap.ElementRecord(150, true, 150, false, DataType.INT),
                new Heap.ElementRecord(300, true, 200, false, DataType.INT),
                new Heap.ElementRecord(500, false, 12, true, DataType.UNKNOWN)
        );
        assertEquals(result,heap.getBlocksSnapshot());
    }

    @Test
    @DisplayName("Defragment - With values")
    public void DefragmentMovedTest(){
        Heap heap = new Heap(512);
        heap.allocate(150,DataType.INT);
        int a2 = heap.allocate(150,DataType.INT);
        heap.allocate(150,DataType.INT);

        // Setting values
        heap.setValue(1, 0, new Value(1));
        heap.setValue(1, 149, new Value(2));
        heap.setValue(3, 0, new Value(3));
        heap.setValue(3, 149, new Value(4));

        heap.free(a2);
        assertEquals(212,heap.getAvailableSize());
        heap.allocate(200,DataType.INT); // This instruction causes a defragmentation

        // Checking the values with getValue
        assertEquals(1,heap.getValue(1, 0).valueInt);
        assertEquals(2,heap.getValue(1, 149).valueInt);
        assertEquals(3,heap.getValue(3, 0).valueInt);
        assertEquals(4,heap.getValue(3, 149).valueInt);

        // Checking the values with direct access
        var array = heap.getStorageSnapshot();
        assertEquals(1, array[0]);
        assertEquals(2, array[149]);
        assertEquals(3, array[150]);
        assertEquals(4, array[299]);
    }

    @Test
    @DisplayName("Defragment - Several free blocks")
    public void DefragmentSeveralFreeTest(){
        Heap heap = new Heap(1024);
        int a1 = heap.allocate(128,DataType.INT);
        heap.allocate(128,DataType.INT);
        int a2 = heap.allocate(50,DataType.INT);
        heap.allocate(18,DataType.INT);
        int a3 = heap.allocate(300,DataType.INT);
        heap.allocate(200,DataType.INT);
        int a4 = heap.allocate(100,DataType.INT);
        heap.allocate(100,DataType.INT);
        heap.free(a1);
        heap.free(a2);
        heap.free(a3);
        heap.free(a4);
        heap.allocate(578, DataType.INT); // This instruction causes a defragmentation
        List<Heap.ElementRecord> result = List.of(
                new Heap.ElementRecord(0, true, 128, false, DataType.INT),
                new Heap.ElementRecord(128, true, 18, false, DataType.INT),
                new Heap.ElementRecord(146, true, 200, false, DataType.INT),
                new Heap.ElementRecord(346, true, 100, false, DataType.INT),
                new Heap.ElementRecord(446, true, 578, true, DataType.INT)
        );
        assertEquals(result,heap.getBlocksSnapshot());
    }

    // Sanitize
    @Test
    @DisplayName("Sanitize - Empty")
    public void SanitizeTest(){
        Heap heap = new Heap(512);
        assertEquals(0, heap.sanitize().size());
    }

    @Test
    @DisplayName("Sanitize - Allocated Blocks")
    public void SanitizeAllocationTest(){
        Heap heap = new Heap(512);
        heap.allocate(12,DataType.INT);
        int h1 = heap.allocate(12,DataType.INT);
        heap.allocate(12,DataType.INT);
        heap.free(h1);
        assertEquals(2, heap.sanitize().size());
    }
}
