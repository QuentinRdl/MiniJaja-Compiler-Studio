package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.SymbolTableEntry;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;

public class AppelENode extends ASTNode implements EvaluableNode {
    private static final String METHOD_PREFIX = "Method ";
    private final IdentNode ident;
    private final ASTNode args;

    public AppelENode(IdentNode ident, ASTNode args) {
        if (ident == null) {
            throw new ASTBuildException("AppelENode cannot have null ident");
        }
        this.ident = ident;
        this.args = args;
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(ident);
        if (args != null) {
            children.add(args);
        }
        return children;
    }

    @Override
    public Value eval(Memory m) {
        AppelINode appelInstruction = new AppelINode(ident, args);
        appelInstruction.interpret(m);

        try {
            Object result = m.val(m.identVarClass());
            if (result instanceof Value value) {
                return value;
            }
            throw new ASTInvalidOperationException(METHOD_PREFIX + ident.identifier + " returned an invalid type.");
        } catch (Exception e) {
            throw new ASTInvalidOperationException(METHOD_PREFIX + ident.identifier + " did not return a value.");
        }
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
        return code;
    }



    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException {
        throw new ASTInvalidOperationException("Cannot interpret AppelEnode");
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException  {
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
                throw new ASTInvalidDynamicTypeException(METHOD_PREFIX + ident.identifier + " is of type void and cannot be used as an expression.");
            default:
                throw new ASTInvalidDynamicTypeException(METHOD_PREFIX + ident.identifier + " has unsupported return type: " + dt);
        }
    }
}