package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import org.junit.jupiter.api.*;

import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.ast.nodes.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class NodeCompileTest {
    @Test
    void AddNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        AddNode tested = new AddNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "add"), tested.compile(1));
    }

    @Test
    void BinMinusNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        BinMinusNode tested = new BinMinusNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "sub"), tested.compile(1));
    }

    @Test
    void AndNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        AndNode tested = new AndNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "and"), tested.compile(1));
    }

    @Test
    void DivNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        DivNode tested = new DivNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "div"), tested.compile(1));
    }

    @Test
    void EqualNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        EqualNode tested = new EqualNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "cmp"), tested.compile(1));
    }

    @Test
    void MulNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        MulNode tested = new MulNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "mul"), tested.compile(1));
    }

    @Test
    void OrNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        OrNode tested = new OrNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "or"), tested.compile(1));
    }

    @Test
    void SupNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        SupNode tested = new SupNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "sup"), tested.compile(1));
    }

    @Test
    void AffectationNode(){
        IdentNode ident = new IdentNode("x");
        NumberNode exp = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        AffectationNode tested = new AffectationNode(ident,exp);
        assertEquals(List.of("push(5)", "store(x)"), tested.compile(1));
    }
    @Test
    @DisplayName("AffectationNode - compile() with array access and constant index")
    void testAffectationNode_CompileArrayAccess_ConstantIndex() {
        IdentNode arrayIdent = new IdentNode("tab");
        ASTNode indexExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(2)"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        ASTNode valueExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(10)"));
        AffectationNode tested = new AffectationNode(tabNode, valueExpr);
        assertEquals(List.of("push(2)", "push(10)", "astore(tab)"), tested.compile(0));
    }

    @Test
    @DisplayName("AffectationNode - compile() with array access and variable index")
    void testAffectationNode_CompileArrayAccess_VariableIndex() {
        IdentNode arrayIdent = new IdentNode("data");
        ASTNode indexExpr = ASTMocks.createNode(IdentNode.class, null, i -> List.of("load(i)"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        ASTNode valueExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(42)"));
        AffectationNode tested = new AffectationNode(tabNode, valueExpr);
        assertEquals(List.of("load(i)", "push(42)", "astore(data)"), tested.compile(0));
    }

    @Test
    @DisplayName("AffectationNode - compile() with array access and different addresses")
    void testAffectationNode_CompileArrayAccess_DifferentAddresses() {
        IdentNode arrayIdent = new IdentNode("tab");
        ASTNode indexExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(0)"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        ASTNode valueExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(5)"));
        AffectationNode tested = new AffectationNode(tabNode, valueExpr);
        List<String> expected = List.of("push(0)", "push(5)", "astore(tab)");
        assertEquals(expected, tested.compile(0));
        assertEquals(expected, tested.compile(10));
        assertEquals(expected, tested.compile(100));
    }

    @Test
    @DisplayName("AffectationNode - compile() with array access and boolean value")
    void testAffectationNode_CompileArrayAccess_BooleanValue() {
        IdentNode arrayIdent = new IdentNode("boolArr");
        ASTNode indexExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(3)"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        ASTNode valueExpr = ASTMocks.createNode(BooleanNode.class, null, i -> List.of("push(jcvrai)"));
        AffectationNode tested = new AffectationNode(tabNode, valueExpr);
        assertEquals(List.of("push(3)", "push(jcvrai)", "astore(boolArr)"), tested.compile(0));
    }

    @Test
    @DisplayName("AffectationNode - compile() with array access and variable value")
    void testAffectationNode_CompileArrayAccess_VariableValue() {
        IdentNode arrayIdent = new IdentNode("arr");
        ASTNode indexExpr = ASTMocks.createNode(IdentNode.class, null, i -> List.of("load(j)"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        ASTNode valueExpr = ASTMocks.createNode(IdentNode.class, null, i -> List.of("load(x)"));
        AffectationNode tested = new AffectationNode(tabNode, valueExpr);
        assertEquals(List.of("load(j)", "load(x)", "astore(arr)"), tested.compile(0));
    }

    @Test
    @DisplayName("AffectationNode - compile() with nested array access index")
    void testAffectationNode_CompileArrayAccess_NestedIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        ASTNode indexExpr = ASTMocks.createNode(ASTNode.class, null, i -> List.of("push(0)", "aload(indices)"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        ASTNode valueExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(99)"));
        AffectationNode tested = new AffectationNode(tabNode, valueExpr);
        assertEquals(List.of("push(0)", "aload(indices)", "push(99)", "astore(arr)"), tested.compile(0));
    }

    @Test
    void BooleanNodeTrue(){
        BooleanNode tested = new BooleanNode(true);
        assertEquals(List.of("push(jcvrai)"),tested.compile(1));
    }

    @Test
    void BooleanNodeFalse(){
        BooleanNode tested = new BooleanNode(false);
        assertEquals(List.of("push(jcfaux)"),tested.compile(1));
    }

    @Test
    void ClassNodeWithDecls(){
        IdentNode ident = new IdentNode("x");
        DeclarationsNode decls = ASTMocks.createWithdrawNode(
                DeclarationsNode.class,
                null,
                i -> List.of("DECLSTEST"),
                null,
                i -> List.of("WITHDECLSTEST")
        );
        MainNode main = ASTMocks.createNode(MainNode.class, null, i -> List.of("MAINTEST"));;
        ClassNode tested = new ClassNode(ident,decls,main);
        assertEquals(List.of("init", "DECLSTEST","MAINTEST","WITHDECLSTEST","pop","jcstop"),tested.compile(1));
    }

    @Test
    void ClassNodeWithoutDecls(){
        IdentNode ident = new IdentNode("x");
        DeclarationsNode decls = null;
        MainNode main = ASTMocks.createNode(MainNode.class, null, i -> List.of("MAINTEST"));;
        ClassNode tested = new ClassNode(ident,decls,main);
        assertEquals(List.of("init","MAINTEST","pop","jcstop"),tested.compile(1));
    }

    @Test
    void DeclarationsNode(){
        VariableNode var1 = ASTMocks.createNode(VariableNode.class,
                null,
                i -> List.of("VARIABLETEST1"));
        VariableNode var2 = ASTMocks.createNode(VariableNode.class,
                null,
                i -> List.of("VARIABLETEST2"));
        DeclarationsNode decls = new DeclarationsNode(var2,null);
        DeclarationsNode tested = new DeclarationsNode(var1,decls);
        assertEquals(List.of("VARIABLETEST1","VARIABLETEST2"),tested.compile(1));
    }

    @Test
    void DeclarationsNodeWithdraw(){
        VariableNode var1 = ASTMocks.createWithdrawNode(
                VariableNode.class,
                null,
                null,
                null,
                i -> List.of("VARIABLETEST1")
        );
        VariableNode var2 = ASTMocks.createWithdrawNode(
                VariableNode.class,
                null,
                null,
                null,
                i -> List.of("VARIABLETEST2")
        );
        DeclarationsNode decls = new DeclarationsNode(var2,null);
        DeclarationsNode tested = new DeclarationsNode(var1,decls);
        assertEquals(List.of("VARIABLETEST2","VARIABLETEST1"),tested.withdrawCompile(1));
    }

    @Test
    void FinalNodeInt(){
        IdentNode ident = new IdentNode("x");
        AddNode exp = ASTMocks.createNode(
                AddNode.class,
                null,
                i -> List.of("ADD")
        );
        TypeNode type = new TypeNode(ValueType.INT);
        FinalNode tested = new FinalNode(type,ident,exp);
        assertEquals(List.of("ADD","new(x,INT,cst,0)"),tested.compile(1));
    }

    @Test
    void FinalNodeBool(){
        IdentNode ident = new IdentNode("x");
        AddNode exp = ASTMocks.createNode(
                AddNode.class,
                null,
                i -> List.of("ADD")
        );
        TypeNode type = new TypeNode(ValueType.BOOL);
        FinalNode tested = new FinalNode(type,ident,exp);
        assertEquals(List.of("ADD","new(x,BOOL,cst,0)"),tested.compile(1));
    }

    @Test
    void FinalNodeWithoutExp(){
        IdentNode ident = new IdentNode("x");
        TypeNode type = new TypeNode(ValueType.INT);
        FinalNode tested = new FinalNode(type,ident,null);
        assertEquals(List.of("new(x,INT,cst,0)"),tested.compile(1));
    }

    @Test
    void FinalNodeWithdraw(){
        IdentNode ident = new IdentNode("x");
        TypeNode type = new TypeNode(ValueType.INT);
        FinalNode tested = new FinalNode(type,ident,null);
        assertEquals(List.of("swap","pop"),tested.withdrawCompile(1));
    }

    @Test
    void IdentNode(){
        IdentNode tested = new IdentNode("x");
        assertEquals(List.of("load(x)"),tested.compile(1));
    }


    @Test
    void IfNodeWithoutElse(){
        AddNode condition = ASTMocks.createNode(
                AddNode.class,
                null,
                i -> List.of("ADD")
        );
        InstructionsNode instr_then = ASTMocks.createNode(
                InstructionsNode.class,
                null,
                i-> List.of("instr1")
        );

        IfNode tested = new IfNode(condition,instr_then,null);
        assertEquals(List.of("ADD","if(4)","goto(5)","instr1"),tested.compile(1));
    }

    @Test
    void IfNodeWithElse(){
        AddNode condition = ASTMocks.createNode(
                AddNode.class,
                null,
                i -> List.of("ADD")
        );
        InstructionsNode instr_then = ASTMocks.createNode(
                InstructionsNode.class,
                null,
                i-> List.of("instr1")
        );
        InstructionsNode instr_else = ASTMocks.createNode(
                InstructionsNode.class,
                null,
                i-> List.of("instr2")
        );

        IfNode tested = new IfNode(condition,instr_then,instr_else);
        assertEquals(List.of("ADD","if(5)","instr2" ,"goto(6)","instr1"),tested.compile(1));
    }

    @Test
    void IfNodeWithoutElseAndElse(){
        AddNode condition = ASTMocks.createNode(
                AddNode.class,
                null,
                i -> List.of("ADD")
        );

        IfNode tested = new IfNode(condition,null,null);
        assertEquals(List.of("ADD","if(4)","goto(4)"),tested.compile(1));
    }

    @Test
    void IfNodeWithoutThen(){
        AddNode condition = ASTMocks.createNode(
                AddNode.class,
                null,
                i -> List.of("ADD")
        );
        InstructionsNode instr_else = ASTMocks.createNode(
                InstructionsNode.class,
                null,
                i-> List.of("instr2")
        );

        IfNode tested = new IfNode(condition,null,instr_else);
        assertEquals(List.of("ADD","if(5)","instr2","goto(5)"),tested.compile(1));
    }

    @Test
    void IncNode(){
        IdentNode ident = new IdentNode("x");
        IncNode tested = new IncNode(ident);
        assertEquals(List.of("push(1)","inc(x)"),tested.compile(1));
    }
    @Test
    @DisplayName("IncNode - compile() with array access and constant index")
    void testIncNode_CompileArrayAccess_ConstantIndex() {
        IdentNode arrayIdent = new IdentNode("tab");
        ASTNode indexExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(2)"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        IncNode tested = new IncNode(tabNode);

        assertEquals(List.of("push(2)", "push(1)", "ainc(tab)"), tested.compile(0));
    }

    @Test
    @DisplayName("IncNode - compile() with array access and variable index")
    void testIncNode_CompileArrayAccess_VariableIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        ASTNode indexExpr = ASTMocks.createNode(IdentNode.class, null, i -> List.of("load(i)"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        IncNode tested = new IncNode(tabNode);

        assertEquals(List.of("load(i)", "push(1)", "ainc(arr)"), tested.compile(0));
    }

    @Test
    @DisplayName("IncNode - compile() with array access and complex index expression")
    void testIncNode_CompileArrayAccess_ComplexIndex() {
        IdentNode arrayIdent = new IdentNode("matrix");
        ASTNode indexExpr = ASTMocks.createNode(AddNode.class, null, i -> List.of("load(i)", "push(5)", "add"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        IncNode tested = new IncNode(tabNode);

        assertEquals(List.of("load(i)", "push(5)", "add", "push(1)", "ainc(matrix)"), tested.compile(0));
    }

    @Test
    @DisplayName("IncNode - compile() with nested array access")
    void testIncNode_CompileArrayAccess_NestedExpression() {
        IdentNode arrayIdent = new IdentNode("data");
        ASTNode indexExpr = ASTMocks.createNode(MulNode.class, null, i -> List.of("load(x)", "push(2)", "mul"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        IncNode tested = new IncNode(tabNode);

        assertEquals(List.of("load(x)", "push(2)", "mul", "push(1)", "ainc(data)"), tested.compile(0));
    }
    @Test
    @DisplayName("SumNode - compile() with simple variable")
    void testSumNode_SimpleVariable() {
        IdentNode ident = new IdentNode("x");
        ASTNode expr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(5)"));
        SumNode tested = new SumNode(ident, expr);

        assertEquals(List.of("push(5)", "inc(x)"), tested.compile(0));
    }

    @Test
    @DisplayName("SumNode - compile() with array access and constant index")
    void testSumNode_CompileArrayAccess_ConstantIndex() {
        IdentNode arrayIdent = new IdentNode("tab");
        ASTNode indexExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(2)"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        ASTNode valueExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(10)"));
        SumNode tested = new SumNode(tabNode, valueExpr);

        assertEquals(List.of("push(2)", "push(10)", "ainc(tab)"), tested.compile(0));
    }

    @Test
    @DisplayName("SumNode - compile() with array access and variable index")
    void testSumNode_CompileArrayAccess_VariableIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        ASTNode indexExpr = ASTMocks.createNode(IdentNode.class, null, i -> List.of("load(i)"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        ASTNode valueExpr = ASTMocks.createNode(IdentNode.class, null, i -> List.of("load(x)"));
        SumNode tested = new SumNode(tabNode, valueExpr);

        assertEquals(List.of("load(i)", "load(x)", "ainc(arr)"), tested.compile(0));
    }

    @Test
    @DisplayName("SumNode - compile() with array access and complex index expression")
    void testSumNode_CompileArrayAccess_ComplexIndex() {
        IdentNode arrayIdent = new IdentNode("matrix");
        ASTNode indexExpr = ASTMocks.createNode(AddNode.class, null, i -> List.of("load(i)", "push(5)", "add"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        ASTNode valueExpr = ASTMocks.createNode(MulNode.class, null, i -> List.of("load(a)", "push(2)", "mul"));
        SumNode tested = new SumNode(tabNode, valueExpr);

        assertEquals(List.of("load(i)", "push(5)", "add", "load(a)", "push(2)", "mul", "ainc(matrix)"), tested.compile(0));
    }

    @Test
    @DisplayName("SumNode - compile() with nested array access")
    void testSumNode_CompileArrayAccess_NestedExpression() {
        IdentNode arrayIdent = new IdentNode("data");
        ASTNode indexExpr = ASTMocks.createNode(MulNode.class, null, i -> List.of("load(x)", "push(2)", "mul"));
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);

        ASTNode valueExpr = ASTMocks.createNode(AddNode.class, null, i -> List.of("load(y)", "push(3)", "add"));
        SumNode tested = new SumNode(tabNode, valueExpr);

        assertEquals(List.of("load(x)", "push(2)", "mul", "load(y)", "push(3)", "add", "ainc(data)"), tested.compile(0));
    }

    @Test
    @DisplayName("SumNode - compile() with variable and complex expression")
    void testSumNode_ComplexExpression() {
        IdentNode ident = new IdentNode("count");
        ASTNode expr = ASTMocks.createNode(AddNode.class, null, i -> List.of("load(a)", "load(b)", "add"));
        SumNode tested = new SumNode(ident, expr);

        assertEquals(List.of("load(a)", "load(b)", "add", "inc(count)"), tested.compile(0));
    }

    @Test
    void InstructionsNode(){
        IncNode instr = ASTMocks.createNode(
                IncNode.class,
                null,
                i-> List.of("INC")
        );
        InstructionsNode tested = new InstructionsNode(instr,null);
        assertEquals(List.of("INC"),tested.compile(1));
    }

    @Test
    void InstructionsNodeManyInstrs(){
        IncNode instr1 = ASTMocks.createNode(
                IncNode.class,
                null,
                i-> List.of("INC1")
        );
        IncNode instr2 = ASTMocks.createNode(
                IncNode.class,
                null,
                i-> List.of("INC2")
        );
        IncNode instr3 = ASTMocks.createNode(
                IncNode.class,
                null,
                i-> List.of("INC3")
        );
        InstructionsNode instrs3 = new InstructionsNode(instr3,null);
        InstructionsNode instrs2 = new InstructionsNode(instr2,instrs3);
        InstructionsNode tested = new InstructionsNode(instr1,instrs2);
        assertEquals(List.of("INC1","INC2","INC3"),tested.compile(1));
    }

    @Test
    void MainNodeWithVarsAndInstrs(){
        VariablesNode vars = ASTMocks.createWithdrawNode(
                VariablesNode.class,
                null,
                i -> List.of("VARS"),
                null,
                i -> List.of("WITHDRAWVARS")
        );
        InstructionsNode instrs = ASTMocks.createNode(
                InstructionsNode.class,
                null,
                i-> List.of("INSTRS")
        );
        MainNode tested = new MainNode(vars,instrs);
        assertEquals(List.of("VARS","INSTRS","push(0)","WITHDRAWVARS"),tested.compile(1));
    }

    @Test
    void MainNodeWithoutVars(){
        InstructionsNode instrs = ASTMocks.createNode(
                InstructionsNode.class,
                null,
                i-> List.of("INSTRS")
        );
        MainNode tested = new MainNode(null,instrs);
        assertEquals(List.of("INSTRS","push(0)"),tested.compile(1));
    }

    @Test
    void MainNodeWithoutInstrs(){
        VariablesNode vars = ASTMocks.createWithdrawNode(
                VariablesNode.class,
                null,
                i -> List.of("VARS"),
                null,
                i -> List.of("WITHDRAWVARS")
        );
        MainNode tested = new MainNode(vars,null);
        assertEquals(List.of("VARS","push(0)","WITHDRAWVARS"),tested.compile(1));
    }

    @Test
    void MainNodeWithoutVarsAndInstrs(){
        MainNode tested = new MainNode(null,null);
        assertEquals(List.of("push(0)"),tested.compile(1));
    }

    @Test
    void NotNode(){
        IdentNode ident = new IdentNode("x");
        NotNode tested = new NotNode(ident);
        assertEquals(List.of("load(x)","not"),tested.compile(1));
    }

    @Test
    void NumberNode(){
        NumberNode tested = new NumberNode(5);
        assertEquals(List.of("push(5)"),tested.compile(1));
    }

    @Test
    void ReturnNode(){
        IdentNode ident = new IdentNode("x");
        ReturnNode tested = new ReturnNode(ident);
        assertEquals(List.of("load(x)"),tested.compile(1));
    }

    @Test
    void UnMinusNode(){
        IdentNode ident = new IdentNode("x");
        UnMinusNode tested = new UnMinusNode(ident);
        assertEquals(List.of("load(x)","neg"),tested.compile(1));
    }

    @Test
    void VariableNodeInt(){
        IdentNode ident = new IdentNode("x");
        TypeNode type = new TypeNode(ValueType.INT);
        NumberNode exp = ASTMocks.createNode(
                NumberNode.class,
                null,
                i-> List.of("5")
        );
        VariableNode tested = new VariableNode(type,ident,exp);
        assertEquals(List.of("5","new(x,INT,var,0)"),tested.compile(1));
    }

    @Test
    void VariableNodeBool(){
        IdentNode ident = new IdentNode("x");
        TypeNode type = new TypeNode(ValueType.BOOL);
        NumberNode exp = ASTMocks.createNode(
                NumberNode.class,
                null,
                i-> List.of("5")
        );
        VariableNode tested = new VariableNode(type,ident,exp);
        assertEquals(List.of("5","new(x,BOOL,var,0)"),tested.compile(1));
    }

    @Test
    void VariableNodeWithoutExp(){
        IdentNode ident = new IdentNode("x");
        TypeNode type = new TypeNode(ValueType.INT);
        VariableNode tested = new VariableNode(type,ident,null);
        assertEquals(List.of("new(x,INT,var,0)"),tested.compile(1));
    }

    @Test
    void VariableNodeWithdraw(){
        IdentNode ident = new IdentNode("x");
        TypeNode type = new TypeNode(ValueType.INT);
        VariableNode tested = new VariableNode(type,ident,null);
        assertEquals(List.of("swap","pop"),tested.withdrawCompile(1));
    }

    @Test
    void VariablesNode(){
        VariableNode var = ASTMocks.createNode(
                VariableNode.class,
                null,
                i-> List.of("VAR")
        );
        VariablesNode tested = new VariablesNode(var,null);
        assertEquals(List.of("VAR"),tested.compile(1));
    }

    @Test
    void VariablesNodeManyVariables(){
        VariableNode var1 = ASTMocks.createNode(
                VariableNode.class,
                null,
                i-> List.of("VAR1")
        );
        VariableNode var2 = ASTMocks.createNode(
                VariableNode.class,
                null,
                i-> List.of("VAR2")
        );
        VariableNode var3 = ASTMocks.createNode(
                VariableNode.class,
                null,
                i-> List.of("VAR3")
        );
        VariablesNode vars3 = new VariablesNode(var3,null);
        VariablesNode vars2 = new VariablesNode(var2,vars3);
        VariablesNode tested = new VariablesNode(var1,vars2);
        assertEquals(List.of("VAR1","VAR2","VAR3"),tested.compile(1));
    }

    @Test
    void VariablesNodeWithdraw(){
        VariableNode var1 = ASTMocks.createWithdrawNode(
                VariableNode.class,
                null,
                null,
                null,
                i -> List.of("VAR1")
        );
        VariableNode var2 = ASTMocks.createWithdrawNode(
                VariableNode.class,
                null,
                null,
                null,
                i -> List.of("VAR2")
        );
        VariablesNode vars2 = new VariablesNode(var2,null);
        VariablesNode tested = new VariablesNode(var1,vars2);
        assertEquals(List.of("VAR2","VAR1"),tested.withdrawCompile(1));
    }

    @Test
    void WhileNode(){
        EqualNode condition = ASTMocks.createNode(
                EqualNode.class,
                null,
                i-> List.of("CMP")
        );
        InstructionsNode iss = ASTMocks.createNode(
                InstructionsNode.class,
                null,
                i-> List.of("ISS")
        );
        WhileNode tested = new WhileNode(condition,iss);
        assertEquals(List.of("CMP","not","if(6)","ISS","goto(1)"),tested.compile(1));
    }

    @Test
    void WhileNodeWithoutInstrs(){
        EqualNode condition = ASTMocks.createNode(
                EqualNode.class,
                null,
                i-> List.of("CMP")
        );
        WhileNode tested = new WhileNode(condition,null);
        assertEquals(List.of("CMP","not","if(5)","goto(1)"),tested.compile(1));
    }
    @Test
    void testParamNode_Compile() {
        TypeNode type = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("x");
        ParamNode param = new ParamNode(type, ident);

        List<String> result = param.compile(0);
        assertEquals(List.of("new(x," + type + ",var,1)"), result);
    }
    @Test
    void testParamNode_WithdrawCompile() {
        TypeNode type = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("x");
        ParamNode param = new ParamNode(type, ident);

        assertEquals(List.of("swap", "pop"), param.withdrawCompile(0));
    }

    @Test
    @DisplayName("ParamListNode - withdrawCompile() generates correct pop order")
    void testParamListNodeWithdrawCompile() {
        ParamNode p1 = new ParamNode(new TypeNode(ValueType.INT), new IdentNode("x"));
        ParamNode p2 = new ParamNode(new TypeNode(ValueType.BOOL), new IdentNode("flag"));
        ParamListNode list = new ParamListNode(p1, new ParamListNode(p2, null));

        List<String> result = list.withdrawCompile(0);

        assertEquals(List.of("swap", "pop", "swap", "pop"), result);
    }
    @Test
    @DisplayName("ParamListNode - compile() with non-zero starting address")
    void testParamListNodeCompileWithOffset() {
        ParamNode p1 = new ParamNode(new TypeNode(ValueType.INT), new IdentNode("a"));
        ParamNode p2 = new ParamNode(new TypeNode(ValueType.BOOL), new IdentNode("b"));

        ParamListNode list = new ParamListNode(p1, new ParamListNode(p2, null));

        List<String> result = list.compile(10);

        assertEquals(
                List.of(
                        "new(b," + p2.type + ",var,1)",
                        "new(a," + p1.type + ",var,2)"
                ),
                result
        );
    }


    @Test
    void testExpListNodeCompile() {
        NumberNode n1 = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(5)"));
        NumberNode n2 = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(10)"));
        ExpListNode list = new ExpListNode(n1, new ExpListNode(n2, null));

        assertEquals(List.of("push(5)", "push(10)"), list.compile(1));
    }
    @Test
    void testAppelINodeCompile() {
        ASTNode arg = ASTMocks.createNode(ASTNode.class, null, i -> List.of("push(5)"));
        ExpListNode argsList = new ExpListNode(arg, null);

        IdentNode ident = new IdentNode("myMethod");
        AppelINode appel = new AppelINode(ident, argsList);

        List<String> expected = List.of("push(5)", "invoke(myMethod)", "swap", "pop", "pop");
        assertEquals(expected, appel.compile(0));
    }
    @Test
    void testMethodeNodeCompile() {
        TypeNode returnType = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("m");

        ASTNode params = ASTMocks.createNode(
                ASTNode.class,
                null,
                i -> List.of("P1", "P2")
        );

        ASTNode vars = ASTMocks.createNode(
                ASTNode.class,
                null,
                i -> List.of("V1")
        );

        ASTNode instrs = ASTMocks.createNode(
                ASTNode.class,
                null,
                i -> List.of("I1", "I2")
        );

        MethodeNode method = new MethodeNode(returnType, ident, params, vars, instrs);

        List<String> code = method.compile(1);
        assertEquals("push(4)", code.get(0));
        assertTrue(code.get(1).startsWith("new(m"));
        assertTrue(code.contains("P1"));
        assertTrue(code.contains("P2"));
        assertTrue(code.contains("V1"));
        assertTrue(code.contains("I1"));
        assertTrue(code.contains("I2"));
        assertEquals("swap", code.get(code.size() - 2));
        assertEquals("return", code.get(code.size() - 1));
    }
    @Test
    void testMethodeNodeCompile_ParamsOnly() {
        ASTNode params = ASTMocks.createNode(
                ASTNode.class,
                null,
                i -> List.of("P1", "P2")
        );

        MethodeNode m = new MethodeNode(
                new TypeNode(ValueType.INT),
                new IdentNode("f"),
                params,
                null,
                null
        );

        List<String> code = m.compile(10);

        assertEquals(
                List.of(
                        "push(13)",
                        "new(f,INT,meth,0)",
                        "goto(17)",
                        "P1",
                        "P2",
                        "swap",
                        "return"
                ),
                code
        );
    }

    @Test
    void testMethodeNodeCompile_ParamsVars() {
        ASTNode params = ASTMocks.createNode(
                ASTNode.class,
                null,
                i -> List.of("P1")
        );

        ASTNode vars = ASTMocks.createNode(
                ASTNode.class,
                null,
                i -> List.of("V1", "V2")
        );

        MethodeNode m = new MethodeNode(
                new TypeNode(ValueType.BOOL),
                new IdentNode("f"),
                params,
                vars,
                null
        );

        List<String> code = m.compile(0);

        assertEquals(
                List.of(
                        "push(3)",
                        "new(f,BOOL,meth,0)",
                        "goto(8)",
                        "P1",
                        "V1",
                        "V2",
                        "swap",
                        "return"
                ),
                code
        );
    }

    @Test
    void testMethodeNodeCompile_WithInstrs() {
        ASTNode params = ASTMocks.createNode(
                ASTNode.class,
                null,
                i -> List.of("P1")
        );

        ASTNode vars = ASTMocks.createNode(
                ASTNode.class,
                null,
                i -> List.of("V1")
        );

        ASTNode instrs = ASTMocks.createNode(
                ASTNode.class,
                null,
                i -> List.of("I1", "I2")
        );

        MethodeNode m = new MethodeNode(
                new TypeNode(ValueType.VOID),
                new IdentNode("g"),
                params,
                vars,
                instrs
        );

        List<String> code = m.compile(0);

        assertEquals(
                List.of(
                        "push(3)",
                        "new(g,VOID,meth,0)",
                        "goto(9)",
                        "P1",
                        "V1",
                        "I1",
                        "I2",
                        "swap",
                        "return"
                ),
                code
        );
    }


    @Test
    @DisplayName("AppelENode.compile() - without arguments")
    void testAppelENodeCompileWithoutArgs() {
        IdentNode ident = new IdentNode("myFunc");
        AppelENode node = new AppelENode(ident, null);

        List<String> expected = List.of("invoke(myFunc)");
        List<String> actual = node.compile(0);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("AppelENode.compile() - minimal case")
    void testAppelENodeCompileMinimal() {
        IdentNode ident = new IdentNode("foo");
        AppelENode node = new AppelENode(ident, null);

        List<String> code = node.compile(0);
        assertEquals(List.of("invoke(foo)"), code);
    }

    @Test
    @DisplayName("ArrayNode - compile() generates correct instructions for int array")
    void testArrayNode_Compile_IntArray() {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("arr");
        NumberNode sizeExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(5)"));

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        List<String> code = node.compile(0);

        assertEquals(List.of("push(5)", "newarray(arr, int)"), code);
    }

    @Test
    @DisplayName("ArrayNode - compile() generates correct instructions for bool array")
    void testArrayNode_Compile_BoolArray() {
        TypeNode typeNode = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flags");
        NumberNode sizeExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(10)"));

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        List<String> code = node.compile(0);

        assertEquals(List.of("push(10)", "newarray(flags, bool)"), code);
    }

    @Test
    @DisplayName("ArrayNode - compile() with complex size expression")
    void testArrayNode_Compile_ComplexSizeExpression() {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("matrix");
        ASTNode sizeExpr = ASTMocks.createNode(ASTNode.class, null, i -> List.of("push(5)", "push(3)", "add"));

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        List<String> code = node.compile(0);

        assertEquals(List.of("push(5)", "push(3)", "add", "newarray(matrix, int)"), code);
    }

    @Test
    @DisplayName("ArrayNode - compile() with variable as size")
    void testArrayNode_Compile_VariableSize() {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("data");
        IdentNode sizeIdent = new IdentNode("n");
        ASTNode sizeExpr = ASTMocks.createNode(IdentNode.class, null, i -> List.of("load(n)"));

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        List<String> code = node.compile(0);

        assertEquals(List.of("load(n)", "newarray(data, int)"), code);
    }

    @Test
    @DisplayName("ArrayNode - withdrawCompile() generates swap and pop")
    void testArrayNode_WithdrawCompile() {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("arr");
        NumberNode sizeExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(5)"));

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        List<String> code = node.withdrawCompile(0);

        assertEquals(List.of("swap", "pop"), code);
    }

    @Test
    @DisplayName("ArrayNode - withdrawCompile() always returns same instructions regardless of address")
    void testArrayNode_WithdrawCompile_DifferentAddresses() {
        TypeNode typeNode = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flags");
        NumberNode sizeExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(10)"));

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);

        List<String> code1 = node.withdrawCompile(0);
        List<String> code2 = node.withdrawCompile(5);
        List<String> code3 = node.withdrawCompile(100);

        assertEquals(List.of("swap", "pop"), code1);
        assertEquals(List.of("swap", "pop"), code2);
        assertEquals(List.of("swap", "pop"), code3);
    }

    @Test
    @DisplayName("TabNode - compile() with constant index")
    void testTabNode_Compile_ConstantIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        NumberNode indexExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(3)"));

        TabNode node = new TabNode(arrayIdent, indexExpr);
        List<String> code = node.compile(0);

        assertEquals(List.of("push(3)", "aload(arr)"), code);
    }

    @Test
    @DisplayName("TabNode - compile() with variable index")
    void testTabNode_Compile_VariableIndex() {
        IdentNode arrayIdent = new IdentNode("data");
        IdentNode indexIdent = new IdentNode("i");
        ASTNode indexExpr = ASTMocks.createNode(IdentNode.class, null, addr -> List.of("load(i)"));

        TabNode node = new TabNode(arrayIdent, indexExpr);
        List<String> code = node.compile(0);

        assertEquals(List.of("load(i)", "aload(data)"), code);
    }

    @Test
    @DisplayName("TabNode - compile() with complex index expression")
    void testTabNode_Compile_ComplexIndexExpression() {
        IdentNode arrayIdent = new IdentNode("matrix");
        ASTNode indexExpr = ASTMocks.createNode(ASTNode.class, null, i -> List.of("push(2)", "push(3)", "add"));

        TabNode node = new TabNode(arrayIdent, indexExpr);
        List<String> code = node.compile(0);

        assertEquals(List.of("push(2)", "push(3)", "add", "aload(matrix)"), code);
    }

    @Test
    @DisplayName("TabNode - compile() with different starting addresses")
    void testTabNode_Compile_DifferentAddresses() {
        IdentNode arrayIdent = new IdentNode("arr");
        NumberNode indexExpr = ASTMocks.createNode(NumberNode.class, null, i -> List.of("push(0)"));

        TabNode node = new TabNode(arrayIdent, indexExpr);

        List<String> code1 = node.compile(0);
        List<String> code2 = node.compile(10);
        List<String> code3 = node.compile(100);
        assertEquals(List.of("push(0)", "aload(arr)"), code1);
        assertEquals(List.of("push(0)", "aload(arr)"), code2);
        assertEquals(List.of("push(0)", "aload(arr)"), code3);
    }

    @Test
    @DisplayName("TabNode - compile() with nested array access")
    void testTabNode_Compile_NestedArrayAccess() {
        IdentNode arrayIdent = new IdentNode("nestedArr");
        ASTNode indexExpr = ASTMocks.createNode(ASTNode.class, null, i -> List.of("load(i)", "push(1)", "add"));

        TabNode node = new TabNode(arrayIdent, indexExpr);
        List<String> code = node.compile(5);

        assertEquals(List.of("load(i)", "push(1)", "add", "aload(nestedArr)"), code);
    }




}
