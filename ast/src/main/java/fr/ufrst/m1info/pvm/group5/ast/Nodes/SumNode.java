package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;

import java.util.ArrayList;
import java.util.List;

public class SumNode extends ASTNode{
    IdentNode identifier;
    ASTNode expression;

    public SumNode(IdentNode identifier, ASTNode expression){
        this.identifier = identifier;
        this.expression = expression;
        if(this.identifier == null){
            throw new ASTBuildException("Sum identifier cannot be null");
        }
        if(this.expression == null){
            throw new ASTBuildException("Sum operand cannot be null");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(expression.compile(address));
        JJCodes.add("inc(" + identifier + ")");
        return List.of();
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException {
        Value v = ((EvaluableNode)expression).eval(m);
        Value u = (Value)m.val(identifier.identifier);
        if(u == null){
            throw new ASTInvalidMemoryException("Variable" + identifier.identifier + " is undefined");
        }
        int res = u.valueInt + v.valueInt;
        m.affectValue(identifier.identifier, new Value(res));
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        String exprType = expression.checkType(m);
        if (!"int".equals(exprType)) {
            throw new ASTInvalidDynamicTypeException(
                    "The operand of SumNode must be of type int, found: " + exprType
            );
        }
        Value v;
        try {
            v = (Value) m.val(identifier.identifier);
        } catch (Exception e) {
            throw new ASTInvalidDynamicTypeException(
                    "Error accessing variable " + identifier.identifier
            );
        }

        if (v == null) {
            throw new ASTInvalidDynamicTypeException(
                    "Variable " + identifier.identifier + " not defined"
            );
        }
        DataType dt = ValueType.toDataType(v.Type);
        if (dt != DataType.INT) {
            throw new ASTInvalidDynamicTypeException(
                    "SumNode impossible : variable " + identifier.identifier + " is not an int"
            );
        }

        return "int";
    }


}
