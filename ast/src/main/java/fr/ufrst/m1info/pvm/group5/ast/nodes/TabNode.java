package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.List;
import java.util.Map;

public class TabNode extends ASTNode implements EvaluableNode {

    IdentNode ident;
    private final ASTNode indexExp;

    public TabNode(IdentNode ident, ASTNode indexExp) {
        if (ident == null || indexExp == null) {
            throw new ASTBuildException("TabNode requires non-null identifier and index expression");
        }
        this.ident = ident;
        this.indexExp = indexExp;
    }

    @Override
    public Value eval(Memory m) {
        if (!(indexExp instanceof EvaluableNode)) {
            throw new ASTInvalidOperationException("TabNode index expression is not evaluable");
        }

        Value indexVal = ((EvaluableNode) indexExp).eval(m);
        if (indexVal.type != ValueType.INT) {
            throw new ASTInvalidDynamicTypeException("TabNode index must be an integer");
        }

        int index = indexVal.valueInt;
        if (index < 0) {
            throw new ASTInvalidOperationException("TabNode index cannot be negative: " + index);
        }

        int arrayLength = m.tabLength(ident.identifier);
        if (index >= arrayLength) {
            throw new ASTInvalidOperationException("TabNode index out of bounds: " + index + " >= " + arrayLength);
        }

        return m.valT(ident.identifier, index);
    }

    @Override
    public void interpret(Memory m) {
        throw new ASTInvalidOperationException("TabNode cannot be interpreted directly");
    }

    @Override
    public String checkType(Memory m) {
        if (!m.contains(ident.identifier)) {
            throw new ASTInvalidDynamicTypeException("TabNode identifier '" + ident.identifier + "' is not declared");
        }

        String indexType = indexExp.checkType(m);
        if (!"int".equals(indexType)) {
            throw new ASTInvalidDynamicTypeException("TabNode index must be of type int, got: " + indexType);
        }
        return m.dataTypeOf(ident.identifier).toString().toLowerCase();
    }

    @Override
    public List<String> compile(int address) {
        List<String> code = new java.util.ArrayList<>();
        code.addAll(indexExp.compile(address));
        code.add("aload(" + ident.identifier + ")");
        return code;
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(ident, indexExp);
    }

    @Override
    protected Map<String, String> getProperties() {
        return Map.of("identifier", ident.identifier);
    }
}
