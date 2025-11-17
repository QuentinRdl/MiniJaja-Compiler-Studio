package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.instructions.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

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

    //init
    @Test
    void init() throws Exception {
        Instruction initInstr = new InitInstruction();
        assertEquals(2,initInstr.execute(1,memory));
    }

    //if
    @Test
    void if_true() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(true));
        Instruction ifInstr = new IfInstruction(5);
        pushInstr.execute(1,memory);
        assertEquals(5,ifInstr.execute(2,memory));
    }


    @Test
    void if_false() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(false));
        Instruction ifInstr = new IfInstruction(5);
        pushInstr.execute(1,memory);
        assertEquals(3,ifInstr.execute(2,memory));
    }

    @Test
    void if_empty_stack(){
        doAnswer(invocationOnMock -> {
            throw new fr.ufrst.m1info.pvm.group5.memory.Stack.StackIsEmptyException("pop with a empty stack");
        }).when(memory).pop();
        Instruction ifInstr = new IfInstruction(5);
        assertThrows(ASTInvalidMemoryException.class,() -> ifInstr.execute(2,memory));
    }

    @Test
    void if_int() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(1));
        Instruction ifInstr = new IfInstruction(5);
        pushInstr.execute(1,memory);
        assertThrows(ASTInvalidDynamicTypeException.class,() -> ifInstr.execute(2,memory));
    }

    @Test
    void if_string() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value("Not a boolean"));
        Instruction ifInstr = new IfInstruction(5);
        pushInstr.execute(1,memory);
        assertThrows(ASTInvalidDynamicTypeException.class,() -> ifInstr.execute(2,memory));
    }

    //jcstop
    @Test
    void jcstop() throws Exception {
        Instruction jcstopInstr = new JcstopInstruction();
        assertEquals(-1,jcstopInstr.execute(1,memory));
    }

    //new
    @Test
    void new_var_int() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(5));
        Instruction newInstr = new NewInstruction("x", DataType.INT, EntryKind.VARIABLE,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertEquals(5,((Value) memory.val("x")).valueInt);
    }

    @Test
    void new_var_bool() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(false));
        Instruction newInstr = new NewInstruction("x", DataType.BOOL, EntryKind.VARIABLE,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertFalse(((Value) memory.val("x")).valueBool);
    }

    @Test
    void new_var_empty_stack() throws Exception {
        doAnswer(invocationOnMock -> {
            throw new fr.ufrst.m1info.pvm.group5.memory.Stack.StackIsEmptyException("pop with a empty stack");
        }).when(memory).pop();
        Instruction newInstr = new NewInstruction("x", DataType.BOOL, EntryKind.VARIABLE,0);
        assertThrows(ASTInvalidMemoryException.class,() -> newInstr.execute(1,memory));
    }

    @Test
    void new_cst_int() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(5));
        Instruction newInstr = new NewInstruction("x", DataType.INT, EntryKind.CONSTANT,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertEquals(5,((Value) memory.val("x")).valueInt);
    }

    @Test
    void new_cst_bool() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(false));
        Instruction newInstr = new NewInstruction("x", DataType.BOOL, EntryKind.CONSTANT,0);
        pushInstr.execute(1,memory);
        assertEquals(3,newInstr.execute(2,memory));
        assertFalse(((Value) memory.val("x")).valueBool);
    }

    @Test
    void new_cst_empty_stack() throws Exception {
        doAnswer(invocationOnMock -> {
            throw new fr.ufrst.m1info.pvm.group5.memory.Stack.StackIsEmptyException("pop with a empty stack");
        }).when(memory).pop();
        Instruction newInstr = new NewInstruction("x", DataType.BOOL, EntryKind.CONSTANT,0);
        assertThrows(ASTInvalidMemoryException.class,() -> newInstr.execute(1,memory));
    }

    @Test
    void new_unknown_entry_kind() throws Exception {
        Instruction pushInstr = new PushInstruction(new Value(false));
        Instruction newInstr = new NewInstruction("x", DataType.BOOL, EntryKind.OTHER,0);
        pushInstr.execute(1,memory);
        assertThrows(ASTBuildException.class,() -> newInstr.execute(1,memory));
    }

    //push
    @Test
    void push_simple_int(){
        PushInstruction p = new PushInstruction(new Value(5));

        var res = p.execute(0,memory);

        assertEquals(1, res);
        assertEquals(5, storage.pop().second().valueInt);
    }

    @Test
    void push_simple_bool(){
        PushInstruction p = new PushInstruction(new Value(true));

        var res = p.execute(0,memory);

        assertEquals(1, res);
        assertEquals(true, storage.pop().second().valueBool);
    }

    @Test
    void push_simple_string(){
        PushInstruction p = new PushInstruction(new Value("test"));

        var res = p.execute(0,memory);

        assertEquals(1, res);
        assertEquals("test", storage.pop().second().valueString);
    }

    @Test
    void push_many(){
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
    void load_simple_int(){
        storage.add(new ASTMocks.Pair<>("test",new Value(5)));
        LoadInstruction l = new LoadInstruction("test");

        var res = l.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(5, top.second().valueInt);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void load_simple_bool(){
        storage.add(new ASTMocks.Pair<>("test",new Value(true)));
        LoadInstruction l = new LoadInstruction("test");

        var res = l.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(true, top.second().valueBool);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void load_simple_string(){
        storage.add(new ASTMocks.Pair<>("test",new Value("t")));
        LoadInstruction l = new LoadInstruction("test");

        assertThrows(ASTInvalidTypeException.class, () -> l.execute(0, memory));
    }

    @Test
    void load_many(){
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

    //store
    @Test
    void store_simple_int(){
        storage.add(new ASTMocks.Pair<>("test",new Value(1)));
        storage.add(new ASTMocks.Pair<>(".",new Value(5)));

        StoreInstruction s = new StoreInstruction("test");
        var res = s.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(5, top.second().valueInt);
        assertEquals("test", top.first());
        assertEquals(1, res);
    }

    @Test
    void store_simple_bool(){
        storage.add(new ASTMocks.Pair<>("test",new Value(true)));
        storage.add(new ASTMocks.Pair<>(".",new Value(false)));

        StoreInstruction s = new StoreInstruction("test");
        var res = s.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(false, top.second().valueBool);
        assertEquals("test", top.first());
        assertEquals(1, res);
    }

    @Test
    void store_simple_string(){
        storage.add(new ASTMocks.Pair<>("test",new Value("t1")));
        storage.add(new ASTMocks.Pair<>(".",new Value("t2")));

        StoreInstruction s = new StoreInstruction("test");

        assertThrows(ASTInvalidTypeException.class, () -> s.execute(0, memory));
    }

    //add
    @Test
    void add_simple_int(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(4)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(5)));

        AddInstruction a = new AddInstruction();
        var res = a.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(9, top.second().valueInt);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void add_simple_bool(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(true)));

        AddInstruction a = new AddInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> a.execute(0, memory));
    }

    @Test
    void add_simple_string(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value("t")));

        AddInstruction a = new AddInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> a.execute(0, memory));
    }

    //mul
    @Test
    void mul_simple_int(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(3)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(5)));

        MulInstruction m = new MulInstruction();
        var res = m.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(15, top.second().valueInt);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void mul_simple_bool(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(true)));

        MulInstruction m = new MulInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> m.execute(0, memory));
    }

    @Test
    void mul_simple_string(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value("t")));

        MulInstruction m = new MulInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> m.execute(0, memory));
    }


    //sub
    @Test
    void sub_simple_int(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(10)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(5)));

        SubInstruction s = new SubInstruction();
        var res = s.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(5, top.second().valueInt);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void sub_simple_bool(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(true)));

        SubInstruction s = new SubInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> s.execute(0, memory));
    }

    @Test
    void sub_simple_string(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value("t")));

        SubInstruction s = new SubInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> s.execute(0, memory));
    }

    //div
    @Test
    void div_simple_int(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(10)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(5)));

        DivInstruction d = new DivInstruction();
        var res = d.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(2, top.second().valueInt);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void div_simple_bool(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(true)));

        DivInstruction d = new DivInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> d.execute(0, memory));
    }

    @Test
    void div_simple_string(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value("t")));

        DivInstruction d = new DivInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> d.execute(0, memory));
    }

    @Test
    void div_divisionByZero(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(10)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(0)));

        DivInstruction d = new DivInstruction();

        assertThrows(ASTInvalidOperationException.class, () -> d.execute(0, memory));
    }

    //or
    @Test
    void or_simple_bool(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(true)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(true)));

        OrInstruction o = new OrInstruction();
        var res = o.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(true, top.second().valueBool);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void or_simple_int(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(true)));

        OrInstruction o = new OrInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> o.execute(0, memory));
    }

    @Test
    void or_simple_string(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(true)));
        storage.add(new ASTMocks.Pair<>("opr",new Value("t")));

        OrInstruction o = new OrInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> o.execute(0, memory));
    }

    //and
    @Test
    void and_simple_bool(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(true)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(false)));

        AndInstruction a = new AndInstruction();
        var res = a.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(false, top.second().valueBool);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void and_simple_int(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(true)));

        AndInstruction a = new AndInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> a.execute(0, memory));
    }

    @Test
    void and_simple_string(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(true)));
        storage.add(new ASTMocks.Pair<>("opr",new Value("t")));

        AndInstruction a = new AndInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> a.execute(0, memory));
    }

    //neg
    @Test
    void neg_simple_int(){
        storage.add(new ASTMocks.Pair<>("op",new Value(2)));

        NegInstruction n = new NegInstruction();
        var res = n.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(-2, top.second().valueInt);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void neg_simple_bool(){
        storage.add(new ASTMocks.Pair<>("op",new Value(true)));

        NegInstruction n = new NegInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> n.execute(0, memory));
    }

    @Test
    void neg_simple_string(){
        storage.add(new ASTMocks.Pair<>("op",new Value("t")));

        NegInstruction n = new NegInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> n.execute(0, memory));
    }

    //not
    @Test
    void not_simple_bool(){
        storage.add(new ASTMocks.Pair<>("op",new Value(true)));

        NotInstruction n = new NotInstruction();
        var res = n.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(false, top.second().valueBool);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void not_simple_int(){
        storage.add(new ASTMocks.Pair<>("op",new Value(1)));

        NotInstruction n = new NotInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> n.execute(0, memory));
    }

    @Test
    void not_simple_string(){
        storage.add(new ASTMocks.Pair<>("op",new Value("t")));

        NotInstruction n = new NotInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> n.execute(0, memory));
    }

    //cmp
    @Test
    void cmp_simple_bool(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(true)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(true)));

        CmpInstruction c = new CmpInstruction();
        var res = c.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(true, top.second().valueBool);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void cmp_simple_int(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(1)));

        CmpInstruction c = new CmpInstruction();
        var res = c.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(true, top.second().valueBool);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void cmp_simple_string_bool(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(true)));
        storage.add(new ASTMocks.Pair<>("opr",new Value("t")));

        CmpInstruction c = new CmpInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> c.execute(0, memory));
    }

    @Test
    void cmp_simple_string_int(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value("t")));

        CmpInstruction c = new CmpInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> c.execute(0, memory));
    }

    @Test
    void cmp_simple_bool_int(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(true)));

        CmpInstruction c = new CmpInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> c.execute(0, memory));
    }

    //sup
    @Test
    void sup_simple_int(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(5)));

        SupInstruction s = new SupInstruction();
        var res = s.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(false, top.second().valueBool);
        assertEquals(".", top.first());
        assertEquals(1, res);
    }

    @Test
    void sup_simple_bool(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value(true)));

        SupInstruction s = new SupInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> s.execute(0, memory));
    }

    @Test
    void sup_simple_string(){
        storage.add(new ASTMocks.Pair<>("opl",new Value(1)));
        storage.add(new ASTMocks.Pair<>("opr",new Value("t")));

        SupInstruction s = new SupInstruction();
        assertThrows(ASTInvalidTypeException.class, () -> s.execute(0, memory));
    }

    //inc
    @Test
    void inc_simple_int(){
        storage.push(new ASTMocks.Pair<>("test",new Value(10)));
        storage.push(new ASTMocks.Pair<>(".",new Value(5)));

        IncInstruction i = new IncInstruction("test");
        var res = i.execute(0,memory);

        ASTMocks.Pair<String, Value> top = storage.pop();
        assertEquals(15, top.second().valueInt);
        assertEquals("test", top.first());
        assertEquals(1, res);
    }

    @Test
    void inc_simple_bool(){
        storage.push(new ASTMocks.Pair<>("test",new Value(10)));
        storage.push(new ASTMocks.Pair<>(".",new Value(true)));

        IncInstruction i = new IncInstruction("test");

        assertThrows(ASTInvalidTypeException.class, () -> i.execute(0, memory));
    }

    @Test
    void inc_simple_string(){
        storage.push(new ASTMocks.Pair<>("test",new Value(10)));
        storage.push(new ASTMocks.Pair<>(".",new Value("t")));

        IncInstruction i = new IncInstruction("test");

        assertThrows(ASTInvalidTypeException.class, () -> i.execute(0, memory));
    }


    //pop
    @Test
    void pop_simple() throws Exception{
        PushInstruction p1 = new PushInstruction(new Value(5));
        p1.execute(0, memory);
        PopInstruction p2 = new PopInstruction();
        int next = p2.execute(1, memory);
        assertEquals(2, next);
        assertTrue(storage.isEmpty());
    }

    @Test
    void pop_empty() {
        PopInstruction p = new PopInstruction();
        assertThrows(StackIsEmptyException.class, () -> p.execute(0, memory));
    }

    //swap
    @Test
    void swap_simple_instruction() throws Exception {
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

    @Test
    void swap_instruction_not_enough_elements(){
        PushInstruction p1 = new PushInstruction(new Value(5));
        p1.execute(0, memory);

        SwapInstruction swap = new SwapInstruction();
        assertThrows(MemoryIllegalArgException.class, () -> swap.execute(1, memory));
    }

    //goto
    @Test
    void goto_instruction() throws Exception {
        GotoInstruction i = new GotoInstruction(5);
        int next = i.execute(0, memory);
        assertEquals(5, next);
    }

    //nop
    @Test
    void nop_instruction() throws Exception {
        NopInstruction i = new NopInstruction();
        int next = i.execute(0, memory);
        assertEquals(1, next);
    }

    //write
    @Test
    void write_integer() throws Exception {
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
    void write_string() throws Exception {
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
    void write_boolean() throws Exception{
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

    //writeln
    @Test
    void writeln_integer() throws Exception {
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
    void writeln_boolean() throws Exception {
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
    void writeln_string() throws Exception {
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

    //return
    @Test
    void return_valid_address() throws Exception{
        PushInstruction p = new PushInstruction(new Value(5));
        p.execute(0, memory);

        ReturnInstruction r = new ReturnInstruction();
        int next = r.execute(1, memory);
        assertEquals(5, next);
        assertTrue(storage.isEmpty());
    }

    @Test
    void return_string_throws_exception(){
        PushInstruction p = new PushInstruction(new Value("Hello"));
        p.execute(0, memory);

        ReturnInstruction r = new ReturnInstruction();
        assertThrows(IllegalStateException.class, () -> r.execute(1, memory));
    }

    @Test
    void return_stack_empty(){
        ReturnInstruction r = new ReturnInstruction();
        assertThrows(StackIsEmptyException.class, () -> r.execute(0, memory));
    }

}
