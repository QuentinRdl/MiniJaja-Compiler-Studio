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
            throw new ASTBuildException("AppelE", "identifier", "AppelE node must have a non-null identifier");
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

        Object result = MemoryCallUtil.safeCall(() -> m.val(m.identVarClass()), this);
        return (Value)result;
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
        throw new ASTInvalidOperationException("interpretation", this);
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        SymbolTableEntry methodEntry = MemoryCallUtil.safeCall(() -> m.getMethod(ident.identifier), this);
        if (methodEntry == null) {
            throw ASTInvalidMemoryException.UndefinedVariable(ident.identifier, this);
        }
        if (methodEntry.getKind() != EntryKind.METHOD) {
            throw new InterpretationInvalidTypeException(this, "method", methodEntry.getKind().toString());
        }

        if (args != null) {
            args.checkType(m);
        }
        DataType dt = methodEntry.getDataType();
        return switch (dt) {
            case INT -> "int";
            case BOOL -> "bool";
            default -> throw new InterpretationInvalidTypeException(this, "[int, bool]", dt.toString());
        };
    }

    public String toString(){return "AppelE";}
}