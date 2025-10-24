package fr.ufrst.m1info.pvm.group5;
import org.junit.jupiter.api.*;

import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/*
Commented tests doesn't work because API memory is not done yet
 */
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

    @Test
    @DisplayName("Interpret File That Doesn't Exist")
    public void InterpretNotExistingFile() {
        String errMessage=imj.interpretFile("src/test/resources/FileThatDoesntExist.mjj");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(NoSuchFileException.class.toString(),errMessage.split(":")[0].trim());
    }

    /*@Test
    @DisplayName("Interpret File BasicOperations")
    public void BasicOperations()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/BasicOperations.mjj"));
    }

    @Test
    @DisplayName("Interpret File Complex")
    public void Complex()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Complex.mjj"));
    }

    @Test
    @DisplayName("Interpret File Conditionals")
    public void Conditionals()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Conditionals.mjj"));
    }

    @Test
    @DisplayName("Interpret File Local Variables")
    public void LocalVariables()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/LocalVariables.mjj"));
    }

    @Test
    @DisplayName("Interpret File Loops")
    public void Loops()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Loops.mjj"));
    }*/

    @Test
    @DisplayName("Interpret File OperationPrevalence")
    public void OperationPrevalence()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/OperationPrevalence.mjj"));
    }

    @Test
    @DisplayName("Interpret File Simple")
    public void Simple()  {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Simple.mjj"));
    }

    @Test
    @DisplayName("Interpret Undefined Variable / sum")
    public void UndefinedVariableSum() {
        String errMessage=imj.interpretCode("class C {int y = 10;main {x += y;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(IllegalArgumentException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Undefined Variable / Inc")
    public void UndefinedVariableInc()  {
        String errMessage=imj.interpretCode("class C {int y = 10;main {x++;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(IllegalArgumentException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Undefined Variable / Evaluation")
    public void UndefinedVariableEval()  {
        String errMessage=imj.interpretCode("class C {int y = 10;main {y = x;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(java.lang.IllegalArgumentException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Unnamed Variable")
    public void UnnamedVariable()  {
        String errMessage=imj.interpretCode("class C {int  = 10;main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(java.lang.IllegalArgumentException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret No Main")
    public void noMain()  {
        String errMessage=imj.interpretCode("class C {int y = 10;}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTBuildException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret No Class")
    public void noClass()  {
        String errMessage=imj.interpretCode("int y = 10; main{}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTBuildException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Without Value")
    public void declarationWithoutValue()  {
        String errMessage=imj.interpretCode("class C {int y = ;main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTBuildException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Without Value")
    public void affectationWithoutValue()  {
        String errMessage=imj.interpretCode("class C {int y; main {y= ;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTBuildException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sum Without Value")
    public void sumWithoutValue()  {
        String errMessage=imj.interpretCode("class C {int y=10; main {y+= ;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTBuildException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Increment Without Value")
    public void incrementWithoutValue()  {
        String errMessage=imj.interpretCode("class C {int y; main {y++;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTBuildException.class.toString(),errMessage.split(":")[0].trim());
    }






}
