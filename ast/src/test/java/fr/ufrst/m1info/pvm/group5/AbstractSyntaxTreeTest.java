package fr.ufrst.m1info.pvm.group5;
import fr.ufrst.m1info.pvm.group5.Memory;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AbstractSyntaxTreeTest {
    Map<String, Value> memoryStorage;
    @Mock
    Memory MemoryMock;

    @BeforeEach
    public void setup(){
        memoryStorage = new HashMap<>();
        MemoryMock = mock();
        doAnswer(invocation -> {
                    String arg =  invocation.getArgument(0);
                    Value value = invocation.getArgument(1);
                    memoryStorage.put(arg, value);
                    return null;
                }
        ).when(MemoryMock).affectValue(any(String.class), any(Object.class));

        doAnswer( invocation -> {
                String ident = invocation.getArgument(0);
                Value value = invocation.getArgument(1);
                memoryStorage.put(ident, value);
                return null;
            }
        ).when(MemoryMock).declVar(any(String.class), any(Value.class), any());

        doAnswer(invocation -> {
                String identifier =  invocation.getArgument(0);
                return memoryStorage.get(identifier);
            }
        ).when(MemoryMock).val(any(String.class));
    }

    @Test
    @DisplayName("Construction - Simple")
    public void SimpleTree(){
        String input = "class C {"
                     + "  main{}"
                     + "}";
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString(input);
    }

    @Test
    @DisplayName("Construction - From file, simple")
    public void FromFile() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Simple.mjj");
    }

    @Test
    @DisplayName("Construction - Complex")
    public void ComplexTree() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Complex.mjj");
    }

    @Test
    @DisplayName("Evaluation - BasicOperations")
    public void BasicOperations() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/BasicOperations.mjj");
        AST.interpret(MemoryMock);
        assertEquals(8, memoryStorage.get("x").valueInt);
    }

    @Test
    @DisplayName("Evaluation - OperationPrevalence")
    public void OperationPrevalence() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/OperationPrevalence.mjj");
        AST.interpret(MemoryMock);
        assertEquals(17, memoryStorage.get("x").valueInt);
        assertEquals(17, memoryStorage.get("y").valueInt);
        assertEquals(-1, memoryStorage.get("z").valueInt);
        assertEquals(true, memoryStorage.get("w").valueBool);
        assertEquals(true, memoryStorage.get("v").valueBool);
    }

    @Test
    @DisplayName("Evaluation - Undefined Variable / sum")
    public void UndefinedVariableSum() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString("class C {int y = 10;main {x += y;}}");
        boolean success = false;
        try {
            AST.interpret(MemoryMock);
        }
        catch (ASTInvalidMemoryException e) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    @DisplayName("Evaluation - Undefined Variable / Inc")
    public void UndefinedVariableInc() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString("class C {int y = 10;main {x++}}");
        boolean success = false;
        try {
            AST.interpret(MemoryMock);
        }
        catch (ASTInvalidMemoryException e) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    @DisplayName("Evaluation - Undefined Variable / Evaluation")
    public void UndefinedVariableEval() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString("class C {int y = 10;main {y = x;}}");
        boolean success = false;
        try {
            AST.interpret(MemoryMock);
        }
        catch (ASTInvalidMemoryException e) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    @DisplayName("Evaluation - Local Variables")
    public void LocalVariables() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/LocalVariables.mjj");
        try {
            AST.interpret(MemoryMock);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(9, memoryStorage.get("x").valueInt);
    }

    @Test
    @DisplayName("Evaluation - Conditionnals")
    public void Conditionnals() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Conditionals.mjj");
        try {
            AST.interpret(MemoryMock);
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
    public void Loops() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Loops.mjj");
        try {
            AST.interpret(MemoryMock);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(104, memoryStorage.get("x").valueInt);
    }
}
