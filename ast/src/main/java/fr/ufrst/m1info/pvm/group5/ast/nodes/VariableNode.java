package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VariableNode extends ASTNode implements WithdrawalNode {
    TypeNode typemeth;
    IdentNode ident;
    ASTNode vexp;

    public VariableNode(TypeNode typemeth, IdentNode ident, ASTNode vexp){
        this.typemeth=typemeth;
        this.ident=ident;
        this.vexp=vexp;
        if(typemeth == null){
            throw new ASTBuildException("Variable must have a valid type");
        }
        if(ident == null){
            throw new ASTBuildException("Variable must have a valid identifier");
        }
        if(vexp != null && !(vexp instanceof EvaluableNode)){
            throw new ASTBuildException("Variable assignation operator must have an evaluable operand");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        if (vexp != null) {
            jajacodes.addAll(vexp.compile(address));
        }
        jajacodes.add("new(" + ident.identifier + "," + typemeth + ",var,0)" );
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        if(vexp == null){
            m.declVar(ident.identifier, new Value(), ValueType.toDataType(typemeth.valueType));
        }
        else {
            Value v = ((EvaluableNode) vexp).eval(m);
            m.declVar(ident.identifier, v, ValueType.toDataType(typemeth.valueType));
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        ValueType varType = typemeth.valueType;
        String vType="";
        if(varType == ValueType.INT){
            m.declVar(ident.identifier, new Value(1), ValueType.toDataType(typemeth.valueType));
            vType="int";
        }
        else if(varType == ValueType.BOOL){
            m.declVar(ident.identifier, new Value(true), ValueType.toDataType(typemeth.valueType));
            vType="bool";
        }else {
            throw new ASTInvalidDynamicTypeException(
                    "Cannot declare variable with type " + varType
            );
        }
        if (vexp != null) {
            String exprType = vexp.checkType(m);


            if (!exprType.equals(vType)) {
                throw new ASTInvalidDynamicTypeException(
                        "Type of expression (" + exprType + ") incompatible with the type of the variable (" + vType + ")"
                );
            }
        }
        return "void";
    }

    @Override
    protected Map<String, String> getProperties(){
        return Map.ofEntries(Map.entry("type", typemeth.toString()));
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(ident);
        if(vexp!=null)
            children.add(vexp);
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
}
