package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ast.WithdrawalNode;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodeNode extends ASTNode implements WithdrawalNode {
    public final TypeNode returnType;
    public final IdentNode ident;
    public final ASTNode params;
    public final ASTNode vars;
    public final ASTNode instrs;

    public MethodeNode(TypeNode returnType, IdentNode ident, ASTNode params, ASTNode vars, ASTNode instrs) {
        if (returnType == null || ident == null) {
            throw new ASTBuildException("MethodeNode requires non-null returnType and ident");
        }
        this.returnType = returnType;
        this.ident = ident;
        this.params = params;
        this.vars = vars;
        this.instrs = instrs;
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(ident);
        if (params != null) children.add(params);
        if (vars != null) children.add(vars);
        if (instrs != null) children.add(instrs);
        return children;
    }

    @Override
    public List<String> compile(int address) {
        List<String> code = new ArrayList<>();
        List<String> pens = params != null ?params.compile(address + 3) : List.of();
        List<String> pdvs = vars != null ? vars.compile(address + 3 + pens.size()) : List.of();
        List<String> piss = instrs != null ? instrs.compile(address + 3 + pens.size() + pdvs.size()) : List.of();
        List<String> prdvs = List.of();

        int n = address;
        int nens = pens.size();
        int ndvs = pdvs.size();
        int niss = piss.size();
        int nrdvs = prdvs.size();
        code.add("jncnil");
        code.add("push(" + (n + 3) + ")");
        code.add("new(" + ident.identifier + ", " + returnType + ", meth, 0)");
        code.add("goto(" + (n + nens + ndvs + niss + nrdvs + 5) + ")");
        code.addAll(pens);
        code.addAll(pdvs);
        code.addAll(piss);
        code.addAll(prdvs);
        code.add("swap");
        code.add("return");

        return code;
    }

    @Override
    public void interpret(Memory m) {
        DataType dataType = ValueType.toDataType(this.returnType.valueType);
        m.declMethod(ident.identifier, dataType, this);
    }

    @Override
    public String checkType(Memory m) {
        DataType dataType = ValueType.toDataType(this.returnType.valueType);
        m.declMethod(ident.identifier, dataType, this);
        m.pushScope();
        if (params != null) params.checkType(m);
        if (instrs != null) instrs.checkType(m);
        if (params != null) {
            if (params instanceof WithdrawalNode withdrawalNode) {
                withdrawalNode.withdrawInterpret(m);
            } else if (params instanceof ParamListNode paramListNode) {
                List<ParamNode> formals = paramListNode.toList();
                for (ParamNode p : formals) {
                    m.withdrawDecl(p.ident.identifier);
                }
            }
        }
        m.popScope();
        return returnType.getValueType().name().toLowerCase();
    }
    @Override
    protected Map<String, String> getProperties(){
        return Map.ofEntries(Map.entry("type", returnType.toString()));
    }

    @Override
    public void withdrawInterpret(Memory m) {
        m.withdrawDecl(ident.identifier);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        return List.of("swap","pop");
    }
}
