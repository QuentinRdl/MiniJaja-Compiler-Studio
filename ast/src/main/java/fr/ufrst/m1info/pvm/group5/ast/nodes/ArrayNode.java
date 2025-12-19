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
        final String name = "Array";
        if(type == null) throw new ASTBuildException(name, "type", "type must not be null");
        if(ident==null) throw new ASTBuildException(name, "ident", "ident must not be null");
        if(sizeExp==null) throw new ASTBuildException(name, "sizeExp", "size must not be null");
        if(!(sizeExp instanceof EvaluableNode))  throw new ASTBuildException(name, "sizeExp", "size must be evaluable");
        this.type = type;
        this.ident = ident;
        this.sizeExp = sizeExp;
    }

    @Override
    public List<String> compile(int address) {
        java.util.List<String> code = new java.util.ArrayList<>();
        code.addAll(sizeExp.compile(address));
        String typeStr = type.valueType.toString().toUpperCase();
        code.add("newarray(" + ident.identifier + "," + typeStr + ")");

        return code;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException, ASTInvalidOperationException, InterpretationInvalidTypeException {
        Value sizeVal = ((EvaluableNode) sizeExp).eval(m);
        int size = sizeVal.valueInt;

        DataType dt = ValueType.toDataType(type.valueType);

        MemoryCallUtil.safeCall(() -> m.declTab(ident.identifier, size, dt), this);
    }

    @Override
    public void withdrawInterpret(Memory m) throws ASTInvalidMemoryException {
        MemoryCallUtil.safeCall(() -> m.withdrawDecl(ident.identifier), this);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        return List.of("swap", "pop");
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        String sizeType = sizeExp.checkType(m);
        if (!sizeType.equals("int")) {
            throw new InterpretationInvalidTypeException(this, "int", sizeType);
        }
        DataType dt = ValueType.toDataType(type.valueType);
        MemoryCallUtil.safeCall(() -> m.declTab(ident.identifier, 1, dt), this);

        return "void";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(ident, sizeExp);
    }

    @Override
    protected Map<String, String> getProperties() {
        return Map.of("identifier", ident.identifier, "type", type.valueType.toString());
    }

    public String toString(){return "array<"+type+">:"+ident.identifier;}
}