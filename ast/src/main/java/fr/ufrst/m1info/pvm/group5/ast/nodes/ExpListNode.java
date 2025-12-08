package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

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
    public void interpret(Memory m) throws ASTInvalidOperationException {
        throw new ASTInvalidOperationException(
                "ExpListNode cannot be interpreted directly — only used within AppelINode or similar."
        );
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
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(head);
        if (tail != null) {
            children.add(tail);
        }
        return children;
    }
    public List<Value> evalList(Memory m)
            throws ASTInvalidOperationException, ASTInvalidMemoryException, ASTInvalidDynamicTypeException {
        List<Value> values = new ArrayList<>();

        if (head instanceof EvaluableNode evaluableHead) {
            values.add(evaluableHead.eval(m));
        } else {
            throw new ASTInvalidOperationException("Head of ExpListNode is not evaluable.");
        }

        if (tail instanceof ExpListNode expListNode) {
            values.addAll(expListNode.evalList(m));
        } else if (tail instanceof EvaluableNode evaluableTail) {
            values.add(evaluableTail.eval(m));
        }

        return values;
    }

    /**
     * Compile the withdrawal of arguments when a method is invoked
     *
     * @param code the list of jajacodes instruction
     */
    public void compileWithdraw(List<String> code) {
        if (head instanceof ExpListNode ehead){
            ehead.compileWithdraw(code);
        }else{
            code.add("swap");
            code.add("pop");
        }
        if (tail != null){
            if (tail instanceof ExpListNode etail){
                etail.compileWithdraw(code);
            }else{
                code.add("swap");
                code.add("pop");
            }
        }
    }
}
