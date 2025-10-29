package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.ArrayList;
import java.util.List;

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
        List<String> jajacodes = new ArrayList<String>();
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
        if (vexp != null) {
            String exprType = vexp.checkType(m);
            String varType = typemeth.valueType.equals(ValueType.INT) ? "int" : "bool";

            if (!exprType.equals(varType)) {
                throw new ASTInvalidDynamicTypeException(
                        "Type of expression (" + exprType + ") incompatible with the type of the variable (" + varType + ")"
                );
            }
        }
        m.declVar(ident.identifier, new Value(), ValueType.toDataType(typemeth.valueType));
        return "void";
    }


    @Override
    public void withdrawInterpret(Memory m) {
        m.withdrawDecl(ident.identifier);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.add("swap");
        jajacodes.add("pop");
        return jajacodes;
    }
}
