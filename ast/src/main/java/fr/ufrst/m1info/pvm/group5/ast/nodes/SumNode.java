package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;

public class SumNode extends ASTNode {
    ASTNode identifier;
    ASTNode expression;

    public SumNode(ASTNode identifier, ASTNode expression) {
        this.identifier = identifier;
        this.expression = expression;
        if (this.identifier == null) {
            throw new ASTBuildException("Sum identifier cannot be null");
        }
        if (!(this.identifier instanceof IdentNode) && !(this.identifier instanceof TabNode)) {
            throw new ASTBuildException("Sum identifier must be IdentNode or TabNode");
        }
        if (this.expression == null) {
            throw new ASTBuildException("Sum operand cannot be null");
        } else if (!(this.expression instanceof EvaluableNode)) {
            throw new ASTBuildException("Sum operand must be evaluable");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jjcodes = new ArrayList<>();

        if (identifier instanceof TabNode) {
            TabNode tabNode = (TabNode) identifier;
            IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
            ASTNode indexExp = tabNode.getChildren().get(1);
            jjcodes.addAll(indexExp.compile(address));
            jjcodes.addAll(expression.compile(address));
            jjcodes.add("ainc(" + arrayIdent.identifier + ")");
        } else {
            jjcodes.addAll(expression.compile(address));
            jjcodes.add("inc(" + ((IdentNode) identifier).identifier + ")");
        }

        return jjcodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException {
        if (identifier instanceof TabNode) {
            TabNode tabNode = (TabNode) identifier;
            IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
            ASTNode indexExp = tabNode.getChildren().get(1);
            Value indexVal = ((EvaluableNode) indexExp).eval(m);
            if (indexVal.type != fr.ufrst.m1info.pvm.group5.memory.ValueType.INT) {
                throw new ASTInvalidDynamicTypeException("Array index must be an integer");
            }
            int index = indexVal.valueInt;
            Value v = ((EvaluableNode) expression).eval(m);
            if (v.type != fr.ufrst.m1info.pvm.group5.memory.ValueType.INT) {
                throw new ASTInvalidDynamicTypeException("Sum operand must be an integer");
            }
            Value currentVal = m.valT(arrayIdent.identifier, index);
            if (currentVal == null) {
                throw new ASTInvalidMemoryException("Array " + arrayIdent.identifier + " at index " + index + " not found in memory");
            }
            int res = currentVal.valueInt + v.valueInt;
            m.affectValT(arrayIdent.identifier, index, new Value(res));
        } else {
            IdentNode identNode = (IdentNode) identifier;
            if (m.isArray(identNode.identifier)){
                throw new ASTInvalidOperationException("Line "+ getLine() +" : Sum operation cannot be used on array.");
            }
            if (expression instanceof IdentNode iNode && m.isArray(iNode.identifier)){
                throw new ASTInvalidOperationException("Line "+ getLine() +" : Sum operation cannot be used with an array.");
            }
            Value v = ((EvaluableNode) expression).eval(m);
            Value u = (Value) m.val(identNode.identifier);
            if (u == null) {
                throw new ASTInvalidMemoryException("Variable " + identNode.identifier + " not found in memory");
            }
            int res = u.valueInt + v.valueInt;
            m.affectValue(identNode.identifier, new Value(res));
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        try {
            String exprType = expression.checkType(m);
            if (!exprType.equals("int")) {
                throw new ASTInvalidDynamicTypeException(
                        "The operand of SumNode must be of type int, found: " + exprType
                );
            }

            if (identifier instanceof TabNode) {
                TabNode tabNode = (TabNode) identifier;
                IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
                ASTNode indexExp = tabNode.getChildren().get(1);
                if (!m.contains(arrayIdent.identifier)) {
                    throw new ASTInvalidDynamicTypeException(
                            "Cannot sum: " + arrayIdent.identifier + " is not declared"
                    );
                }
                int tabLen = m.tabLength(arrayIdent.identifier);
                if (tabLen < 0) {
                    throw new ASTInvalidDynamicTypeException(
                            "Cannot sum: " + arrayIdent.identifier + " is not an array"
                    );
                }
                String indexType = indexExp.checkType(m);
                if (!"int".equals(indexType)) {
                    throw new ASTInvalidDynamicTypeException(
                            "Cannot sum: array index must be of type int, got " + indexType
                    );
                }
            } else {
                IdentNode identNode = (IdentNode) identifier;
                DataType dataType = m.dataTypeOf(identNode.identifier);

                if (dataType != DataType.INT) {
                    throw new ASTInvalidDynamicTypeException(
                            "SumNode impossible : variable " + identNode.identifier + " is not an int"
                    );
                }
            }

            return "int";

        } catch (ASTInvalidMemoryException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new ASTInvalidMemoryException(
                    "Memory error while checking type: " + e.getMessage()
            );
        } catch (Exception e) {
            throw new ASTInvalidDynamicTypeException(
                    "Unknown error while checking type: " + e.getMessage()
            );
        }
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(identifier, expression);
    }
}
