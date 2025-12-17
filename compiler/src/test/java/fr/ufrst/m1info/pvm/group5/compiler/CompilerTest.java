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
    @DisplayName("Compile File One")
    void CompileFileOne()  {
        String res= comp.compileFile("src/test/resources/1.mjj");
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
                "push(14)\n" +
                "new(somme,INT,meth,0)\n" +
                "goto(34)\n" +
                "new(max,INT,var,1)\n" +
                "load(VAL)\n" +
                "new(t,INT,var,0)\n" +
                "load(max)\n" +
                "push(0)\n" +
                "sup\n" +
                "not\n" +
                "if(29)\n" +
                "load(max)\n" +
                "inc(t)\n" +
                "load(max)\n" +
                "push(1)\n" +
                "sub\n" +
                "store(max)\n" +
                "goto(17)\n" +
                "load(t)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "return\n" +
                "push(37)\n" +
                "new(test,BOOL,meth,0)\n" +
                "goto(44)\n" +
                "new(b,INT,var,1)\n" +
                "new(a,INT,var,2)\n" +
                "load(a)\n" +
                "load(b)\n" +
                "sup\n" +
                "swap\n" +
                "return\n" +
                "push(47)\n" +
                "new(f,VOID,meth,0)\n" +
                "goto(61)\n" +
                "new(z,INT,var,1)\n" +
                "load(y)\n" +
                "load(z)\n" +
                "mul\n" +
                "store(x)\n" +
                "load(x)\n" +
                "load(z)\n" +
                "div\n" +
                "store(x)\n" +
                "push(1)\n" +
                "inc(x)\n" +
                "push(0)\n" +
                "swap\n" +
                "return\n" +
                "load(y)\n" +
                "push(9)\n" +
                "invoke(test)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "load(b1)\n" +
                "load(b2)\n" +
                "not\n" +
                "or\n" +
                "and\n" +
                "if(77)\n" +
                "push(1)\n" +
                "inc(x)\n" +
                "goto(86)\n" +
                "push(2)\n" +
                "invoke(somme)\n" +
                "swap\n" +
                "pop\n" +
                "store(x)\n" +
                "push(\"x : \")\n" +
                "write\n" +
                "load(x)\n" +
                "writeln\n" +
                "push(10)\n" +
                "invoke(f)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "push(\"x : \")\n" +
                "write\n" +
                "load(x)\n" +
                "writeln\n" +
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
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String[] listE=expected.split("\n");
        String[] listR=res.split("\n");
        StringBuilder differences = new StringBuilder();
        for (int i = 0; i < listE.length; i++) {
            if (!listE[i].equals(listR[i])) {
                differences.append("Line ").append(i + 1).append(" : Expected : ").append(listE[i]).append(" Res : ").append(listR[i]).append("\n");
            }
        }
        Assertions.assertEquals(expected, res, differences.toString());
    }

    @Test
    @DisplayName("Compile File Ainc")
    void CompileAinc()  {
        String res= comp.compileFile("src/test/resources/Ainc.mjj");
        String expected = "init\n" +
                "push(1)\n" +
                "newarray(t,INT)\n" +
                "push(0)\n" +
                "push(80)\n" +
                "astore(t)\n" +
                "push(0)\n" +
                "aload(t)\n" +
                "writeln\n" +
                "push(0)\n" +
                "push(1)\n" +
                "ainc(t)\n" +
                "push(0)\n" +
                "aload(t)\n" +
                "writeln\n" +
                "push(0)\n" +
                "push(8)\n" +
                "ainc(t)\n" +
                "push(0)\n" +
                "aload(t)\n" +
                "writeln\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        Assertions.assertEquals(expected,res);
    }

    @Test
    @DisplayName("Compile File Array")
    void CompileArray()  {
        String res= comp.compileFile("src/test/resources/Array.mjj");
        String expected = "init\n" +
                "push(3)\n" +
                "newarray(arrI,INT)\n" +
                "push(4)\n" +
                "newarray(arrB,BOOL)\n" +
                "push(0)\n" +
                "new(x,INT,var,0)\n" +
                "push(0)\n" +
                "push(4)\n" +
                "astore(arrI)\n" +
                "push(1)\n" +
                "push(33)\n" +
                "astore(arrI)\n" +
                "push(2)\n" +
                "push(81)\n" +
                "astore(arrI)\n" +
                "push(0)\n" +
                "push(jcfaux)\n" +
                "astore(arrB)\n" +
                "push(1)\n" +
                "push(jcvrai)\n" +
                "astore(arrB)\n" +
                "push(2)\n" +
                "push(jcfaux)\n" +
                "astore(arrB)\n" +
                "push(3)\n" +
                "push(jcfaux)\n" +
                "astore(arrB)\n" +
                "push(3)\n" +
                "load(x)\n" +
                "sup\n" +
                "not\n" +
                "if(40)\n" +
                "load(x)\n" +
                "aload(arrI)\n" +
                "writeln\n" +
                "push(1)\n" +
                "inc(x)\n" +
                "goto(29)\n" +
                "push(0)\n" +
                "store(x)\n" +
                "push(4)\n" +
                "load(x)\n" +
                "sup\n" +
                "not\n" +
                "if(53)\n" +
                "load(x)\n" +
                "aload(arrB)\n" +
                "writeln\n" +
                "push(1)\n" +
                "inc(x)\n" +
                "goto(42)\n" +
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
    @DisplayName("Compile File Constant")
    void CompileConstant()  {
        String res= comp.compileFile("src/test/resources/Constant.mjj");
        String expected = "init\n" +
                "push(5)\n" +
                "new(VAL,INT,cst,0)\n" +
                "new(X,INT,cst,0)\n" +
                "new(X2,INT,cst,0)\n" +
                "push(jcfaux)\n" +
                "new(B,BOOL,cst,0)\n" +
                "new(T,BOOL,cst,0)\n" +
                "new(T2,BOOL,cst,0)\n" +
                "load(VAL)\n" +
                "writeln\n" +
                "load(B)\n" +
                "writeln\n" +
                "push(8)\n" +
                "load(VAL)\n" +
                "mul\n" +
                "store(X)\n" +
                "load(B)\n" +
                "not\n" +
                "store(T)\n" +
                "load(X)\n" +
                "writeln\n" +
                "load(T)\n" +
                "writeln\n" +
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
    @DisplayName("Compile File Method")
    void CompileMethod()  {
        String res= comp.compileFile("src/test/resources/Method.mjj");
        String expected = "init\n" +
                "push(1)\n" +
                "new(x,INT,var,0)\n" +
                "push(100)\n" +
                "new(y,INT,var,0)\n" +
                "push(9)\n" +
                "new(addition,INT,meth,0)\n" +
                "goto(16)\n" +
                "new(op2,INT,var,1)\n" +
                "new(op1,INT,var,2)\n" +
                "load(op1)\n" +
                "load(op2)\n" +
                "add\n" +
                "swap\n" +
                "return\n" +
                "load(x)\n" +
                "load(y)\n" +
                "invoke(addition)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "store(x)\n" +
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
    @DisplayName("Compile File MethodComplex")
    void CompileMethodComplex()  {
        String res= comp.compileFile("src/test/resources/MethodComplex.mjj");
        String expected = "init\n" +
                "push(1)\n" +
                "new(x,INT,var,0)\n" +
                "push(100)\n" +
                "new(y,INT,var,0)\n" +
                "new(resInt,INT,var,0)\n" +
                "new(resBool,BOOL,var,0)\n" +
                "push(11)\n" +
                "new(addition,INT,meth,0)\n" +
                "goto(18)\n" +
                "new(op2,INT,var,1)\n" +
                "new(op1,INT,var,2)\n" +
                "load(op1)\n" +
                "load(op2)\n" +
                "add\n" +
                "swap\n" +
                "return\n" +
                "push(21)\n" +
                "new(substraction,INT,meth,0)\n" +
                "goto(28)\n" +
                "new(op2,INT,var,1)\n" +
                "new(op1,INT,var,2)\n" +
                "load(op1)\n" +
                "load(op2)\n" +
                "sub\n" +
                "swap\n" +
                "return\n" +
                "push(31)\n" +
                "new(inferior,BOOL,meth,0)\n" +
                "goto(38)\n" +
                "new(op2,INT,var,1)\n" +
                "new(op1,INT,var,2)\n" +
                "load(op2)\n" +
                "load(op1)\n" +
                "sup\n" +
                "swap\n" +
                "return\n" +
                "push(41)\n" +
                "new(helloWorld,VOID,meth,0)\n" +
                "goto(46)\n" +
                "push(\"Hello World\")\n" +
                "writeln\n" +
                "push(0)\n" +
                "swap\n" +
                "return\n" +
                "push(\"addition : \")\n" +
                "write\n" +
                "load(x)\n" +
                "load(y)\n" +
                "invoke(addition)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "store(resInt)\n" +
                "load(resInt)\n" +
                "writeln\n" +
                "push(\"substraction : \")\n" +
                "write\n" +
                "load(x)\n" +
                "load(y)\n" +
                "invoke(substraction)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "store(resInt)\n" +
                "load(resInt)\n" +
                "writeln\n" +
                "push(\"inferior : \")\n" +
                "write\n" +
                "load(x)\n" +
                "load(y)\n" +
                "invoke(inferior)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "store(resBool)\n" +
                "load(resBool)\n" +
                "writeln\n" +
                "invoke(helloWorld)\n" +
                "pop\n" +
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
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String[] listE=expected.split("\n");
        String[] listR=res.split("\n");
        StringBuilder differences = new StringBuilder();
        for (int i = 0; i < listE.length; i++) {
            if (!listE[i].equals(listR[i])) {
                differences.append("Line ").append(i + 1).append(" : Expected : ").append(listE[i]).append(" Res : ").append(listR[i]).append("\n");
            }
        }
        Assertions.assertEquals(expected, res, differences.toString());
    }

    @Test
    @DisplayName("Compile File MethodRecursive")
    void CompileMethodRecursive()  {
        String res= comp.compileFile("src/test/resources/MethodRecursive.mjj");
        String expected = "init\n" +
                "push(5)\n" +
                "new(x,INT,var,0)\n" +
                "push(7)\n" +
                "new(countdown,VOID,meth,0)\n" +
                "goto(26)\n" +
                "new(n,INT,var,1)\n" +
                "load(n)\n" +
                "push(1)\n" +
                "neg\n" +
                "sup\n" +
                "if(14)\n" +
                "goto(23)\n" +
                "load(n)\n" +
                "writeln\n" +
                "load(n)\n" +
                "push(1)\n" +
                "sub\n" +
                "invoke(countdown)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "push(0)\n" +
                "swap\n" +
                "return\n" +
                "push(29)\n" +
                "new(countup,VOID,meth,0)\n" +
                "goto(48)\n" +
                "new(n,INT,var,1)\n" +
                "load(n)\n" +
                "push(1)\n" +
                "neg\n" +
                "sup\n" +
                "if(36)\n" +
                "goto(45)\n" +
                "load(n)\n" +
                "push(1)\n" +
                "sub\n" +
                "invoke(countup)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "load(n)\n" +
                "writeln\n" +
                "push(0)\n" +
                "swap\n" +
                "return\n" +
                "load(x)\n" +
                "invoke(countdown)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "load(x)\n" +
                "invoke(countup)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String[] listE=expected.split("\n");
        String[] listR=res.split("\n");
        StringBuilder differences = new StringBuilder();
        for (int i = 0; i < listE.length; i++) {
            if (!listE[i].equals(listR[i])) {
                differences.append("Line ").append(i + 1).append(" : Expected : ").append(listE[i]).append(" Res : ").append(listR[i]).append("\n");
            }
        }
        Assertions.assertEquals(expected, res, differences.toString());
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
    @DisplayName("Compile File QuickSort")
    void CompileQuickSort()  {
        String res= comp.compileFile("src/test/resources/Quick_sort.mjj");
        String expected = "init\n" +
                "push(20)\n" +
                "new(longueur,INT,cst,0)\n" +
                "load(longueur)\n" +
                "newarray(tableau,INT)\n" +
                "push(9)\n" +
                "new(afficher,VOID,meth,0)\n" +
                "goto(41)\n" +
                "length(tableau)\n" +
                "new(taille,INT,var,0)\n" +
                "push(0)\n" +
                "new(i,INT,var,0)\n" +
                "push(0)\n" +
                "new(a,INT,var,0)\n" +
                "load(taille)\n" +
                "load(i)\n" +
                "sup\n" +
                "not\n" +
                "if(30)\n" +
                "load(i)\n" +
                "aload(tableau)\n" +
                "store(a)\n" +
                "load(a)\n" +
                "write\n" +
                "push(\",\")\n" +
                "write\n" +
                "push(1)\n" +
                "inc(i)\n" +
                "goto(15)\n" +
                "push(\" \")\n" +
                "writeln\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "return\n" +
                "push(44)\n" +
                "new(partition,INT,meth,0)\n" +
                "goto(107)\n" +
                "new(fin,INT,var,1)\n" +
                "new(deb,INT,var,2)\n" +
                "load(deb)\n" +
                "new(compt,INT,var,0)\n" +
                "load(deb)\n" +
                "aload(tableau)\n" +
                "new(pivot,INT,var,0)\n" +
                "load(deb)\n" +
                "push(1)\n" +
                "add\n" +
                "new(i,INT,var,0)\n" +
                "new(temp,INT,var,0)\n" +
                "load(fin)\n" +
                "load(i)\n" +
                "sup\n" +
                "load(i)\n" +
                "load(fin)\n" +
                "cmp\n" +
                "or\n" +
                "not\n" +
                "if(86)\n" +
                "load(pivot)\n" +
                "load(i)\n" +
                "aload(tableau)\n" +
                "sup\n" +
                "if(71)\n" +
                "goto(83)\n" +
                "push(1)\n" +
                "inc(compt)\n" +
                "load(compt)\n" +
                "aload(tableau)\n" +
                "store(temp)\n" +
                "load(compt)\n" +
                "load(i)\n" +
                "aload(tableau)\n" +
                "astore(tableau)\n" +
                "load(i)\n" +
                "load(temp)\n" +
                "astore(tableau)\n" +
                "push(1)\n" +
                "inc(i)\n" +
                "goto(56)\n" +
                "load(compt)\n" +
                "aload(tableau)\n" +
                "store(temp)\n" +
                "load(compt)\n" +
                "load(deb)\n" +
                "aload(tableau)\n" +
                "astore(tableau)\n" +
                "load(deb)\n" +
                "load(temp)\n" +
                "astore(tableau)\n" +
                "load(compt)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "return\n" +
                "push(110)\n" +
                "new(pause,VOID,meth,0)\n" +
                "goto(113)\n" +
                "push(0)\n" +
                "swap\n" +
                "return\n" +
                "push(116)\n" +
                "new(trirapide,VOID,meth,0)\n" +
                "goto(157)\n" +
                "new(fin,INT,var,1)\n" +
                "new(debut,INT,var,2)\n" +
                "new(pivot,INT,var,0)\n" +
                "load(fin)\n" +
                "load(debut)\n" +
                "sup\n" +
                "if(124)\n" +
                "goto(152)\n" +
                "load(debut)\n" +
                "load(fin)\n" +
                "invoke(partition)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "store(pivot)\n" +
                "load(debut)\n" +
                "load(pivot)\n" +
                "push(1)\n" +
                "sub\n" +
                "invoke(trirapide)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "load(pivot)\n" +
                "push(1)\n" +
                "add\n" +
                "load(fin)\n" +
                "invoke(trirapide)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "return\n" +
                "push(0)\n" +
                "push(4)\n" +
                "astore(tableau)\n" +
                "push(1)\n" +
                "push(81)\n" +
                "astore(tableau)\n" +
                "push(2)\n" +
                "push(63)\n" +
                "astore(tableau)\n" +
                "push(3)\n" +
                "push(12)\n" +
                "astore(tableau)\n" +
                "push(4)\n" +
                "push(33)\n" +
                "astore(tableau)\n" +
                "push(5)\n" +
                "push(22)\n" +
                "astore(tableau)\n" +
                "push(6)\n" +
                "push(16)\n" +
                "astore(tableau)\n" +
                "push(7)\n" +
                "push(44)\n" +
                "astore(tableau)\n" +
                "push(8)\n" +
                "push(55)\n" +
                "astore(tableau)\n" +
                "push(9)\n" +
                "push(23)\n" +
                "astore(tableau)\n" +
                "push(10)\n" +
                "push(27)\n" +
                "astore(tableau)\n" +
                "push(11)\n" +
                "push(5)\n" +
                "astore(tableau)\n" +
                "push(12)\n" +
                "push(14)\n" +
                "astore(tableau)\n" +
                "push(13)\n" +
                "push(18)\n" +
                "astore(tableau)\n" +
                "push(14)\n" +
                "push(6)\n" +
                "astore(tableau)\n" +
                "push(15)\n" +
                "push(30)\n" +
                "astore(tableau)\n" +
                "push(16)\n" +
                "push(87)\n" +
                "astore(tableau)\n" +
                "push(17)\n" +
                "push(31)\n" +
                "astore(tableau)\n" +
                "push(18)\n" +
                "push(10)\n" +
                "astore(tableau)\n" +
                "push(19)\n" +
                "push(43)\n" +
                "astore(tableau)\n" +
                "invoke(afficher)\n" +
                "pop\n" +
                "push(0)\n" +
                "load(longueur)\n" +
                "push(1)\n" +
                "sub\n" +
                "invoke(trirapide)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "invoke(afficher)\n" +
                "pop\n" +
                "invoke(pause)\n" +
                "pop\n" +
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
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String[] listE=expected.split("\n");
        String[] listR=res.split("\n");
        StringBuilder differences = new StringBuilder();
        for (int i = 0; i < listE.length; i++) {
            if (!listE[i].equals(listR[i])) {
                differences.append("Line ").append(i + 1).append(" : Expected : ").append(listE[i]).append(" Res : ").append(listR[i]).append("\n");
            }
        }
        Assertions.assertEquals(expected, res, differences.toString());
    }

    @Test
    @DisplayName("Compile File Simple")
    void CompileSimple()  {
        String res= comp.compileFile("src/test/resources/Simple.mjj");
        Assertions.assertEquals("init\npush(0)\npop\njcstop",res);
    }

    @Test
    @DisplayName("Compile File Synonymie")
    void CompileSynonymie()  {
        String res= comp.compileFile("src/test/resources/Synonymie.mjj");
        String expected = "init\n" +
                "push(4)\n" +
                "newarray(t,INT)\n" +
                "push(4)\n" +
                "new(taille,INT,var,0)\n" +
                "push(9)\n" +
                "new(afficher,VOID,meth,0)\n" +
                "goto(38)\n" +
                "new(taille,INT,var,1)\n" +
                "push(0)\n" +
                "new(i,INT,var,0)\n" +
                "push(0)\n" +
                "new(a,INT,var,0)\n" +
                "load(taille)\n" +
                "load(i)\n" +
                "sup\n" +
                "not\n" +
                "if(29)\n" +
                "load(i)\n" +
                "aload(t)\n" +
                "store(a)\n" +
                "load(a)\n" +
                "write\n" +
                "push(\",\")\n" +
                "write\n" +
                "push(1)\n" +
                "inc(i)\n" +
                "goto(14)\n" +
                "push(\" \")\n" +
                "writeln\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "return\n" +
                "push(41)\n" +
                "new(f,VOID,meth,0)\n" +
                "goto(69)\n" +
                "new(x,INT,var,1)\n" +
                "push(10)\n" +
                "newarray(t1,INT)\n" +
                "push(0)\n" +
                "new(i,INT,var,0)\n" +
                "push(10)\n" +
                "load(i)\n" +
                "sup\n" +
                "not\n" +
                "if(57)\n" +
                "load(i)\n" +
                "load(x)\n" +
                "astore(t1)\n" +
                "push(1)\n" +
                "inc(i)\n" +
                "goto(46)\n" +
                "load(t1)\n" +
                "store(t)\n" +
                "load(x)\n" +
                "load(taille)\n" +
                "astore(t)\n" +
                "push(0)\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "pop\n" +
                "swap\n" +
                "return\n" +
                "push(72)\n" +
                "new(pause,VOID,meth,0)\n" +
                "goto(75)\n" +
                "push(0)\n" +
                "swap\n" +
                "return\n" +
                "push(0)\n" +
                "new(i,INT,var,0)\n" +
                "push(4)\n" +
                "load(i)\n" +
                "sup\n" +
                "not\n" +
                "if(90)\n" +
                "load(i)\n" +
                "load(taille)\n" +
                "push(1)\n" +
                "sub\n" +
                "astore(t)\n" +
                "push(1)\n" +
                "inc(i)\n" +
                "goto(77)\n" +
                "push(3)\n" +
                "invoke(f)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "push(10)\n" +
                "invoke(afficher)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "invoke(pause)\n" +
                "pop\n" +
                "push(7)\n" +
                "invoke(f)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "push(10)\n" +
                "invoke(afficher)\n" +
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "invoke(pause)\n" +
                "pop\n" +
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
                "swap\n" +
                "pop\n" +
                "pop\n" +
                "jcstop";
        String[] listE=expected.split("\n");
        String[] listR=res.split("\n");
        StringBuilder differences = new StringBuilder();
        for (int i = 0; i < listE.length; i++) {
            if (!listE[i].equals(listR[i])) {
                differences.append("Line ").append(i + 1).append(" : Expected : ").append(listE[i]).append(" Res : ").append(listR[i]).append("\n");
            }
        }
        Assertions.assertEquals(expected, res, differences.toString());
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
