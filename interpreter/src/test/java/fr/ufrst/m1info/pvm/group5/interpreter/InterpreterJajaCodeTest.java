package fr.ufrst.m1info.pvm.group5.interpreter;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.memory.Writer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterJajaCodeTest {
    InterpreterJajaCode ijc;
    Writer writer;

    @BeforeEach
    public void setup() {
        writer = new Writer();
        ijc=new InterpreterJajaCode(writer);
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
    @DisplayName("Interpret File WriteBasicOperations")
    void WriteBasicOperations() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/WriteBasicOperations.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("x : 7\nx : 8\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File WriteComplex")
    void WriteComplex() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/WriteComplex.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("y : 10\nVAL : 5\nb1 : true\nb2 : false\nx : 15\nx : 50\nx : 10\nx : 11\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File WriteConditionals")
    void WriteConditionals() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/WriteConditionals.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("a : 5\nb : 5\nc : 5\nb : 6\nv : 6\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File WriteLocalVariables")
    void WriteLocalVariables() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/WriteLocalVariables.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("x : 4\ny : 3\nz : 6\nx : 8\nx : 9\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File WriteLoops")
    void WriteLoops() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/WriteLoops.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("x : 0\nx : 8\nx : 16\nx : 24\nx : 32\nx : 40\nx : 48\nx : 56\nx : 64\nx : 72\nx : 80\nx : 88\nx : 96\nx : 104\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File WriteOperationPrevalence")
    void WriteOperationPrevalence() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/WriteOperationPrevalence.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("x : 17\ny : 17\nz : -1\nw : true\nv : true\n", e.oldText());
        });
        writer.write("");
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
