package fr.ufrst.m1info.pvm.group5.compiler;

import org.junit.jupiter.api.*;

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

    // Line x++; return push(1) load(x) instead of push(1) inc(x)
    @Disabled
    @Test
    @DisplayName("Compile File BasicOperations")
    public void CompileBasicOperations()  {
        String res= comp.compileFile("src/test/resources/BasicOperations.mjj");
        Assertions.assertEquals("init\nnew(x,INT,var,0)\npush(3)\npush(4)\nadd\nstore(x)\npush(1)\ninc(x)\npush(0)\nswap\npop\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File Complex")
    public void CompileComplex()  {
        String res= comp.compileFile("src/test/resources/Complex.mjj");
        String expected = "init\n" +
                "new(x,INT,var,0)\n" +
                "push(10)\n" +
                "new(y,INT,var,0)\n" +
                "push(5)\n" +
                "new(VAL,INT,cst,0)\n" +
                "push(jcvrai)\n" +
                "new(b1,BOOL,var,0)\n" +
                "push(jcfaux)\n" +
                "new(b2,BOOL,var,0)\n" +
                "load(b1)\n" +
                "load(b1)\n" +
                "load(b2)\n" +
                "not\n" +
                "or\n" +
                "and\n" +
                "if(21)\n" +
                "push(1)\n" +
                "inc(x)\n" +
                "goto(27)\n" +
                "load(VAL)\n" +
                "load(y)\n" +
                "add\n" +
                "push(0)\n" +
                "sub\n" +
                "store(x)\n" +
                "load(y)\n" +
                "load(VAL)\n" +
                "mul\n" +
                "store(x)\n" +
                "load(x)\n" +
                "load(VAL)\n" +
                "div\n" +
                "store(x)\n" +
                "push(1)\n" +
                "inc(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        Assertions.assertEquals(expected,res);
    }

    @Disabled
    @Test
    @DisplayName("Compile File Conditionals")
    public void CompileConditionals()  {
        String res= comp.compileFile("src/test/resources/Conditionals.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Disabled
    @Test
    @DisplayName("Compile File LocalVariables")
    public void CompileLocalVariables()  {
        String res= comp.compileFile("src/test/resources/LocalVariables.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Disabled
    @Test
    @DisplayName("Compile File Loops")
    public void CompileLoops()  {
        String res= comp.compileFile("src/test/resources/Loops.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Disabled
    @Test
    @DisplayName("Compile File OperationPrevalence")
    public void CompileOperationPrevalence()  {
        String res= comp.compileFile("src/test/resources/OperationPrevalence.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File Simple")
    public void CompileSimple()  {
        String res= comp.compileFile("src/test/resources/Simple.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }
}
