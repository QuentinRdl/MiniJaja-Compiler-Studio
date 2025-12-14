package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.List;
import java.util.Map;

public class TabNode extends ASTNode implements EvaluableNode {

    IdentNode ident;
    private final ASTNode indexExp;

    public TabNode(IdentNode ident, ASTNode indexExp) {
        if (ident == null || indexExp == null) {
            throw new ASTBuildException("Tab", (ident == null)?"identifier":"index", "Tab must have a non-null"+((ident == null)?"identifier":"index"));
        }
        if (!(indexExp instanceof EvaluableNode)) {
            throw new ASTBuildException("Tab", "index" ,"TabNode index must be evaluable");
        }
        this.ident = ident;
        this.indexExp = indexExp;
    }

    @Override
    public Value eval(Memory m) {

        Value indexVal = ((EvaluableNode) indexExp).eval(m);

        int index = indexVal.valueInt;
        return m.valT(ident.identifier, index);
    }

    @Override
    public void interpret(Memory m) {
        throw new ASTInvalidOperationException("interpretation", "Tab", this.getLine());
    }

    @Override
    public String checkType(Memory m) {
        if (!m.contains(ident.identifier)) {
            throw ASTInvalidMemoryException.UndefinedVariable(ident.identifier, this.getLine());
        }

        String indexType = indexExp.checkType(m);
        if (!"int".equals(indexType)) {
            throw new InterpretationInvalidTypeException(this.getLine(), "int", indexType, "Tab");
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
