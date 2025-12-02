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
            throw new ASTBuildException("IncNode cannot have a null identifier");
        }
        if(!(ident instanceof IdentNode) && !(ident instanceof TabNode)){
            throw new ASTBuildException("IncNode identifier must be IdentNode or TabNode");
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
            if (indexVal.type != fr.ufrst.m1info.pvm.group5.memory.ValueType.INT) {
                throw new ASTInvalidDynamicTypeException("Array index must be an integer");
            }
            int index = indexVal.valueInt;
            Value currentVal = m.valT(arrayIdent.identifier, index);
            if (currentVal == null || currentVal.type == ValueType.EMPTY) {
                throw new ASTInvalidMemoryException("Array " + arrayIdent.identifier + " at index " + index + " is not assigned a value");
            }
            Value newVal = new Value(currentVal.valueInt + 1);
            m.affectValT(arrayIdent.identifier, index, newVal);
        } else {
            String varName = ((IdentNode)ident).identifier;
            Value v = (Value) m.val(varName);
            if (v == null || v.type == fr.ufrst.m1info.pvm.group5.memory.ValueType.EMPTY) {
                throw new ASTInvalidMemoryException("Variable " + varName + " is not assigned a value");
            }
            Value res = new Value(v.valueInt + 1);
            m.affectValue(varName, res);
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        try {
            if (ident instanceof TabNode) {
                TabNode tabNode = (TabNode) ident;
                IdentNode arrayIdent = (IdentNode) tabNode.getChildren().get(0);
                ASTNode indexExp = tabNode.getChildren().get(1);
                if (!m.contains(arrayIdent.identifier)) {
                    throw new ASTInvalidDynamicTypeException(
                            "Cannot increment: " + arrayIdent.identifier + " is not declared"
                    );
                }
                int tabLen = m.tabLength(arrayIdent.identifier);
                if (tabLen < 0) {
                    throw new ASTInvalidDynamicTypeException(
                            "Cannot increment: " + arrayIdent.identifier + " is not an array"
                    );
                }
                String indexType = indexExp.checkType(m);
                if (!"int".equals(indexType)) {
                    throw new ASTInvalidDynamicTypeException(
                            "Cannot increment: array index must be of type int, got " + indexType
                    );
                }
            } else {
                IdentNode identNode = (IdentNode) ident;
                DataType dataType = m.dataTypeOf(identNode.identifier);

                if (dataType != DataType.INT) {
                    throw new ASTInvalidDynamicTypeException(
                            "Cannot increment: " + identNode.identifier + " is not an integer"
                    );
                }
            }

            return "int";

        } catch (ASTInvalidMemoryException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new ASTInvalidMemoryException(
                    "Memory error while checkingType: " + e.getMessage()
            );
        } catch (Exception e) {
            throw new ASTInvalidDynamicTypeException(
                    "Unknown error while checkingType: " + e.getMessage()
            );
        }
    }

    @Override
    protected List<ASTNode> getChildren() {
        return List.of(ident);
    }

}