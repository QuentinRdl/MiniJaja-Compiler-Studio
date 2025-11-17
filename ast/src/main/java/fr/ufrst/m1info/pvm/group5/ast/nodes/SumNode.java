package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;

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
        else if(!(this.expression instanceof EvaluableNode)){
            throw new ASTBuildException("Sum operand must be evaluable");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(expression.compile(address));
        JJCodes.add("inc(" + identifier.identifier + ")");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException {
        Value v = ((EvaluableNode)expression).eval(m);
        Value u = identifier.eval(m);
        int res = u.valueInt + v.valueInt;
        m.affectValue(identifier.identifier, new Value(res));
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        String exprType = expression.checkType(m);
        if (!exprType.equals("int")) {
            throw new ASTInvalidDynamicTypeException(
                    "The operand of SumNode must be of type int, found: " + exprType
            );
        }
        DataType dataType;
        try{
            dataType = m.dataTypeOf(identifier.identifier);
        } catch (Exception e) {
            throw new ASTInvalidMemoryException(e.getMessage());
        }
        if (dataType != DataType.INT) {
            throw new ASTInvalidDynamicTypeException(
                    "SumNode impossible : variable " + identifier.identifier + " is not an int"
            );
        }

        return "int";
    }

    @Override
    protected List<ASTNode> getChildren() {
        return List.of(identifier, expression);
    }


}
