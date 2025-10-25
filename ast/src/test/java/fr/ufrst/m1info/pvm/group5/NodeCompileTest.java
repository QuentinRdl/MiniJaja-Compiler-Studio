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


}
