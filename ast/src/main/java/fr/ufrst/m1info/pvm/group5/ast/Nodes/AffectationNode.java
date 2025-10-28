package fr.ufrst.m1info.pvm.group5.ast.Nodes;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;

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
        String exprType = expression.checkType(m);
        Value v;
        try {
            v = (Value) m.val(identifier.identifier);
            if (v == null) {
                throw new ASTInvalidDynamicTypeException(
                        "AffectationNode: variable " + identifier.identifier + " not defined"
                );
            }
        } catch (ASTInvalidMemoryException e) {
            throw new ASTInvalidDynamicTypeException(
                    "AffectationNode: error accessing variable " + identifier.identifier
            );
        }

        // Vérifie la compatibilité de type via DataType
        DataType varDataType = ValueType.toDataType(v.Type);
        String varTypeStr;
        if (varDataType == DataType.INT) varTypeStr = "int";
        else if (varDataType == DataType.BOOL) varTypeStr = "bool";
        else throw new ASTInvalidDynamicTypeException(
                    "AffectationNode: variable type not supported for" + identifier.identifier
            );

        if (!exprType.equals(varTypeStr)) {
            throw new ASTInvalidDynamicTypeException(
                    "AffectationNode: type of expression (" + exprType +
                            ") incompatible with the type of the variable(" + varTypeStr + ")"
            );
        }

        return "void"; // Affectation n'a pas de type
    }



}
