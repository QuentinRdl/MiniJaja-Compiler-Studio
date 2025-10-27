package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import org.junit.jupiter.api.*;

import fr.ufrst.m1info.pvm.group5.ast.Nodes.*;

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
        VariableNode decl = ASTMocks.createNode(VariableNode.class, m -> m.declVar("x", new Value(1), DataType.INT), null);
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

    @Test
    public void DeclarationsNode_SeveralDeclarations() {
        VariableNode decl = ASTMocks.createNode(VariableNode.class, m -> m.declVar("x", new Value(1), DataType.INT), null);
        FinalNode decl1 = ASTMocks.createNode(FinalNode.class, m -> m.declVar("y", new Value(2), DataType.INT), null);
        VariableNode decl2 = ASTMocks.createNode(VariableNode.class, m -> m.declVar("z", new Value(3), DataType.INT), null);
        DeclarationsNode decls = new DeclarationsNode(decl,
                new DeclarationsNode(
                        decl1,
                        new DeclarationsNode(decl2, null)));
        decls.interpret(memory);
        assertEquals(1, memoryStorage.get("x").valueInt);
        assertEquals(2, memoryStorage.get("y").valueInt);
        assertEquals(3, memoryStorage.get("z").valueInt);
    }

    @Test
    public void DeclarationsNode_SeveralDeclarations_Order() {
        VariableNode decl = ASTMocks.createNode(VariableNode.class, m -> m.declVar("x", new Value(1), DataType.INT), null);
        FinalNode decl1 = ASTMocks.createNode(FinalNode.class, m -> {
            int a = ((Value)m.val("x")).valueInt;
            m.declVar("y", new Value(a + 1), DataType.INT);
        }, null);
        VariableNode decl2 = ASTMocks.createNode(VariableNode.class, m -> {
            int a = ((Value)m.val("y")).valueInt;
            m.declVar("z", new Value(a + 1), DataType.INT);
        }, null);
        DeclarationsNode decls = new DeclarationsNode(decl,
                new DeclarationsNode(
                        decl1,
                        new DeclarationsNode(decl2, null)));
        decls.interpret(memory);
        assertEquals(1, memoryStorage.get("x").valueInt);
        assertEquals(2, memoryStorage.get("y").valueInt);
        assertEquals(3, memoryStorage.get("z").valueInt);
    }

    @Test
    public void DeclarationsNode_OneDeclaration_EmptyMemory() {
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        VariableNode decl = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("x", new Value(1), DataType.INT),
                null,
                m->m.withdrawDecl("x")
                );
        DeclarationsNode decls = new DeclarationsNode(decl, null);
        decls.interpret(mem);
        decls.withradawInterpret(mem);
        assertFalse( memoryStorage.containsKey("x"));
    }

    @Test
    public void DeclarationsNode_SeveralDeclarations_EmptyMemory() {
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        VariableNode decl = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("x", new Value(1), DataType.INT),
                null,
                m->m.withdrawDecl("x")
        );
        VariableNode decl1 = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("y", new Value(2), DataType.INT),
                null,
                m->m.withdrawDecl("y")
        );
        VariableNode decl2 = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("z", new Value(3), DataType.INT),
                null,
                m->m.withdrawDecl("z")
        );
        DeclarationsNode decls = new DeclarationsNode(decl, new DeclarationsNode(decl1, new DeclarationsNode(decl2, null)));
        decls.interpret(mem);
        decls.withradawInterpret(mem);
        assertFalse( memoryStorage.containsKey("x"));
        assertFalse( memoryStorage.containsKey("y"));
        assertFalse( memoryStorage.containsKey("z"));
    }

    @Test
    public void DeclarationsNode_WithdrawOrder(){
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        VariableNode decl = ASTMocks.createWithdrawNode(VariableNode.class, // Declarations Withdrawals should be done in the inverse order of declarations
                m -> m.declVar("x", new Value(1), DataType.INT),
                null,
                m->{
                    assertFalse( memoryStorage.containsKey("y") || memoryStorage.containsKey("z"));
                    m.withdrawDecl("x");
                }
        );
        VariableNode decl1 = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("y", new Value(2), DataType.INT),
                null,
                m->{
                    assertFalse(memoryStorage.containsKey("z"));
                    m.withdrawDecl("y");
                }
        );
        VariableNode decl2 = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("z", new Value(3), DataType.INT),
                null,
                m->m.withdrawDecl("z")
        );
        DeclarationsNode decls = new DeclarationsNode(decl, new DeclarationsNode(decl1, new DeclarationsNode(decl2, null)));
        decls.interpret(mem);
        decls.withradawInterpret(mem);
        assertFalse( memoryStorage.containsKey("x"));
        assertFalse( memoryStorage.containsKey("y"));
        assertFalse( memoryStorage.containsKey("z"));
    }

    /**
     * DivNode
     */
    @Test
    public void DivNode_Operation() {
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(10));
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(5));
        DivNode tested = new DivNode(lop,rop);
        assertEquals(2, tested.eval(memory).valueInt);
    }

    @Test
    public void DivNode_DivisionByZero(){
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(10));
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(0));
        DivNode tested = new DivNode(lop,rop);
        assertThrows(ASTInvalidOperationException.class, () -> tested.eval(memory));
    }

    @Test
    public void DivNode_NumeratorAtZero(){
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(10));
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(0));
        DivNode tested = new DivNode(lop,rop);
        assertEquals(0, tested.eval(memory).valueInt);
    }

    /**
     * EqualNode
     */
    @Test
    public void EqualNode_Operation_Numbers() {
        NumberNode ten = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(10));
        NumberNode five = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(5));
        NumberNode five2 = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(5));
        EqualNode tested = new EqualNode(ten,five);
        assertFalse(tested.eval(memory).valueBool);
        tested = new EqualNode(five,ten);
        assertFalse(tested.eval(memory).valueBool);
        tested = new EqualNode(five,five2);
        assertTrue(tested.eval(memory).valueBool);
    }

    @Test
    public void EqualNode_Operation_Booleans() {
        NumberNode t1 = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(true));
        NumberNode f1 = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(false));
        NumberNode t2 = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(true));
        NumberNode f2 = ASTMocks.createEvalNode(NumberNode.class, null,null, m -> new Value(false));
        EqualNode tested = new EqualNode(t1,t2);
        assertTrue(tested.eval(memory).valueBool);
        tested = new EqualNode(t1,f1);
        assertFalse(tested.eval(memory).valueBool);
        tested = new EqualNode(f1,t2);
        assertFalse(tested.eval(memory).valueBool);
        tested = new EqualNode(f1,f2);
        assertTrue(tested.eval(memory).valueBool);
    }

    /**
     * Skipping FinalNode, as the tests would be the same as the VariableNode within the representation of the memory we use now
     */

    /**
     * IdentNode
     */

    @Test
    public void IdentNode_Integer(){
        IdentNode id = new IdentNode("a");
        memory.declVar("a", new Value(5),  DataType.INT);
        assertEquals(5, id.eval(memory).valueInt);
    }

    @Test
    public void IdentNode_Boolean(){
        IdentNode id = new IdentNode("a");
        memory.declVar("a", new Value(true),  DataType.BOOL);
        assertTrue(id.eval(memory).valueBool);
    }

    @Test
    public void IdentNode_Undeclared(){
        IdentNode id = new IdentNode("a");
        assertThrows(ASTInvalidMemoryException.class, () -> id.eval(memory));
    }
}
