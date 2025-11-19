package fr.ufrst.m1info.pvm.group5.interpreter;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;

public class InterpreterJajaCodeTest {
    InterpreterJajaCode ijc;

    @BeforeEach
    public void setup() {
        ijc=new InterpreterJajaCode();
    }

    @Test
    @DisplayName("Interpret Code Without Error")
    void InterpretCodeNoError() {
        String input = "init\n"
                + "jcstop";
        Assertions.assertNull(ijc.interpretCode(input));
    }

    @Test
    @DisplayName("Interpret File PushPopSwap")
    void PushPopSwap() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/PushPopSwap.jjc"));
    }

    @Test
    @DisplayName("Interpret File BasicOperations")
    void BasicOperations() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/BasicOperations.jjc"));
    }

    @Test
    @DisplayName("Interpret File Complex")
    void Complex() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/Complex.jjc"));
    }

    @Test
    @DisplayName("Interpret File Conditionals")
    void Conditionals() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/Conditionals.jjc"));
    }

    @Test
    @DisplayName("Interpret File Local Variables")
    void LocalVariables() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/LocalVariables.jjc"));
    }

    @Test
    @DisplayName("Interpret File Loops")
    void Loops() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/Loops.jjc"));
    }

    @Test
    @DisplayName("Interpret File OperationPrevalence")
    void OperationPrevalence() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/OperationPrevalence.jjc"));
    }

    @Test
    @DisplayName("Interpret File Simple")
    void Simple() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/Simple.jjc"));
    }

    @Test
    @DisplayName("Interpret File That Doesn't Exist")
    void InterpretNotExistingFile() {
        String errMessage=ijc.interpretFile("src/test/resources/FileThatDoesntExist.jjc");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(NoSuchFileException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Code Without Jcstop")
    void InterpretCodeNoJcstop() {
        String input = "init\npush(0)\npop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(IndexOutOfBoundsException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Push Without Value")
    void InterpretPushWithoutValue() {
        String input = "init\npush()\npop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

}
