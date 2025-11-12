package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.SymbolTableEntry;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;

import java.util.ArrayList;
import java.util.List;

public class AppelINode extends ASTNode {
    public final IdentNode ident;
    public final ASTNode args;

    public AppelINode(IdentNode ident, ASTNode args) {
        if (ident == null || args == null) {
            throw new ASTBuildException("AppelINode cannot have null nodes");
        }
        this.ident = ident;
        this.args = args;
    }

    @Override
    protected List<ASTNode> getChildren() {
        return List.of(ident, args);
    }

    @Override
    public List<String> compile(int address) {
        List<String> code = new ArrayList<>();
        code.addAll(args.compile(address));
        code.add("invoke(" + ident.identifier + ")");
        return code;
    }

    @Override
    public void interpret(Memory m)
            throws ASTInvalidMemoryException, ASTInvalidOperationException {
        SymbolTableEntry methodEntry = m.getMethod(ident.identifier);

        if (methodEntry == null) {
            throw new ASTInvalidMemoryException("Method " + ident.identifier + " not found.");
        }
        if (methodEntry.getKind() != EntryKind.METHOD) {
            throw new ASTInvalidOperationException(ident.identifier + " is not a method !");
        }
        if (args != null) {
            args.interpret(m);
        }
        System.out.println("Calling the method '" + ident.identifier + "'");
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
