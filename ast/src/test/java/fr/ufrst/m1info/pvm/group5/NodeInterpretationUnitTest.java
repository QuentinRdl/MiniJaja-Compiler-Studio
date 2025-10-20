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
     * Binary operators common tests
     */
    @Test
    public void BOPNode_MissingOperand(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(5));
        assertThrows(ASTBuildException.class, () -> new AddNode(lop,null));
    }

    @Test
    public void BOPNode_InvalidOperand(){
        ASTNode lop = mock(ASTNode.class);
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(10));
        assertThrows(ASTBuildException.class, () -> new AddNode(lop,rop));
    }

    @Test
    public void BOPNode_InvalidOperation(){
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(10));
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(5));
        AddNode addNode = new AddNode(lop,rop);
        assertThrows(ASTInvalidOperationException.class, () -> addNode.interpret(memory));
    }

    /**
     * ADD node
     */
    @Test
    public void AddNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(5));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(10));
        AddNode tested = new AddNode(lop,rop);
        assertEquals(15, tested.eval(memory).valueInt);
    }

    /**
     * Affectation node
     */
    @Test
    public void AffectationNode_Default(){
        IdentNode lop = new IdentNode("x");
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(5));
        memoryStorage.put("x", null);
        AffectationNode tested = new AffectationNode(lop,rop);
        tested.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    public void AffectationNode_InvalidOperand(){
        IdentNode lop = new IdentNode("x");
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,null, m -> new Value(5));
        assertThrows(ASTBuildException.class, () -> new AffectationNode(lop,null));
        assertThrows(ASTBuildException.class, () -> new AffectationNode(null,rop));
        ASTNode newrop =  mock(ASTNode.class);
        assertThrows(ASTBuildException.class, () -> new AffectationNode(lop,null));
    }


}
