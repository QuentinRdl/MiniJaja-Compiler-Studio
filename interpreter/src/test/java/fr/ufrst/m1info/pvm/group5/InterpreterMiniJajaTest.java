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

    /*@Test
    @DisplayName("Interpret File Complex")
    public void Complex()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Complex.mjj"));
    }*/

    @Test
    @DisplayName("Interpret Undefined Variable / sum")
    public void UndefinedVariableSum() {
        String errMessage=imj.interpretCode("class C {int y = 10;main {x += y;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Undefined Variable / Inc")
    public void UndefinedVariableInc()  {
        String errMessage=imj.interpretCode("class C {int y = 10;main {x++;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Undefined Variable / Evaluation")
    public void UndefinedVariableEval()  {
        String errMessage=imj.interpretCode("class C {int y = 10;main {y = x;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    /*@Test
    @DisplayName("Interpret Local Variables")
    public void LocalVariables()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/LocalVariables.mjj"));
    }

    @Test
    @DisplayName("Interpret Conditionnals")
    public void Conditionnals()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Conditionals.mjj"));
    }

    @Test
    @DisplayName("Interpret Loops")
    public void Loops()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Loops.mjj"));
    }*/
}
