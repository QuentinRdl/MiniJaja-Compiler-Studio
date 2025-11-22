package fr.ufrst.m1info.pvm.group5.interpreter;

import fr.ufrst.m1info.pvm.group5.ast.*;
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
    @DisplayName("Interpret If Out Of Bounds")
    void InterpretIfOutOfBounds() {
        String input = "init\npush(jcvrai)\nif(7)\npush(0)\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(IndexOutOfBoundsException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret GoTo Out Of Bounds")
    void InterpretGoToOutOfBounds() {
        String input = "init\ngoto(6)\npush(0)\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(IndexOutOfBoundsException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret If Zero")
    void InterpretIfZero() {
        String input = "init\npush(jcvrai)\nif(0)\npush(0)\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(IndexOutOfBoundsException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret GoTo Zero")
    void InterpretGoToZero() {
        String input = "init\ngoto(0)\npush(0)\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(IndexOutOfBoundsException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret If Negative")
    void InterpretIfNegative() {
        String input = "init\npush(jcvrai)\nif(-1)\npush(0)\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret GoTo Negative")
    void InterpretGoToNegative() {
        String input = "init\ngoto(-1)\npush(0)\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Push Without Value")
    void InterpretPushWithoutValue() {
        String input = "init\npush\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Inc Without Value")
    void InterpretIncWithoutValue() {
        String input = "init\npush(1)\ninc\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Load Without Value")
    void InterpretLoadWithoutValue() {
        String input = "init\nload\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Store Without Value")
    void InterpretStoreWithoutValue() {
        String input = "init\nstore\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret New Without Value")
    void InterpretNewWithoutValue() {
        String input = "init\npush\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret GoTo Without Value")
    void InterpretGoTOWithoutValue() {
        String input = "init\ngoto\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret If Without Value")
    void InterpretIfWithoutValue() {
        String input = "init\npush(jcvrai)\nif\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret If Int")
    void InterpretIfInt() {
        String input = "init\npush(8)\nif(5)\ngoto(5)\npush(0)\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret New Var Int Value Bool")
    void InterpretNewVarIntValueBool() {
        String input = "init\npush(jcvrai)\nnew(x,INT,var,0)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret New Cst Int Value Bool")
    void InterpretNewCstIntValueBool() {
        String input = "init\npush(jcvrai)\nnew(x,INT,cst,0)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret New Var Bool Value Int")
    void InterpretNewVarBoolValueInt() {
        String input = "init\npush(8)\nnew(x,BOOL,var,0)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret New Cst Bool Value Int")
    void InterpretNewCstBoolValueInt() {
        String input = "init\npush(8)\nnew(x,BOOL,cst,0)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Store Var Int Push Bool")
    void InterpretStoreVarIntPushBool() {
        String input = "init\npush(8)\nnew(x,INT,var,0)\npush(jcvrai)\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Store Var Bool Push Int")
    void InterpretStoreVarBoolPushInt() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(8)\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Store Var Int Load Bool")
    void InterpretStoreVarIntLoadBool() {
        String input = "init\npush(8)\nnew(x,INT,var,0)\npush(jcvrai)\nnew(y,BOOL,var,0)\nload(y)\nstore(x)\npush(0)\nswap\npop\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Store Var Bool Load Int")
    void InterpretStoreVarBoolLoadInt() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(8)\nnew(y,INT,var,0)\nload(y)\nstore(x)\npush(0)\nswap\npop\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Load Undeclared Variable")
    void InterpretLoadUndeclaredVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\nload(y)\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(IllegalArgumentException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Load Undefined Variable")
    void InterpretLoadUndefinedVar() {
        String input = "init\nnew(x,BOOL,var,0)\nload(x)\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Inc Undeclared Variable")
    void InterpretIncUndeclaredVar() {
        String input = "init\npush(3)\ninc(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(IllegalArgumentException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Inc Undefined Variable")
    void InterpretIncUndefinedVar() {
        String input = "init\nnew(x,INT,var,0)\npush(3)\ninc(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Inc Bool Variable")
    void InterpretIncBoolVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\ninc(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Inc Bool Value")
    void InterpretIncBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\ninc(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Add Bool Variable")
    void InterpretAddBoolVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\npush(3)\nadd\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Add Bool Value")
    void InterpretAddBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(3)\nadd\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Div Bool Variable")
    void InterpretDivBoolVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\npush(3)\ndiv\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Div Bool Value")
    void InterpretDivBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(3)\ndiv\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Mul Bool Variable")
    void InterpretMulBoolVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\npush(3)\nmul\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Mul Bool Value")
    void InterpretMulBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(3)\nmul\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Neg Bool Variable")
    void InterpretNegBoolVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\nneg\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Neg Bool Value")
    void InterpretNegBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\nneg\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sub Bool Variable")
    void InterpretSubBoolVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\npush(3)\nsub\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sub Bool Value")
    void InterpretSubBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(3)\nsub\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sup Int Variable")
    void InterpretSupIntVar() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(3)\npush(3)\nsup\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sup Bool Value")
    void InterpretSupBoolValue() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(jcvrai)\npush(3)\nsup\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret And Int Variable")
    void InterpretAndIntVar() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(jcvrai)\nand\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret And Int Value")
    void InterpretAndIntValue() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(jcvrai)\npush(3)\nand\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Not Int Variable")
    void InterpretNotIntVar() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\nnot\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Not Int Value")
    void InterpretNotIntValue() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\nnot\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Or Int Variable")
    void InterpretOrIntVar() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(jcvrai)\nor\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Or Int Value")
    void InterpretOrIntValue() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(jcvrai)\npush(3)\nor\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Cmp Int Variable")
    void InterpretCmpIntVar() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(jcvrai)\ncmp\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Cmp Int Bool Value")
    void InterpretCmpIntBoolValue() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(jcvrai)\npush(3)\ncmp\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret If Int Value")
    void InterpretIfIntValue() {
        String input = "init\npush(3)\nif(4)\ngoto(4)\npush(0)\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Div By Zero")
    void InterpretDivByZero() {
        String input = "init\npush(4)\nnew(x,INT,var,0)\npush(3)\npush(0)\ndiv\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidOperationException.class.toString(),errMessage.split(":")[0].trim());
    }

}
