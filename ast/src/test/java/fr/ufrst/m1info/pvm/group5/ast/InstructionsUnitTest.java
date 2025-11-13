package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.Instructions.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

import fr.ufrst.m1info.pvm.group5.memory.Stack.StackIsEmptyException;
import fr.ufrst.m1info.pvm.group5.memory.Memory.MemoryIllegalArgException;

public class InstructionsUnitTest {
    @Mock
    Memory memory;
    Stack<ASTMocks.Pair<String, Value>> storage;

    @BeforeEach
    public void setup(){
        storage = new Stack<>();
        memory = ASTMocks.createMemoryWithStack(storage);
    }


    //push
    @Test
    public void push_simple(){
        PushInstruction p = new PushInstruction(new Value(5));
        var res = p.execute(0,memory);
        assertEquals(1, res);
        assertEquals(5, storage.pop().second().valueInt);
    }

    @Test
    public void push_many(){
        PushInstruction p = new PushInstruction(new Value(5));
        PushInstruction z = new PushInstruction(new Value(0));
        p.execute(0,memory);
        p.execute(1,memory);
        var res = z.execute(2, memory);
        assertEquals(3, res);
        assertEquals(0, storage.pop().second().valueInt);
    }

    @Test
    public void pop_simple() throws Exception{
        PushInstruction p1 = new PushInstruction(new Value(5));
        p1.execute(0, memory);
        PopInstruction p2 = new PopInstruction();
        int next = p2.execute(1, memory);
        assertEquals(2, next);
        assertTrue(storage.isEmpty());
    }

    @Disabled
    @Test
    public void pop_empty() {
        PopInstruction p = new PopInstruction();
        assertThrows(StackIsEmptyException.class, () -> p.execute(0, memory));
    }

    @Test
    public void swap_simple_instruction() throws Exception {
        PushInstruction p1 = new PushInstruction(new Value(2));
        PushInstruction p2 = new PushInstruction(new Value(5));

        p1.execute(0, memory);
        p2.execute(1, memory);

        SwapInstruction swap = new SwapInstruction();
        int next = swap.execute(2, memory);

        assertEquals(3, next);
        assertEquals(2, storage.pop().second().valueInt);
        assertEquals(5, storage.pop().second().valueInt);
        assertTrue(storage.isEmpty());
    }

    @Disabled
    @Test
    public void swap_instruction_not_enough_elements(){
        PushInstruction p1 = new PushInstruction(new Value(5));
        p1.execute(0, memory);

        SwapInstruction swap = new SwapInstruction();
        assertThrows(Memory.MemoryIllegalArgException.class, () -> swap.execute(1, memory));
    }

    @Test
    public void goto_instruction() throws Exception {
        GotoInstruction i = new GotoInstruction(5);
        int next = i.execute(0, memory);
        assertEquals(5, next);
    }

    @Test
    public void nop_instruction() throws Exception {
        NopInstruction i = new NopInstruction();
        int next = i.execute(0, memory);
        assertEquals(1, next);
    }

    @Test
    public void write_integer() throws Exception {
        List<String> writerRef = new ArrayList<>();
        ASTMocks.addWriterToMock(memory, writerRef);
        PushInstruction p = new PushInstruction(new Value(5));
        p.execute(0, memory);

        WriteInstruction w = new WriteInstruction();
        int next = w.execute(1, memory);
        assertEquals(2, next);
        assertEquals(1, writerRef.size());
        assertEquals("5", writerRef.get(0));
        assertTrue(storage.isEmpty());
    }

    @Test
    public void write_string() throws Exception {
        List<String> writerRef = new ArrayList<>();
        ASTMocks.addWriterToMock(memory, writerRef);
        PushInstruction p = new PushInstruction(new Value("Hello World"));
        p.execute(0, memory);

        WriteInstruction w = new WriteInstruction();
        int next = w.execute(1, memory);
        assertEquals(2, next);
        assertEquals(1, writerRef.size());
        assertEquals("Hello World", writerRef.get(0));
    }

    @Test
    public void write_boolean() throws Exception{
        List<String> writerRef = new ArrayList<>();
        ASTMocks.addWriterToMock(memory, writerRef);
        PushInstruction p = new PushInstruction(new Value(true));
        p.execute(0, memory);

        WriteInstruction w = new WriteInstruction();
        int next = w.execute(1, memory);
        assertEquals(2, next);
        assertEquals(1, writerRef.size());
        assertEquals("true", writerRef.get(0));
    }

    @Test
    public void writeln_integer() throws Exception {
        List<String> writerRef = new ArrayList<>();
        ASTMocks.addWriterToMock(memory, writerRef);
        PushInstruction p = new PushInstruction(new Value(5));
        p.execute(0, memory);

        WritelnInstruction w = new WritelnInstruction();
        int next = w.execute(1, memory);
        assertEquals(2, next);
        assertEquals(1, writerRef.size());
        assertEquals("5\n", writerRef.get(0));
    }

    @Test
    public void writeln_boolean() throws Exception {
        List<String> writerRef = new ArrayList<>();
        ASTMocks.addWriterToMock(memory, writerRef);
        PushInstruction p = new PushInstruction(new Value(false));
        p.execute(0, memory);

        WritelnInstruction w = new WritelnInstruction();
        int next = w.execute(1, memory);
        assertEquals(2, next);
        assertEquals(1, writerRef.size());
        assertEquals("false\n", writerRef.get(0));
    }

    @Test
    public void writeln_string() throws Exception {
        List<String> writerRef = new ArrayList<>();
        ASTMocks.addWriterToMock(memory, writerRef);
        PushInstruction p = new PushInstruction(new Value("Hello World"));
        p.execute(0, memory);

        WritelnInstruction w = new WritelnInstruction();
        int next = w.execute(1, memory);
        assertEquals(2, next);
        assertEquals(1, writerRef.size());
        assertEquals("Hello World\n", writerRef.get(0));
    }

    //TODO: return test


}
