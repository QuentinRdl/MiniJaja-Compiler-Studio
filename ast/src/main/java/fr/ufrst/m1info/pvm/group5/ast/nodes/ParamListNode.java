package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.ArrayList;
import java.util.List;

public class ParamListNode extends ASTNode  implements WithdrawalNode {

    private final ParamNode param;
    private final ParamListNode next;

    public ParamListNode(ParamNode param, ParamListNode next) {
        if (param == null) {
            throw new ASTBuildException("ParamList", "param", "ParamList cannot have a null parameter");
        }
        this.param = param;
        this.next = next;
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        compileParam(jajacodes);
        return jajacodes;
    }

    /**
     * compile the param list with the use of a counter
     *
     * @param jajacodes the list of jajacodes instruction
     * @return the counter
     */
    private int compileParam(List<String> jajacodes) {
        int k;
        if (next != null){
            k=next.compileParam(jajacodes) + 1;
        }else{
            k=1;
        }
        jajacodes.add("new(" + param.ident.identifier + "," + param.type + ",var,"+k+")");
        return k;
    }

    @Override
    public void interpret(Memory m)
            throws ASTInvalidOperationException, ASTInvalidMemoryException {
        param.interpret(m);

        if (next != null) {
            next.interpret(m);
        }
    }

    @Override
    public String checkType(Memory m)
            throws InterpretationInvalidTypeException {
        param.checkType(m);
        if (next != null) {
            next.checkType(m);
        }
        return "void";
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(param);
        if (next != null)
            children.add(next);
        return children;
    }
    public List<ParamNode> toList() {
        List<ParamNode> list = new ArrayList<>();
        ParamListNode current = this;
        while (current != null) {
            list.add(current.param);
            current = current.next;
        }
        return list;
    }

    @Override
    public void withdrawInterpret(Memory m) {
        if (next instanceof WithdrawalNode wNext)
            wNext.withdrawInterpret(m);
        MemoryCallUtil.safeCall(() -> m.withdrawDecl(param.ident.identifier), this);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> code = new ArrayList<>();
        if (next instanceof WithdrawalNode wNext)
            code.addAll(wNext.withdrawCompile(address));
        code.add("swap");
        code.add("pop");
        return code;
    }
    public String toString(){return "parameters";}

}
