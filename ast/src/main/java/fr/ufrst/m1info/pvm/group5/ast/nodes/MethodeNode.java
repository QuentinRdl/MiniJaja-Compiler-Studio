package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
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
            throw new ASTBuildException("Methode", (ident == null)?"identifier":"return Type", "Main node" + ((ident == null)?"identifier":"return Type") + "must not be null");
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
        List<String> prdvs = vars != null && vars instanceof WithdrawalNode wvars ? wvars.withdrawCompile(address + 3 + pens.size() + pdvs.size() + piss.size()) : List.of();

        int n = address;
        int nens = pens.size();
        int ndvs = pdvs.size();
        int niss = piss.size();
        if (returnType.valueType==ValueType.VOID){
            if (instrs != null){
                piss.add("push(0)");
                niss++;
            }
            else{
                piss=List.of("push(0)");
                niss=1;
            }
        }
        int nrdvs = prdvs.size();
        code.add("push(" + (n + 3) + ")");
        code.add("new(" + ident.identifier + "," + returnType + ",meth,0)");
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
        MemoryCallUtil.safeCall(() -> m.declMethod(ident.identifier, dataType, this), this);
    }

    @Override
    public String checkType(Memory m) {
        DataType dataType = ValueType.toDataType(this.returnType.valueType);
        MemoryCallUtil.safeCall(() -> m.declMethod(ident.identifier, dataType, this), this);
        MemoryCallUtil.safeCall(m::pushScope, this);
        ASTNode root=getRoot();
        setAsRoot();
        if (params != null) params.checkType(m);
        if (vars != null) vars.checkType(m);
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
        if (root!= null){
            root.setAsRoot();
        }
        MemoryCallUtil.safeCall(m::popScope, this);
        return returnType.getValueType().toString().toLowerCase();
    }
    @Override
    protected Map<String, String> getProperties(){
        return Map.ofEntries(Map.entry("type", returnType.toString()));
    }

    @Override
    public void withdrawInterpret(Memory m) {
        MemoryCallUtil.safeCall(() -> m.withdrawDecl(ident.identifier), this);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        return List.of("swap","pop");
    }

    public String toString(){return "method:"+ident;}
}
