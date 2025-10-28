package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.Nodes.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CheckDynamicTypeTest {

    Map<String, Value> memoryStorage;
    @Mock
    Memory memoryMock;

    @BeforeEach
    public void setup() throws Exception {
        memoryStorage = new HashMap<>();
        memoryMock = mock(Memory.class);
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            return memoryStorage.get(ident);
        }).when(memoryMock).val(any(String.class));
    }

    @Test
    @DisplayName("IdentNode - checkType() with int")
    public void testIdentNode_Int() throws Exception {
        memoryStorage.put("x", new Value(10)); // int
        IdentNode node = new IdentNode("x");
        assertEquals("int", node.checkType(memoryMock));
    }

    @Test
    @DisplayName("IdentNode - checkType() with bool")
    public void testIdentNode_Bool() throws Exception {
        memoryStorage.put("flag", new Value(true)); // bool
        IdentNode node = new IdentNode("flag");
        assertEquals("bool", node.checkType(memoryMock));
    }

    @Test
    @DisplayName("IdentNode - checkType() variable not defined")
    public void testIdentNode_Undefined() {
        IdentNode node = new IdentNode("y");
        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AffectationNode.checkType - int = int (valid)")
    public void testAffectationNode_IntOk() throws Exception {
        memoryStorage.put("x", new Value(10));
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");
        AffectationNode affectNode = new AffectationNode(new IdentNode("x"), expr);

        String result = affectNode.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("AffectationNode.checkType - int = bool (error)")
    public void testAffectationNode_IncompatibleTypes() throws Exception {
        memoryStorage.put("x", new Value(5));

        ASTNode exprBool = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(exprBool.checkType(memoryMock)).thenReturn("bool");

        AffectationNode node = new AffectationNode(new IdentNode("x"), exprBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AffectationNode.checkType - undefined variable")
    public void testAffectationNode_UndefinedVariable() throws Exception {
        memoryStorage.put("x", null);

        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        AffectationNode node = new AffectationNode(new IdentNode("x"), expr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AffectationNode.checkType - bool = bool (valid)")
    public void testAffectationNode_BoolOk() throws Exception {
        memoryStorage.put("flag", new Value(true));

        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("bool");

        AffectationNode node = new AffectationNode(new IdentNode("flag"), expr);

        String result = node.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("BinaryOperator - checkType() valid with two int")
    public void testBinaryOperator_IntOk() throws Exception {
        ASTNode left = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(left.checkType(memoryMock)).thenReturn("int");

        ASTNode right = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(right.checkType(memoryMock)).thenReturn("int");

        BinaryOperator op = new BinaryOperator(left, right) {
            @Override
            protected String getCompileName() {
                return "add";
            }

            @Override
            protected Value mainOperation(Value leftOperand, Value rightOperand) {
                return new Value(leftOperand.valueInt + rightOperand.valueInt);
            }
        };

        String result = op.checkType(memoryMock);
        assertEquals("int", result);
    }

    @Test
    @DisplayName("BinaryOperator - checkType() fails if any operand is not int")
    public void testBinaryOperator_NonInt() {
        ASTNode left = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(left.checkType(memoryMock)).thenReturn("int");

        ASTNode right = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(right.checkType(memoryMock)).thenReturn("bool");

        BinaryOperator op = new BinaryOperator(left, right) {
            @Override
            protected String getCompileName() {
                return "add";
            }

            @Override
            protected Value mainOperation(Value leftOperand, Value rightOperand) {
                return new Value(leftOperand.valueInt + rightOperand.valueInt);
            }
        };

        assertThrows(ASTInvalidDynamicTypeException.class, () -> op.checkType(memoryMock));
    }

    @Test
    @DisplayName("BooleanNode - checkType() return bool")
    public void testBooleanNode_CheckType() throws Exception {
        BooleanNode trueNode = new BooleanNode(true);
        BooleanNode falseNode = new BooleanNode(false);

        assertEquals("bool", trueNode.checkType(memoryMock));
        assertEquals("bool", falseNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("ClassNode - checkType() with decls and main")
    public void testClassNode_CheckType_WithDeclsAndMain() throws Exception {
        DummyWithdrNode declsNode = new DummyWithdrNode();
        IdentNode classIdent = new IdentNode("C");
        ASTNode mainNode = mock(ASTNode.class);
        when(mainNode.checkType(memoryMock)).thenReturn("void");

        ClassNode classNode = new ClassNode(classIdent, declsNode, mainNode);

        String result = classNode.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("DeclarationsNode - checkType() with a single statement")
    public void testDeclarationsNode_SingleDecl() throws Exception {
        DummyWithdrNode decl = new DummyWithdrNode();

        DeclarationsNode node = new DeclarationsNode(decl, null);
        String result = node.checkType(memoryMock);

        assertEquals("void", result);
    }

    @Test
    @DisplayName("DeclarationsNode - checkType() with multiple statements")
    public void testDeclarationsNode_MultipleDecls() throws Exception {
        DummyWithdrNode decl1 = new DummyWithdrNode();
        DummyWithdrNode decl2 = new DummyWithdrNode();

        DeclarationsNode node = new DeclarationsNode(decl1, decl2);
        String result = node.checkType(memoryMock);

        assertEquals("void", result);
    }

    @Test
    @DisplayName("FinalNode - checkType() valid int")
    public void testFinalNode_IntOk() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("x");

        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        FinalNode finalNode = new FinalNode(typeNode, ident, expr);
        String result = finalNode.checkType(memoryMock);

        assertEquals("void", result);
    }

    @Test
    @DisplayName("FinalNode - checkType() valid bool")
    public void testFinalNode_BoolOk() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flag");

        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("bool");

        FinalNode finalNode = new FinalNode(typeNode, ident, expr);
        String result = finalNode.checkType(memoryMock);

        assertEquals("void", result);
    }

    @Test
    @DisplayName("FinalNode - checkType() fails if incompatible type")
    public void testFinalNode_TypeMismatch() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("x");

        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("bool");

        FinalNode finalNode = new FinalNode(typeNode, ident, expr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> finalNode.checkType(memoryMock));
    }


    @Test
    @DisplayName("FinalNode - checkType() fails if type not supported")
    public void testFinalNode_UnsupportedType() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.VOID);
        IdentNode ident = new IdentNode("x");
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("void");

        FinalNode finalNode = new FinalNode(typeNode, ident, expr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> finalNode.checkType(memoryMock));
    }


    @Test
    @DisplayName("IfNode - checkType() valid with condition bool")
    public void testIfNode_ValidCondition() throws Exception {
        ASTNode condition = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(condition.checkType(memoryMock)).thenReturn("bool");

        ASTNode instrThen = mock(ASTNode.class);
        ASTNode instrElse = mock(ASTNode.class);

        IfNode ifNode = new IfNode(condition, instrThen, instrElse);
        String result = ifNode.checkType(memoryMock);

        assertEquals("void", result);
        verify(instrThen).checkType(memoryMock);
        verify(instrElse).checkType(memoryMock);
    }

    @Test
    @DisplayName("IfNode - checkType() fails if condition no bool")
    public void testIfNode_InvalidCondition() {
        ASTNode condition = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(condition.checkType(memoryMock)).thenReturn("int"); // mauvais type

        ASTNode instrThen = mock(ASTNode.class);
        ASTNode instrElse = mock(ASTNode.class);

        IfNode ifNode = new IfNode(condition, instrThen, instrElse);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> ifNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("IfNode - checkType() valid if else null")
    public void testIfNode_NullElse() throws Exception {
        ASTNode condition = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(condition.checkType(memoryMock)).thenReturn("bool");

        ASTNode instrThen = mock(ASTNode.class);

        IfNode ifNode = new IfNode(condition, instrThen, null);
        String result = ifNode.checkType(memoryMock);

        assertEquals("void", result);
        verify(instrThen).checkType(memoryMock);
    }

    @Test
    @DisplayName("IfNode - checkType() valid if then null")
    public void testIfNode_NullThen() throws Exception {
        ASTNode condition = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(condition.checkType(memoryMock)).thenReturn("bool");

        ASTNode instrElse = mock(ASTNode.class);

        IfNode ifNode = new IfNode(condition, null, instrElse);
        String result = ifNode.checkType(memoryMock);

        assertEquals("void", result);
        verify(instrElse).checkType(memoryMock);
    }

    @Test
    @DisplayName("IncNode - checkType() valid with int")
    public void testIncNode_IntOk() throws Exception {
        memoryStorage.put("x", new Value(5));

        IdentNode identNode = new IdentNode("x");
        IncNode incNode = new IncNode(identNode);

        assertEquals("int", incNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("IncNode - checkType() fails if variable not defined")
    public void testIncNode_UndefinedVariable() {
        IdentNode identNode = new IdentNode("y"); // pas dans memoryStorage
        IncNode incNode = new IncNode(identNode);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> incNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("IncNode - checkType() fails if variable not int")
    public void testIncNode_NonIntVariable() {
        memoryStorage.put("flag", new Value(true));

        IdentNode identNode = new IdentNode("flag");
        IncNode incNode = new IncNode(identNode);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> incNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("InstructionsNode - checkType() with instruction only")
    public void testInstructionsNode_SingleInstruction() throws Exception {
        ASTNode instr = mock(ASTNode.class);
        when(instr.checkType(memoryMock)).thenReturn("void");

        InstructionsNode node = new InstructionsNode(instr, null);

        assertEquals("void", node.checkType(memoryMock));
        verify(instr, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("InstructionsNode - checkType() with several instructions")
    public void testInstructionsNode_MultipleInstructions() throws Exception {
        ASTNode instr1 = mock(ASTNode.class);
        ASTNode instr2 = mock(ASTNode.class);

        when(instr1.checkType(memoryMock)).thenReturn("void");
        when(instr2.checkType(memoryMock)).thenReturn("void");

        InstructionsNode node = new InstructionsNode(instr1, instr2);

        assertEquals("void", node.checkType(memoryMock));
        verify(instr1, times(1)).checkType(memoryMock);
        verify(instr2, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("InstructionsNode - checkType() fails if statement fails")
    public void testInstructionsNode_FailingInstruction() throws Exception {
        ASTNode instr = mock(ASTNode.class);
        when(instr.checkType(memoryMock)).thenThrow(new ASTInvalidDynamicTypeException("Error"));

        InstructionsNode node = new InstructionsNode(instr, null);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MainNode - checkType() with vars And instrs")
    public void testMainNode_WithVarsAndInstrs() throws Exception {
        ASTNode vars = mock(ASTNode.class, withSettings().extraInterfaces(WithdrawalNode.class));
        ASTNode instrs = mock(ASTNode.class);

        when(vars.checkType(memoryMock)).thenReturn("void");
        when(instrs.checkType(memoryMock)).thenReturn("void");

        MainNode node = new MainNode(vars, instrs);

        assertEquals("void", node.checkType(memoryMock));
        verify(vars, times(1)).checkType(memoryMock);
        verify(instrs, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("MainNode - checkType() With vars null")
    public void testMainNode_VarsNull() throws Exception {
        ASTNode instrs = mock(ASTNode.class);
        when(instrs.checkType(memoryMock)).thenReturn("void");

        MainNode node = new MainNode(null, instrs);

        assertEquals("void", node.checkType(memoryMock));
        verify(instrs, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("MainNode - checkType() With instrs null")
    public void testMainNode_InstrsNull() throws Exception {
        ASTNode vars = mock(ASTNode.class, withSettings().extraInterfaces(WithdrawalNode.class));
        when(vars.checkType(memoryMock)).thenReturn("void");

        MainNode node = new MainNode(vars, null);

        assertEquals("void", node.checkType(memoryMock));
        verify(vars, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("MainNode - checkType() fails if vars fails")
    public void testMainNode_VarsFail() throws Exception {
        ASTNode vars = mock(ASTNode.class, withSettings().extraInterfaces(WithdrawalNode.class));
        when(vars.checkType(memoryMock)).thenThrow(new ASTInvalidDynamicTypeException("Error"));

        MainNode node = new MainNode(vars, null);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MainNode - checkType() fails if instrs fails")
    public void testMainNode_InstrsFail() throws Exception {
        ASTNode instrs = mock(ASTNode.class);
        when(instrs.checkType(memoryMock)).thenThrow(new ASTInvalidDynamicTypeException("Error"));

        MainNode node = new MainNode(null, instrs);

        assertThrows(ASTInvalidDynamicTypeException.class, ()->node.checkType(memoryMock));
    }

    @Test
    @DisplayName("NotNode - checkType() valid with bool")
    public void testNotNode_Bool() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("bool");

        NotNode node = new NotNode(expr);

        assertEquals("bool", node.checkType(memoryMock));
        verify(expr, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("NotNode - checkType() fails if non-bool")
    public void testNotNode_NonBool() {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        NotNode node = new NotNode(expr);

        ASTInvalidDynamicTypeException exception = assertThrows(
                ASTInvalidDynamicTypeException.class,
                () -> node.checkType(memoryMock)
        );
        assertTrue(exception.getMessage().contains("non-bool"));
    }

    @Test
    @DisplayName("NotNode - checkType() fails if expr is null in constructor")
    public void testNotNode_NullExpr() {
        assertThrows(ASTBuildException.class, () -> new NotNode(null));
    }

    @Test
    @DisplayName("NumberNode - checkType() return int")
    public void testNumberNode_CheckType() throws Exception {
        NumberNode node = new NumberNode(42);
        assertEquals("int", node.checkType(memoryMock));
    }

    @Test
    @DisplayName("NumberNode - eval() returns the correct value")
    public void testNumberNode_Eval() throws Exception {
        NumberNode node = new NumberNode(99);
        Value val = node.eval(new Memory());
        assertEquals(99, val.valueInt);
    }

    @Test
    @DisplayName("ReturnNode - checkType() returns type of expression int")
    public void testReturnNode_CheckType_Int() throws Exception {
        NumberNode numberExpr = new NumberNode(10);
        ReturnNode returnNode = new ReturnNode(numberExpr);

        assertEquals("int", returnNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("ReturnNode - checkType() returns type of bool expression")
    public void testReturnNode_CheckType_Bool() throws Exception {
        BooleanNode boolExpr = new BooleanNode(true);
        ReturnNode returnNode = new ReturnNode(boolExpr);

        assertEquals("bool", returnNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("ReturnNode - checkType() fails if expression does not exist")
    public void testReturnNode_CheckType_NullExpr() {
        ReturnNode returnNode = new ReturnNode(null);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> returnNode.checkType(memoryMock));
    }
    @Test
    @DisplayName("SumNode - checkType() valid with int")
    public void testSumNode_IntOk() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(any(Memory.class))).thenReturn("int");
        Memory memoryMock = mock(Memory.class);
        when(memoryMock.val("x")).thenReturn(new Value(10));

        SumNode sumNode = new SumNode(new IdentNode("x"), expr);

        String result = sumNode.checkType(memoryMock);
        assertEquals("int", result);
    }

    @Test
    @DisplayName("SumNode - checkType() fails if expression is not int")
    public void testSumNode_ExprNonInt() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(any(Memory.class))).thenReturn("bool");

        SumNode sumNode = new SumNode(new IdentNode("x"), expr);

        Memory memoryMock = mock(Memory.class);
        when(memoryMock.val("x")).thenReturn(new Value(10));

        assertThrows(ASTInvalidDynamicTypeException.class, () -> sumNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("SumNode - checkType() fails if variable not defined")
    public void testSumNode_UndefinedVariable() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(any(Memory.class))).thenReturn("int");

        SumNode sumNode = new SumNode(new IdentNode("x"), expr);

        Memory memoryMock = mock(Memory.class);
        when(memoryMock.val("x")).thenReturn(null);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> sumNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("SumNode - checkType() fails if variable not int")
    public void testSumNode_VariableNonInt() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(any(Memory.class))).thenReturn("int");

        SumNode sumNode = new SumNode(new IdentNode("x"), expr);

        Memory memoryMock = mock(Memory.class);
        Value v = new Value(true);
        when(memoryMock.val("x")).thenReturn(v);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> sumNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("UnMinusNode - checkType() valid with int")
    public void testUnMinusNode_IntOk() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        UnMinusNode node = new UnMinusNode(expr);
        String result = node.checkType(memoryMock);
        assertEquals("int", result);
    }

    @Test
    @DisplayName("UnMinusNode - checkType() fails if expression not int")
    public void testUnMinusNode_NonInt() {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("bool");

        UnMinusNode node = new UnMinusNode(expr);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("VariableNode - checkType() valid with compatible expression")
    public void testVariableNode_CheckType_Valid() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("x");
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        VariableNode node = new VariableNode(typeNode, ident, expr);
        String result = node.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("VariableNode - checkType() fails if incompatible type")
    public void testVariableNode_CheckType_Incompatible() {
        TypeNode typeNode = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flag");
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        VariableNode node = new VariableNode(typeNode, ident, expr);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("VariableNode - checkType() valid if no expression")
    public void testVariableNode_CheckType_NoExpression() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flag");

        VariableNode node = new VariableNode(typeNode, ident, null);
        String result = node.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("VariablesNode - checkType() valid with a variable")
    public void testVariablesNode_CheckType_Single() throws Exception {
        ASTNode var = new DummyWithdrNode();

        VariablesNode node = new VariablesNode(var, null);
        String result = node.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("VariablesNode - checkType() valid with multiple variables")
    public void testVariablesNode_CheckType_Multiple() throws Exception {
        ASTNode var1 = new DummyWithdrNode();
        ASTNode var2 = new DummyWithdrNode();

        VariablesNode node = new VariablesNode(var1, var2);
        String result = node.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("VariablesNode - checkType() fails if incompatible variable")
    public void testVariablesNode_CheckType_Incompatible() {
        ASTNode notWithdrawable = mock(ASTNode.class);

        assertThrows(ASTBuildException.class, () -> new VariablesNode(notWithdrawable, null));
    }
    @Test
    @DisplayName("WhileNode - checkType() with bool condition and instructions")
    public void testWhileNode_CheckType_Valid() throws Exception {
        ASTNode cond = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(cond.checkType(memoryMock)).thenReturn("bool");
        ASTNode instr = mock(ASTNode.class);
        when(instr.checkType(memoryMock)).thenReturn("void");

        WhileNode whileNode = new WhileNode(cond, instr);
        String result = whileNode.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("WhileNode - checkType() with non-bool condition")
    public void testWhileNode_CheckType_InvalidCondition() throws Exception {
        ASTNode cond = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(cond.checkType(memoryMock)).thenReturn("int");

        ASTNode instr = mock(ASTNode.class);

        WhileNode whileNode = new WhileNode(cond, instr);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> whileNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("WhileNode - checkType() without instructions")
    public void testWhileNode_CheckType_NoInstructions() throws Exception {
        ASTNode cond = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(cond.checkType(memoryMock)).thenReturn("bool");

        WhileNode whileNode = new WhileNode(cond, null);
        String result = whileNode.checkType(memoryMock);
        assertEquals("void", result);
    }

    class DummyWithdrNode extends ASTNode implements WithdrawalNode {
        @Override
        public List<String> compile(int address) { return List.of(); }
        @Override
        public void interpret(Memory m) { }
        @Override
        public void withdrawInterpret(Memory m) { }
        @Override
        public List<String> withdrawCompile(int address) { return List.of(); }
        @Override
        public String checkType(Memory m) { return "void"; }
    }




}
