package fr.ufrst.m1info.pvm.group5.ast;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AbstractSyntaxTreeTest {
    Map<String, Value> memoryStorage;
    @Mock
    Memory memory;

    @BeforeEach
    public void setup(){
        memoryStorage = new HashMap<>();
        memory = ASTMocks.createMemoryWithStorage(memoryStorage);
    }

    @Test
    @DisplayName("Construction - Simple")
    void SimpleTree(){
        String input = "class C {"
                     + "  main{}"
                     + "}";
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString(input);
    }

    @Test
    @DisplayName("Construction - From file, simple")
    void FromFile() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Simple.mjj");
    }

    @Test
    @DisplayName("Construction - Complex")
    void ComplexTree() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Complex.mjj");
    }

    @Test
    @DisplayName("Evaluation - BasicOperations")
    void BasicOperations() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/BasicOperations.mjj");
        AST.interpret(memory);
        assertEquals(8, memoryStorage.get("x").valueInt);
    }

    @Test
    @DisplayName("Evaluation - OperationPrevalence")
    void OperationPrevalence() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/OperationPrevalence.mjj");
        AST.interpret(memory);
        assertEquals(17, memoryStorage.get("x").valueInt);
        assertEquals(17, memoryStorage.get("y").valueInt);
        assertEquals(-1, memoryStorage.get("z").valueInt);
        assertEquals(true, memoryStorage.get("w").valueBool);
        assertEquals(true, memoryStorage.get("v").valueBool);
    }

    @Test
    @DisplayName("Evaluation - Undefined Variable / sum")
    void UndefinedVariableSum() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString("class C {int y = 10;main {x += y;}}");
        boolean success = false;
        try {
            AST.interpret(memory);
        }
        catch (ASTInvalidMemoryException e) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    @DisplayName("Evaluation - Undefined Variable / Inc")
    void UndefinedVariableInc() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString("class C {int y = 10;main {x++;}}");
        boolean success = false;
        try {
            AST.interpret(memory);
        }
        catch (ASTInvalidMemoryException e) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    @DisplayName("Evaluation - Undefined Variable / Evaluation")
    void UndefinedVariableEval() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString("class C {int y = 10;main {y = x;}}");
        boolean success = false;
        try {
            AST.interpret(memory);
        }
        catch (ASTInvalidMemoryException e) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    @DisplayName("Evaluation - Local Variables")
    void LocalVariables() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/LocalVariables.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(9, memoryStorage.get("x").valueInt);
    }

    @Test
    @DisplayName("Evaluation - Conditionnals")
    void Conditionnals() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Conditionals.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(5, memoryStorage.get("a").valueInt);
        assertEquals(6, memoryStorage.get("b").valueInt);
        assertEquals(5, memoryStorage.get("c").valueInt);
        assertEquals(6, memoryStorage.get("v").valueInt);
    }

    @Test
    @DisplayName("Evaluation - Loops")
    void Loops() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Loops.mjj");
        try {
            AST.interpret(memory);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(104, memoryStorage.get("x").valueInt);
    }

    // Confirmation test
    @Test
    @DisplayName("Evaluation - Unassigned variable usage")
    void UnassignedVar() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Unassigned.mjj");
        assertThrows(ASTInvalidMemoryException.class, () -> AST.interpret(memory));
    }
}
