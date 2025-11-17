package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.SymbolTableEntry;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;

import java.util.ArrayList;
import java.util.List;

public class AppelINode extends ASTNode {
    public final IdentNode ident;
    public final ASTNode args;

    public AppelINode(IdentNode ident, ASTNode args) {
        if (ident == null) {
            throw new ASTBuildException("AppelINode cannot have null ident");
        }
        this.ident = ident;
        this.args = args;
    }

    @Override
    protected List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(ident);
        if (args != null) children.add(args);
        return children;
    }

    @Override
    public List<String> compile(int address) {
        List<String> code = new ArrayList<>();
        if (args != null) code.addAll(args.compile(address));
        code.add("invoke(" + ident.identifier + ")");
        return code;
    }

    @Override
    public void interpret(Memory m)
            throws ASTInvalidMemoryException, ASTInvalidOperationException, ASTInvalidDynamicTypeException {
        SymbolTableEntry methodEntry = m.getMethod(ident.identifier);
        if (methodEntry == null) {
            throw new ASTInvalidMemoryException("Method " + ident.identifier + " not found.");
        }
        if (methodEntry.getKind() != EntryKind.METHOD) {
            throw new ASTInvalidOperationException(ident.identifier + " is not a method!");
        }
        List<Value> evaluatedArgs = new ArrayList<>();
        if (args != null) {
            if (args instanceof ExpListNode) {
                evaluatedArgs.addAll(((ExpListNode) args).evalList(m));
            } else if (args instanceof EvaluableNode) {
                evaluatedArgs.add(((EvaluableNode) args).eval(m));
            } else {
                throw new ASTInvalidOperationException("Arguments node is not an ExpListNode/evaluable.");
            }
        }
        Object ref = methodEntry.getReference();
        if (!(ref instanceof MethodeNode)) {
            throw new ASTInvalidOperationException("Method reference for " + ident.identifier + " is not a MethodeNode");
        }
        MethodeNode methodNode = (MethodeNode) ref;
        m.pushScope();

        if (methodNode.params != null) {
            if (!(methodNode.params instanceof ParamListNode)) {
                throw new ASTInvalidOperationException("Method parameters are not a ParamListNode");
            }
            ParamListNode paramList = (ParamListNode) methodNode.params;
            List<ParamNode> formals = paramList.toList();
            if (evaluatedArgs.size() != formals.size()) {
                throw new ASTInvalidOperationException(
                        "Arity mismatch when calling " + ident.identifier + ": expected " + formals.size() + " got " + evaluatedArgs.size()
                );
            }
            for (int i = 0; i < formals.size(); i++) {
                ParamNode p = formals.get(i);
                Value argVal = evaluatedArgs.get(i);
                m.declVar(p.ident.identifier, argVal, ValueType.toDataType(p.type.valueType));
            }
        }
        if (methodNode.instrs != null) {
            methodNode.instrs.interpret(m);
        }

        if (methodNode.params != null) {
            if (methodNode.params instanceof WithdrawalNode) {
                ((WithdrawalNode) methodNode.params).withdrawInterpret(m);
            } else if (methodNode.params instanceof ParamListNode) {
                List<ParamNode> formals = ((ParamListNode) methodNode.params).toList();
                for (ParamNode p : formals) {
                    m.withdrawDecl(p.ident.identifier);
                }
            }
        }
        m.popScope();
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        SymbolTableEntry methodEntry = m.getMethod(ident.identifier);
        if (methodEntry == null) {
            throw new ASTInvalidDynamicTypeException("Unknown method " + ident.identifier);
        }
        if (methodEntry.getKind() != EntryKind.METHOD) {
            throw new ASTInvalidDynamicTypeException(ident.identifier + " is not a method");
        }

        if (args != null) {
            args.checkType(m);
        }
        DataType dt = methodEntry.getDataType();
        switch (dt) {
            case INT:
                return "int";
            case BOOL:
                return "bool";
            case VOID:
                return "void";
            case UNKNOWN:
            default:
                throw new ASTInvalidDynamicTypeException("Method " + ident.identifier + " has unsupported return type: " + dt);
        }
    }
}
