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
            throw new ASTBuildException("Param must have a valid type");
        }
        if (ident == null) {
            throw new ASTBuildException("param must have a valid identifier");
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
        jajacodes.add("new(" + ident.identifier + "," + type + ",var,0)");
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
        m.declVar(ident.identifier, val, ValueType.toDataType(type.valueType));
    }


    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        if (type.valueType.equals(ValueType.VOID)) {
            throw new ASTInvalidDynamicTypeException("Parameter cannot be of type void");
        }else if (type.valueType.equals(ValueType.INT)) {
            m.declVar(ident.identifier, new Value(1), ValueType.toDataType(type.valueType));
        }
        else if (type.valueType.equals(ValueType.BOOL)) {
            m.declVar(ident.identifier, new Value(1), ValueType.toDataType(type.valueType));
        }
        return "void";
    }

    @Override
    public void withdrawInterpret(Memory m) {
        m.withdrawDecl(ident.identifier);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> code = new ArrayList<>();
        code.add("swap");
        code.add("pop");
        return code;
    }

}
