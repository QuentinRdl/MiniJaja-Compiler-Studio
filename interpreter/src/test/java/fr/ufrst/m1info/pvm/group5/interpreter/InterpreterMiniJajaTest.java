package fr.ufrst.m1info.pvm.group5.interpreter;
import fr.ufrst.m1info.pvm.group5.memory.Writer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.*;

import fr.ufrst.m1info.pvm.group5.ast.*;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterpreterMiniJajaTest {
    InterpreterMiniJaja imj;
    Writer writer;

    @BeforeEach
    public void setup() {
        writer = new Writer();
        imj=new InterpreterMiniJaja(writer);
    }

    @Test
    @DisplayName("Interpret Code Without Error")
    void InterpretCodeNoError() {
        String input = "class C {"
                + "  main{}"
                + "}";
        Assertions.assertNull(imj.interpretCode(input));
    }

    @Test
    @DisplayName("Interpret File BasicOperations")
    void InterpretFileBasicOperations() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/BasicOperations.mjj"));
    }

    @Test
    @DisplayName("Interpret File That Doesn't Exist")
    void InterpretNotExistingFile() {
        String errMessage=imj.interpretFile("src/test/resources/FileThatDoesntExist.mjj");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(NoSuchFileException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret File One")
    void FileOne() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/1.mjj"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("x : 8\nx : 11\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File Array")
    void Array() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Array.mjj"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("4\n33\n81\nfalse\ntrue\nfalse\nfalse\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File BasicOperations")
    void BasicOperations() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/BasicOperations.mjj"));
    }

    @Test
    @DisplayName("Interpret File Complex")
    void Complex() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Complex.mjj"));
    }

    @Test
    @DisplayName("Interpret File Conditionals")
    void Conditionals() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Conditionals.mjj"));
    }

    @Test
    @DisplayName("Interpret File Constant")
    void Constant() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Constant.mjj"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("5\nfalse\n40\ntrue\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File Fact")
    void Fact() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Fact.mjj"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("x : 7\nres : 5040\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File Local Variables")
    void LocalVariables() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/LocalVariables.mjj"));
    }

    @Test
    @DisplayName("Interpret File Loops")
    void Loops() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Loops.mjj"));
    }

    @Test
    @DisplayName("Interpret File Method")
    void Method() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Method.mjj"));
    }

    @Test
    @DisplayName("Interpret File MethodComplex")
    void MethodComplex() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/MethodComplex.mjj"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("addition : 101\nsubstraction : -99\ninferior : true\nHello World\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File MethodRecursive")
    void MethodRecursive() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/MethodRecursive.mjj"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("5\n4\n3\n2\n1\n0\n0\n1\n2\n3\n4\n5\n", e.oldText());
        });
        writer.write("");
    }



    @Test
    @DisplayName("Interpret File OperationPrevalence")
    void OperationPrevalence() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/OperationPrevalence.mjj"));
    }

    @Test
    @DisplayName("Interpret File Quicksort")
    void Quicksort() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Quick_sort.mjj"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("4,81,63,12,33,22,16,44,55,23,27,5,14,18,6,30,87,31,10,43, \n4,5,6,10,12,14,16,18,22,23,27,30,31,33,43,44,55,63,81,87, \n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret File Simple")
    void Simple() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Simple.mjj"));
    }

    @Test
    @DisplayName("Interpret File Synonymie")
    void Synonymie() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Synonymie.mjj"));
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("3,3,3,4,3,3,3,3,3,3, \n7,7,7,7,7,7,7,4,7,7, \n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret Undefined Variable / sum")
    void UndefinedVariableSum() {
        String errMessage=imj.interpretCode("class C {int y = 10;main {x += y;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Undefined Variable / Inc")
    void UndefinedVariableInc() {
        String errMessage=imj.interpretCode("class C {int y = 10;main {x++;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Undefined Variable / Evaluation")
    void UndefinedVariableEval() {
        String errMessage=imj.interpretCode("class C {int y = 10;main {y = x;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Unnamed Variable")
    void UnnamedVariable() {
        String errMessage=imj.interpretCode("class C {int  = 10;main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret No Main")
    void noMain() {
        String errMessage=imj.interpretCode("class C {int y = 10;}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret No Class")
    void noClass() {
        String errMessage=imj.interpretCode("int y = 10; main{}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Without Value")
    void declarationWithoutValue() {
        String errMessage=imj.interpretCode("class C {int y = ;main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Without Value")
    void affectationWithoutValue() {
        String errMessage=imj.interpretCode("class C {int y; main {y= ;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sum Without Value")
    void sumWithoutValue() {
        String errMessage=imj.interpretCode("class C {int y=10; main {y+= ;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Increment on unassigned variable")
    void incrementUnassigned() {
        String errMessage=imj.interpretCode("class C {int y; main {y++;}}");
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Increment")
    void increment() {
        String errMessage=imj.interpretCode("class C {int y=1; main {y++;}}");
        Assertions.assertNull(errMessage);
    }

    @Test
    @DisplayName("Interpret Multiple Class")
    void multipleClass() {
        String errMessage=imj.interpretCode("class C {main {}} class D {main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Multiple Main")
    void multipleMain() {
        String errMessage=imj.interpretCode("class C {main {} main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Class into Class")
    void classIntoClass() {
        String errMessage=imj.interpretCode("class C {class D {main {}}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Class Into Main")
    void classIntoMain() {
        String errMessage=imj.interpretCode("main {class C {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Main Into Main")
    void mainIntoMain() {
        String errMessage=imj.interpretCode("class C {main {main {}}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret String Error")
    void stringError() {
        String errMessage=imj.interpretCode("ERROR");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Unnamed Class")
    void unnamedClass() {
        String errMessage=imj.interpretCode("class {main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Named Main")
    void namedMain() {
        String errMessage=imj.interpretCode("class C {main M{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Every Operations At Once")
    void everyOperations() {
        String errMessage=imj.interpretCode("class C {main{int y=1 +-*/> 2;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Every Types At Once")
    void everyTypes() {
        String errMessage=imj.interpretCode("class C {void int boolean y; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Variable Named Int")
    void variableNamedInt() {
        String errMessage=imj.interpretCode("class C {int int; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Variable Named Int2")
    void variableNamedInt2() {
        String errMessage=imj.interpretCode("class C {int int2; main{}}");
        Assertions.assertNull(errMessage);
    }


    @Test
    @DisplayName("Interpret If Without Condition")
    void ifWithoutCondition() {
        String errMessage=imj.interpretCode("class C { main{if(){};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Else Without If")
    void elseWithoutIf() {
        String errMessage=imj.interpretCode("class C { main{else{};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Instruction Not In Main")
    void instructionNotInMain() {
        String errMessage=imj.interpretCode("class C { int y; y = 10; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration With 2 Equals")
    void declarationWith2Equals() {
        String errMessage=imj.interpretCode("class C { int y == 10; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation With 2 Equals")
    void affectationWith2Equals() {
        String errMessage=imj.interpretCode("class C { int y; main{y == 10;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Double Declaration")
    void doubleDeclaration() {
        String errMessage=imj.interpretCode("class C { int y = 5 = 10; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Double Affectation")
    void doubleAffectation() {
        String errMessage=imj.interpretCode("class C { int y; main{y = 5 = 10;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Increment With 3 Plus")
    void incrementWith3Plus() {
        String errMessage=imj.interpretCode("class C { int y = 10; main{y +++;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Increment With Number")
    void incrementWithNumber() {
        String errMessage=imj.interpretCode("class C { int y = 10; main{y ++ 5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Increment Affectation")
    void incrementAffectation() {
        String errMessage=imj.interpretCode("class C { int y = 10; main{y ++= 5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sum Without Ident")
    void sumWithoutIdent() {
        String errMessage=imj.interpretCode("class C { int y = 10; main{ += 5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Without Ident")
    void affectationWithoutIdent() {
        String errMessage=imj.interpretCode("class C { int y; main{ = 5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation On A Number")
    void affectationOnANumber() {
        String errMessage=imj.interpretCode("class C { int y; main{ 1 = 5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Number")
    void number() {
        String errMessage=imj.interpretCode("class C { main{10;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret I Forgot A Semicolon")
    void iForgotASemiColon() {
        String errMessage=imj.interpretCode("class C {main{int y=1 + 2}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Wrong Type")
    void declarationWrongType() {
        String errMessage=imj.interpretCode("class C {int y=false; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Wrong Type 2")
    void declarationWrongType2() {
        String errMessage=imj.interpretCode("class C {boolean y=0; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Void")
    void declarationVoid() {
        String errMessage=imj.interpretCode("class C {void y; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Boolean Add")
    void declarationBooleanAdd() {
        String errMessage=imj.interpretCode("class C {boolean y=1+5; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Boolean Minus")
    void declarationBooleanMinus() {
        String errMessage=imj.interpretCode("class C {boolean y=1-5; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Boolean Mul")
    void declarationBooleanMul() {
        String errMessage=imj.interpretCode("class C {boolean y=1*5; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Boolean Div")
    void declarationBooleanDiv() {
        String errMessage=imj.interpretCode("class C {boolean y=1/5; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Boolean Neg")
    void declarationBooleanNeg() {
        String errMessage=imj.interpretCode("class C {boolean y=-5; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Int And")
    void declarationIntAnd() {
        String errMessage=imj.interpretCode("class C {int y=false && true; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Int Or")
    void declarationIntOr() {
        String errMessage=imj.interpretCode("class C {int y=false || true; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Int Not")
    void declarationIntNot() {
        String errMessage=imj.interpretCode("class C {int y=! true; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Int Sup")
    void declarationIntSup() {
        String errMessage=imj.interpretCode("class C {int y=4 > 1; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Wrong Type")
    void affectationWrongType() {
        String errMessage=imj.interpretCode("class C {int y; main{y=false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Wrong Type 2")
    void affectationWrongType2() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=0;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Boolean Add")
    void affectationBooleanAdd() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1+5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Boolean Minus")
    void affectationBooleanMinus() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1-5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Boolean Mul")
    void affectationBooleanMul() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1*5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Boolean Div")
    void affectationBooleanDiv() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1/5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Boolean Neg")
    void affectationBooleanNeg() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=-5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Int And")
    void affectationIntAnd() {
        String errMessage=imj.interpretCode("class C {int y; main{y=false && true;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Int Or")
    void affectationIntOr() {
        String errMessage=imj.interpretCode("class C {int y; main{y=false || true;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Int Not")
    void affectationIntNot() {
        String errMessage=imj.interpretCode("class C {int y; main{y=! true;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Int Sup")
    void affectationIntSup() {
        String errMessage=imj.interpretCode("class C {int y; main{y=4 > 1;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Add Boolean")
    void addBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true+false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Minus Boolean")
    void minusBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true-false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Mul Boolean")
    void mulBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true*false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Div Boolean")
    void divBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true/false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Sup Boolean")
    void supBoolean() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=true>false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Neg Boolean")
    void negBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=-false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Not Int")
    void notInt() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=!4;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret And Int")
    void andInt() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=81 && 1;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Or Int")
    void orInt() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=4 || 81;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret If Int")
    void ifInt() {
        String errMessage=imj.interpretCode("class C { main{if(4){};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret If Variable Int")
    void ifVarInt() {
        String errMessage=imj.interpretCode("class C {int y=4; main{if(y){};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret While Int")
    void whileInt() {
        String errMessage=imj.interpretCode("class C { main{while(4){};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret While Variable Int")
    void whileVarInt() {
        String errMessage=imj.interpretCode("class C {int y=4; main{while(y){};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Inc Bool")
    void incBool() {
        String errMessage=imj.interpretCode("class C {boolean y=false; main{y++;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sum Variable Bool")
    void sumVarBool() {
        String errMessage=imj.interpretCode("class C {boolean y=false; main{y+=4;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sum Bool")
    void sumBool() {
        String errMessage=imj.interpretCode("class C {int y=4; main{y+=false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Write Variable Bool")
    void WriteVarBool() {
        String errMessage=imj.interpretCode("class C {boolean y=false; main{write(y);}}");
        Assertions.assertNull(errMessage);
    }

    @Test
    @DisplayName("Interpret Write Variable Int")
    void WriteVarInt() {
        String errMessage=imj.interpretCode("class C {int y=4; main{write(y);}}");
        Assertions.assertNull(errMessage);
    }

    @Test
    @DisplayName("Interpret Write String")
    void WriteString() {
        String errMessage=imj.interpretCode("class C {int y; main{write(\"Hello World\");}}");
        Assertions.assertNull(errMessage);
    }

    @Test
    @DisplayName("Interpret Write Bool")
    void WriteBool() {
        String errMessage=imj.interpretCode("class C {int y; main{write(false);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Write Int")
    void WriteInt() {
        String errMessage=imj.interpretCode("class C {int y; main{write(2);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Write Undefined Variable")
    void WriteUndefinedVar() {
        String errMessage=imj.interpretCode("class C {int y; main{write(y);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Write Not Existing Variable")
    void WriteNotExistingVar() {
        String errMessage=imj.interpretCode("class C { main{write(y);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Writeln Variable Bool")
    void WritelnVarBool() {
        String errMessage=imj.interpretCode("class C {boolean y=false; main{writeln(y);}}");
        Assertions.assertNull(errMessage);
    }

    @Test
    @DisplayName("Interpret Writeln Variable Int")
    void WritelnVarInt() {
        String errMessage=imj.interpretCode("class C {int y=4; main{writeln(y);}}");
        Assertions.assertNull(errMessage);
    }

    @Test
    @DisplayName("Interpret Writeln String")
    void WritelnString() {
        String errMessage=imj.interpretCode("class C {int y; main{writeln(\"Hello World\");}}");
        Assertions.assertNull(errMessage);
    }

    @Test
    @DisplayName("Interpret Writeln Bool")
    void WritelnBool() {
        String errMessage=imj.interpretCode("class C {int y; main{writeln(false);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Writeln Int")
    void WritelnInt() {
        String errMessage=imj.interpretCode("class C {int y; main{writeln(2);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Writeln Undefined Variable")
    void WritelnUndefinedVar() {
        String errMessage=imj.interpretCode("class C {int y; main{writeln(y);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Writeln Not Existing Variable")
    void WritelnNotExistingVar() {
        String errMessage=imj.interpretCode("class C { main{writeln(y);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Method Without Type")
    void MethodWithoutType() {
        String errMessage=imj.interpretCode("class C { f(){};main{f();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Void Method With Return")
    void VoidMethodWithReturn() {
        String errMessage=imj.interpretCode("class C { void f(){return 1;};main{f();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Non-Void Method Without Return")
    void NonVoidMethodWithoutReturn() {
        String errMessage=imj.interpretCode("class C { int f(){};main{f();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Int Method With Bool Return")
    void IntMethodBoolReturn() {
        String errMessage=imj.interpretCode("class C { int f(){return false;};main{f();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Bool Method With Int Return")
    void BoolMethodIntReturn() {
        String errMessage=imj.interpretCode("class C { boolean f(){return 1;};main{f();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret String Method")
    void StringMethod() {
        String errMessage=imj.interpretCode("class C { string f(){return \"Hello World\";};main{f();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Method Instr Before Var")
    void MethodInstrBeforeVar() {
        String errMessage=imj.interpretCode("class C { void f(){write(\"f\"); int x = 0;};main{f();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Call Void Method In Var")
    void CallVoidMethodInVar() {
        String errMessage=imj.interpretCode("class C { void f(){write(\"f\"); };main{int x = f();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Call Int Method In Bool Var")
    void CallIntMethodInBoolVar() {
        String errMessage=imj.interpretCode("class C { int f(){return 5; };main{boolean x = f();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Call Bool Method In Int Var")
    void CallBoolMethodIntVar() {
        String errMessage=imj.interpretCode("class C { boolean f(){return false; };main{int x = f();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Method With Void Args")
    void MethodWithVoidArgs() {
        String errMessage=imj.interpretCode("class C { void f(void arg1){ };main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Method In Method")
    void MethodInMethod() {
        String errMessage=imj.interpretCode("class C { void f(){void f2(){ }; };main{f();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Method In Main")
    void MethodInMain() {
        String errMessage=imj.interpretCode("class C { void f(){ };main{void f2(){ };}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Main In Method")
    void MainInMethod() {
        String errMessage=imj.interpretCode("class C { void f(){ main{ f()}};}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Method Args Int Call With Bool")
    void MethodArgsIntCallWithBool() {
        String errMessage=imj.interpretCode("class C { void f(int x){ }; main{ f(false);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Method Args Bool Call With Int")
    void MethodArgsBoolCallWithInt() {
        String errMessage=imj.interpretCode("class C { void f(boolean x){ }; main{ f(1);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Method Many Args Type Error")
    void MethodManyArgsTypeError() {
        String errMessage=imj.interpretCode("class C { void f(boolean x,boolean y,int z){ }; main{ f(false,1,99);}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Method Three Args")
    void MethodThreeArgs() {
        String errMessage=imj.interpretCode("class C { void f(boolean x,int y,int z){ writeln(x); writeln(y); writeln(z);}; main{ f(false,1,99);}}");
        Assertions.assertNull(errMessage);
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("false\n1\n99\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret Method Eight Args")
    void MethodEightArgs() {
        String errMessage=imj.interpretCode("class C { void f(boolean a,int b,int c,int d,boolean e,int f,int g,int h){ writeln(a); writeln(b); writeln(c); writeln(d); writeln(e); writeln(f); writeln(g); writeln(h); }; main{ f(false,1,99,27,true,5,10,81);}}");
        Assertions.assertNull(errMessage);
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("false\n1\n99\n27\ntrue\n5\n10\n81\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret Variable Defined In Method Used In Main")
    void VariableDefinedInMethodUsedInMain() {
        String errMessage=imj.interpretCode("class C { void f(){ int x=0;}; main{ f(); write(x);}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Variable Defined In Method Used In Another Method")
    void VariableDefinedInMethodUsedInAnotherMethod() {
        String errMessage=imj.interpretCode("class C { void f(){ int x=0;}; void g(){ write(x);}; main{ f(); g();}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Boolean Index")
    void BooleanIndex() {
        String errMessage=imj.interpretCode("class C { int t[20]; main{t[false]=3;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Negative Index")
    void NegativeIndex() {
        String errMessage=imj.interpretCode("class C { int t[20]; main{t[-1]=3;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Out Of Bounds Index")
    void OutOfBoundsIndex() {
        String errMessage=imj.interpretCode("class C { int t[20]; main{t[20]=3;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Boolean Array Size")
    void BooleanArraySize() {
        String errMessage=imj.interpretCode("class C { boolean t[false]; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Negative Array Size")
    void NegativeArraySize() {
        String errMessage=imj.interpretCode("class C { int t[-20]; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidMemoryException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Empty Array Size")
    void EmptyArraySize() {
        String errMessage=imj.interpretCode("class C { int t[]; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Empty Array Index 1")
    void EmptyArrayIndex1() {
        String errMessage=imj.interpretCode("class C { int t[8]; main{ t[]=3;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Empty Array Index 2")
    void EmptyArrayIndex2() {
        String errMessage=imj.interpretCode("class C { int t[1]; main{ int x; t[0]=3; x=t[];}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Empty Length")
    void EmptyLength() {
        String errMessage=imj.interpretCode("class C { int t[8]; main{ int x=length();}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Length Not Array")
    void LengthNotArray() {
        String errMessage=imj.interpretCode("class C { int t=8; main{ int x=length(t);}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Length Affected In Bool Variable")
    void LengthAffectedInBoolVariable() {
        String errMessage=imj.interpretCode("class C { int t[5]; main{ boolean x=length(t);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Length Undefined Variable")
    void LengthAffectedUndefinedVariable() {
        String errMessage=imj.interpretCode("class C { main{ int x=length(t);}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Length As Instruction")
    void LengthAsInstruction() {
        String errMessage=imj.interpretCode("class C { int t[5]; main{ length(t);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Array Initialize With Value")
    void ArrayInitializeWithValue() {
        String errMessage=imj.interpretCode("class C { int t[5]=8; main{ }}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Final Array")
    void FinalArray() {
        String errMessage=imj.interpretCode("class C { final int t[5]; main{ }}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affect Value In Array")
    void AffectValueInArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; int x=5; main{ t=x;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Affect Array In Value")
    void AffectArrayInValue() {
        String errMessage=imj.interpretCode("class C { int t[5]; int x=5; main{ x=t;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(InterpretationInvalidTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Add Array")
    void AddArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; main{ t=t+t;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Add Array And Number")
    void AddArrayAndNumber() {
        String errMessage=imj.interpretCode("class C { int t[5]; int x=5; main{ t=t+x;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Sub Array")
    void SubArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; main{ t=t-t;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Sub Array And Number")
    void SubArrayAndNumber() {
        String errMessage=imj.interpretCode("class C { int t[5]; int x=5; main{ t=t-x;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Mul Array")
    void MulArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; main{ t=t*t;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Mul Array And Number")
    void MulArrayAndNumber() {
        String errMessage=imj.interpretCode("class C { int t[5]; int x=5; main{ t=t*x;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Div Array")
    void DivArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; main{ t=t/t;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Div Array And Number")
    void DivArrayAndNumber() {
        String errMessage=imj.interpretCode("class C { int t[5]; int x=5; main{ t=t/x;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Sup Array")
    void SupArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; boolean t2[5]; main{ t2=t>t;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Sup Array And Number")
    void SupArrayAndNumber() {
        String errMessage=imj.interpretCode("class C { int t[5]; boolean t2[5]; int x=5; main{ t2=t>x;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret And Array")
    void AndArray() {
        String errMessage=imj.interpretCode("class C { boolean t[5]; main{ t=t&&t;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret And Array And Number")
    void AndArrayAndNumber() {
        String errMessage=imj.interpretCode("class C { boolean t[5]; boolean x=false; main{ t=t&&x;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Or Array")
    void OrArray() {
        String errMessage=imj.interpretCode("class C { boolean t[5]; main{ t=t||t;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Or Array And Number")
    void OrArrayAndNumber() {
        String errMessage=imj.interpretCode("class C { boolean t[5]; boolean x=false; main{ t=t||x;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Negative Array")
    void NegativeArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; main{ t=-t;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Not Array")
    void NotArray() {
        String errMessage=imj.interpretCode("class C { boolean t[5]; main{ t=!t;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Boolean Array")
    void BooleanArray() {
        String errMessage=imj.interpretCode("class C { boolean t[5]; main{ t[0]=false; t[1]=true; t[2]=false; t[3]=true; t[4]=false; writeln(t[0]); writeln(t[1]); writeln(t[2]); writeln(t[3]); writeln(t[4]);}}");
        Assertions.assertNull(errMessage);
        writer.textChangedEvent.subscribe(e -> {
            assertEquals("false\ntrue\nfalse\ntrue\nfalse\n", e.oldText());
        });
        writer.write("");
    }

    @Test
    @DisplayName("Interpret If Array")
    void IfArray() {
        String errMessage=imj.interpretCode("class C { boolean t[5]; main{ if(t){};}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret While Array")
    void WhileArray() {
        String errMessage=imj.interpretCode("class C { boolean t[5]; main{ while(t){};}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Write Array")
    void WriteArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; main{ write(t);}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Writeln Array")
    void WritelnArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; main{ writeln(t);}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Method Argument Array")
    void MethodArgumentArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; f(int x){}; main{ f(t);}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Method Many Argument Array 1")
    void MethodManyArgumentArray1() {
        String errMessage=imj.interpretCode("class C { int t[5]; f(int x,boolean y,int z){}; main{ f(t,false,3);}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Method Many Argument Array 2")
    void MethodManyArgumentArray2() {
        String errMessage=imj.interpretCode("class C { boolean t[5]; f(int x,boolean y,int z){}; main{ f(5,t,7);}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Method Many Argument Array 3")
    void MethodManyArgumentArray3() {
        String errMessage=imj.interpretCode("class C { int t[5]; f(int x,boolean y,int z){}; main{ f(3,false,t);}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Increment Array")
    void IncrementArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; main{ t++;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Sum Array on Array")
    void SumArrayOnArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; main{ t+=t;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Sum Array on Value")
    void SumArrayOnValue() {
        String errMessage=imj.interpretCode("class C { int t[5]; int x=5; main{ t+=x;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Sum Value on Array")
    void SumValueOnArray() {
        String errMessage=imj.interpretCode("class C { int t[5]; int x=5; main{ x+=t;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Var Bool 1")
    void FinalIntVarBool1() {
        String errMessage=imj.interpretCode("class C { final int x=false; main{ }}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Var Bool 2")
    void FinalIntVarBool2() {
        String errMessage=imj.interpretCode("class C { final int x; main{ x=false; }}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Bool Var Int 1")
    void FinalBoolVarInt1() {
        String errMessage=imj.interpretCode("class C { final boolean x=1; main{ }}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Bool Var Int 2")
    void FinalBoolVarInt2() {
        String errMessage=imj.interpretCode("class C { final boolean x; main{ x=1; }}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final String 1")
    void FinalString1() {
        String errMessage=imj.interpretCode("class C { final string x=\"Error\"; main{ }}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final String 2")
    void FinalString2() {
        String errMessage=imj.interpretCode("class C { final string x; main{ x=\"Error\"; }}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Void")
    void FinalVoid() {
        String errMessage=imj.interpretCode("class C { final void x; main{ }}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Affectation After Initialization 1")
    void FinalIntAffectationAfterInitialization1() {
        String errMessage=imj.interpretCode("class C { final int x=15; main{ x=157;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Affectation After Initialization 2")
    void FinalIntAffectationAfterInitialization2() {
        String errMessage=imj.interpretCode("class C { final int x; main{ x=15; x=157; }}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Bool Affectation After Initialization 1")
    void FinalBoolAffectationAfterInitialization1() {
        String errMessage=imj.interpretCode("class C { final boolean x=false; main{ x=true;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Bool Affectation After Initialization 2")
    void FinalBoolAffectationAfterInitialization2() {
        String errMessage=imj.interpretCode("class C { final boolean x; main{ x=false; x=true; }}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Increment After Initialization 1")
    void FinalIntIncrementAfterInitialization1() {
        String errMessage=imj.interpretCode("class C { final int x=15; main{ x++;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Increment After Initialization 2")
    void FinalIntIncrementAfterInitialization2() {
        String errMessage=imj.interpretCode("class C { final int x; main{ x=15; x++; }}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Sum After Initialization 1")
    void FinalIntSumAfterInitialization1() {
        String errMessage=imj.interpretCode("class C { final int x=15; main{ x+=157;}}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Final Int Sum After Initialization 2")
    void FinalIntSumAfterInitialization2() {
        String errMessage=imj.interpretCode("class C { final int x; main{ x=15; x+=157; }}");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Random String")
    void randomString() {
        String errMessage=imj.interpretCode("ezudzedezezbclassdezdoncCdzedo{dezodjintezpodjy;podkezdmain{ydpocke=dozejd10;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Empty String")
    void emptyString() {
        String errMessage=imj.interpretCode("");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Null String")
    void nullString() {
        String errMessage=imj.interpretCode(null);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Null Filepath")
    void nullFilepath() {
        String errMessage=imj.interpretFile(null);
        Assertions.assertNotEquals(null,errMessage);
    }

    /* Step by step // Breakpoints */
    @Test
    @DisplayName("Interpret -  step by step")
    void stepByStep() {
        int[] steps = {0};
        InterpreterMiniJaja imjj=new InterpreterMiniJaja();
        InterpretationMode im = InterpretationMode.STEP_BY_STEP;
        imjj.interpretationHaltedEvent.subscribe(event -> {
            steps[0]++;
            System.out.println(event);
            imjj.resumeInterpretation(im);
        });
        imjj.startFileInterpretation("src/test/resources/Complex.mjj", im);
        imjj.waitInterpretation();
        Assertions.assertTrue(steps[0] == 5 || steps[0] == 6); // 5 steps + end event trigger potentially triggered
    }

    @Test
    @DisplayName("Interpret -  breakpoints")
    void breakpoints() throws InterruptedException {
        int[] steps = {0};
        InterpretationMode im = InterpretationMode.BREAKPOINTS;
        InterpreterMiniJaja imjj=new InterpreterMiniJaja();
        imjj.interpretationHaltedEvent.subscribe(event -> {
            steps[0]++;
            if(event.isPursuable())
                imjj.resumeInterpretation(im);
        });
        imjj.setBreakpoints(List.of(14,16));
        imjj.startFileInterpretation("src/test/resources/Complex.mjj", im);
        imjj.waitInterpretation();
        Assertions.assertTrue(steps[0] == 2 || steps[0] == 3); // 2 breakpoints + end event trigger potentially triggered
    }

    @Test
    @DisplayName("Interpret - error during step by step")
    void errorDuringStepByStep() {
        int[] steps = {0};
        List<String> errors = new ArrayList<>();
        InterpreterMiniJaja imjj=new InterpreterMiniJaja();
        // Creating result
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add(null);
        expectedErrors.add(null);
        expectedErrors.add("Symbol b is not defined (at line 1, ++)");

        InterpretationMode im = InterpretationMode.STEP_BY_STEP;
        imjj.interpretationHaltedEvent.subscribe(event -> {
            steps[0]++;
            errors.add(event.error());
            imjj.resumeInterpretation(im);
        });
        imjj.startCodeInterpretation("class C { int a = 1; int b; main{a++; b++;}}", im);
        imjj.waitInterpretation();
        Assertions.assertTrue(steps[0] == 2 || steps[0] == 3);
        Assertions.assertTrue(errors.containsAll(expectedErrors));
    }

    @Test
    @DisplayName("Interpret Code With Single Line Comments")
    void interpretCodeWithComments() {
        String input = "// This is a comment\n"
                + "class C {\n"
                + "  // Variable declaration\n"
                + "  int x = 10; // Initialize x to 10\n"
                + "  main {\n"
                + "    // Increment x\n"
                + "    x = x + 1;\n"
                + "  }\n"
                + "}";
        Assertions.assertNull(imj.interpretCode(input));
    }

    @Test
    @DisplayName("Interpret Code With Comments At Different Positions")
    void interpretCodeWithCommentsAtDifferentPositions() {
        String input = "// Start comment\n"
                + "class C { // Class comment\n"
                + "  int y = 5;\n"
                + "  // Method comment\n"
                + "  main { // Main comment\n"
                + "    y = y * 2; // Multiplication\n"
                + "  } // End main\n"
                + "} // End class";
        Assertions.assertNull(imj.interpretCode(input));
    }

    @Test
    @DisplayName("Interpret Code With Empty Comments")
    void interpretCodeWithEmptyComments() {
        String input = "//\n"
                + "class C {\n"
                + "  //\n"
                + "  int z = 1;\n"
                + "  main {\n"
                + "    //\n"
                + "    z++;\n"
                + "  }\n"
                + "}";
        Assertions.assertNull(imj.interpretCode(input));
    }
}
