package fr.ufrst.m1info.pvm.group5.ast.nodes;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;

public class AffectationNode extends ASTNode{
    ASTNode identifier;
    ASTNode expression;

    public AffectationNode(ASTNode identifier, ASTNode expression) {
        this.identifier = identifier;
        this.expression = expression;
        if(identifier == null || expression == null){
            throw new ASTBuildException("AffectationNode cannot have null nodes");
        }
        if(!(identifier instanceof IdentNode) && !(identifier instanceof TabNode)){
            throw new ASTBuildException("AffectationNode identifier must be IdentNode or TabNode");
        }
        if(!(expression instanceof EvaluableNode)){
            throw new ASTBuildException("AffectationNode cannot have non-evaluable nodes");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jjcodes = new ArrayList<>();

        if (identifier instanceof TabNode) {
            TabNode tabNode = (TabNode) identifier;
            jjcodes.addAll(tabNode.getChildren().get(1).compile(address));
            jjcodes.addAll(expression.compile(address));
            jjcodes.add("astore(" + ((IdentNode)tabNode.getChildren().get(0)).identifier + ")");
        } else {
            jjcodes.addAll(expression.compile(address));
            jjcodes.add("store(" + ((IdentNode)identifier).identifier + ")");
        }

        return jjcodes;
    }


    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException, ASTInvalidOperationException {
        if (identifier instanceof TabNode) {
            TabNode tabNode = (TabNode) identifier;
            IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
            ASTNode indexExp = tabNode.getChildren().get(1);
            Value indexVal = ((EvaluableNode) indexExp).eval(m);
            if (indexVal.type != fr.ufrst.m1info.pvm.group5.memory.ValueType.INT) {
                throw new ASTInvalidDynamicTypeException("Array index must be an integer");
            }
            int index = indexVal.valueInt;
            Value value = ((EvaluableNode) expression).eval(m);
            m.affectValT(arrayIdent.identifier, index, value);
        } else {
            Value v = ((EvaluableNode) expression).eval(m);
            String id = ((IdentNode) identifier).identifier;
            if (m.isArray(id) && (!(expression instanceof IdentNode)|| !m.isArray(((IdentNode) expression).identifier))){
                throw new ASTInvalidOperationException("Line "+ getLine() +" : Value cannot be affected into array");
            }
            if (!m.isArray(id) && expression instanceof IdentNode && m.isArray(((IdentNode) expression).identifier)){
                throw new ASTInvalidOperationException("Line "+ getLine() +" : Array cannot be affected into value");
            }
            m.affectValue(id, v);
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        String exprType = expression.checkType(m);
        try {

            if (identifier instanceof TabNode) {
                TabNode tabNode = (TabNode) identifier;
                IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
                ASTNode indexExp = tabNode.getChildren().get(1);
                String indexType = indexExp.checkType(m);
                if (!"int".equals(indexType)) {
                    throw new ASTInvalidDynamicTypeException(
                            "AffectationNode: array index must be of type int, got " + indexType
                    );
                }
                DataType arrayDataType = m.tabType(arrayIdent.identifier);
                String arrayTypeStr;
                if (arrayDataType == DataType.INT) arrayTypeStr = "int";
                else if (arrayDataType == DataType.BOOL) arrayTypeStr = "bool";
                else throw new ASTInvalidDynamicTypeException(
                            "AffectationNode: array type not supported for " + arrayIdent.identifier
                    );
                if (!exprType.equals(arrayTypeStr)) {
                    throw new ASTInvalidDynamicTypeException(
                            "AffectationNode: type of expression (" + exprType +
                                    ") incompatible with array type (" + arrayTypeStr + ")"
                    );
                }
            } else {
                DataType varDataType = m.dataTypeOf(((IdentNode) identifier).identifier);
                String varTypeStr;
                if (varDataType == DataType.INT) varTypeStr = "int";
                else if (varDataType == DataType.BOOL) varTypeStr = "bool";
                else throw new ASTInvalidDynamicTypeException(
                            "AffectationNode: variable type not supported for " + ((IdentNode) identifier).identifier
                    );

                if (!exprType.equals(varTypeStr)) {
                    throw new ASTInvalidDynamicTypeException(
                            "AffectationNode: type of expression (" + exprType +
                                    ") incompatible with the type of the variable (" + varTypeStr + ")"
                    );
                }
            }
        } catch (IllegalArgumentException e) {
            String identName = identifier instanceof TabNode
                    ? ((IdentNode) ((TabNode) identifier).getChildren().get(0)).identifier
                    : ((IdentNode) identifier).identifier;
            throw new ASTInvalidMemoryException(
                    "AffectationNode: variable " + identName + " not defined"
            );
        }

        return "void";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(identifier,expression);
    }


}
