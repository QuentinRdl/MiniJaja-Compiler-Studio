package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;

public class IncNode extends ASTNode{
    ASTNode ident;

    public IncNode(ASTNode ident){
        if(ident == null){
            throw new ASTBuildException("Inc", "ident", "Inc node identifier must not be null");
        }
        if(!(ident instanceof IdentNode) && !(ident instanceof TabNode)){
            throw new ASTBuildException("Inc", "ident", "Inc node identifier must be an Ident node or Tab node");
        }
        this.ident = ident;
    }

    @Override
    public List<String> compile(int address) {
        List<String> jjcodes = new ArrayList<>();

        if (ident instanceof TabNode) {
            TabNode tabNode = (TabNode) ident;
            IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
            ASTNode indexExp = tabNode.getChildren().get(1);
            jjcodes.addAll(indexExp.compile(address));
            jjcodes.add("push(1)");
            jjcodes.add("ainc(" + arrayIdent.identifier + ")");
        } else {
            jjcodes.add("push(1)");
            jjcodes.add("inc(" + ((IdentNode)ident).identifier + ")");
        }

        return jjcodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException {
        if (ident instanceof TabNode) {
            TabNode tabNode = (TabNode) ident;
            IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
            ASTNode indexExp = tabNode.getChildren().get(1);
            Value indexVal = ((EvaluableNode) indexExp).eval(m);
            int index = indexVal.valueInt;
            Value currentVal = m.valT(arrayIdent.identifier, index);
            if (currentVal == null || currentVal.type == ValueType.EMPTY) {
                throw ASTInvalidMemoryException.UndefinedVariable(arrayIdent.identifier, this.getLine());
            }
            Value newVal = new Value(currentVal.valueInt + 1);
            m.affectValT(arrayIdent.identifier, index, newVal);
        } else {
            String varName = ((IdentNode)ident).identifier;
            Value v = (Value) m.val(varName);
            if (v == null || v.type == fr.ufrst.m1info.pvm.group5.memory.ValueType.EMPTY) {
                throw ASTInvalidMemoryException.UndefinedVariable(varName, this.getLine());
            }
            Value res = new Value(v.valueInt + 1);
            m.affectValue(varName, res);
        }
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        if (ident instanceof TabNode) {
            TabNode tabNode = (TabNode) ident;
            IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
            ASTNode indexExp = tabNode.getChildren().get(1);
            if (!m.contains(arrayIdent.identifier)) {
                throw ASTInvalidMemoryException.UndefinedVariable(arrayIdent.identifier, this.getLine());
            }
            String indexType = indexExp.checkType(m);
            if (!"int".equals(indexType)) {
                throw new InterpretationInvalidTypeException(this.getLine(), "int", indexType, "inc");
            }
        } else {
            IdentNode identNode = (IdentNode) ident;
            DataType dataType = m.dataTypeOf(identNode.identifier);

            if (dataType != DataType.INT) {
                throw new InterpretationInvalidTypeException(this.getLine(), "int", dataType.name(), "inc");
            }
        }

        return "int";
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(ident);
    }

}