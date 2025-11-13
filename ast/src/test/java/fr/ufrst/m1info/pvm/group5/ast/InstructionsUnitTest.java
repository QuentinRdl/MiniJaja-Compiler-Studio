package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.Instructions.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

public class InstructionsUnitTest {
    @Mock
    Memory memory;
    Stack<ASTMocks.Pair<String, Value>> storage;

    @BeforeEach
    public void setup(){
        storage = new Stack<>();
        memory = ASTMocks.createMemoryWithStack(storage);
    }

    //init
    @Test
    public void init() throws Exception {
        Instruction initInstr = new InitInstruction();
        assertEquals(2,initInstr.execute(1,memory));
    }

    //if
    @Test
    public void if_true() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(true));
        Instruction ifInstr = new IfInstruction(5);
        pushInstr.execute(1,memory);
        assertEquals(5,ifInstr.execute(2,memory));
    }


    @Test
    public void if_false() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(false));
        Instruction ifInstr = new IfInstruction(5);
        pushInstr.execute(1,memory);
        assertEquals(3,ifInstr.execute(2,memory));
    }

    @Test
    public void if_empty_stack(){
        doAnswer(invocationOnMock -> {
            throw new fr.ufrst.m1info.pvm.group5.memory.Stack.StackIsEmptyException("pop with a empty stack");
        }).when(memory).pop();
        Instruction ifInstr = new IfInstruction(5);
        assertThrows(ASTInvalidMemoryException.class,() -> ifInstr.execute(2,memory));
    }

    @Test
    public void if_int() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(1));
        Instruction ifInstr = new IfInstruction(5);
        pushInstr.execute(1,memory);
        assertThrows(ASTInvalidDynamicTypeException.class,() -> ifInstr.execute(2,memory));
    }

    @Test
    public void if_string() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value("Not a boolean"));
        Instruction ifInstr = new IfInstruction(5);
        pushInstr.execute(1,memory);
        assertThrows(ASTInvalidDynamicTypeException.class,() -> ifInstr.execute(2,memory));
    }

    //jcstop
    @Test
    public void jcstop() throws Exception {
        Instruction jcstopInstr = new JcstopInstruction();
        assertEquals(-1,jcstopInstr.execute(1,memory));
    }

    //new
    @Test
    public void new_var_int() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(5));
        Instruction newInstr = new NewInstruction("x", DataType.INT, EntryKind.VARIABLE,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertEquals(5,((Value) memory.val("x")).valueInt);
    }

    @Test
    public void new_var_bool() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(false));
        Instruction newInstr = new NewInstruction("x", DataType.BOOL, EntryKind.VARIABLE,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertFalse(((Value) memory.val("x")).valueBool);
    }

    @Test
    public void new_cst_int() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(5));
        Instruction newInstr = new NewInstruction("x", DataType.INT, EntryKind.CONSTANT,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertEquals(5,((Value) memory.val("x")).valueInt);
    }

    @Test
    public void new_cst_bool() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(false));
        Instruction newInstr = new NewInstruction("x", DataType.BOOL, EntryKind.CONSTANT,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertFalse(((Value) memory.val("x")).valueBool);
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

    //load
    @Test
    public void load_simple(){
        storage.add(new ASTMocks.Pair<>("test",new Value(5)));
        LoadInstruction l = new LoadInstruction("test");
        var res = l.execute(0,memory);
        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(5, top.second().valueInt);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    public void load_many(){
        storage.add(new ASTMocks.Pair<>("test1",new Value(1)));
        storage.add(new ASTMocks.Pair<>("test2",new Value(2)));
        storage.add(new ASTMocks.Pair<>("test3",new Value(3)));
        storage.add(new ASTMocks.Pair<>("test4",new Value(4)));

        LoadInstruction l = new LoadInstruction("test3");
        var res = l.execute(0,memory);
        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(3, top.second().valueInt);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    public void load_notExist(){
        storage.add(new ASTMocks.Pair<>("test1",new Value(1)));

        LoadInstruction l = new LoadInstruction("test3");
        assertThrows(Exception.class, () -> l.execute(0,memory));
    }

    @Test
    public void pop_simple() throws Exception{
        PushInstruction p1 = new PushInstruction(new Value(5));
        var res = p1.execute(0, memory);
        assertEquals(1, res);
        PopInstruction p2 = new PopInstruction();
        res = p2.execute(1, memory);
        assertEquals(2, res);
        assertTrue(storage.isEmpty());
    }

    //TODO: empty pop test

    @Test
    public void swap_simple_instruction() throws Exception {
        PushInstruction p1 = new PushInstruction(new Value(2));
        PushInstruction p2 = new PushInstruction(new Value(5));

        p1.execute(0, memory);
        p2.execute(1, memory);

        SwapInstruction swap = new SwapInstruction();
        var res = swap.execute(2, memory);

        assertEquals(3, res);
        assertEquals(2, storage.pop().second().valueInt);
        assertEquals(5, storage.pop().second().valueInt);
    }

    //TODO: empty swap test

    @Test
    public void goto_instruction() throws Exception {
        GotoInstruction i = new GotoInstruction(5);
        var res = i.execute(0, memory);
        assertEquals(5, res);
    }

    @Test
    public void nop_instruction() throws Exception {
        NopInstruction i = new NopInstruction();
        var res = i.execute(0, memory);
        assertEquals(1, res);
    }



}
