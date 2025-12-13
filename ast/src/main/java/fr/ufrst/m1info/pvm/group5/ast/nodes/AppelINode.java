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
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(ident);
        if (args != null) children.add(args);
        return children;
    }

    @Override
    public List<String> compile(int address) {
        List<String> code = new ArrayList<>();
        List<String> plexp = args != null ? args.compile(address) : List.of();
        code.addAll(plexp);
        code.add("invoke(" + ident.identifier + ")");
        if (args != null){
            if (args instanceof ExpListNode rargs){
                rargs.compileWithdraw(code);
            }else {
                code.add("swap");
                code.add("pop");
            }
        }
        code.add("pop");
        return code;
    }

    @Override
    public void interpret(Memory m)
            throws ASTInvalidMemoryException, ASTInvalidOperationException, ASTInvalidDynamicTypeException {
        SymbolTableEntry methodEntry = validateMethodEntry(m);
        List<Value> evaluatedArgs = evaluateArguments(m);
        MethodeNode methodNode = getMethodNode(methodEntry);

        m.pushScope();
        bindParameters(m, methodNode, evaluatedArgs);
        executeMethodBody(m, methodNode);
        cleanupParameters(m, methodNode);
        m.popScope();
    }

    private SymbolTableEntry validateMethodEntry(Memory m) {
        SymbolTableEntry methodEntry = m.getMethod(ident.identifier);
        if (methodEntry == null) {
            throw new ASTInvalidMemoryException("Method " + ident.identifier + " not found.");
        }
        if (methodEntry.getKind() != EntryKind.METHOD) {
            throw new ASTInvalidOperationException(ident.identifier + " is not a method!");
        }
        return methodEntry;
    }

    private List<Value> evaluateArguments(Memory m) {
        List<Value> evaluatedArgs = new ArrayList<>();
        if (args == null) {
            return evaluatedArgs;
        }

        if (args instanceof ExpListNode expListNode) {
            evaluatedArgs.addAll(expListNode.evalList(m));
        } else if (args instanceof EvaluableNode evaluableNode) {
            evaluatedArgs.add(evaluableNode.eval(m));
        } else {
            throw new ASTInvalidOperationException("Arguments node is not an ExpListNode/evaluable.");
        }
        return evaluatedArgs;
    }

    private MethodeNode getMethodNode(SymbolTableEntry methodEntry) {
        Object ref = methodEntry.getReference();
        if (!(ref instanceof MethodeNode methodNode)) {
            throw new ASTInvalidOperationException("Method reference for " + ident.identifier + " is not a MethodeNode");
        }
        return methodNode;
    }

    private void bindParameters(Memory m, MethodeNode methodNode, List<Value> evaluatedArgs) {
        if (methodNode.params == null) {
            return;
        }

        if (!(methodNode.params instanceof ParamListNode paramList)) {
            throw new ASTInvalidOperationException("Method parameters are not a ParamListNode");
        }

        List<ParamNode> formals = paramList.toList();
        validateArity(formals.size(), evaluatedArgs.size());

        for (int i = 0; i < formals.size(); i++) {
            ParamNode p = formals.get(i);
            Value argVal = evaluatedArgs.get(i);
            if (p.type.valueType!=argVal.type){
                throw new ASTInvalidDynamicTypeException("Line "+getLine()+" Arguments types doesn't match parameters types");
            }
            m.declVar(p.ident.identifier, argVal, ValueType.toDataType(p.type.valueType));
        }
    }

    private void validateArity(int expected, int actual) {
        if (expected != actual) {
            throw new ASTInvalidOperationException(
                    "Arity mismatch when calling " + ident.identifier + ": expected " + expected + " got " + actual
            );
        }
    }

    private void executeMethodBody(Memory m, MethodeNode methodNode) {
        if (methodNode.vars != null) {
            methodNode.vars.interpret(m);
        }
        if (methodNode.instrs != null) {
            methodNode.instrs.interpret(m);
        }
    }

    private void cleanupParameters(Memory m, MethodeNode methodNode) {
        if (methodNode.params == null) {
            return;
        }

        if (methodNode.params instanceof WithdrawalNode withdrawalNode) {
            withdrawalNode.withdrawInterpret(m);
        } else if (methodNode.params instanceof ParamListNode paramListNode) {
            List<ParamNode> formals = paramListNode.toList();
            for (ParamNode p : formals) {
                m.withdrawDecl(p.ident.identifier);
            }
        }
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
            default:
                throw new ASTInvalidDynamicTypeException("Method " + ident.identifier + " has unsupported return type: " + dt);
        }
    }
}
