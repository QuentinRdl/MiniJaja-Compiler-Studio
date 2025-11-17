package fr.ufrst.m1info.pvm.group5.integration_tests;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.ast.AbstractSyntaxTree;

import java.util.HashMap;
import java.util.Map;

import fr.ufrst.m1info.pvm.group5.ast.instructions.PushInstruction;
import fr.ufrst.m1info.pvm.group5.ast.instructions.ReturnInstruction;
import fr.ufrst.m1info.pvm.group5.ast.nodes.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Stack;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AstMemoryIT {
    private final String testPath = "../ast/";
    Map<String, Value> memoryStorage;
    Memory memory;

    @BeforeEach
    public void setup(){
        memoryStorage = new HashMap<>();
        memory = new Memory();
        memory.setPreserveAfterInterpret(true);
    }

    // Conversion of AbstractSyntaxTreeTest

    @Test
    @DisplayName("AST -> Memory Int Test | Evaluation - BasicOperations")
    void BasicOperations() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("../ast/src/test/resources/BasicOperations.mjj");

        AST.interpret(memory);
        Object obj = memory.val("x");
        Value val = (Value) obj;
        assertEquals(8, val.valueInt);
    }

    @Test
    @DisplayName("AST -> Memory Int Test | Evaluation - OperationPrevalence")
    void OperationPrevalence() throws Exception {

        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile(testPath + "src/test/resources/OperationPrevalence.mjj");
        AST.interpret(memory);

        Object obj = memory.val("x");
        Value val = (Value) obj;
        assertEquals(17, val.valueInt);

        obj = memory.val("y");
        val = (Value) obj;
        assertEquals(17, val.valueInt);

        obj = memory.val("z");
        val = (Value) obj;
        assertEquals(-1, val.valueInt);

        obj = memory.val("w");
        val = (Value) obj;
        assertTrue(val.valueBool);

        obj = memory.val("v");
        val = (Value) obj;
        assertTrue(val.valueBool);
    }

    @Test
    @DisplayName("AST -> Memory Int Test | Evaluation - Local Variables")
    void LocalVariables() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile(testPath + "src/test/resources/LocalVariables.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }

        Object obj = memory.val("x");
        Value val = (Value) obj;
        assertEquals(9, val.valueInt);
    }

    @Test
    @DisplayName("AST -> Memory Int Test | Evaluation - Conditionnals")
    void Conditionnals() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile(testPath + "src/test/resources/Conditionals.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }

        Object obj = memory.val("a");
        Value val = (Value) obj;
        assertEquals(5, val.valueInt);

        obj = memory.val("b");
        val = (Value) obj;
        assertEquals(6, val.valueInt);

        obj = memory.val("c");
        val = (Value) obj;
        assertEquals(5, val.valueInt);

        obj = memory.val("v");
        val = (Value) obj;
        assertEquals(6, val.valueInt);
    }

    @Test
    @DisplayName("AST -> Memory Int Test | Evaluation - Loops")
    void Loops() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile(testPath + "src/test/resources/Loops.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }

        Object obj = memory.val("x");
        Value val = (Value) obj;
        assertEquals(104, val.valueInt);
    }

    // End of conversion of AbstractSyntaxTreeTest
    // Conversion of NodeInterpretationUnitTest
    @Test
    void AddNode_Operation(){
        NumberNode lop = new NumberNode(5);
        NumberNode rop = new NumberNode(10);
        AddNode tested = new AddNode(lop, rop);
        Value res = tested.eval(memory);
        assertEquals(15, res.valueInt);
    }

    @Test
    void AffectationNode_Default() throws Exception {
        // declare variable x in memory
        memory.declVar("x", null, DataType.INT);
        IdentNode lop = new IdentNode("x");
        NumberNode rop = new NumberNode(5);
        AffectationNode tested = new AffectationNode(lop, rop);
        tested.interpret(memory);
        Object ret = memory.val("x");
        assertInstanceOf(Value.class, ret);
        Value retv = (Value) ret;
        assertEquals(5, retv.valueInt);
    }

    @Test
    void AffectationNode_UndefinedOperand(){
        IdentNode lop = new IdentNode("x");
        NumberNode rop = new NumberNode(5);
        AffectationNode tested = new AffectationNode(lop, rop);
        assertThrows(IllegalArgumentException.class, () -> tested.interpret(memory));
    }

    @Test
    void BooleanNode_Evaluation(){
        BooleanNode t = new BooleanNode(true);
        BooleanNode f = new BooleanNode(false);
        assertTrue(t.eval(memory).valueBool);
        assertFalse(f.eval(memory).valueBool);
    }

    @Test
    void ClassNode_NoContent() throws Exception{
        IdentNode ident = new IdentNode("C");
        MainNode main = new MainNode(null, null);
        ClassNode c = new ClassNode(ident, null, main);
        c.interpret(memory);
        Object val = memory.val("C");
        assertInstanceOf(Value.class, val);
    }


    @Test
    void AndNode_Operation(){
        BooleanNode TNode = new BooleanNode(true);
        BooleanNode FNode = new BooleanNode(false);
        AndNode tested = new AndNode(TNode,FNode);
        assertFalse(tested.eval(memory).valueBool);
        tested = new AndNode(TNode,TNode);
        assertTrue(tested.eval(memory).valueBool);
        tested = new AndNode(FNode,FNode);
        assertFalse(tested.eval(memory).valueBool);
        tested = new AndNode(FNode,TNode);
        assertFalse(tested.eval(memory).valueBool);
    }

    @Test
    void MinusNode_Operation(){
        NumberNode lop = new NumberNode(5);
        NumberNode rop = new NumberNode(10);
        BinMinusNode tested = new BinMinusNode(lop,rop);
        assertEquals(-5, tested.eval(memory).valueInt);
    }

    @Test
    void BooleanNode_InvalidOperation(){
        BooleanNode t = new BooleanNode(true);
        assertThrows(ASTInvalidOperationException.class, () -> t.interpret(memory));
    }

    @Test
    void ClassNode_NoContent_EmptyMemory(){
        IdentNode ident = new IdentNode("C");
        MainNode main = new MainNode(null, null);
        ClassNode c = new ClassNode(ident, null, main);
        Memory m = new Memory();
        c.interpret(m);
        assertThrows(Stack.StackIsEmptyException.class, m::pop);
    }

    // Confirmation test
    @Test
    void return_valid_address() throws Exception{
        PushInstruction p = new PushInstruction(new Value(5));
        p.execute(0, memory);

        ReturnInstruction r = new ReturnInstruction();
        int next = r.execute(1, memory);
        assertEquals(5, next);
    }
}
