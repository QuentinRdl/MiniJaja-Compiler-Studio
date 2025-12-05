package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import org.junit.jupiter.api.*;

import fr.ufrst.m1info.pvm.group5.ast.nodes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InOrder;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NodeInterpretationUnitTest {

    Map<String, Value> memoryStorage;
    @Mock
    Memory memory;

    @BeforeEach
    public void setup() {
        memoryStorage = new HashMap<>();
        memory = ASTMocks.createMemoryWithStorage(memoryStorage);
    }

    /**
     * Binary operators common tests
     */
    @Test
    void BOPNode_MissingOperand() {
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        assertThrows(ASTBuildException.class, () -> new AddNode(lop, null));
    }

    @Test
    void BOPNode_InvalidOperand() {
        ASTNode lop = mock(ASTNode.class);
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(10));
        assertThrows(ASTBuildException.class, () -> new AddNode(lop, rop));
    }

    @Test
    void BOPNode_InvalidOperation() {
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(10));
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        AddNode addNode = new AddNode(lop, rop);
        assertThrows(ASTInvalidOperationException.class, () -> addNode.interpret(memory));
    }

    /**
     * ADD node
     */
    @Test
    void AddNode_Operation() {
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(10));
        AddNode tested = new AddNode(lop, rop);
        assertEquals(15, tested.eval(memory).valueInt);
    }

    /**
     * Affectation node
     */
    @Test
    void AffectationNode_Default() {
        IdentNode lop = new IdentNode("x");
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        memoryStorage.put("x", null);
        AffectationNode tested = new AffectationNode(lop, rop);
        tested.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    void AffectationNode_UndefinedOperand() {
        IdentNode lop = new IdentNode("x");
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        AffectationNode tested = new AffectationNode(lop, rop);
        assertThrows(ASTInvalidMemoryException.class, () -> tested.interpret(memory));
    }

    @Test
    void AffectationNode_MissingOperand() {
        assertThrows(ASTBuildException.class, () -> new AffectationNode(new IdentNode("x"), null));
    }

    @Test
    void AffectationNode_InvalidOperand_NullRightOperand() {
        IdentNode lop = new IdentNode("x");
        assertThrows(ASTBuildException.class, () -> new AffectationNode(lop, null));
    }

    @Test
    void AffectationNode_InvalidOperand_NullLeftOperand() {
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        assertThrows(ASTBuildException.class, () -> new AffectationNode(null, rop));
    }


    /**
     * And Node
     */
    @Test
    void AndNode_Operation() {
        BooleanNode TNode = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(true));
        BooleanNode FNode = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(false));
        AndNode tested = new AndNode(TNode, FNode);
        assertEquals(false, tested.eval(memory).valueBool);
        tested = new AndNode(TNode, TNode);
        assertEquals(true, tested.eval(memory).valueBool);
        tested = new AndNode(FNode, FNode);
        assertEquals(false, tested.eval(memory).valueBool);
        tested = new AndNode(FNode, TNode);
        assertEquals(false, tested.eval(memory).valueBool);
    }

    /**
     * Binary minus node
     */
    @Test
    void MinusNode_Operation() {
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(10));
        BinMinusNode tested = new BinMinusNode(lop, rop);
        assertEquals(-5, tested.eval(memory).valueInt);
    }

    /**
     * Boolean Node
     */
    @Test
    void BooleanNode_Evaluation() {
        BooleanNode t = new BooleanNode(true);
        BooleanNode f = new BooleanNode(false);
        assertTrue(t.eval(memory).valueBool);
        assertFalse(f.eval(memory).valueBool);
    }

    @Test
    void BooleanNode_InvalidOperation() {
        BooleanNode t = new BooleanNode(true);
        assertThrows(ASTInvalidOperationException.class, () -> t.interpret(memory));
    }

    /**
     * Class Node
     */
    @Test
    void ClassNode_NoContent() {
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {
        }, null);
        ClassNode c = new ClassNode(ident, null, main);
        c.interpret(memory);
        assertTrue(memoryStorage.containsKey("C"));
    }

    @Test
    void ClassNode_NoContent_EmptyMemory() {
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {
        }, null);
        ClassNode c = new ClassNode(ident, null, main);
        Memory m = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        c.interpret(m);
        assertTrue(memoryStorage.isEmpty());
    }

    @Test
    void ClassNode_Declarations() {
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {
        }, null);
        DeclarationsNode d = ASTMocks.createWithdrawNode(
                DeclarationsNode.class,
                m -> m.declVar("x", new Value(5), DataType.INT),
                null,
                m -> {
                },
                null
        );
        ClassNode c = new ClassNode(ident, d, main);
        c.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
        assertTrue(memoryStorage.containsKey("x"));
    }

    @Test
    void ClassNode_Declarations_EmptyMemory() {
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {
        }, null);
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        DeclarationsNode d = ASTMocks.createWithdrawNode(
                DeclarationsNode.class,
                m -> m.declVar("x", new Value(5), DataType.INT),
                null,
                m -> m.withdrawDecl("x"),
                null
        );
        ClassNode c = new ClassNode(ident, d, main);
        c.interpret(mem);
        assertTrue(memoryStorage.isEmpty());
    }

    @Test
    void ClassNode_Instructions() {
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(
                MainNode.class,
                m -> m.declVar("x", new Value(5), DataType.INT),
                null
        );
        DeclarationsNode d = ASTMocks.createWithdrawNode(DeclarationsNode.class, m -> {
        }, null, m -> {
        }, null);
        ClassNode c = new ClassNode(ident, d, main);
        c.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    void ClassNode_Instructions_Order() {
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(
                MainNode.class,
                m -> m.affectValue("x", new Value(10)),
                null
        );
        DeclarationsNode d = ASTMocks.createWithdrawNode(
                DeclarationsNode.class,
                m -> m.declVar("x", new Value(5), DataType.INT),
                null,
                m -> m.withdrawDecl("x"),
                null
        );
        ClassNode c = new ClassNode(ident, d, main);
        c.interpret(memory);
        assertEquals(10, memoryStorage.get("x").valueInt);
    }

    @Test
    void ClassNode_MissingIdent() {
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {
        }, null);
        assertThrows(ASTBuildException.class, () -> new ClassNode(null, null, main));
    }

    @Test
    void ClassNode_MissingMain() {
        IdentNode ident = new IdentNode("C");
        assertThrows(ASTBuildException.class, () -> new ClassNode(ident, null, null));
    }

    @Test
    void ClassNode_NonWithdrawableDecls() {
        IdentNode ident = new IdentNode("C");
        MainNode main = ASTMocks.createNode(MainNode.class, m -> {
        }, null);
        assertThrows(ASTBuildException.class, () -> new ClassNode(ident, ASTMocks.createNode(ASTNode.class, null, null), main));
    }

    /**
     * DeclarationsNodes
     */
    @Test
    void DeclarationsNode_OneDeclaration() {
        VariableNode decl = ASTMocks.createNode(VariableNode.class, m -> m.declVar("x", new Value(1), DataType.INT), null);
        DeclarationsNode decls = new DeclarationsNode(decl, null);
        decls.interpret(memory);
        assertEquals(1, memoryStorage.get("x").valueInt);
    }

    @Test
    void DeclarationsNode_MissingDeclaration() {
        assertThrows(ASTBuildException.class, () -> new DeclarationsNode(null, null));
    }

    @Test
    void DeclarationsNode_InvalidDeclaration() {
        ASTNode decl = ASTMocks.createNode(ASTNode.class, m -> {
        }, null);
        assertThrows(ASTBuildException.class, () -> new DeclarationsNode(decl, null));
    }

    @Test
    void DeclarationsNode_SeveralDeclarations() {
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
    void DeclarationsNode_SeveralDeclarations_Order() {
        VariableNode decl = ASTMocks.createNode(VariableNode.class, m -> m.declVar("x", new Value(1), DataType.INT), null);
        FinalNode decl1 = ASTMocks.createNode(FinalNode.class, m -> {
            int a = ((Value) m.val("x")).valueInt;
            m.declVar("y", new Value(a + 1), DataType.INT);
        }, null);
        VariableNode decl2 = ASTMocks.createNode(VariableNode.class, m -> {
            int a = ((Value) m.val("y")).valueInt;
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
    void DeclarationsNode_OneDeclaration_EmptyMemory() {
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        VariableNode decl = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("x", new Value(1), DataType.INT),
                null,
                m -> m.withdrawDecl("x"),
                null
        );
        DeclarationsNode decls = new DeclarationsNode(decl, null);
        decls.interpret(mem);
        decls.withdrawInterpret(mem);
        assertFalse(memoryStorage.containsKey("x"));
    }

    @Test
    void DeclarationsNode_SeveralDeclarations_EmptyMemory() {
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        VariableNode decl = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("x", new Value(1), DataType.INT),
                null,
                m -> m.withdrawDecl("x"),
                null
        );
        VariableNode decl1 = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("y", new Value(2), DataType.INT),
                null,
                m -> m.withdrawDecl("y"),
                null
        );
        VariableNode decl2 = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("z", new Value(3), DataType.INT),
                null,
                m -> m.withdrawDecl("z"),
                null
        );
        DeclarationsNode decls = new DeclarationsNode(decl, new DeclarationsNode(decl1, new DeclarationsNode(decl2, null)));
        decls.interpret(mem);
        decls.withdrawInterpret(mem);
        assertFalse(memoryStorage.containsKey("x"));
        assertFalse(memoryStorage.containsKey("y"));
        assertFalse(memoryStorage.containsKey("z"));
    }

    @Test
    void DeclarationsNode_WithdrawOrder() {
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        VariableNode decl = ASTMocks.createWithdrawNode(VariableNode.class, // Declarations Withdrawals should be done in the inverse order of declarations
                m -> m.declVar("x", new Value(1), DataType.INT),
                null,
                m -> {
                    assertFalse(memoryStorage.containsKey("y") || memoryStorage.containsKey("z"));
                    m.withdrawDecl("x");
                },
                null
        );
        VariableNode decl1 = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("y", new Value(2), DataType.INT),
                null,
                m -> {
                    assertFalse(memoryStorage.containsKey("z"));
                    m.withdrawDecl("y");
                },
                null
        );
        VariableNode decl2 = ASTMocks.createWithdrawNode(VariableNode.class,
                m -> m.declVar("z", new Value(3), DataType.INT),
                null,
                m -> m.withdrawDecl("z"),
                null
        );
        DeclarationsNode decls = new DeclarationsNode(decl, new DeclarationsNode(decl1, new DeclarationsNode(decl2, null)));
        decls.interpret(mem);
        decls.withdrawInterpret(mem);
        assertFalse(memoryStorage.containsKey("x"));
        assertFalse(memoryStorage.containsKey("y"));
        assertFalse(memoryStorage.containsKey("z"));
    }

    /**
     * DivNode
     */
    @Test
    void DivNode_Operation() {
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(10));
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        DivNode tested = new DivNode(lop, rop);
        assertEquals(2, tested.eval(memory).valueInt);
    }

    @Test
    void DivNode_DivisionByZero() {
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(10));
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(0));
        DivNode tested = new DivNode(lop, rop);
        assertThrows(ASTInvalidOperationException.class, () -> tested.eval(memory));
    }

    @Test
    void DivNode_NumeratorAtZero() {
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(10));
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(0));
        DivNode tested = new DivNode(lop, rop);
        assertEquals(0, tested.eval(memory).valueInt);
    }

    /**
     * EqualNode
     */
    @Test
    void EqualNode_Operation_Numbers() {
        NumberNode ten = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(10));
        NumberNode five = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        NumberNode five2 = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        EqualNode tested = new EqualNode(ten, five);
        assertFalse(tested.eval(memory).valueBool);
        tested = new EqualNode(five, ten);
        assertFalse(tested.eval(memory).valueBool);
        tested = new EqualNode(five, five2);
        assertTrue(tested.eval(memory).valueBool);
    }

    @Test
    void EqualNode_Operation_Booleans() {
        NumberNode t1 = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(true));
        NumberNode f1 = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(false));
        NumberNode t2 = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(true));
        NumberNode f2 = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(false));
        EqualNode tested = new EqualNode(t1, t2);
        assertTrue(tested.eval(memory).valueBool);
        tested = new EqualNode(t1, f1);
        assertFalse(tested.eval(memory).valueBool);
        tested = new EqualNode(f1, t2);
        assertFalse(tested.eval(memory).valueBool);
        tested = new EqualNode(f1, f2);
        assertTrue(tested.eval(memory).valueBool);
    }

    /**
     * Skipping FinalNode, as the tests would be the same as the VariableNode within the representation of the memory we use now
     */

    /**
     * IdentNode
     */

    @Test
    void IdentNode_Integer() {
        IdentNode id = new IdentNode("a");
        memory.declVar("a", new Value(5), DataType.INT);
        assertEquals(5, id.eval(memory).valueInt);
    }

    @Test
    void IdentNode_Boolean() {
        IdentNode id = new IdentNode("a");
        memory.declVar("a", new Value(true), DataType.BOOL);
        assertTrue(id.eval(memory).valueBool);
    }

    @Test
    void IdentNode_Undeclared() {
        IdentNode id = new IdentNode("a");
        assertThrows(ASTInvalidMemoryException.class, () -> id.eval(memory));
    }

    /**
     * IfNode
     */

    @Test
    void IfNode_NoCondition() {
        assertThrows(ASTBuildException.class, () -> new IfNode(null, null, null));
    }

    @Test
    void IfNode_NonEvaluableCondition() {
        ASTNode expr = ASTMocks.createNode(ASTNode.class, null, null);
        assertThrows(ASTBuildException.class, () -> new IfNode(expr, null, null));
    }

    @Test
    void IfNode_NoInstructions() {
        BooleanNode expr = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(true));
        IfNode node = new IfNode(expr, null, null);
        node.interpret(memory);
        assertTrue(memoryStorage.isEmpty());
    }

    @Test
    void IfNode_OnlyThen_True() {
        BooleanNode expr = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(true));
        ASTNode then = ASTMocks.createNode(ASTNode.class, m -> m.declVar("x", new Value(5), DataType.INT), null);
        IfNode node = new IfNode(expr, then, null);
        node.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    void IfNode_OnlyThen_False() {
        BooleanNode expr = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(false));
        ASTNode then = ASTMocks.createNode(ASTNode.class, m -> m.declVar("x", new Value(5), DataType.INT), null);
        IfNode node = new IfNode(expr, then, null);
        node.interpret(memory);
        assertTrue(memoryStorage.isEmpty());
    }

    @Test
    void IfNode_OnlyElse_True() {
        BooleanNode expr = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(true));
        ASTNode other = ASTMocks.createNode(ASTNode.class, m -> m.declVar("x", new Value(5), DataType.INT), null);
        IfNode node = new IfNode(expr, null, other);
        node.interpret(memory);
        assertTrue(memoryStorage.isEmpty());
    }

    @Test
    void IfNode_OnlyElse_False() {
        BooleanNode expr = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(false));
        ASTNode other = ASTMocks.createNode(ASTNode.class, m -> m.declVar("x", new Value(5), DataType.INT), null);
        IfNode node = new IfNode(expr, null, other);
        node.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    void IfNode_BothInstructions_True() {
        BooleanNode expr = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(true));
        ASTNode then = ASTMocks.createNode(ASTNode.class, m -> m.affectValue("x", new Value(5)), null);
        ASTNode other = ASTMocks.createNode(ASTNode.class, m -> m.affectValue("x", new Value(6)), null);
        IfNode node = new IfNode(expr, then, other);
        memoryStorage.put("x", new Value());
        node.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    void IfNode_BothInstructions_False() {
        BooleanNode expr = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(false));
        ASTNode then = ASTMocks.createNode(ASTNode.class, m -> m.affectValue("x", new Value(5)), null);
        ASTNode other = ASTMocks.createNode(ASTNode.class, m -> m.affectValue("x", new Value(6)), null);
        IfNode node = new IfNode(expr, then, other);
        memoryStorage.put("x", new Value());
        node.interpret(memory);
        assertEquals(6, memoryStorage.get("x").valueInt);
    }

    /**
     * IncNode
     */
    @Test
    void IncNode_NoIdentifier() {
        assertThrows(ASTBuildException.class, () -> new IncNode(null));
    }

    @Test
    void IncNode_InvalidIdentifier() {
        IdentNode ident = new IdentNode("x");
        IncNode inc = new IncNode(ident);
        assertThrows(ASTInvalidMemoryException.class, () -> inc.interpret(memory));
    }

    @Test
    void IncNode() {
        IdentNode ident = new IdentNode("x");
        IncNode inc = new IncNode(ident);
        memoryStorage.put("x", new Value(5));
        inc.interpret(memory);
        assertEquals(6, memoryStorage.get("x").valueInt);
    }
    @Test
    @DisplayName("IncNode - interpret() with array access and constant index")
    void IncNode_ArrayAccess_ConstantIndex() {
        IdentNode arrayIdent = new IdentNode("tab");
        NumberNode indexExpr = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(2));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            int index = invocation.getArgument(1);
            if ("tab".equals(ident) && index == 2) {
                return new Value(10);
            }
            throw new ASTInvalidMemoryException("Array access error");
        }).when(memory).valT(any(String.class), any(Integer.class));
        final Value[] capturedValue = new Value[1];
        doAnswer(invocation -> {
            capturedValue[0] = invocation.getArgument(2);
            return null;
        }).when(memory).affectValT(any(String.class), any(Integer.class), any(Value.class));

        IncNode inc = new IncNode(tabNode);
        inc.interpret(memory);
        assertNotNull(capturedValue[0]);
        assertEquals(11, capturedValue[0].valueInt);
        verify(memory).valT("tab", 2);
        verify(memory).affectValT(eq("tab"), eq(2), any(Value.class));
    }

    @Test
    @DisplayName("IncNode - interpret() with array access and variable index")
    void IncNode_ArrayAccess_VariableIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        IdentNode indexExpr = new IdentNode("i");
        memoryStorage.put("i", new Value(5));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            int index = invocation.getArgument(1);
            if ("arr".equals(ident) && index == 5) {
                return new Value(42);
            }
            throw new ASTInvalidMemoryException("Array access error");
        }).when(memory).valT(any(String.class), any(Integer.class));
        final Value[] capturedValue = new Value[1];
        final int[] capturedIndex = new int[1];
        doAnswer(invocation -> {
            capturedIndex[0] = invocation.getArgument(1);
            capturedValue[0] = invocation.getArgument(2);
            return null;
        }).when(memory).affectValT(any(String.class), any(Integer.class), any(Value.class));

        IncNode inc = new IncNode(tabNode);
        inc.interpret(memory);
        assertNotNull(capturedValue[0]);
        assertEquals(43, capturedValue[0].valueInt);
        assertEquals(5, capturedIndex[0]);
        verify(memory).valT("arr", 5);
        verify(memory).affectValT(eq("arr"), eq(5), any(Value.class));
    }

    @Test
    @DisplayName("IncNode - interpret() with array access and expression index")
    void IncNode_ArrayAccess_ExpressionIndex() {
        IdentNode arrayIdent = new IdentNode("data");
        IdentNode varIdent = new IdentNode("i");
        NumberNode oneNode = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(1));
        AddNode indexExpr = new AddNode(varIdent, oneNode);
        memoryStorage.put("i", new Value(3));

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            int index = invocation.getArgument(1);
            if ("data".equals(ident) && index == 4) {
                return new Value(100);
            }
            throw new ASTInvalidMemoryException("Array access error");
        }).when(memory).valT(any(String.class), any(Integer.class));
        final Value[] capturedValue = new Value[1];
        doAnswer(invocation -> {
            capturedValue[0] = invocation.getArgument(2);
            return null;
        }).when(memory).affectValT(any(String.class), any(Integer.class), any(Value.class));

        IncNode inc = new IncNode(tabNode);
        inc.interpret(memory);
        assertNotNull(capturedValue[0]);
        assertEquals(101, capturedValue[0].valueInt);
        verify(memory).valT("data", 4);
        verify(memory).affectValT(eq("data"), eq(4), any(Value.class));
    }

    @Test
    @DisplayName("IncNode - interpret() fails with non-integer index")
    void IncNode_ArrayAccess_NonIntegerIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        BooleanNode indexExpr = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(true));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        IncNode inc = new IncNode(tabNode);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> inc.interpret(memory));
    }

    @Test
    @DisplayName("IncNode - interpret() with array access at index 0")
    void IncNode_ArrayAccess_IndexZero() {
        IdentNode arrayIdent = new IdentNode("values");
        NumberNode indexExpr = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(0));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            int index = invocation.getArgument(1);
            if ("values".equals(ident) && index == 0) {
                return new Value(999);
            }
            throw new ASTInvalidMemoryException("Array access error");
        }).when(memory).valT(any(String.class), any(Integer.class));
        final Value[] capturedValue = new Value[1];
        doAnswer(invocation -> {
            capturedValue[0] = invocation.getArgument(2);
            return null;
        }).when(memory).affectValT(any(String.class), any(Integer.class), any(Value.class));

        IncNode inc = new IncNode(tabNode);
        inc.interpret(memory);
        assertNotNull(capturedValue[0]);
        assertEquals(1000, capturedValue[0].valueInt);
        verify(memory).valT("values", 0);
        verify(memory).affectValT(eq("values"), eq(0), any(Value.class));
    }

    @Test
    @DisplayName("IncNode - interpret() with negative initial value")
    void IncNode_ArrayAccess_NegativeValue() {
        IdentNode arrayIdent = new IdentNode("numbers");
        NumberNode indexExpr = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(1));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            int index = invocation.getArgument(1);
            if ("numbers".equals(ident) && index == 1) {
                return new Value(-5);
            }
            throw new ASTInvalidMemoryException("Array access error");
        }).when(memory).valT(any(String.class), any(Integer.class));
        final Value[] capturedValue = new Value[1];
        doAnswer(invocation -> {
            capturedValue[0] = invocation.getArgument(2);
            return null;
        }).when(memory).affectValT(any(String.class), any(Integer.class), any(Value.class));

        IncNode inc = new IncNode(tabNode);
        inc.interpret(memory);
        assertNotNull(capturedValue[0]);
        assertEquals(-4, capturedValue[0].valueInt);
        verify(memory).valT("numbers", 1);
        verify(memory).affectValT(eq("numbers"), eq(1), any(Value.class));
    }

    /**
     * InstructionsNode
     */
    @Test
    void InstructionsNode_MissingInstruction() {
        assertThrows(ASTBuildException.class, () -> new InstructionsNode(null, null));
    }

    @Test
    void InstructionsNode_OneInstruction() {
        ASTNode node = ASTMocks.createNode(
                ASTNode.class,
                m -> m.affectValue("x", new Value(5)),
                null
        );
        memoryStorage.put("x", new Value());
        InstructionsNode instrs = new InstructionsNode(node, null);
        instrs.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    void InstructionsNode_MultipleInstructions() {
        ASTNode first = ASTMocks.createNode(
                ASTNode.class,
                m -> m.affectValue("x", new Value(5)),
                null
        );
        ASTNode second = ASTMocks.createNode(
                ASTNode.class,
                m -> {
                    assertEquals(5, memoryStorage.get("x").valueInt);
                    m.affectValue("x", new Value(6));
                },
                null
        );
        ASTNode third = ASTMocks.createNode(
                ASTNode.class,
                m -> {
                    assertEquals(6, memoryStorage.get("x").valueInt);
                    m.affectValue("x", new Value(7));
                },
                null
        );
        memoryStorage.put("x", new Value());
        InstructionsNode instrs = new InstructionsNode(first, new InstructionsNode(second, new InstructionsNode(third, null)));
        instrs.interpret(memory);
        assertEquals(7, memoryStorage.get("x").valueInt);
    }

    /**
     * Main Node
     */
    @Test
    void MainNode_InvalidVariables() {
        ASTNode vars = ASTMocks.createNode(ASTNode.class, null, null);
        assertThrows(ASTBuildException.class, () -> new MainNode(vars, null));
    }

    @Test
    void MainNode_OnlyVariables() {
        VariableNode vars = ASTMocks.createWithdrawNode(
                VariableNode.class,
                m -> m.declVar("x", new Value(5), DataType.INT),
                null,
                m -> {
                },
                null
        );
        MainNode main = new MainNode(vars, null);
        main.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    void MainNode_OnlyVariables_EmptyMemory() {
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        VariableNode vars = ASTMocks.createWithdrawNode(
                VariableNode.class,
                m -> m.declVar("x", new Value(5), DataType.INT),
                null,
                m -> m.withdrawDecl("x"),
                null
        );
        MainNode main = new MainNode(vars, null);
        main.interpret(mem);
        assertFalse(memoryStorage.containsKey("x"));
    }

    @Test
    void MainNode_OnlyInstrs() {
        InstructionsNode instrs = ASTMocks.createNode(
                InstructionsNode.class,
                m -> m.affectValue("x", new Value(10)),
                null
        );
        memoryStorage.put("x", new Value());
        MainNode main = new MainNode(null, instrs);
        main.interpret(memory);
        assertEquals(10, memoryStorage.get("x").valueInt);
    }

    @Test
    void MainNode_Complete() {
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        InstructionsNode instrs = ASTMocks.createNode(
                InstructionsNode.class,
                m -> m.declVar("y", m.val("x"), DataType.INT),
                null
        );
        VariableNode vars = ASTMocks.createWithdrawNode(
                VariableNode.class,
                m -> m.declVar("x", new Value(5), DataType.INT),
                null,
                m -> m.withdrawDecl("x"),
                null
        );
        MainNode main = new MainNode(vars, instrs);
        main.interpret(mem);
        assertFalse(memoryStorage.containsKey("x"));
        assertEquals(5, memoryStorage.get("y").valueInt);
    }

    /**
     * Mul node
     */

    @Test
    void MulNode_Operation() {
        NumberNode lop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(10));
        MulNode tested = new MulNode(lop, rop);
        assertEquals(50, tested.eval(memory).valueInt);
    }

    /**
     * Not node
     */

    @Test
    void NotNode_Operation() {
        NumberNode t = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(true));
        NumberNode f = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(false));
        NotNode not = new NotNode(t);
        assertFalse(not.eval(memory).valueBool);
        not = new NotNode(f);
        assertTrue(not.eval(memory).valueBool);
    }

    @Test
    void NotNode_InvalidOperation() {
        NumberNode t = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(true));
        NotNode not = new NotNode(t);
        assertThrows(ASTInvalidOperationException.class, () -> not.interpret(memory));
    }

    @Test
    void NotNode_InvalidOperand() {
        ASTNode node = ASTMocks.createNode(ASTNode.class, null, null);
        assertThrows(ASTBuildException.class, () -> new NotNode(node));
    }

    /**
     * NumberNode
     */
    @Test
    void NumberNode_Evaluation() {
        NumberNode n = new NumberNode(5);
        assertEquals(5, n.eval(memory).valueInt);
    }

    @Test
    void NumberNode_InvalidOperation() {
        NumberNode n = new NumberNode(5);
        assertThrows(ASTInvalidOperationException.class, () -> n.interpret(memory));
    }

    /**
     * OrNode
     */
    @Test
    void OrNode_Operation() {
        BooleanNode TNode = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(true));
        BooleanNode FNode = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(false));
        OrNode tested = new OrNode(TNode, FNode);
        assertEquals(true, tested.eval(memory).valueBool);
        tested = new OrNode(TNode, TNode);
        assertEquals(true, tested.eval(memory).valueBool);
        tested = new OrNode(FNode, FNode);
        assertEquals(false, tested.eval(memory).valueBool);
        tested = new OrNode(FNode, TNode);
        assertEquals(true, tested.eval(memory).valueBool);
    }

    /**
     * ReturnNode
     */
    @Test
    void ReturnNode_MissingExpression() {
        assertThrows(ASTBuildException.class, () -> new ReturnNode(null));
    }

    @Test
    void ReturnNode_InvalidOperand() {
        ASTNode node = ASTMocks.createNode(ASTNode.class, null, null);
        assertThrows(ASTBuildException.class, () -> new ReturnNode(node));
    }

    @Test
    void ReturnNode_Valid() {
        memory.declVarClass("C");
        ASTMocks.addClassVariableToMock(memory, "C");
        NumberNode n = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        ReturnNode tested = new ReturnNode(n);
        tested.interpret(memory);
        assertEquals(5, memoryStorage.get("C").valueInt);
    }

    /**
     * SumNode
     */

    @Test
    void SumNode_Default() {
        IdentNode lop = new IdentNode("x");
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        memoryStorage.put("x", new Value(10));
        SumNode tested = new SumNode(lop, rop);
        tested.interpret(memory);
        assertEquals(15, memoryStorage.get("x").valueInt);
    }

    @Test
    void SumNode_UndefinedOperand() {
        IdentNode lop = new IdentNode("x");
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        SumNode tested = new SumNode(lop, rop);
        assertThrows(ASTInvalidMemoryException.class, () -> tested.interpret(memory));
    }

    @Test
    void SumNode_MissingOperand() {
        assertThrows(ASTBuildException.class, () -> new SumNode(new IdentNode("x"), null));
    }

    @Test
    void SumNode_InvalidOperand_NullRightOperand() {
        IdentNode lop = new IdentNode("x");
        assertThrows(ASTBuildException.class, () -> new SumNode(lop, null));
    }

    @Test
    void SumNode_InvalidOperand_NullLeftOperand() {
        NumberNode rop = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        assertThrows(ASTBuildException.class, () -> new SumNode(null, rop));
    }
    @Test
    @DisplayName("SumNode - interpret() with array access and constant index")
    void SumNode_ArrayAccess_ConstantIndex() {
        IdentNode arrayIdent = new IdentNode("tab");
        NumberNode indexExpr = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(2));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        NumberNode valueExpr = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(10));
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            int index = invocation.getArgument(1);
            if ("tab".equals(ident) && index == 2) {
                return new Value(20);
            }
            throw new ASTInvalidMemoryException("Array access error");
        }).when(memory).valT(any(String.class), any(Integer.class));
        final Value[] capturedValue = new Value[1];
        doAnswer(invocation -> {
            capturedValue[0] = invocation.getArgument(2);
            return null;
        }).when(memory).affectValT(any(String.class), any(Integer.class), any(Value.class));

        SumNode sumNode = new SumNode(tabNode, valueExpr);
        sumNode.interpret(memory);
        assertNotNull(capturedValue[0]);
        assertEquals(30, capturedValue[0].valueInt);
        verify(memory).valT("tab", 2);
        verify(memory).affectValT(eq("tab"), eq(2), any(Value.class));
    }

    @Test
    @DisplayName("SumNode - interpret() with array access and variable index")
    void SumNode_ArrayAccess_VariableIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        IdentNode indexExpr = new IdentNode("i");
        memoryStorage.put("i", new Value(5));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        IdentNode valueExpr = new IdentNode("x");
        memoryStorage.put("x", new Value(7));
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            int index = invocation.getArgument(1);
            if ("arr".equals(ident) && index == 5) {
                return new Value(42);
            }
            throw new ASTInvalidMemoryException("Array access error");
        }).when(memory).valT(any(String.class), any(Integer.class));
        final Value[] capturedValue = new Value[1];
        final int[] capturedIndex = new int[1];
        doAnswer(invocation -> {
            capturedIndex[0] = invocation.getArgument(1);
            capturedValue[0] = invocation.getArgument(2);
            return null;
        }).when(memory).affectValT(any(String.class), any(Integer.class), any(Value.class));

        SumNode sumNode = new SumNode(tabNode, valueExpr);
        sumNode.interpret(memory);
        assertNotNull(capturedValue[0]);
        assertEquals(49, capturedValue[0].valueInt);
        assertEquals(5, capturedIndex[0]);
        verify(memory).valT("arr", 5);
        verify(memory).affectValT(eq("arr"), eq(5), any(Value.class));
    }

    @Test
    @DisplayName("SumNode - interpret() with array access and expression index")
    void SumNode_ArrayAccess_ExpressionIndex() {
        IdentNode arrayIdent = new IdentNode("data");
        IdentNode varIdent = new IdentNode("i");
        NumberNode oneNode = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(1));
        AddNode indexExpr = new AddNode(varIdent, oneNode);
        memoryStorage.put("i", new Value(3));

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        IdentNode valueExpr = new IdentNode("y");
        memoryStorage.put("y", new Value(15));
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            int index = invocation.getArgument(1);
            if ("data".equals(ident) && index == 4) {
                return new Value(100);
            }
            throw new ASTInvalidMemoryException("Array access error");
        }).when(memory).valT(any(String.class), any(Integer.class));
        final Value[] capturedValue = new Value[1];
        doAnswer(invocation -> {
            capturedValue[0] = invocation.getArgument(2);
            return null;
        }).when(memory).affectValT(any(String.class), any(Integer.class), any(Value.class));

        SumNode sumNode = new SumNode(tabNode, valueExpr);
        sumNode.interpret(memory);
        assertNotNull(capturedValue[0]);
        assertEquals(115, capturedValue[0].valueInt);
        verify(memory).valT("data", 4);
        verify(memory).affectValT(eq("data"), eq(4), any(Value.class));
    }

    @Test
    @DisplayName("SumNode - interpret() fails with non-integer index")
    void SumNode_ArrayAccess_NonIntegerIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        BooleanNode indexExpr = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(true));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        NumberNode valueExpr = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));

        SumNode sumNode = new SumNode(tabNode, valueExpr);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> sumNode.interpret(memory));
    }

    @Test
    @DisplayName("SumNode - interpret() fails with non-integer value")
    void SumNode_ArrayAccess_NonIntegerValue() {
        IdentNode arrayIdent = new IdentNode("arr");
        NumberNode indexExpr = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(2));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        BooleanNode valueExpr = ASTMocks.createEvalNode(BooleanNode.class, null, null, m -> new Value(true));

        SumNode sumNode = new SumNode(tabNode, valueExpr);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> sumNode.interpret(memory));
    }

    @Test
    @DisplayName("SumNode - interpret() with array access at index 0")
    void SumNode_ArrayAccess_IndexZero() {
        IdentNode arrayIdent = new IdentNode("values");
        NumberNode indexExpr = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(0));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        NumberNode valueExpr = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(50));
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            int index = invocation.getArgument(1);
            if ("values".equals(ident) && index == 0) {
                return new Value(999);
            }
            throw new ASTInvalidMemoryException("Array access error");
        }).when(memory).valT(any(String.class), any(Integer.class));
        final Value[] capturedValue = new Value[1];
        doAnswer(invocation -> {
            capturedValue[0] = invocation.getArgument(2);
            return null;
        }).when(memory).affectValT(any(String.class), any(Integer.class), any(Value.class));

        SumNode sumNode = new SumNode(tabNode, valueExpr);
        sumNode.interpret(memory);
        assertNotNull(capturedValue[0]);
        assertEquals(1049, capturedValue[0].valueInt);
        verify(memory).valT("values", 0);
        verify(memory).affectValT(eq("values"), eq(0), any(Value.class));
    }

    @Test
    @DisplayName("SumNode - interpret() with negative values")
    void SumNode_ArrayAccess_NegativeValues() {
        IdentNode arrayIdent = new IdentNode("numbers");
        NumberNode indexExpr = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(1));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        NumberNode valueExpr = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(-10));
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            int index = invocation.getArgument(1);
            if ("numbers".equals(ident) && index == 1) {
                return new Value(5);
            }
            throw new ASTInvalidMemoryException("Array access error");
        }).when(memory).valT(any(String.class), any(Integer.class));
        final Value[] capturedValue = new Value[1];
        doAnswer(invocation -> {
            capturedValue[0] = invocation.getArgument(2);
            return null;
        }).when(memory).affectValT(any(String.class), any(Integer.class), any(Value.class));

        SumNode sumNode = new SumNode(tabNode, valueExpr);
        sumNode.interpret(memory);
        assertNotNull(capturedValue[0]);
        assertEquals(-5, capturedValue[0].valueInt);
        verify(memory).valT("numbers", 1);
        verify(memory).affectValT(eq("numbers"), eq(1), any(Value.class));
    }

    /**
     * SupNode
     */
    @Test
    void SupNode_Operation() {
        NumberNode ten = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(10));
        NumberNode five = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        NumberNode five2 = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        SupNode tested = new SupNode(ten, five);
        assertTrue(tested.eval(memory).valueBool);
        tested = new SupNode(five, ten);
        assertFalse(tested.eval(memory).valueBool);
        tested = new SupNode(five, five2);
        assertFalse(tested.eval(memory).valueBool);
    }

    /**
     * Unary Minus Node
     */
    @Test
    void UnMinusNode_Operation() {
        NumberNode t = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        UnMinusNode um = new UnMinusNode(t);
        assertEquals(-5, um.eval(memory).valueInt);
    }

    @Test
    void UnMinusNode_InvalidOperation() {
        NumberNode n = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        UnMinusNode um = new UnMinusNode(n);
        assertThrows(ASTInvalidOperationException.class, () -> um.interpret(memory));
    }

    @Test
    void UnMinusNode_InvalidOperand() {
        ASTNode node = ASTMocks.createNode(ASTNode.class, null, null);
        assertThrows(ASTBuildException.class, () -> new UnMinusNode(node));
    }

    /**
     * VariableNode
     */
    @Test
    void VariableNode_MissingType() {
        assertThrows(ASTBuildException.class, () -> new VariableNode(null, new IdentNode("x"), null));
    }

    @Test
    void VariableNode_MissingIdentifierType() {
        assertThrows(ASTBuildException.class, () -> new VariableNode(new TypeNode(ValueType.BOOL), null, null));
    }

    @Test
    void VariableNode_InvalidExpression() {
        ASTNode node = ASTMocks.createNode(ASTNode.class, null, null);
        assertThrows(ASTBuildException.class, () -> new VariableNode(new TypeNode(ValueType.BOOL), new IdentNode("a"), node));
    }

    @Test
    void VariableNode_Declaration_NoExpression() {
        VariableNode var = new VariableNode(new TypeNode(ValueType.BOOL), new IdentNode("x"), null);
        var.interpret(memory);
        assertTrue(memoryStorage.containsKey("x"));
        assertEquals(ValueType.EMPTY, memoryStorage.get("x").type);
    }

    @Test
    void VariableNode_Declaration() {
        NumberNode exp = ASTMocks.createEvalNode(NumberNode.class, null, null, m -> new Value(5));
        VariableNode var = new VariableNode(new TypeNode(ValueType.BOOL), new IdentNode("x"), exp);
        var.interpret(memory);
        assertTrue(memoryStorage.containsKey("x"));
        assertEquals(ValueType.INT, memoryStorage.get("x").type);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    void VariableNode_Withdrawal() {
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        VariableNode var = new VariableNode(new TypeNode(ValueType.BOOL), new IdentNode("x"), null);
        var.interpret(mem);
        var.withdrawInterpret(mem);
        assertTrue(memoryStorage.isEmpty());
    }

    /**
     * VariablesNodes
     */
    @Test
    void VariablesNode_MissingVariable() {
        assertThrows(ASTBuildException.class, () -> new VariablesNode(null, null));
    }

    @Test
    void VariablesNode_InvalidVariable() {
        ASTNode node = ASTMocks.createNode(ASTNode.class, null, null);
        assertThrows(ASTBuildException.class, () -> new VariablesNode(node, null));
    }

    @Test
    void VariablesNode_Declaration_OneDeclaration() {
        VariableNode var = ASTMocks.createWithdrawNode(
                VariableNode.class,
                m -> m.declVar("x", new Value(5), DataType.INT),
                null,
                null,
                null
        );
        VariablesNode tested = new VariablesNode(var, null);
        tested.interpret(memory);
        assertTrue(memoryStorage.containsKey("x"));
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    void VariablesNode_Withdraw_OneDeclaration() {
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        VariableNode var = ASTMocks.createWithdrawNode(
                VariableNode.class,
                m -> m.declVar("x", new Value(5), DataType.INT),
                null,
                m -> m.withdrawDecl("x"),
                null
        );
        VariablesNode tested = new VariablesNode(var, null);
        tested.interpret(mem);
        assertTrue(memoryStorage.containsKey("x"));
        tested.withdrawInterpret(mem);
        assertFalse(memoryStorage.containsKey("x"));
    }

    @Test
    void VariablesNode_Declaration_MultipleDeclarations() {
        VariableNode x = ASTMocks.createWithdrawNode(
                VariableNode.class,
                m -> m.declVar("x", new Value(5), DataType.INT),
                null,
                null,
                null
        );
        VariableNode y = ASTMocks.createWithdrawNode(
                VariableNode.class,
                m -> {
                    assertTrue(memoryStorage.containsKey("x"));
                    m.declVar("y", new Value(5), DataType.INT);
                },
                null,
                null,
                null
        );
        VariableNode z = ASTMocks.createWithdrawNode(
                VariableNode.class,
                m -> {
                    assertTrue(memoryStorage.containsKey("y"));
                    m.declVar("z", new Value(5), DataType.INT);
                },
                null,
                null,
                null
        );
        VariablesNode tested = new VariablesNode(x, new VariablesNode(y, new VariablesNode(z, null)));
        tested.interpret(memory);
        assertTrue(memoryStorage.containsKey("x"));
        assertTrue(memoryStorage.containsKey("y"));
        assertTrue(memoryStorage.containsKey("z"));
    }

    @Test
    void VariablesNode_Withdrawal_MultipleDeclarations() {
        memoryStorage.put("x", new Value(5));
        memoryStorage.put("y", new Value(5));
        memoryStorage.put("z", new Value(5));
        Memory mem = ASTMocks.createMemoryWithWithdraw(memoryStorage);
        VariableNode z = ASTMocks.createWithdrawNode(
                VariableNode.class,
                null,
                null,
                m -> m.withdrawDecl("z"),
                null
        );
        VariableNode y = ASTMocks.createWithdrawNode(
                VariableNode.class,
                null,
                null,
                m -> {
                    assertFalse(memoryStorage.containsKey("z"));
                    m.withdrawDecl("y");
                },
                null
        );
        VariableNode x = ASTMocks.createWithdrawNode(
                VariableNode.class,
                null,
                null,
                m -> {
                    assertFalse(memoryStorage.containsKey("y"));
                    m.withdrawDecl("x");
                },
                null
        );
        VariablesNode tested = new VariablesNode(x, new VariablesNode(y, new VariablesNode(z, null)));
        tested.withdrawInterpret(mem);
        assertTrue(memoryStorage.isEmpty());
    }

    /**
     * WhileNode
     */
    @Test
    void WhileNode_MissingCondition() {
        assertThrows(ASTBuildException.class, () -> new WhileNode(null, null));
    }

    @Test
    void WhileNode_InvalidCondition() {
        ASTNode node = ASTMocks.createNode(ASTNode.class, null, null);
        assertThrows(ASTBuildException.class, () -> new WhileNode(node, null));
    }

    @Test
    void WhileNode_NoIteration() {
        memoryStorage.put("x", new Value(10));
        BooleanNode node = ASTMocks.createEvalNode(
                BooleanNode.class,
                null,
                null,
                m -> new Value(false)
        );
        ASTNode instr = ASTMocks.createNode(ASTNode.class, m -> m.affectValue("x", new Value(5)), null);
        WhileNode wn = new WhileNode(node, instr);
        wn.interpret(memory);
        assertEquals(10, memoryStorage.get("x").valueInt);
    }

    @Test
    void WhileNode_OneIteration() {
        memoryStorage.put("x", new Value(10));
        BooleanNode node = ASTMocks.createEvalNode(
                BooleanNode.class,
                null,
                null,
                m -> new Value(memoryStorage.get("x").valueInt != 5)
        );
        ASTNode instr = ASTMocks.createNode(ASTNode.class, m -> m.affectValue("x", new Value(memoryStorage.get("x").valueInt - 5)), null);
        WhileNode wn = new WhileNode(node, instr);
        wn.interpret(memory);
        assertEquals(5, memoryStorage.get("x").valueInt);
    }

    @Test
    void WhileNode_Stressed() {
        memoryStorage.put("x", new Value(100000));
        BooleanNode node = ASTMocks.createEvalNode(
                BooleanNode.class,
                null,
                null,
                m -> new Value(memoryStorage.get("x").valueInt > 0)
        );
        ASTNode instr = ASTMocks.createNode(
                ASTNode.class,
                m -> m.affectValue("x", new Value(memoryStorage.get("x").valueInt - 1)),
                null
        );
        WhileNode wn = new WhileNode(node, instr);
        wn.interpret(memory);
        assertEquals(0, memoryStorage.get("x").valueInt);
    }

    /**
     * WriteLineNode
     */
    @Test
    void WriteLineNode_String() {
        List<String> ref = new ArrayList<String>();
        ASTMocks.addWriterToMock(memory, ref);
        WriteLineNode wln = new WriteLineNode("abcd");
        wln.interpret(memory);
        assertEquals(1, ref.size());
        assertEquals("abcd\n", ref.get(0));
    }

    @Test
    void WriteLineNode_Ident_int() {
        List<String> ref = new ArrayList<String>();
        memoryStorage.put("x", new Value(5));
        ASTMocks.addWriterToMock(memory, ref);
        WriteLineNode wln = new WriteLineNode(new IdentNode("x"));
        wln.interpret(memory);
        assertEquals(1, ref.size());
        assertEquals("5\n", ref.get(0));
    }

    @Test
    void WriteLineNode_Ident_bool() {
        List<String> ref = new ArrayList<String>();
        memoryStorage.put("x", new Value(true));
        ASTMocks.addWriterToMock(memory, ref);
        WriteLineNode wln = new WriteLineNode(new IdentNode("x"));
        wln.interpret(memory);
        assertEquals(1, ref.size());
        assertEquals("true\n", ref.get(0));
    }

    @Test
    void WriteLineNode_InvalidIdentifier() {
        List<String> ref = new ArrayList<String>();
        ASTMocks.addWriterToMock(memory, ref);
        WriteLineNode wln = new WriteLineNode(new IdentNode("x"));
        assertThrows(ASTInvalidMemoryException.class, () -> wln.interpret(memory));
    }

    /**
     * WriteNode
     */
    @Test
    void WriteNode_String() {
        List<String> ref = new ArrayList<String>();
        ASTMocks.addWriterToMock(memory, ref);
        WriteNode wln = new WriteNode("abcd");
        wln.interpret(memory);
        assertEquals(1, ref.size());
        assertEquals("abcd", ref.get(0));
    }

    @Test
    void WriteNode_Ident_int() {
        List<String> ref = new ArrayList<String>();
        memoryStorage.put("x", new Value(5));
        ASTMocks.addWriterToMock(memory, ref);
        WriteNode wln = new WriteNode(new IdentNode("x"));
        wln.interpret(memory);
        assertEquals(1, ref.size());
        assertEquals("5", ref.get(0));
    }

    @Test
    void WriteNode_Ident_bool() {
        List<String> ref = new ArrayList<String>();
        memoryStorage.put("x", new Value(true));
        ASTMocks.addWriterToMock(memory, ref);
        WriteNode wln = new WriteNode(new IdentNode("x"));
        wln.interpret(memory);
        assertEquals(1, ref.size());
        assertEquals("true", ref.get(0));
    }

    @Test
    void WriteNode_InvalidIdentifier() {
        List<String> ref = new ArrayList<String>();
        ASTMocks.addWriterToMock(memory, ref);
        WriteNode wln = new WriteNode(new IdentNode("x"));
        assertThrows(ASTInvalidMemoryException.class, () -> wln.interpret(memory));
    }

    @Test
    @DisplayName("ParamNode - interpret() BOOL parameter")
    void testParamNodeInterpret_BOOL() throws Exception {
        TypeNode type = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flag");
        ParamNode node = new ParamNode(type, ident);

        node.interpret(memory);

        assertTrue(memoryStorage.containsKey("flag"));
        Value val = memoryStorage.get("flag");
        assertEquals(ValueType.BOOL, val.type);
        assertFalse(val.valueBool);
    }

    @Test
    @DisplayName("ParamNode - interpret() INT parameter")
    void testParamNodeInterpret_INT() throws Exception {
        TypeNode type = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("x");
        ParamNode node = new ParamNode(type, ident);

        node.interpret(memory);

        assertTrue(memoryStorage.containsKey("x"));
        Value val = memoryStorage.get("x");
        assertEquals(ValueType.INT, val.type);
        assertEquals(0, val.valueInt);
    }

    @Test
    @DisplayName("ParamNode - withdrawInterpret() removes variable")
    void testParamNodeWithdrawInterpret() throws Exception {
        Memory memory = new Memory();
        TypeNode type = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("x");
        ParamNode node = new ParamNode(type, ident);
        node.interpret(memory);
        assertTrue(memory.contains("x"));
        node.withdrawInterpret(memory);
        assertFalse(memory.contains("x"));
    }


    @Test
    @DisplayName("ParamListNode - interpret() declares all parameters")
    void testParamListNodeInterpret() throws Exception {
        ParamNode p1 = new ParamNode(new TypeNode(ValueType.INT), new IdentNode("x"));
        ParamNode p2 = new ParamNode(new TypeNode(ValueType.BOOL), new IdentNode("flag"));
        ParamListNode list = new ParamListNode(p1, new ParamListNode(p2, null));

        list.interpret(memory);

        Value valX = memoryStorage.get("x");
        Value valFlag = memoryStorage.get("flag");

        assertNotNull(valX);
        assertEquals(ValueType.INT, valX.type);
        assertEquals(0, valX.valueInt);

        assertNotNull(valFlag);
        assertEquals(ValueType.BOOL, valFlag.type);
        assertFalse(valFlag.valueBool);
    }

    @Test
    @DisplayName("ParamListNode - withdrawInterpret() removes declarations in reverse order")
    void testParamListNodeWithdrawInterpret() throws Exception {

        Memory m = mock(Memory.class);

        ParamNode p1 = new ParamNode(new TypeNode(ValueType.INT), new IdentNode("x"));
        ParamNode p2 = new ParamNode(new TypeNode(ValueType.BOOL), new IdentNode("flag"));

        ParamListNode list = new ParamListNode(p1, new ParamListNode(p2, null));
        list.interpret(m);
        list.withdrawInterpret(m);

        InOrder order = inOrder(m);
        order.verify(m).declVar(eq("x"), any(), any());
        order.verify(m).declVar(eq("flag"), any(), any());
        order.verify(m).withdrawDecl("flag");
        order.verify(m).withdrawDecl("x");
    }


    @Test
    void testExpListNodeInterpretThrows() {
        ASTNode exp = ASTMocks.createNode(
                ASTNode.class,
                null,
                i -> List.of("push(5)")
        );

        ExpListNode list = new ExpListNode(exp, null);

        assertThrows(ASTInvalidOperationException.class, () -> list.interpret(memory));

    }

    @Test
    void testMethodeNodeInterpret() {
        Memory memory = mock(Memory.class);

        TypeNode returnType = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("foo");
        ASTNode params = ASTMocks.createNode(ASTNode.class, null, i -> List.of());

        MethodeNode method = new MethodeNode(returnType, ident, params, null, null);

        method.interpret(memory);

        verify(memory).declMethod("foo",
                ValueType.toDataType(ValueType.INT),
                method);
    }

    @Test
    void testMethodeNodeInterpret_BoolType() {
        Memory mem = mock(Memory.class);

        MethodeNode m = new MethodeNode(
                new TypeNode(ValueType.BOOL),
                new IdentNode("isReady"),
                ASTMocks.createNode(ASTNode.class, null, i -> List.of()),
                null,
                null
        );

        m.interpret(mem);

        verify(mem).declMethod("isReady", DataType.BOOL, m);
    }

    @Test
    void testMethodeNodeInterpret_VoidType() {
        Memory mem = mock(Memory.class);

        MethodeNode m = new MethodeNode(
                new TypeNode(ValueType.VOID),
                new IdentNode("reset"),
                ASTMocks.createNode(ASTNode.class, null, i -> List.of()),
                null,
                null
        );

        m.interpret(mem);

        verify(mem).declMethod("reset", DataType.VOID, m);
    }

    @Test
    @DisplayName("AppelENode.interpret() - throws ASTInvalidOperationException")
    public void testAppelENode_Interpret_ThrowsException() {
        IdentNode ident = new IdentNode("anyFunc");
        AppelENode node = new AppelENode(ident, null);
        assertThrows(ASTInvalidOperationException.class, () -> node.interpret(memory));
    }
    @Test
    @DisplayName("ArrayNode.interpret() - declares int array with correct size")
    public void testArrayNode_Interpret_IntArray() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("arr");
        NumberNode sizeExpr = new NumberNode(5);

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        assertDoesNotThrow(() -> node.interpret(memory));

        verify(memory).declTab(eq("arr"), eq(5), eq(DataType.INT));
    }

    @Test
    @DisplayName("ArrayNode.interpret() - declares bool array with correct size")
    public void testArrayNode_Interpret_BoolArray() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flags");
        NumberNode sizeExpr = new NumberNode(10);

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        assertDoesNotThrow(() -> node.interpret(memory));

        verify(memory).declTab(eq("flags"), eq(10), eq(DataType.BOOL));
    }

    @Test
    @DisplayName("ArrayNode.interpret() - evaluates size expression correctly")
    public void testArrayNode_Interpret_ExpressionSize() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("matrix");
        NumberNode left = new NumberNode(5);
        NumberNode right = new NumberNode(3);
        AddNode sizeExpr = new AddNode(left, right);

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        assertDoesNotThrow(() -> node.interpret(memory));

        verify(memory).declTab(eq("matrix"), eq(8), eq(DataType.INT));
    }

    @Test
    @DisplayName("ArrayNode.interpret() - throws exception for non-int size")
    public void testArrayNode_Interpret_NonIntSize() {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("arr");
        BooleanNode sizeExpr = new BooleanNode(true);

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.interpret(memory));
    }

    @Test
    @DisplayName("ArrayNode.interpret() - throws exception for negative size")
    public void testArrayNode_Interpret_NegativeSize() {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("arr");
        NumberNode sizeExpr = new NumberNode(-5);

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        assertThrows(ASTInvalidOperationException.class, () -> node.interpret(memory));
    }

    @Test
    @DisplayName("ArrayNode.interpret() - throws exception for zero size")
    public void testArrayNode_Interpret_ZeroSize() {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("arr");
        NumberNode sizeExpr = new NumberNode(0);

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        assertThrows(ASTInvalidOperationException.class, () -> node.interpret(memory));
    }

    @Test
    @DisplayName("ArrayNode.withdrawInterpret() - withdraws array declaration")
    public void testArrayNode_WithdrawInterpret() {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("arr");
        NumberNode sizeExpr = new NumberNode(5);

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        assertDoesNotThrow(() -> node.withdrawInterpret(memory));

        verify(memory).withdrawDecl(eq("arr"));
    }

    @Test
    @DisplayName("ArrayNode.withdrawInterpret() - works for bool array")
    public void testArrayNode_WithdrawInterpret_BoolArray() {
        TypeNode typeNode = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flags");
        NumberNode sizeExpr = new NumberNode(10);

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        assertDoesNotThrow(() -> node.withdrawInterpret(memory));

        verify(memory).withdrawDecl(eq("flags"));
    }

    @Test
    @DisplayName("TabNode.interpret() - throws exception (not interpretable)")
    public void testTabNode_Interpret_ThrowsException() {
        IdentNode ident = new IdentNode("arr");
        NumberNode indexExpr = new NumberNode(0);

        TabNode node = new TabNode(ident, indexExpr);
        assertThrows(ASTInvalidOperationException.class, () -> node.interpret(memory));
    }

    @Test
    @DisplayName("TabNode.eval() - returns value at valid index")
    public void testTabNode_Eval_ValidIndex() {
        IdentNode ident = new IdentNode("arr");
        NumberNode indexExpr = new NumberNode(2);

        when(memory.tabLength("arr")).thenReturn(5);
        when(memory.valT("arr", 2)).thenReturn(new Value(42));

        TabNode node = new TabNode(ident, indexExpr);
        Value result = node.eval(memory);

        assertEquals(ValueType.INT, result.type);
        assertEquals(42, result.valueInt);
        verify(memory).valT(eq("arr"), eq(2));
    }

    @Test
    @DisplayName("TabNode.eval() - returns bool value at valid index")
    public void testTabNode_Eval_ValidBoolIndex() {
        IdentNode ident = new IdentNode("flags");
        NumberNode indexExpr = new NumberNode(0);

        when(memory.tabLength("flags")).thenReturn(3);
        when(memory.valT("flags", 0)).thenReturn(new Value(true));

        TabNode node = new TabNode(ident, indexExpr);
        Value result = node.eval(memory);

        assertEquals(ValueType.BOOL, result.type);
        assertTrue(result.valueBool);
        verify(memory).valT(eq("flags"), eq(0));
    }

    @Test
    @DisplayName("TabNode.eval() - works with expression as index")
    public void testTabNode_Eval_ExpressionIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        NumberNode left = new NumberNode(2);
        NumberNode right = new NumberNode(3);
        AddNode indexExpr = new AddNode(left, right);

        when(memory.tabLength("arr")).thenReturn(10);
        when(memory.valT("arr", 5)).thenReturn(new Value(123));

        TabNode node = new TabNode(arrayIdent, indexExpr);
        Value result = node.eval(memory);

        assertEquals(ValueType.INT, result.type);
        assertEquals(123, result.valueInt);
        verify(memory).valT(eq("arr"), eq(5));
    }

    @Test
    @DisplayName("TabNode.eval() - throws exception for negative index")
    public void testTabNode_Eval_NegativeIndex() {
        IdentNode ident = new IdentNode("arr");
        NumberNode indexExpr = new NumberNode(-1);

        when(memory.tabLength("arr")).thenReturn(5);

        TabNode node = new TabNode(ident, indexExpr);
        assertThrows(ASTInvalidOperationException.class, () -> node.eval(memory));
    }

    @Test
    @DisplayName("TabNode.eval() - throws exception for index out of bounds")
    public void testTabNode_Eval_IndexOutOfBounds() {
        IdentNode ident = new IdentNode("arr");
        NumberNode indexExpr = new NumberNode(10);

        when(memory.tabLength("arr")).thenReturn(5);

        TabNode node = new TabNode(ident, indexExpr);
        assertThrows(ASTInvalidOperationException.class, () -> node.eval(memory));
    }

    @Test
    @DisplayName("TabNode.eval() - throws exception for index equal to array length")
    public void testTabNode_Eval_IndexEqualToLength() {
        IdentNode ident = new IdentNode("arr");
        NumberNode indexExpr = new NumberNode(5);

        when(memory.tabLength("arr")).thenReturn(5);

        TabNode node = new TabNode(ident, indexExpr);
        assertThrows(ASTInvalidOperationException.class, () -> node.eval(memory));
    }

    @Test
    @DisplayName("TabNode.eval() - throws exception for non-int index")
    public void testTabNode_Eval_NonIntIndex() {
        IdentNode ident = new IdentNode("arr");
        BooleanNode indexExpr = new BooleanNode(true);

        when(memory.tabLength("arr")).thenReturn(5);

        TabNode node = new TabNode(ident, indexExpr);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.eval(memory));
    }

    @Test
    @DisplayName("TabNode.eval() - throws exception for non-evaluable index")
    public void testTabNode_Eval_NonEvaluableIndex() {
        IdentNode ident = new IdentNode("arr");
        ASTNode indexExpr = mock(ASTNode.class);

        TabNode node = new TabNode(ident, indexExpr);
        assertThrows(ASTInvalidOperationException.class, () -> node.eval(memory));
    }

    @Test
    @DisplayName("TabNode.eval() - accesses first element (index 0)")
    public void testTabNode_Eval_FirstElement() {
        IdentNode ident = new IdentNode("arr");
        NumberNode indexExpr = new NumberNode(0);

        when(memory.tabLength("arr")).thenReturn(5);
        when(memory.valT("arr", 0)).thenReturn(new Value(100));

        TabNode node = new TabNode(ident, indexExpr);
        Value result = node.eval(memory);

        assertEquals(ValueType.INT, result.type);
        assertEquals(100, result.valueInt);
    }

    @Test
    @DisplayName("TabNode.eval() - accesses last element")
    public void testTabNode_Eval_LastElement() {
        IdentNode ident = new IdentNode("arr");
        NumberNode indexExpr = new NumberNode(4);

        when(memory.tabLength("arr")).thenReturn(5);
        when(memory.valT("arr", 4)).thenReturn(new Value(200));

        TabNode node = new TabNode(ident, indexExpr);
        Value result = node.eval(memory);

        assertEquals(ValueType.INT, result.type);
        assertEquals(200, result.valueInt);
    }

}
