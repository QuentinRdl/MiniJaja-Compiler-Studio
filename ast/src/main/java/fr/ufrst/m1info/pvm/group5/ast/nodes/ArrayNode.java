package fr.ufrst.m1info.pvm.group5.ast.nodes;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.List;
import java.util.Map;

public class ArrayNode extends ASTNode implements WithdrawalNode {

    private final TypeNode type;
    private final IdentNode ident;
    private final ASTNode sizeExp;

    public ArrayNode(TypeNode type, IdentNode ident, ASTNode sizeExp) {
        if (type == null || ident == null || sizeExp == null) {
            throw new ASTBuildException("ArrayNode requires non-null type, identifier, and size expression");
        }
        this.type = type;
        this.ident = ident;
        this.sizeExp = sizeExp;
    }

    @Override
    public List<String> compile(int address) {
        java.util.List<String> code = new java.util.ArrayList<>();
        code.addAll(sizeExp.compile(address));
        String typeStr = type.valueType.name().toLowerCase();
        code.add("newarray(" + ident.identifier + ", " + typeStr + ")");

        return code;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException, ASTInvalidOperationException, ASTInvalidDynamicTypeException {
        if (!(sizeExp instanceof EvaluableNode)) {
            throw new ASTInvalidOperationException("Array size expression is not evaluable");
        }
        Value sizeVal = ((EvaluableNode) sizeExp).eval(m);

        if (sizeVal.type != ValueType.INT) {
            throw new ASTInvalidDynamicTypeException("Array size must be an integer");
        }
        int size = sizeVal.valueInt;

        if (size <= 0) {
            throw new ASTInvalidOperationException("Array size must be positive (greater than 0)");
        }
        DataType dt = ValueType.toDataType(type.valueType);

        m.declTab(ident.identifier, size, dt);
    }

    @Override
    public void withdrawInterpret(Memory m) throws ASTInvalidMemoryException {
        m.withdrawDecl(ident.identifier);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        return List.of("swap", "pop");
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        if (!(sizeExp instanceof EvaluableNode)) {
            throw new ASTInvalidDynamicTypeException("Array size expression is not evaluable");
        }

        String sizeType = sizeExp.checkType(m);
        if (!sizeType.equals("int")) {
            throw new ASTInvalidDynamicTypeException("Array size must be of type int, but got: " + sizeType);
        }
        DataType dt = ValueType.toDataType(type.valueType);
        m.declTab(ident.identifier, 1, dt);

        return "void";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(ident, sizeExp);
    }

    @Override
    protected Map<String, String> getProperties() {
        return Map.of("identifier", ident.identifier, "type", type.valueType.name());
    }
}