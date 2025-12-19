package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            MemoryCallUtil.safeCall(() -> m.affectValT(arrayIdent.identifier, index, new Value(res)), this);
        } else {
            IdentNode identNode = (IdentNode) identifier;
            Value v = ((EvaluableNode) expression).eval(m);
            Value u = ((IdentNode) identifier).eval(m);
            int res = u.valueInt + v.valueInt;
            MemoryCallUtil.safeCall(() -> m.affectValue(identNode.identifier, new Value(res)), this);
        }
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        String exprType = expression.checkType(m);
        if (!exprType.equals("int")) {
            throw new InterpretationInvalidTypeException(this, "int", exprType);
        }

        if (identifier instanceof TabNode) {
            String identType = identifier.checkType(m);
            if(!identType.equals("int")){
                throw new InterpretationInvalidTypeException(this, "int", exprType);
            }
        } else {
            IdentNode identNode = (IdentNode) identifier;
            String identType = identNode.checkType(m);
            if (!Objects.equals(identType, "int")) {
                throw new InterpretationInvalidTypeException(this, "int", identType);
            }
        }

        return "int";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(identifier, expression);
    }

    public String toString(){return "+=";}
}
