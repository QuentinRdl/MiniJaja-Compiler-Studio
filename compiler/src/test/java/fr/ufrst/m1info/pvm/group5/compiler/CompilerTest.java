package fr.ufrst.m1info.pvm.group5.compiler;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.*;

import java.nio.file.NoSuchFileException;

class CompilerTest {
    Compiler comp;

    @BeforeEach
    public void setup(){
        comp=new Compiler();
    }

    @Test
    @DisplayName("Compile Code Without Error")
    void CompileCodeNoError()  {
        String input = "class C {"
                + "  main{}"
                + "}";
        String res= comp.compileCode(input);
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File BasicOperations")
    void CompileBasicOperations()  {
        String res= comp.compileFile("src/test/resources/BasicOperations.mjj");
        Assertions.assertEquals("init\nnew(x,INT,var,0)\npush(3)\npush(4)\nadd\nstore(x)\npush(1)\ninc(x)\npush(0)\nswap\npop\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File Complex")
    void CompileComplex()  {
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

    @Test
    @DisplayName("Compile File Conditionals")
    void CompileConditionals()  {
        String res= comp.compileFile("src/test/resources/Conditionals.mjj");
        String expected = "init\n" +
                "push(5)\n" +
                "new(a,INT,var,0)\n" +
                "push(5)\n" +
                "new(b,INT,var,0)\n" +
                "push(5)\n" +
                "new(c,INT,var,0)\n" +
                "new(v,INT,var,0)\n" +
                "push(5)\n" +
                "load(a)\n" +
                "sup\n" +
                "if(14)\n" +
                "goto(16)\n" +
                "push(1)\n" +
                "inc(a)\n" +
                "load(b)\n" +
                "load(a)\n" +
                "sup\n" +
                "if(23)\n" +
                "push(1)\n" +
                "inc(b)\n" +
                "goto(25)\n" +
                "push(0)\n" +
                "store(b)\n" +
                "load(b)\n" +
                "push(5)\n" +
                "cmp\n" +
                "if(39)\n" +
                "load(a)\n" +
                "load(b)\n" +
                "sup\n" +
                "if(36)\n" +
                "load(b)\n" +
                "store(v)\n" +
                "goto(38)\n" +
                "load(a)\n" +
                "store(v)\n" +
                "goto(41)\n" +
                "load(c)\n" +
                "store(v)\n" +
                "push(0)\n" +
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


    @Test
    @DisplayName("Compile File LocalVariables")
    void CompileLocalVariables()  {
        String res= comp.compileFile("src/test/resources/LocalVariables.mjj");
        String expected = "init\n" +
                "push(4)\n" +
                "new(x,INT,var,0)\n" +
                "push(3)\n" +
                "new(y,INT,var,0)\n" +
                "push(6)\n" +
                "new(z,INT,var,0)\n" +
                "load(z)\n" +
                "load(y)\n" +
                "div\n" +
                "push(2)\n" +
                "add\n" +
                "inc(x)\n" +
                "push(1)\n" +
                "inc(x)\n" +
                "push(0)\n" +
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

    @Test
    @DisplayName("Compile File Loops")
    void CompileLoops()  {
        String res= comp.compileFile("src/test/resources/Loops.mjj");
        String expected = "init\n" +
                "push(0)\n" +
                "new(x,INT,var,0)\n" +
                "push(100)\n" +
                "load(x)\n" +
                "sup\n" +
                "not\n" +
                "if(12)\n" +
                "push(8)\n" +
                "inc(x)\n" +
                "goto(4)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        Assertions.assertEquals(expected,res);
    }

    @Test
    @DisplayName("Compile File OperationPrevalence")
    void CompileOperationPrevalence()  {
        String res= comp.compileFile("src/test/resources/OperationPrevalence.mjj");
        String expected = "init\n" +
                "new(x,INT,var,0)\n" +
                "new(y,INT,var,0)\n" +
                "new(z,INT,var,0)\n" +
                "new(w,BOOL,var,0)\n" +
                "new(v,BOOL,var,0)\n" +
                "push(3)\n" +
                "push(4)\n" +
                "mul\n" +
                "push(5)\n" +
                "add\n" +
                "store(x)\n" +
                "push(5)\n" +
                "push(3)\n" +
                "push(4)\n" +
                "mul\n" +
                "add\n" +
                "store(y)\n" +
                "push(3)\n" +
                "neg\n" +
                "push(2)\n" +
                "add\n" +
                "store(z)\n" +
                "push(jcfaux)\n" +
                "not\n" +
                "push(jcvrai)\n" +
                "and\n" +
                "store(w)\n" +
                "push(jcvrai)\n" +
                "push(jcfaux)\n" +
                "or\n" +
                "push(jcvrai)\n" +
                "and\n" +
                "store(v)\n" +
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

    @Test
    @DisplayName("Compile File Simple")
    void CompileSimple()  {
        String res= comp.compileFile("src/test/resources/Simple.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File Write")
    void CompileWrite()  {
        String res= comp.compileFile("src/test/resources/Write.mjj");
        String expected = "init\n" +
                "push(3)\n" +
                "new(x,INT,var,0)\n" +
                "push(jcfaux)\n" +
                "new(b,BOOL,var,0)\n" +
                "push(\"Hello \")\n" +
                "write\n" +
                "push(\"World\")\n" +
                "writeln\n" +
                "load(x)\n" +
                "write\n" +
                "load(b)\n" +
                "writeln\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        Assertions.assertEquals(expected,res);
    }

    @Test
    @DisplayName("Compile Number")
    void number() {
        String res=comp.compileCode("class C { main{10;}}");
        Assertions.assertNull(res);
    }

    @Test
    @DisplayName("Compile File That Doesn't Exist")
    public void InterpretNotExistingFile() {
        String res=comp.compileFile("src/test/resources/FileThatDoesntExist.mjj");
        Assertions.assertNull(res);
    }
}
