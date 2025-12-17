package fr.ufrst.m1info.pvm.group5.interpreter;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Writer;
import fr.ufrst.m1info.pvm.group5.memory.heap.Heap;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.*;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class InterpreterJajaCodeTest {
    InterpreterJajaCode ijc;
    Writer writer;

    void assertInterval(int[] expected, int value){
        if(value < expected[0] || value > expected[1]){
            fail("Expected integer in interval ["+expected[0]+","+expected[1]+"] but got "+value);
        }
    }

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
    @DisplayName("Interpret Pop Array")
    void InterpretPopArray() {
        String input = "init\npush(3)\nnewarray(xa,INT)\npop\n"
                + "jcstop";
        Assertions.assertNull(ijc.interpretCode(input));
        Heap h=ijc.getMemory().getHeap();
        assertEquals(h.getTotalSize(),h.getAvailableSize());
    }

    @Test
    @DisplayName("Interpret File PushPopSwap")
    void PushPopSwap() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/PushPopSwap.jjc"));
    }

    @Test
    @DisplayName("Interpret File One")
    void FileOne() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/1.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("x : 8\nx : 11\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File Ainc")
    void Ainc() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/Ainc.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("80\n81\n89\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File Array")
    void Array() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/Array.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("4\n33\n81\nfalse\ntrue\nfalse\nfalse\n", e.oldText());
        });
        writer.write("");
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
    @DisplayName("Interpret File Constant")
    void Constant() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/Constant.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("5\nfalse\n40\ntrue\n", e.oldText());
        });
        writer.write("");
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
    @DisplayName("Interpret File Method")
    void Method() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/Method.jjc"));
    }

    @Test
    @DisplayName("Interpret File MethodComplex")
    void MethodComplex() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/MethodComplex.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("addition : 101\nsubstraction : -99\ninferior : true\nHello World\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File MethodRecursive")
    void MethodRecursive() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/MethodRecursive.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("5\n4\n3\n2\n1\n0\n0\n1\n2\n3\n4\n5\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File OperationPrevalence")
    void OperationPrevalence() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/OperationPrevalence.jjc"));
    }

    @Test
    @DisplayName("Interpret File Quicksort")
    void Quicksort() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/Quick_sort.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("4,81,63,12,33,22,16,44,55,23,27,5,14,18,6,30,87,31,10,43, \n4,5,6,10,12,14,16,18,22,23,27,30,31,33,43,44,55,63,81,87, \n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File Simple")
    void Simple() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/Simple.jjc"));
    }

    @Test
    @DisplayName("Interpret File Synonymie")
    void Synonymie() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/Synonymie.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("3,3,3,4,3,3,3,3,3,3, \n7,7,7,7,7,7,7,4,7,7, \n", e.oldText());
        });
        writer.write("");
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
    @DisplayName("Interpret File WriteMethod")
    void WriteMethod() {
        Assertions.assertNull(ijc.interpretFile("src/test/resources/WriteMethod.jjc"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("x : 1\ny : 100\nop2 : 100\nop1 : 1\nx : 101\n", e.oldText());
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
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret New Var Int Value Bool")
    void InterpretNewVarIntValueBool() {
        String input = "init\npush(jcvrai)\nnew(x,INT,var,0)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret New Cst Int Value Bool")
    void InterpretNewCstIntValueBool() {
        String input = "init\npush(jcvrai)\nnew(x,INT,cst,0)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret New Var Bool Value Int")
    void InterpretNewVarBoolValueInt() {
        String input = "init\npush(8)\nnew(x,BOOL,var,0)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret New Cst Bool Value Int")
    void InterpretNewCstBoolValueInt() {
        String input = "init\npush(8)\nnew(x,BOOL,cst,0)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Store Var Int Push Bool")
    void InterpretStoreVarIntPushBool() {
        String input = "init\npush(8)\nnew(x,INT,var,0)\npush(jcvrai)\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Store Var Bool Push Int")
    void InterpretStoreVarBoolPushInt() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(8)\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Store Var Int Load Bool")
    void InterpretStoreVarIntLoadBool() {
        String input = "init\npush(8)\nnew(x,INT,var,0)\npush(jcvrai)\nnew(y,BOOL,var,0)\nload(y)\nstore(x)\npush(0)\nswap\npop\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Store Var Bool Load Int")
    void InterpretStoreVarBoolLoadInt() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(8)\nnew(y,INT,var,0)\nload(y)\nstore(x)\npush(0)\nswap\npop\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Load Undeclared Variable")
    void InterpretLoadUndeclaredVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\nload(y)\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
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
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
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
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Inc Bool Value")
    void InterpretIncBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\ninc(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Add Bool Variable")
    void InterpretAddBoolVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\npush(3)\nadd\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Add Bool Value")
    void InterpretAddBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(3)\nadd\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Div Bool Variable")
    void InterpretDivBoolVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\npush(3)\ndiv\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Div Bool Value")
    void InterpretDivBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(3)\ndiv\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Mul Bool Variable")
    void InterpretMulBoolVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\npush(3)\nmul\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Mul Bool Value")
    void InterpretMulBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(3)\nmul\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Neg Bool Variable")
    void InterpretNegBoolVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\nneg\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Neg Bool Value")
    void InterpretNegBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\nneg\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sub Bool Variable")
    void InterpretSubBoolVar() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\npush(3)\nsub\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sub Bool Value")
    void InterpretSubBoolValue() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(3)\nsub\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sup Int Variable")
    void InterpretSupIntVar() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(3)\npush(3)\nsup\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sup Bool Value")
    void InterpretSupBoolValue() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(jcvrai)\npush(3)\nsup\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret And Int Variable")
    void InterpretAndIntVar() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(jcvrai)\nand\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret And Int Value")
    void InterpretAndIntValue() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(jcvrai)\npush(3)\nand\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Not Int Variable")
    void InterpretNotIntVar() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\nnot\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Not Int Value")
    void InterpretNotIntValue() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(3)\nnot\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Or Int Variable")
    void InterpretOrIntVar() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(jcvrai)\nor\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Or Int Value")
    void InterpretOrIntValue() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(jcvrai)\npush(3)\nor\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Cmp Int Variable")
    void InterpretCmpIntVar() {
        String input = "init\npush(3)\nnew(x,INT,var,0)\npush(jcvrai)\npush(jcvrai)\ncmp\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Cmp Int Bool Value")
    void InterpretCmpIntBoolValue() {
        String input = "init\npush(jcvrai)\nnew(x,BOOL,var,0)\npush(jcvrai)\npush(3)\ncmp\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret If Int Value")
    void InterpretIfIntValue() {
        String input = "init\npush(3)\nif(4)\ngoto(4)\npush(0)\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Div By Zero")
    void InterpretDivByZero() {
        String input = "init\npush(4)\nnew(x,INT,var,0)\npush(3)\npush(0)\ndiv\nstore(x)\npush(0)\nswap\npop\npop\njcstop";
        String errMessage= ijc.interpretCode(input);
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidOperationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Final Int Var Bool 1")
    void FinalIntVarBool1() {
        String code = "init\n" +
                "push(jcfaux)\n" +
                "new(x,INT,cst,0)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Var Bool 2")
    void FinalIntVarBool2() {
        String code = "init\n" +
                "new(x,INT,cst,0)\n" +
                "push(jcfaux)\n" +
                "store(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Bool Var Int 1")
    void FinalBoolVarInt1() {
        String code = "init\n" +
                "push(1)\n" +
                "new(x,BOOL,cst,0)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Bool Var Int 2")
    void FinalBoolVarInt2() {
        String code = "init\n" +
                "new(x,BOOL,cst,0)\n" +
                "push(1)\n" +
                "store(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final String 1")
    void FinalString1() {
        String code = "init\n" +
                "push(\"Error\")\n" +
                "new(x,STRING,cst,0)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final String 2")
    void FinalString2() {
        String code = "init\n" +
                "new(x,STRING,cst,0)\n" +
                "push(\"Error\")\n" +
                "store(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Void")
    void FinalVoid() {
        String code = "init\n" +
                "new(x,VOID,cst,0)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Affectation After Initialization 1")
    void FinalIntAffectationAfterInitialization1() {
        String code = "init\n" +
                "push(15)\n" +
                "new(x,INT,cst,0)\n" +
                "push(157)\n" +
                "store(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Affectation After Initialization 2")
    void FinalIntAffectationAfterInitialization2() {
        String code = "init\n" +
                "new(x,INT,cst,0)\n" +
                "push(15)\n" +
                "store(x)\n" +
                "push(157)\n" +
                "store(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Bool Affectation After Initialization 1")
    void FinalBoolAffectationAfterInitialization1() {
        String code = "init\n" +
                "push(jcfaux)\n" +
                "new(x,BOOL,cst,0)\n" +
                "push(jcvrai)\n" +
                "store(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Bool Affectation After Initialization 2")
    void FinalBoolAffectationAfterInitialization2() {
        String code = "init\n" +
                "new(x,BOOL,cst,0)\n" +
                "push(jcfaux)\n" +
                "store(x)\n" +
                "push(jcvrai)\n" +
                "store(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Increment After Initialization 1")
    void FinalIntIncrementAfterInitialization1() {
        String code = "init\n" +
                "push(15)\n" +
                "new(x,INT,cst,0)\n" +
                "push(1)\n" +
                "inc(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Increment After Initialization 2")
    void FinalIntIncrementAfterInitialization2() {
        String code = "init\n" +
                "new(x,INT,cst,0)\n" +
                "push(15)\n" +
                "store(x)\n" +
                "push(1)\n" +
                "inc(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Sum After Initialization 1")
    void FinalIntSumAfterInitialization1() {
        String code = "init\n" +
                "push(15)\n" +
                "new(x,INT,cst,0)\n" +
                "push(157)\n" +
                "inc(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Sum After Initialization 2")
    void FinalIntSumAfterInitialization2() {
        String code = "init\n" +
                "new(x,INT,cst,0)\n" +
                "push(15)\n" +
                "store(x)\n" +
                "push(157)\n" +
                "inc(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String errMessage=ijc.interpretCode(code);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Disabled
    /* Step by step // Breakpoints */
    @Test
    @DisplayName("Interpret - step by step")
    void Step_By_Step() {
        int[] steps = {1};
        InterpreterJajaCode ijjc = new InterpreterJajaCode();
        InterpretationMode im = InterpretationMode.STEP_BY_STEP;
        ijjc.interpretationHaltedEvent.subscribe(event -> {
            steps[0]++;
            ijjc.resumeInterpretation(im);
        });
        ijjc.startFileInterpretation("src/test/resources/Complex.jjc", im);
        ijjc.waitInterpretation();
        assertInterval(new int[]{48,49}, steps[0]);
    }

    @Test
    @DisplayName("Interpret - breakpoints")
    void Breakpoints() {
        List<Integer> encountered = new ArrayList<>();
        InterpreterJajaCode ijjc = new InterpreterJajaCode();
        InterpretationMode im = InterpretationMode.BREAKPOINTS;
        ijjc.interpretationHaltedEvent.subscribe(event -> {
            encountered.add(event.line());
            if(event.isPursuable())
                ijjc.resumeInterpretation(im);
        });
        ijjc.setBreakpoints(List.of(3,13,18,43));
        ijjc.startFileInterpretation("src/test/resources/Complex.jjc", im);
        ijjc.waitInterpretation();
        Assertions.assertTrue(encountered.containsAll(List.of(3,13,43)));
        Assertions.assertFalse(encountered.contains(18));// 18 should not be met, it's "branched over"
    }

    @Disabled
    @Test
    @DisplayName("Interpret - error during step by step")
    void errorDuringStepByStep() {
        List<String> errors = new ArrayList<>();
        InterpreterJajaCode ijjc = new InterpreterJajaCode();
        // Creating result
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Invalid type, type BOOL given when int is expected (at line 4, add)");

        InterpretationMode im = InterpretationMode.STEP_BY_STEP;
        ijjc.interpretationHaltedEvent.subscribe(event -> {
            if(event.error() != null)
                errors.add(event.error());
            ijjc.resumeInterpretation(im);
        });
        ijjc.startCodeInterpretation("init\npush(jcvrai)\npush(1)\nadd\njcstop", im);
        ijjc.waitInterpretation();
        assertEquals(expectedErrors, errors);
    }
}