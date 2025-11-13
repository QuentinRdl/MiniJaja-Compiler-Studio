package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.ArrayList;
import java.util.List;

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
    protected List<ASTNode> getChildren() {
        return List.of(ident);
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        jajacodes.add("new(" + ident.identifier + "," + type + ",param,0)");
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        m.declVar(ident.identifier, new Value(), ValueType.toDataType(type.valueType));
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        if (type.valueType.equals(ValueType.VOID)) {
            throw new ASTInvalidDynamicTypeException("Parameter cannot be of type void");
        }
        m.declVar(ident.identifier, new Value(), ValueType.toDataType(type.valueType));
        return "void";
    }

    @Override
    public void withdrawInterpret(Memory m) {
        m.withdrawDecl(ident.identifier);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> code = new ArrayList<>();
        code.add("pop(" + ident.identifier + ")");
        return code;
    }
}
