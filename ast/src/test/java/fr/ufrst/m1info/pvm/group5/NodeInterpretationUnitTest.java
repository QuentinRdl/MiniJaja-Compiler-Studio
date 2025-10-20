package fr.ufrst.m1info.pvm.group5;

import org.junit.jupiter.api.*;

import fr.ufrst.m1info.pvm.group5.Nodes.*;

import java.util.HashMap;
import java.util.Map;

import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
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
    public void AddNodeOperationTest(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(5));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(10));
        AddNode tested = new AddNode(lop,rop);
        assertEquals(15, tested.eval(memory).valueInt);
    }

    @Test
    public void AddNodeMissingOperand(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(5));
        assertThrows(ASTBuildException.class, () -> new AddNode(lop,null));
    }

    @Test
    public void AddNodeInvalidOperand(){
        ASTNode lop = mock(ASTNode.class);
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(10));
        assertThrows(ASTBuildException.class, () -> new AddNode(lop,rop));
    }
}
