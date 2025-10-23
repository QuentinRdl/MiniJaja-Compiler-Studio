package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.SymbolTable.DataType;
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
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(5));
        assertThrows(ASTBuildException.class, () -> new AddNode(lop,null));
    }

    @Test
    public void BOPNode_InvalidOperand(){
        ASTNode lop = mock(ASTNode.class);
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(10));
        assertThrows(ASTBuildException.class, () -> new AddNode(lop,rop));
    }

    @Test
    public void BOPNode_InvalidOperation(){
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(10));
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(5));
        AddNode addNode = new AddNode(lop,rop);
        assertThrows(ASTInvalidOperationException.class, () -> addNode.interpret(memory));
    }

    /**
     * ADD node
     */
    @Test
    public void AddNode_Operation(){
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(5));
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(10));
        AddNode tested = new AddNode(lop,rop);
        assertEquals(15, tested.eval(memory).valueInt);
    }

    /**
     * Affectation node
     */
    @Test
    public void AffectationNode_Default(){
        IdentNode lop = new IdentNode("x");
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(5));
        memoryStorage.put("x", null);
        AffectationNode tested = new AffectationNode(lop,rop);
        tested.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    public void AffectationNode_InvalidOperand(){
        IdentNode lop = new IdentNode("x");
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(5));
        assertThrows(ASTBuildException.class, () -> new AffectationNode(lop,null));
        assertThrows(ASTBuildException.class, () -> new AffectationNode(null,rop));
        ASTNode newrop =  mock(ASTNode.class);
        assertThrows(ASTBuildException.class, () -> new AffectationNode(lop,null));
    }

    /**
     * And Node
     */
    @Test
    public void AndNode_Operation(){
        BooleanNode TNode = ASTMocks.createEvalNode(BooleanNode.class, null,null, m -> new Value(true));
        BooleanNode FNode = ASTMocks.createEvalNode(BooleanNode.class, null,null, m -> new Value(false));
        AndNode tested = new AndNode(TNode,FNode);
        assertEquals(false, tested.eval(memory).valueBool);
        tested = new AndNode(TNode,TNode);
        assertEquals(true, tested.eval(memory).valueBool);
        tested = new AndNode(FNode,FNode);
        assertEquals(false, tested.eval(memory).valueBool);
        tested = new AndNode(FNode,TNode);
        assertEquals(false, tested.eval(memory).valueBool);
    }

    /**
     * Binary minus node
     */
    @Test
    public void MinusNode_Operation(){
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(5));
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(10));
        BinMinusNode tested = new BinMinusNode(lop,rop);
        assertEquals(-5, tested.eval(memory).valueInt);
    }

    /**
     * Boolean Node
     */
    @Test
    public void BooleanNode_Evaluation(){
        BooleanNode t = new BooleanNode(true);
        BooleanNode f = new BooleanNode(false);
        assertTrue(t.eval(memory).valueBool);
        assertFalse(f.eval(memory).valueBool);
    }

    @Test
    public void BooleanNode_InvalidOperation(){
        BooleanNode t = new BooleanNode(true);
        assertThrows(ASTInvalidOperationException.class, () -> t.interpret(memory));
    }

    /**
     * Class Node
     */
    @Test
    public void ClassNode_NoContent(){
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {}, null);
        ClassNode c = new ClassNode(ident, null, main);
        c.interpret(memory);
        assertTrue(memoryStorage.containsKey("C"));
    }

    @Test
    public void ClassNode_NoContent_EmptyMemory(){
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {}, null);
        ClassNode c = new ClassNode(ident, null, main);
        Memory m = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        c.interpret(m);
        assertTrue(memoryStorage.isEmpty());
    }

    @Test
    public void ClassNode_Declarations(){
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {}, null);
        DeclarationsNode d = ASTMocks.createWithdrawNode(
                DeclarationsNode.class,
                m->m.declVar("x", new Value(5), DataType.INT),
                null,
                m->{}
                );
        ClassNode c = new ClassNode(ident, d, main);
        c.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
        assertTrue(memoryStorage.containsKey("x"));
    }

    @Test
    public void ClassNode_Declarations_EmptyMemory(){
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {}, null);
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        DeclarationsNode d = ASTMocks.createWithdrawNode(
                DeclarationsNode.class,
                m->m.declVar("x", new Value(5), DataType.INT),
                null,
                m->m.withdrawDecl("x")
        );
        ClassNode c = new ClassNode(ident, d, main);
        c.interpret(mem);
        assertTrue(memoryStorage.isEmpty());
    }

    @Test
    public void ClassNode_Instructions(){
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(
                MainNode.class,
                m->m.declVar("x", new Value(5), DataType.INT),
                null
        );
        DeclarationsNode d = ASTMocks.createWithdrawNode(DeclarationsNode.class, m -> {}, null, m->{});
        ClassNode c = new ClassNode(ident, d, main);
        c.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    public void ClassNode_Instructions_Order(){
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(
                MainNode.class,
                m->m.affectValue("x", new Value(10)),
                null
        );
        DeclarationsNode d = ASTMocks.createWithdrawNode(
                DeclarationsNode.class,
                m->m.declVar("x", new Value(5), DataType.INT),
                null,
                m->m.withdrawDecl("x")
        );
        ClassNode c = new ClassNode(ident, d, main);
        c.interpret(memory);
        assertEquals(10, memoryStorage.get("x").valueInt);
    }

    @Test
    public void ClassNode_MissingIdent(){
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {}, null);
        assertThrows(ASTBuildException.class,() -> new ClassNode(null, null, main));
    }

    @Test
    public void ClassNode_MissingMain(){
        IdentNode ident = new IdentNode("C");
        assertThrows(ASTBuildException.class,() -> new ClassNode(ident, null, null));
    }

    @Test
    public void ClassNode_NonWithdrawableDecls(){
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {}, null);
        assertThrows(ASTBuildException.class,() -> new ClassNode(ident, ASTMocks.createNode(ASTNode.class, null, null), main));
    }

    /**
     * DeclarationsNodes
     */
    @Test
    public void DeclarationsNode_OneDeclaration(){
        VariableNode decl = ASTMocks.createNode(VariableNode.class, m -> memoryStorage.put("x", new Value(1)), null);
        DeclarationsNode decls = new DeclarationsNode(decl, null);
        decls.interpret(memory);
        assertEquals(1, memoryStorage.get("x").valueInt);
    }

    @Test
    public void DeclarationsNode_MissingDeclaration(){
        assertThrows(ASTBuildException.class, () -> new DeclarationsNode(null, null));
    }

    @Test
    public void DeclarationsNode_InvalidDeclaration() {
        ASTNode decl = ASTMocks.createNode(ASTNode.class, m -> {
        }, null);
        assertThrows(ASTBuildException.class, () -> new DeclarationsNode(decl, null));
    }
}
