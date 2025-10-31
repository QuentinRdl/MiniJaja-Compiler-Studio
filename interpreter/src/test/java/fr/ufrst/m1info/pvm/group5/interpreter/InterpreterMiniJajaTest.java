package fr.ufrst.m1info.pvm.group5.interpreter;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.*;

import fr.ufrst.m1info.pvm.group5.ast.*;

import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/*
Commented tests doesn't work because API memory is not done yet
 */
public class InterpreterMiniJajaTest {
    InterpreterMiniJaja imj;

    @BeforeEach
    public void setup() {
        imj=new InterpreterMiniJaja();
    }

    @Test
    @DisplayName("Interpret Code Without Error")
    public void InterpretCodeNoError() {
        String input = "class C {"
                + "  main{}"
                + "}";
        Assertions.assertNull(imj.interpretCode(input));
    }

    @Test
    @DisplayName("Interpret File BasicOperations")
    public void InterpretFileBasicOperations() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/BasicOperations.mjj"));
    }

    @Test
    @DisplayName("Interpret File That Doesn't Exist")
    public void InterpretNotExistingFile() {
        String errMessage=imj.interpretFile("src/test/resources/FileThatDoesntExist.mjj");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(NoSuchFileException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret File BasicOperations")
    public void BasicOperations() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/BasicOperations.mjj"));
    }

    @Test
    @DisplayName("Interpret File Complex")
    public void Complex() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Complex.mjj"));
    }

    @Test
    @DisplayName("Interpret File Conditionals")
    public void Conditionals() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Conditionals.mjj"));
    }

    @Test
    @DisplayName("Interpret File Local Variables")
    public void LocalVariables() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/LocalVariables.mjj"));
    }

    @Test
    @DisplayName("Interpret File Loops")
    public void Loops() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Loops.mjj"));
    }

    @Test
    @DisplayName("Interpret File OperationPrevalence")
    public void OperationPrevalence() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/OperationPrevalence.mjj"));
    }

    @Test
    @DisplayName("Interpret File Simple")
    public void Simple() {
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
    public void UndefinedVariableInc() {
        String errMessage=imj.interpretCode("class C {int y = 10;main {x++;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(IllegalArgumentException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Undefined Variable / Evaluation")
    public void UndefinedVariableEval() {
        String errMessage=imj.interpretCode("class C {int y = 10;main {y = x;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(java.lang.IllegalArgumentException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Unnamed Variable")
    public void UnnamedVariable() {
        String errMessage=imj.interpretCode("class C {int  = 10;main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret No Main")
    public void noMain() {
        String errMessage=imj.interpretCode("class C {int y = 10;}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret No Class")
    public void noClass() {
        String errMessage=imj.interpretCode("int y = 10; main{}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Without Value")
    public void declarationWithoutValue() {
        String errMessage=imj.interpretCode("class C {int y = ;main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Without Value")
    public void affectationWithoutValue() {
        String errMessage=imj.interpretCode("class C {int y; main {y= ;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sum Without Value")
    public void sumWithoutValue() {
        String errMessage=imj.interpretCode("class C {int y=10; main {y+= ;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Increment")
    public void increment() {
        String errMessage=imj.interpretCode("class C {int y; main {y++;}}");
        Assertions.assertNull(errMessage);
    }

    @Disabled // TODO : Re-enable this test when the issue is fixed
    @Test
    @DisplayName("Interpret Multiple Class")
    public void multipleClass() {
        String errMessage=imj.interpretCode("class C {main {}} class D {main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Multiple Main")
    public void multipleMain() {
        String errMessage=imj.interpretCode("class C {main {} main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Class into Class")
    public void classIntoClass() {
        String errMessage=imj.interpretCode("class C {class D {main {}}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Class Into Main")
    public void classIntoMain() {
        String errMessage=imj.interpretCode("main {class C {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Main Into Main")
    public void mainIntoMain() {
        String errMessage=imj.interpretCode("class C {main {main {}}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret String Error")
    public void stringError() {
        String errMessage=imj.interpretCode("ERROR");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Unnamed Class")
    public void unnamedClass() {
        String errMessage=imj.interpretCode("class {main {}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Named Main")
    public void namedMain() {
        String errMessage=imj.interpretCode("class C {main M{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Every Operations At Once")
    public void everyOperations() {
        String errMessage=imj.interpretCode("class C {main{int y=1 +-*/> 2;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Every Types At Once")
    public void everyTypes() {
        String errMessage=imj.interpretCode("class C {void int boolean y; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Variable Named Int")
    public void variableNamedInt() {
        String errMessage=imj.interpretCode("class C {int int; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Variable Named Int2")
    public void variableNamedInt2() {
        String errMessage=imj.interpretCode("class C {int int2; main{}}");
        Assertions.assertNull(errMessage);
    }


    @Test
    @DisplayName("Interpret If Without Condition")
    public void ifWithoutCondition() {
        String errMessage=imj.interpretCode("class C { main{if(){};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Else Without If")
    public void elseWithoutIf() {
        String errMessage=imj.interpretCode("class C { main{else{};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Instruction Not In Main")
    public void instructionNotInMain() {
        String errMessage=imj.interpretCode("class C { int y; y = 10; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration With 2 Equals")
    public void declarationWith2Equals() {
        String errMessage=imj.interpretCode("class C { int y == 10; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation With 2 Equals")
    public void affectationWith2Equals() {
        String errMessage=imj.interpretCode("class C { int y; main{y == 10;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Double Declaration")
    public void doubleDeclaration() {
        String errMessage=imj.interpretCode("class C { int y = 5 = 10; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Double Affectation")
    public void doubleAffectation() {
        String errMessage=imj.interpretCode("class C { int y; main{y = 5 = 10;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Increment With 3 Plus")
    public void incrementWith3Plus() {
        String errMessage=imj.interpretCode("class C { int y = 10; main{y +++;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Increment With Number")
    public void incrementWithNumber() {
        String errMessage=imj.interpretCode("class C { int y = 10; main{y ++ 5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Increment Affectation")
    public void incrementAffectation() {
        String errMessage=imj.interpretCode("class C { int y = 10; main{y ++= 5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sum Without Ident")
    public void sumWithoutIdent() {
        String errMessage=imj.interpretCode("class C { int y = 10; main{ += 5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Without Ident")
    public void affectationWithoutIdent() {
        String errMessage=imj.interpretCode("class C { int y; main{ = 5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation On A Number")
    public void affectationOnANumber() {
        String errMessage=imj.interpretCode("class C { int y; main{ 1 = 5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Number")
    public void number() {
        String errMessage=imj.interpretCode("class C { main{10;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret I Forgot A Semicolon")
    public void iForgotASemiColon() {
        String errMessage=imj.interpretCode("class C {main{int y=1 + 2}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Declaration Wrong Type")
    public void declarationWrongType() {
        String errMessage=imj.interpretCode("class C {int y=false; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Declaration Wrong Type 2")
    public void declarationWrongType2() {
        String errMessage=imj.interpretCode("class C {boolean y=0; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Declaration Void")
    public void declarationVoid() {
        String errMessage=imj.interpretCode("class C {void y; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Affectation Wrong Type")
    public void affectationWrongType() {
        String errMessage=imj.interpretCode("class C {int y; main{y=false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Affectation Wrong Type 2")
    public void affectationWrongType2() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=0;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Affectation Boolean Add")
    public void affectationBooleanAdd() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1+5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Affectation Boolean Minus")
    public void affectationBooleanMinus() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1-5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Affectation Boolean Mul")
    public void affectationBooleanMul() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1*5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Affectation Boolean Div")
    public void affectationBooleanDiv() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1/5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Affectation Boolean Neg")
    public void affectationBooleanNeg() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=-5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Add Boolean")
    public void addBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true+false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Minus Boolean")
    public void minusBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true-false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Mul Boolean")
    public void mulBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true*false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Div Boolean")
    public void divBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true/false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Sup Boolean")
    public void supBoolean() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=true>false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Neg Boolean")
    public void negBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=-false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Not Int")
    public void notInt() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=!4;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret And Int")
    public void andInt() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=81 && 1;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Disabled
    @Test
    @DisplayName("Interpret Or Int")
    public void orInt() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=4 || 81;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Writeln Int")
    public void WritelnInt() {
        String errMessage=imj.interpretCode("class C {int y; main{writeln(2);}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Random String")
    public void randomString() {
        String errMessage=imj.interpretCode("ezudzedezezbclassdezdoncCdzedo{dezodjintezpodjy;podkezdmain{ydpocke=dozejd10;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ParseCancellationException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Empty String")
    public void emptyString() {
        String errMessage=imj.interpretCode("");
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Null String")
    public void nullString() {
        String errMessage=imj.interpretCode(null);
        Assertions.assertNotEquals(null,errMessage);
    }

    @Test
    @DisplayName("Interpret Null Filepath")
    public void nullFilepath() {
        String errMessage=imj.interpretFile(null);
        Assertions.assertNotEquals(null,errMessage);
    }


}
