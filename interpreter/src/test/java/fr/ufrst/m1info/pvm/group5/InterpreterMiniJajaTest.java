package fr.ufrst.m1info.pvm.group5;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InterpreterMiniJajaTest {
    InterpreterMiniJaja imj;

    @BeforeEach
    public void setup(){
        imj=new InterpreterMiniJaja();
    }

    @Test
    @DisplayName("Interpret Code Without Error")
    public void InterpretCodeNoError()  {
        String input = "class C {"
                + "  main{}"
                + "}";
        Assertions.assertNull(imj.interpretCode(input));
    }

    /*@Test
    @DisplayName("Interpret File BasicOperations")
    public void InterpretFileBasicOperations()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/BasicOperations.mjj"));
    }*/

    @Test
    @DisplayName("Interpret File That Doesn't Exist")
    public void InterpretNotExistingFile() {
        String errMessage=imj.interpretFile("src/test/resources/FileThatDoesntExist.mjj");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(NoSuchFileException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret File OperationPrevalence")
    public void OperationPrevalence()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/OperationPrevalence.mjj"));
    }

    @Test
    @DisplayName("Interpret Undefined Variable / sum")
    public void UndefinedVariableSum() {
        String errMessage=imj.interpretCode("class C {int y = 10;main {x++;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }
/*
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
    }*/
}
