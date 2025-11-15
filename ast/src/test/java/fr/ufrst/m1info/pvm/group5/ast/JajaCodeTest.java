package fr.ufrst.m1info.pvm.group5.ast;
import fr.ufrst.m1info.pvm.group5.ast.Instructions.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.ast.ASTMocks.Pair;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


public class JajaCodeTest {
    @Mock
    Memory memory;
    Stack<Pair<String, Value>> storage;

    @BeforeEach
    public void setup(){
        storage = new Stack<>();
        memory = ASTMocks.createMemoryWithStack(storage);
    }

    //TODO : Do the tests once the jajacode has been implemented.
    /**
    @Disabled
    @Test
    public void fromFile_simple() throws IOException {
        List<Instruction> instrs = Jajacode.fromFile("src/test/resources/Simple.jjc");
        assertEquals(12, instrs.size());
        assertTrue(instrs.get(0) instanceof InitInstruction);
        assertTrue(instrs.get(1) instanceof PushInstruction);
        PushInstruction p = instrs.get(1);
        assertEquals(3, p.get().valueInt);
        assertTrue(instrs.get(2) instanceof StoreInstruction);
        assertTrue(instrs.get(3) instanceof PushInstruction);
        p = instrs.get(3);
        assertEquals(3, p.get().valueInt);
        assertTrue(instrs.get(4) instanceof StoreInstruction);
        assertTrue(instrs.get(5) instanceof LoadInstruction);
        assertTrue(instrs.get(6) instanceof LoadInstruction);
        assertTrue(instrs.get(7) instanceof OpBinInstruction);
        assertTrue(instrs.get(8) instanceof PopInstruction);
        assertTrue(instrs.get(9) instanceof PopInstruction);
        assertTrue(instrs.get(10) instanceof PopInstruction);
        assertTrue(instrs.get(11) instanceof JcstopInstruction);
    }
    */

}

