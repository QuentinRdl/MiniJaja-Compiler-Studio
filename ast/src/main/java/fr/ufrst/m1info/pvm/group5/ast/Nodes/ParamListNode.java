package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.ArrayList;
import java.util.List;

public class ParamListNode extends ASTNode  implements WithdrawalNode {

    private final ParamNode param;
    private final ParamListNode next;

    public ParamListNode(ParamNode param, ParamListNode next) {
        if (param == null) {
            throw new ASTBuildException("ParamListNode cannot have a null parameter");
        }
        this.param = param;
        this.next = next;
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        jajacodes.addAll(param.compile(address));
        if (next != null) {
            jajacodes.addAll(next.compile(address + jajacodes.size()));
        }
        return jajacodes;
    }

    @Override
    public void interpret(Memory m)
            throws ASTInvalidOperationException, ASTInvalidMemoryException {
        ValueType vt = param.type.valueType;

        Value val;
        switch (vt) {
            case INT -> val = new Value(0);
            case BOOL -> val = new Value(false);
            default -> val = new Value();
        }

        m.declVar(param.ident.identifier, val, ValueType.toDataType(vt));

        if (next != null) {
            next.interpret(m);
        }
    }

    @Override
    public String checkType(Memory m)
            throws ASTInvalidDynamicTypeException {
        if (param.type.valueType == ValueType.VOID) {
            throw new ASTInvalidDynamicTypeException(
                    "Parameter " + param.ident.identifier + " cannot have type void"
            );
        }
        if (next != null) {
            next.checkType(m);
        }
        return "void";
    }

    @Override
    protected List<ASTNode> getChildren() {
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
        m.withdrawDecl(param.ident.identifier);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> code = new ArrayList<>();
        if (next instanceof WithdrawalNode wNext)
            code.addAll(wNext.withdrawCompile(address));
        code.add("pop(" + param.ident.identifier + ")");
        return code;
    }
}
