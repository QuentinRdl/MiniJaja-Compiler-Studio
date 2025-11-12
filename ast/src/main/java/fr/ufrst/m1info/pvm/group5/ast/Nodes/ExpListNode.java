package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.ArrayList;
import java.util.List;

public class ExpListNode extends ASTNode {
    private final ASTNode head;
    private final ASTNode tail;

    public ExpListNode(ASTNode head, ASTNode tail) {
        if (head == null) {
            throw new ASTBuildException("ExpListNode cannot have a null head expression");
        }
        this.head = head;
        this.tail = tail;
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        jajacodes.addAll(head.compile(address));
        if (tail != null) {
            jajacodes.addAll(tail.compile(address + jajacodes.size()));
        }

        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        if (head instanceof EvaluableNode evalNode) {
            evalNode.eval(m);
        } else {
            head.interpret(m);
        }

        if (tail != null) {
            tail.interpret(m);
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        head.checkType(m);
        if (tail != null) {
            tail.checkType(m);
        }
        return "void";
    }

    @Override
    protected List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(head);
        if (tail != null) {
            children.add(tail);
        }
        return children;
    }
}
