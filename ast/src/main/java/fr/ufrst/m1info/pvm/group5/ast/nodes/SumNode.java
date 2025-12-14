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
            throw new ASTBuildException("Sum", "identifier", "Sum node must have a non-null identifier");
        }
        if (!(this.identifier instanceof IdentNode) && !(this.identifier instanceof TabNode)) {
            throw new ASTBuildException("Sum", "identifier", "Sum node identifier must be either an array or a variable");
        }
        if (this.expression == null) {
            throw new ASTBuildException("Sum", "expression","Sum operand cannot be null");
        } else if (!(this.expression instanceof EvaluableNode)) {
            throw new ASTBuildException("Sum", "expression","Sum operand must be evaluable");
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
            int index = indexVal.valueInt;
            Value v = ((EvaluableNode) expression).eval(m);
            Value currentVal = ((TabNode) identifier).eval(m);
            int res = currentVal.valueInt + v.valueInt;
            m.affectValT(arrayIdent.identifier, index, new Value(res));
        } else {
            IdentNode identNode = (IdentNode) identifier;
            Value v = ((EvaluableNode) expression).eval(m);
            Value u = ((IdentNode) identifier).eval(m);
            int res = u.valueInt + v.valueInt;
            m.affectValue(identNode.identifier, new Value(res));
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        String exprType = expression.checkType(m);
        if (!exprType.equals("int")) {
            throw new ASTInvalidDynamicTypeException(this.getLine(), "int", exprType, "sum");
        }

        if (identifier instanceof TabNode) {
            TabNode tabNode = (TabNode) identifier;
            IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
            if (!m.contains(arrayIdent.identifier)) {
                throw ASTInvalidMemoryException.UndefinedVariable(arrayIdent.identifier, this.getLine());
            }
            identifier.checkType(m);
        } else {
            IdentNode identNode = (IdentNode) identifier;
            DataType dataType = m.dataTypeOf(identNode.identifier);

            if (dataType != DataType.INT) {
                throw new ASTInvalidDynamicTypeException(this.getLine(), "int", dataType.name(), "sum");
            }
        }

        return "int";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(identifier, expression);
    }
}
