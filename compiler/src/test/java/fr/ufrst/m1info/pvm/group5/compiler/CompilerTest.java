package fr.ufrst.m1info.pvm.group5.compiler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompilerTest {
    Compiler comp;

    @BeforeEach
    public void setup(){
        comp=new Compiler();
    }

    @Test
    @DisplayName("Compile Code Without Error")
    public void CompileCodeNoError()  {
        String input = "class C {"
                + "  main{}"
                + "}";
        String res= comp.compileCode(input);
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File BasicOperations")
    public void CompileBasicOperations()  {
        String res= comp.compileCode("src/test/resources/BasicOperations.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File Complex")
    public void CompileComplex()  {
        String res= comp.compileCode("src/test/resources/Complex.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File Conditionals")
    public void CompileConditionals()  {
        String res= comp.compileCode("src/test/resources/Conditionals.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File LocalVariables")
    public void CompileLocalVariables()  {
        String res= comp.compileCode("src/test/resources/LocalVariables.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File Loops")
    public void CompileLoops()  {
        String res= comp.compileCode("src/test/resources/Loops.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File OperationPrevalence")
    public void CompileOperationPrevalence()  {
        String res= comp.compileCode("src/test/resources/OperationPrevalence.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File Simple")
    public void CompileSimple()  {
        String res= comp.compileCode("src/test/resources/Simple.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }
}
