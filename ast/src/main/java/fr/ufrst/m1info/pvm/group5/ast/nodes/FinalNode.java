package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinalNode extends ASTNode implements WithdrawalNode {
    TypeNode type;
    IdentNode ident;
    ASTNode expression;

    public FinalNode(TypeNode type, IdentNode ident, ASTNode expression){
        this.type = type;
        this.ident = ident;
        this.expression = expression;
        if(this.type == null){
            throw new ASTBuildException("Final", "type", "type of final node cannot be null");
        }
        if(this.ident == null){
            throw new ASTBuildException("Final", "identifier", "identifier of final node cannot be null");
        }
        if (expression != null && !(expression instanceof EvaluableNode)) {
            throw new ASTBuildException("Final", "expression", "expression of final node must be evaluable");
        }
    }


    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        if(expression != null) {
            jajacodes.addAll(expression.compile(address));
        }
        jajacodes.add("new(" + ident.identifier + "," + type + ",cst,0)");
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) {
        if(expression == null){
            m.declCst(ident.identifier, null, ValueType.toDataType(type.valueType));
            return;
        }
        Value v = ((EvaluableNode)expression).eval(m);
        MemoryCallUtil.safeCall(() -> m.declCst(ident.identifier, v, ValueType.toDataType(type.valueType)), this);
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        if(expression == null){
            MemoryCallUtil.safeCall(() -> m.declCst(ident.identifier, null, ValueType.toDataType(type.valueType)), this);
            return "void";
        }
        String exprType = expression.checkType(m);

        String declaredType;
        switch (type.valueType) {
            case INT :
                declaredType = "int";
                MemoryCallUtil.safeCall(() -> m.declCst(ident.identifier, new Value(1), ValueType.toDataType(type.valueType)), this);
                break;
            case BOOL :
                declaredType = "bool";
                MemoryCallUtil.safeCall(() -> m.declCst(ident.identifier, new Value(false), ValueType.toDataType(type.valueType)), this);
                break;
            default :
                throw new InterpretationInvalidTypeException(this, "[int, bool]", type.valueType.toString());
        }
        if (!exprType.equals(declaredType)) {
            throw new InterpretationInvalidTypeException(this, "[int, bool]", declaredType);
        }

        return "void";
    }

    @Override
    protected Map<String,String> getProperties(){
        return Map.ofEntries(Map.entry("type",type.toString()));
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(ident);
        if(expression != null)
            children.add(expression);
        return children;
    }


    @Override
    public void withdrawInterpret(Memory m) {
        m.withdrawDecl(ident.identifier);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> jajacodes = new ArrayList<>();
        jajacodes.add("swap");
        jajacodes.add("pop");
        return jajacodes;
    }

    public String toString(){return "final";}
}
