package fr.ufrst.m1info.pvm.group5.ast;

import org.junit.jupiter.api.*;

import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.ast.Nodes.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class NodeCompileTest {
    @Test
    public void AddNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        AddNode tested = new AddNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "add"), tested.compile(1));
    }

    @Test
    public void BinMinusNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        BinMinusNode tested = new BinMinusNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "sub"), tested.compile(1));
    }

    @Test
    public void AndNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        AndNode tested = new AndNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "and"), tested.compile(1));
    }

    @Test
    public void DivNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        DivNode tested = new DivNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "div"), tested.compile(1));
    }

    @Test
    public void EqualNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        EqualNode tested = new EqualNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "cmp"), tested.compile(1));
    }

    @Test
    public void MulNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        MulNode tested = new MulNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "mul"), tested.compile(1));
    }

    @Test
    public void OrNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        OrNode tested = new OrNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "or"), tested.compile(1));
    }

    @Test
    public void SupNode_Operation(){
        NumberNode lop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        NumberNode rop = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        SupNode tested = new SupNode(lop,rop);
        assertEquals(List.of("push(5)","push(5)", "sup"), tested.compile(1));
    }

    @Test
    public void AffectationNode(){
        IdentNode ident = new IdentNode("x");
        NumberNode exp = ASTMocks.createNode(NumberNode.class, null,i -> List.of("push(5)"));
        AffectationNode tested = new AffectationNode(ident,exp);
        assertEquals(List.of("push(5)", "store(x)"), tested.compile(1));
    }

    @Test
    public void BooleanNodeTrue(){
        BooleanNode tested = new BooleanNode(true);
        assertEquals(List.of("push(jcvrai)"),tested.compile(1));
    }

    @Test
    public void BooleanNodeFalse(){
        BooleanNode tested = new BooleanNode(false);
        assertEquals(List.of("push(jcfaux)"),tested.compile(1));
    }

    @Test
    public void ClassNodeWithDecls(){
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
    public void ClassNodeWithoutDecls(){
        IdentNode ident = new IdentNode("x");
        DeclarationsNode decls = null;
        MainNode main = ASTMocks.createNode(MainNode.class, null, i -> List.of("MAINTEST"));;
        ClassNode tested = new ClassNode(ident,decls,main);
        assertEquals(List.of("init","MAINTEST","pop","jcstop"),tested.compile(1));
    }

    @Test
    public void DeclarationsNode(){
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
    public void DeclarationsNodeWithdraw(){
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
    public void FinalNodeInt(){
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
    public void FinalNodeBool(){
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
    public void FinalNodeWithoutExp(){
        IdentNode ident = new IdentNode("x");
        TypeNode type = new TypeNode(ValueType.INT);
        FinalNode tested = new FinalNode(type,ident,null);
        assertEquals(List.of("new(x,INT,cst,0)"),tested.compile(1));
    }

    @Test
    public void FinalNodeWithdraw(){
        IdentNode ident = new IdentNode("x");
        TypeNode type = new TypeNode(ValueType.INT);
        FinalNode tested = new FinalNode(type,ident,null);
        assertEquals(List.of("swap","pop"),tested.withdrawCompile(1));
    }

    @Test
    public void IdentNode(){
        IdentNode tested = new IdentNode("x");
        assertEquals(List.of("load(x)"),tested.compile(1));
    }


    @Test
    public void IfNodeWithoutElse(){
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
    public void IfNodeWithElse(){
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
    public void IfNodeWithoutElseAndElse(){
        AddNode condition = ASTMocks.createNode(
                AddNode.class,
                null,
                i -> List.of("ADD")
        );

        IfNode tested = new IfNode(condition,null,null);
        assertEquals(List.of("ADD","if(4)","goto(4)"),tested.compile(1));
    }

    @Test
    public void IfNodeWithoutThen(){
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
    public void IncNode(){
        IdentNode ident = new IdentNode("x");
        IncNode tested = new IncNode(ident);
        assertEquals(List.of("push(1)","inc(x)"),tested.compile(1));
    }

    @Test
    public void InstructionsNode(){
        IncNode instr = ASTMocks.createNode(
                IncNode.class,
                null,
                i-> List.of("INC")
        );
        InstructionsNode tested = new InstructionsNode(instr,null);
        assertEquals(List.of("INC"),tested.compile(1));
    }

    @Test
    public void InstructionsNodeManyInstrs(){
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
    public void MainNodeWithVarsAndInstrs(){
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
    public void MainNodeWithoutVars(){
        InstructionsNode instrs = ASTMocks.createNode(
                InstructionsNode.class,
                null,
                i-> List.of("INSTRS")
        );
        MainNode tested = new MainNode(null,instrs);
        assertEquals(List.of("INSTRS","push(0)"),tested.compile(1));
    }

    @Test
    public void MainNodeWithoutInstrs(){
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
    public void MainNodeWithoutVarsAndInstrs(){
        MainNode tested = new MainNode(null,null);
        assertEquals(List.of("push(0)"),tested.compile(1));
    }

    @Test
    public void NotNode(){
        IdentNode ident = new IdentNode("x");
        NotNode tested = new NotNode(ident);
        assertEquals(List.of("load(x)","not"),tested.compile(1));
    }

    @Test
    public void NumberNode(){
        NumberNode tested = new NumberNode(5);
        assertEquals(List.of("push(5)"),tested.compile(1));
    }

    @Test
    public void ReturnNode(){
        IdentNode ident = new IdentNode("x");
        ReturnNode tested = new ReturnNode(ident);
        assertEquals(List.of("load(x)"),tested.compile(1));
    }

    @Test
    public void UnMinusNode(){
        IdentNode ident = new IdentNode("x");
        UnMinusNode tested = new UnMinusNode(ident);
        assertEquals(List.of("load(x)","neg"),tested.compile(1));
    }

    @Test
    public void VariableNodeInt(){
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
    public void VariableNodeBool(){
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
    public void VariableNodeWithoutExp(){
        IdentNode ident = new IdentNode("x");
        TypeNode type = new TypeNode(ValueType.INT);
        VariableNode tested = new VariableNode(type,ident,null);
        assertEquals(List.of("new(x,INT,var,0)"),tested.compile(1));
    }

    @Test
    public void VariableNodeWithdraw(){
        IdentNode ident = new IdentNode("x");
        TypeNode type = new TypeNode(ValueType.INT);
        VariableNode tested = new VariableNode(type,ident,null);
        assertEquals(List.of("swap","pop"),tested.withdrawCompile(1));
    }

    @Test
    public void VariablesNode(){
        VariableNode var = ASTMocks.createNode(
                VariableNode.class,
                null,
                i-> List.of("VAR")
        );
        VariablesNode tested = new VariablesNode(var,null);
        assertEquals(List.of("VAR"),tested.compile(1));
    }

    @Test
    public void VariablesNodeManyVariables(){
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
    public void VariablesNodeWithdraw(){
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
    public void WhileNode(){
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
    public void WhileNodeWithoutInstrs(){
        EqualNode condition = ASTMocks.createNode(
                EqualNode.class,
                null,
                i-> List.of("CMP")
        );
        WhileNode tested = new WhileNode(condition,null);
        assertEquals(List.of("CMP","not","if(5)","goto(1)"),tested.compile(1));
    }

}
