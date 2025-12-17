package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParamNode extends ASTNode implements WithdrawalNode {
    public final TypeNode type;
    public final IdentNode ident;

    public ParamNode(TypeNode type, IdentNode ident) {
        if (type == null) {
            throw new ASTBuildException("Param", "type", "ParamNode cannot have a null type");
        }
        if (ident == null) {
            throw new ASTBuildException("Param", "identifier", "ParamNode cannot have a null identifier");
        }
        this.type = type;
        this.ident = ident;
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(ident);
    }

    @Override
    protected Map<String, String> getProperties(){
        return Map.ofEntries(Map.entry("type", type.toString()));
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        jajacodes.add("new(" + ident.identifier + "," + type + ",var,1)");
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        Value val;
        switch (type.valueType) {
            case BOOL -> val = new Value(false);
            case INT -> val = new Value(0);
            default -> val = new Value();
        }
        MemoryCallUtil.safeCall(() -> m.declVar(ident.identifier, val, ValueType.toDataType(type.valueType)), this);
    }


    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        if (type.valueType.equals(ValueType.VOID)) {
            throw new InterpretationInvalidTypeException(this, "[int, bool]", type.valueType.toString());
        }else if (type.valueType.equals(ValueType.INT)) {
            MemoryCallUtil.safeCall(() -> m.declVar(ident.identifier, new Value(1), ValueType.toDataType(type.valueType)), this);
        }
        else if (type.valueType.equals(ValueType.BOOL)) {
            MemoryCallUtil.safeCall(() -> m.declVar(ident.identifier, new Value(1), ValueType.toDataType(type.valueType)), this);
        }
        return "void";
    }

    @Override
    public void withdrawInterpret(Memory m) {
        MemoryCallUtil.safeCall(() -> m.withdrawDecl(ident.identifier), this);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> code = new ArrayList<>();
        code.add("swap");
        code.add("pop");
        return code;
    }

    public String toString(){return "parameter:{"+ident+"}";}
}
