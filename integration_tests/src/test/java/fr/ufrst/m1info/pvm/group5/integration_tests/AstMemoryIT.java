package fr.ufrst.m1info.pvm.group5.integration_tests;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.ast.AbstractSyntaxTree;

import java.util.HashMap;
import java.util.Map;

import fr.ufrst.m1info.pvm.group5.ast.Instructions.PushInstruction;
import fr.ufrst.m1info.pvm.group5.ast.Instructions.ReturnInstruction;
import fr.ufrst.m1info.pvm.group5.ast.Nodes.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Stack;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
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
    public void BasicOperations() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("../ast/src/test/resources/BasicOperations.mjj");

        AST.interpret(memory);
        Object obj = memory.val("x");
        Value val = (Value) obj;
        assertEquals(8, val.valueInt);
    }

    @Test
    @DisplayName("AST -> Memory Int Test | Evaluation - OperationPrevalence")
    public void OperationPrevalence() throws Exception {

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
    public void LocalVariables() throws Exception {
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
    public void Conditionnals() throws Exception {
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
    public void Loops() throws Exception {
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
    public void AddNode_Operation(){
        NumberNode lop = new NumberNode(5);
        NumberNode rop = new NumberNode(10);
        AddNode tested = new AddNode(lop, rop);
        Value res = tested.eval(memory);
        assertEquals(15, res.valueInt);
    }

    @Test
    public void AffectationNode_Default() throws Exception {
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
    public void AffectationNode_UndefinedOperand(){
        IdentNode lop = new IdentNode("x");
        NumberNode rop = new NumberNode(5);
        AffectationNode tested = new AffectationNode(lop, rop);
        assertThrows(IllegalArgumentException.class, () -> tested.interpret(memory));
    }

    @Test
    public void BooleanNode_Evaluation(){
        BooleanNode t = new BooleanNode(true);
        BooleanNode f = new BooleanNode(false);
        assertTrue(t.eval(memory).valueBool);
        assertFalse(f.eval(memory).valueBool);
    }

    @Test
    public void ClassNode_NoContent() throws Exception{
        IdentNode ident = new IdentNode("C");
        MainNode main = new MainNode(null, null);
        ClassNode c = new ClassNode(ident, null, main);
        c.interpret(memory);
        Object val = memory.val("C");
        assertInstanceOf(Value.class, val);
    }


    @Test
    public void AndNode_Operation(){
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
    public void MinusNode_Operation(){
        NumberNode lop = new NumberNode(5);
        NumberNode rop = new NumberNode(10);
        BinMinusNode tested = new BinMinusNode(lop,rop);
        assertEquals(-5, tested.eval(memory).valueInt);
    }

    @Test
    public void BooleanNode_InvalidOperation(){
        BooleanNode t = new BooleanNode(true);
        assertThrows(ASTInvalidOperationException.class, () -> t.interpret(memory));
    }

    @Test
    public void ClassNode_NoContent_EmptyMemory(){
        IdentNode ident = new IdentNode("C");
        MainNode main = new MainNode(null, null);
        ClassNode c = new ClassNode(ident, null, main);
        Memory m = new Memory();
        c.interpret(m);
        assertThrows(Stack.StackIsEmptyException.class, m::pop);
    }

    // Confirmation test
    @Test
    public void return_valid_address() throws Exception{
        PushInstruction p = new PushInstruction(new Value(5));
        p.execute(0, memory);

        ReturnInstruction r = new ReturnInstruction();
        int next = r.execute(1, memory);
        assertEquals(5, next);
    }
}
