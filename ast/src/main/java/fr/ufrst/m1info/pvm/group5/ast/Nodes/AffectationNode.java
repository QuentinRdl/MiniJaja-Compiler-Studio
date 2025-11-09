package fr.ufrst.m1info.pvm.group5.ast.Nodes;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.ArrayList;
import java.util.List;

public class AffectationNode extends ASTNode{
    IdentNode identifier;
    ASTNode expression;

    public AffectationNode(IdentNode identifier, ASTNode expression) {
        this.identifier = identifier;
        this.expression = expression;
        if(identifier == null || expression == null){
            throw new ASTBuildException("AffectationNode cannot have null nodes");
        }
        if(!(expression instanceof EvaluableNode)){
            throw new ASTBuildException("AffectationNode cannot have non-evaluable nodes");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.addAll(expression.compile(address));
        JJCodes.add("store("+identifier.identifier+")");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException, ASTInvalidOperationException {
        Value v = ((EvaluableNode)expression).eval(m);
        m.affectValue(identifier.identifier, v);
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {

        try {
            String exprType = expression.checkType(m);

            DataType varDataType = m.dataTypeOf(identifier.identifier);
            String varTypeStr;
            if (varDataType == DataType.INT) varTypeStr = "int";
            else if (varDataType == DataType.BOOL) varTypeStr = "bool";
            else throw new ASTInvalidDynamicTypeException(
                        "AffectationNode: variable type not supported for " + identifier.identifier
                );

            if (!exprType.equals(varTypeStr)) {
                throw new ASTInvalidDynamicTypeException(
                        "AffectationNode: type of expression (" + exprType +
                                ") incompatible with the type of the variable(" + varTypeStr + ")"
                );
            }
        } catch (IllegalArgumentException e){
            throw new ASTInvalidMemoryException(
                    "AffectationNode: variable " + identifier.identifier + " not defined"
            );
        }

        return "void"; // Affectation n'a pas de type
    }

    @Override
    protected List<ASTNode> getChildren() {
        return List.of(identifier,expression);
    }


}
