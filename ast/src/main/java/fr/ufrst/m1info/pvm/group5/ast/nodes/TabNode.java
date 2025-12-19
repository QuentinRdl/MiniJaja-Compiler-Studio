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
            throw new ASTBuildException("Tab", indexExp.getClass().getName() ,"TabNode index must be evaluable");
        }
        this.ident = ident;
        this.indexExp = indexExp;
    }

    @Override
    public Value eval(Memory m) {

        Value indexVal = ((EvaluableNode) indexExp).eval(m);

        int index = indexVal.valueInt;
        return MemoryCallUtil.safeCall(() -> m.valT(ident.identifier, index), this);
    }

    @Override
    public void interpret(Memory m) {
        throw new ASTInvalidOperationException("interpretation", this);
    }

    @Override
    public String checkType(Memory m) {
        if (!MemoryCallUtil.safeCall(() -> m.contains(ident.identifier), this)) {
            throw ASTInvalidMemoryException.UndefinedVariable(ident.identifier, this);
        }
        if(!MemoryCallUtil.safeCall(() -> m.isArray(ident.identifier), this)){
            throw new InterpretationInvalidTypeException("Expected "+ident.identifier+" to be an array", this);
        }
        String indexType = indexExp.checkType(m);
        if (!"int".equals(indexType)) {
            throw new InterpretationInvalidTypeException(this, "int", indexType);
        }
        return MemoryCallUtil.safeCall(() -> m.dataTypeOf(ident.identifier).toString().toLowerCase(), this);
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

    public String toString(){return "array reference:{"+ident+"}";}
}
