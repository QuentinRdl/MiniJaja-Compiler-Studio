package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.Instructions.PushInstruction;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

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

    //
}
