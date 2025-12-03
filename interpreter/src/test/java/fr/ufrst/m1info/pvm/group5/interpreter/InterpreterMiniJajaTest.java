package fr.ufrst.m1info.pvm.group5.interpreter;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.*;

import fr.ufrst.m1info.pvm.group5.ast.*;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

class InterpreterMiniJajaTest {
    InterpreterMiniJaja imj;

    @BeforeEach
    public void setup() {
        imj=new InterpreterMiniJaja();
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
    @DisplayName("Interpret File OperationPrevalence")
    void OperationPrevalence() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/OperationPrevalence.mjj"));
    }

    @Test
    @DisplayName("Interpret File Simple")
    void Simple() {
        Assertions.assertNull(imj.interpretFile("src/test/resources/Simple.mjj"));
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

     // TODO : Re-enable this test when the issue is fixed
    @Disabled
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
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Wrong Type 2")
    void declarationWrongType2() {
        String errMessage=imj.interpretCode("class C {boolean y=0; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Void")
    void declarationVoid() {
        String errMessage=imj.interpretCode("class C {void y; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Boolean Add")
    void declarationBooleanAdd() {
        String errMessage=imj.interpretCode("class C {boolean y=1+5; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Boolean Minus")
    void declarationBooleanMinus() {
        String errMessage=imj.interpretCode("class C {boolean y=1-5; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Boolean Mul")
    void declarationBooleanMul() {
        String errMessage=imj.interpretCode("class C {boolean y=1*5; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Boolean Div")
    void declarationBooleanDiv() {
        String errMessage=imj.interpretCode("class C {boolean y=1/5; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Declaration Boolean Neg")
    void declarationBooleanNeg() {
        String errMessage=imj.interpretCode("class C {boolean y=-5; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Int And")
    void declarationIntAnd() {
        String errMessage=imj.interpretCode("class C {int y=false && true; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Int Or")
    void declarationIntOr() {
        String errMessage=imj.interpretCode("class C {int y=false || true; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Int Not")
    void declarationIntNot() {
        String errMessage=imj.interpretCode("class C {int y=! true; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Declaration Int Sup")
    void declarationIntSup() {
        String errMessage=imj.interpretCode("class C {int y=4 > 1; main{}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Wrong Type")
    void affectationWrongType() {
        String errMessage=imj.interpretCode("class C {int y; main{y=false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Wrong Type 2")
    void affectationWrongType2() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=0;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Boolean Add")
    void affectationBooleanAdd() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1+5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Boolean Minus")
    void affectationBooleanMinus() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1-5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Boolean Mul")
    void affectationBooleanMul() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1*5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Boolean Div")
    void affectationBooleanDiv() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=1/5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Affectation Boolean Neg")
    void affectationBooleanNeg() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=-5;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Int And")
    void affectationIntAnd() {
        String errMessage=imj.interpretCode("class C {int y; main{y=false && true;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Int Or")
    void affectationIntOr() {
        String errMessage=imj.interpretCode("class C {int y; main{y=false || true;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Int Not")
    void affectationIntNot() {
        String errMessage=imj.interpretCode("class C {int y; main{y=! true;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Affectation Int Sup")
    void affectationIntSup() {
        String errMessage=imj.interpretCode("class C {int y; main{y=4 > 1;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Add Boolean")
    void addBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true+false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Minus Boolean")
    void minusBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true-false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Mul Boolean")
    void mulBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true*false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Div Boolean")
    void divBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=true/false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Sup Boolean")
    void supBoolean() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=true>false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Neg Boolean")
    void negBoolean() {
        String errMessage=imj.interpretCode("class C {int y; main{y=-false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret Not Int")
    void notInt() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=!4;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }


    @Test
    @DisplayName("Interpret And Int")
    void andInt() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=81 && 1;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Or Int")
    void orInt() {
        String errMessage=imj.interpretCode("class C {boolean y; main{y=4 || 81;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret If Int")
    void ifInt() {
        String errMessage=imj.interpretCode("class C { main{if(4){};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret If Variable Int")
    void ifVarInt() {
        String errMessage=imj.interpretCode("class C {int y=4; main{if(y){};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret While Int")
    void whileInt() {
        String errMessage=imj.interpretCode("class C { main{while(4){};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret While Variable Int")
    void whileVarInt() {
        String errMessage=imj.interpretCode("class C {int y=4; main{while(y){};}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Inc Bool")
    void incBool() {
        String errMessage=imj.interpretCode("class C {boolean y=false; main{y++;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sum Variable Bool")
    void sumVarBool() {
        String errMessage=imj.interpretCode("class C {boolean y=false; main{y+=4;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
    }

    @Test
    @DisplayName("Interpret Sum Bool")
    void sumBool() {
        String errMessage=imj.interpretCode("class C {int y=4; main{y+=false;}}");
        Assertions.assertNotEquals(null,errMessage);
        Assertions.assertEquals(ASTInvalidDynamicTypeException.class.toString(),errMessage.split(":")[0].trim());
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
    @Disabled
    @Test
    @DisplayName("Interpret -  step by step")
    void stepByStep() {
        int[] steps = {0};
        InterpretationMode im = InterpretationMode.STEP_BY_STEP;
        imj.interpretationHaltedEvent.subscribe(event -> {
            steps[0]++;
            imj.resumeInterpretation(im);
        });
        imj.startFileInterpretation("src/test/resources/Complex.mjj", im);
        imj.waitInterpretation();
        Assertions.assertTrue(steps[0] >= 5); // 5 steps + end event trigger potentially triggered
    }

    @Disabled
    @Test
    @DisplayName("Interpret -  breakpoints")
    void breakpoints() throws InterruptedException {
        int[] steps = {0};
        InterpretationMode im = InterpretationMode.BREAKPOINTS;
        imj.interpretationHaltedEvent.subscribe(event -> {
            steps[0]++;
            if(event.isPursuable())
                imj.resumeInterpretation(im);
        });
        imj.setBreakpoints(List.of(14,16));
        imj.startFileInterpretation("src/test/resources/Complex.mjj", im);
        imj.waitInterpretation();
        Assertions.assertTrue(steps[0] >= 2); // 2 breakpoints + end event trigger potentially triggered
    }

    @Disabled
    @Test
    @DisplayName("Interpret - error during step by step")
    void errorDuringStepByStep() {
        int[] steps = {0};
        List<String> errors = new ArrayList<>();
        // Creating result
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add(null);
        expectedErrors.add(null);
        expectedErrors.add("Variable b is undefined");

        InterpretationMode im = InterpretationMode.STEP_BY_STEP;
        imj.interpretationHaltedEvent.subscribe(event -> {
            steps[0]++;
            errors.add(event.error());
            imj.resumeInterpretation(im);
        });

        imj.startCodeInterpretation("class C { int a = 1; int b; main{a++; b++;}}", im);
        imj.waitInterpretation();
        Assertions.assertTrue(steps[0] >= 2);
        Assertions.assertTrue(errors.containsAll(expectedErrors));
    }
}
