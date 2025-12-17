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
            throw new ASTBuildException("AppelI", "identifier", "AppelI node must have a non-null identifier");
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
            throws ASTInvalidMemoryException, ASTInvalidOperationException, InterpretationInvalidTypeException {
        SymbolTableEntry methodEntry = validateMethodEntry(m);
        List<Value> evaluatedArgs = evaluateArguments(m);
        MethodeNode methodNode = getMethodNode(methodEntry);

        MemoryCallUtil.safeCall(m::pushScope, this);
        bindParameters(m, methodNode, evaluatedArgs);
        executeMethodBody(m, methodNode);
        cleanupParameters(m, methodNode);
        MemoryCallUtil.safeCall(m::popScope, this);
    }

    /**
     * Validates that the method exists in memory and is indeed a method.
     *
     * @param m the memory containing the symbol table
     * @return the symbol table entry for the method
     * @throws ASTInvalidMemoryException if the method is not found
     * @throws ASTInvalidOperationException if the identifier is not a method
     */
    private SymbolTableEntry validateMethodEntry(Memory m) {
        SymbolTableEntry methodEntry = MemoryCallUtil.safeCall(() -> m.getMethod(ident.identifier), this);
        if (methodEntry == null) {
            throw ASTInvalidMemoryException.UndefinedVariable(ident.identifier, this);
        }
        if (methodEntry.getKind() != EntryKind.METHOD) {
            throw ASTInvalidMemoryException.InvalidVariable(ident.identifier, this, "method", methodEntry.getKind().toString());
        }
        return methodEntry;
    }

    /**
     * Evaluates the arguments passed to the method call.
     *
     * @param m the memory used for evaluation
     * @return a list of evaluated argument values
     * @throws ASTInvalidOperationException if arguments are not evaluable
     */
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
            throw new ASTBuildException("AppelI", "arguments", "AppelI call must have evaluable arguments");
        }
        return evaluatedArgs;
    }

    /**
     * Retrieves the MethodeNode from the symbol table entry.
     *
     * @param methodEntry the symbol table entry for the method
     * @return the MethodeNode containing method definition
     * @throws ASTInvalidOperationException if the reference is not a MethodeNode
     */
    private MethodeNode getMethodNode(SymbolTableEntry methodEntry) {
        Object ref = MemoryCallUtil.safeCall(methodEntry::getReference, this);
        if (!(ref instanceof MethodeNode methodNode)) {
            throw ASTInvalidMemoryException.InvalidVariable(ident.identifier, this, "MethodNode", ref.getClass().getSimpleName());
        }
        return methodNode;
    }

    /**
     * Binds the evaluated arguments to the method's formal parameters in memory.
     *
     * @param m the memory where variables will be declared
     * @param methodNode the method node containing parameter definitions
     * @param evaluatedArgs the list of evaluated argument values
     * @throws ASTInvalidOperationException if parameters are invalid or arity mismatch
     */
    private void bindParameters(Memory m, MethodeNode methodNode, List<Value> evaluatedArgs) {
        if (methodNode.params == null) {
            return;
        }

        if (!(methodNode.params instanceof ParamListNode paramList)) {
            throw ASTInvalidMemoryException.InvalidVariable(ident.identifier,  this, "method", methodNode.params.getClass().getSimpleName());
        }

        List<ParamNode> formals = paramList.toList();
        validateArity(formals.size(), evaluatedArgs.size());

        for (int i = 0; i < formals.size(); i++) {
            ParamNode p = formals.get(i);
            Value argVal = evaluatedArgs.get(i);
            if (p.type.valueType!=argVal.type){
                throw new InterpretationInvalidTypeException(this, p.type.valueType.toString(), argVal.type.toString());
            }
            m.declVar(p.ident.identifier, argVal, ValueType.toDataType(p.type.valueType));
        }
    }

    /**
     * Validates that the number of arguments matches the expected parameter count.
     *
     * @param expected the expected number of parameters
     * @param actual the actual number of arguments provided
     * @throws ASTInvalidOperationException if arity does not match
     */
    private void validateArity(int expected, int actual) {
        if (expected != actual) {
            throw new RuntimeException(String.format("Arity mismatch, expected %d arguments, got %d (at line %d, %s)", expected, actual, this.getLine(), this.toString()));
        }
    }

    /**
     * Executes the method body by interpreting its variables and instructions.
     *
     * @param m the memory context for interpretation
     * @param methodNode the method node containing the body to execute
     */
    private void executeMethodBody(Memory m, MethodeNode methodNode) {
        if (methodNode.vars != null) {
            methodNode.vars.interpret(m);
        }
        if (methodNode.instrs != null) {
            methodNode.instrs.interpret(m);
        }
    }

    /**
     * Cleans up the method parameters from memory after method execution.
     *
     * @param m the memory from which to withdraw parameters
     * @param methodNode the method node containing the parameters to clean up
     */
    private void cleanupParameters(Memory m, MethodeNode methodNode) {
        if (methodNode.params == null) {
            return;
        }

        if (methodNode.params instanceof WithdrawalNode withdrawalNode) {
            withdrawalNode.withdrawInterpret(m);
        } else if (methodNode.params instanceof ParamListNode paramListNode) {
            List<ParamNode> formals = paramListNode.toList();
            for (ParamNode p : formals) {
                MemoryCallUtil.safeCall(() -> m.withdrawDecl(p.ident.identifier), this);
            }
        }
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        SymbolTableEntry methodEntry = validateMethodEntry(m);

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
                throw new InterpretationInvalidTypeException(this, "int, bool or void", dt.toString());
        }
    }

    public String toString(){return "AppelI";}
}
