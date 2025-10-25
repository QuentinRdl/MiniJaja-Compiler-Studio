package fr.ufrst.m1info.pvm.group5;

import org.junit.jupiter.api.*;

import fr.ufrst.m1info.pvm.group5.Nodes.*;

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
        assertEquals(List.of("push(5)","push(5)", "minus"), tested.compile(1));
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


}
