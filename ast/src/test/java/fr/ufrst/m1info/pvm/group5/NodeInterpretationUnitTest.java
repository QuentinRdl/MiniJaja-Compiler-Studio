package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.Nodes.ASTNode;
import fr.ufrst.m1info.pvm.group5.Nodes.AddNode;
import fr.ufrst.m1info.pvm.group5.Nodes.NumberNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NodeInterpretationUnitTest {

    Map<String, Value> memoryStorage;
    @Mock
    Memory memory;

    @BeforeEach
    public void setup(){
        memoryStorage = new HashMap<>();
        memory = ASTMocks.createMemoryWithStorage(memoryStorage);
    }

    /**
     * ADD node
     */
    @Test
    @DisplayName("Add - Default")
    public void AddNodeOperationTest(){
        NumberNode lop = mock();
        when(lop.eval(any(Memory.class))).thenReturn(new Value(5));
        NumberNode rop = mock();
        when(rop.eval(any(Memory.class))).thenReturn(new Value(10));
        AddNode tested = new AddNode(lop,rop);
        assertEquals(10, rop.eval(memory).valueInt);
    }
}
