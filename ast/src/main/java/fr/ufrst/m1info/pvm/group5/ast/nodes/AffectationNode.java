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
            throw new ASTBuildException("=",(identifier==null)?"identifier":"expression", "affectation cannot have null children");
        }
        if(!(identifier instanceof IdentNode) && !(identifier instanceof TabNode)){
            throw new ASTBuildException("=","identifier", "affectation identifier must be a variable or an array");
        }
        if(!(expression instanceof EvaluableNode)){
            throw new ASTBuildException("=","expression", "affectation's expression must be evaluable");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jjcodes = new ArrayList<>();

        if (identifier instanceof TabNode tabNode) {
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
        if (identifier instanceof TabNode tabNode) {
            IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
            ASTNode indexExp = tabNode.getChildren().get(1);
            Value indexVal = ((EvaluableNode) indexExp).eval(m);
            int index = indexVal.valueInt;
            Value value = ((EvaluableNode) expression).eval(m);
            MemoryCallUtil.safeCall(() -> m.affectValT(arrayIdent.identifier, index, value), this);
        } else {
            Value v = ((EvaluableNode) expression).eval(m);
            String id = ((IdentNode) identifier).identifier;
            MemoryCallUtil.safeCall(() -> m.affectValue(id, v), this);
        }
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        String exprType = expression.checkType(m);
        if (identifier instanceof TabNode tabNode) {
            IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
            ASTNode indexExp = tabNode.getChildren().get(1);
            String indexType = indexExp.checkType(m);
            if (!"int".equals(indexType)) {
                throw new InterpretationInvalidTypeException(this, "int", indexType);
            }
            DataType arrayDataType = MemoryCallUtil.safeCall(() -> m.tabType(arrayIdent.identifier), this);
            String arrayTypeStr;
            if (arrayDataType == DataType.INT) arrayTypeStr = "int";
            else if (arrayDataType == DataType.BOOL) arrayTypeStr = "bool";
            else throw new InterpretationInvalidTypeException(this, "[int,bool]", (arrayDataType==null)?"null":arrayDataType.toString());
            if (!exprType.equals(arrayTypeStr)) {
                throw new InterpretationInvalidTypeException(this, arrayTypeStr, exprType);
            }
        } else {
            DataType varDataType = MemoryCallUtil.safeCall(() -> m.dataTypeOf(((IdentNode) identifier).identifier), this);
            String varTypeStr;
            if (varDataType == DataType.INT) varTypeStr = "int";
            else if (varDataType == DataType.BOOL) varTypeStr = "bool";
            else throw new InterpretationInvalidTypeException(this, "[[int, bool]]", varDataType.toString());
            if(MemoryCallUtil.safeCall(() -> m.isArray(((IdentNode) identifier).identifier), this)){
                varTypeStr = "Array<"+varTypeStr+">";
            }
            if (!exprType.equals(varTypeStr)) {
                throw new InterpretationInvalidTypeException(this, varTypeStr, exprType);
            }
        }
        return "void";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(identifier,expression);
    }

    public String toString(){
        return "=";
    }
}
