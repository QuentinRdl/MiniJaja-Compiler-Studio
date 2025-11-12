package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodeNode extends ASTNode {
    public final TypeNode returnType;
    public final IdentNode ident;
    public final ASTNode params;
    public final ASTNode vars;
    public final ASTNode instrs;

    public MethodeNode(TypeNode returnType, IdentNode ident, ASTNode params, ASTNode vars, ASTNode instrs) {
        if (returnType == null || ident == null || params == null) {
            throw new ASTBuildException("MethodeNode requires non-null returnType, ident, and params");
        }
        this.returnType = returnType;
        this.ident = ident;
        this.params = params;
        this.vars = vars;
        this.instrs = instrs;
    }

    @Override
    protected List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(ident);
        children.add(params);
        if (vars != null) children.add(vars);
        if (instrs != null) children.add(instrs);
        return children;
    }

    @Override
    public List<String> compile(int address) {
        List<String> code = new ArrayList<>();// label méthode
        if (vars != null) code.addAll(vars.compile(address));
        if (instrs != null) code.addAll(instrs.compile(address));
        code.add("return");
        return code;
    }

    @Override
    public void interpret(Memory m) {
        DataType dataType = ValueType.toDataType(this.returnType.valueType);
        m.declMethod(ident.identifier, dataType, this);
        System.out.println("Method '" + ident.identifier + "' declared with type " + returnType.getValueType());
    }

    @Override
    public String checkType(Memory m) {
        if (instrs != null) instrs.checkType(m);
        return returnType.getValueType().name().toLowerCase();
    }
    @Override
    protected Map<String, String> getProperties(){
        return Map.ofEntries(Map.entry("type", returnType.toString()));
    }
}
