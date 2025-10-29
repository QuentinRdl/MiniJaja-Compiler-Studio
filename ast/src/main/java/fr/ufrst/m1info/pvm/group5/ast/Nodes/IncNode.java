package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;

public class IncNode extends ASTNode{
    IdentNode ident;

    public IncNode(IdentNode ident){
        this.ident = ident;
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.add("push(1)");
        JJCodes.add("load("+ident.identifier+")");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException {
        Value v = (Value)m.val(ident.identifier);
        if(v == null){
            throw new ASTInvalidMemoryException("Variable " + ident + " is undefined");
        }
        Value res = new Value(v.valueInt + 1);
        m.affectValue(ident.identifier, res);
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        try {
            Value v = (Value) m.val(ident.identifier);

            if (v == null) {
                throw new ASTInvalidDynamicTypeException(
                        "Variable " + ident.identifier + " not defined for increment"
                );
            }
            DataType dt = ValueType.toDataType(v.Type);
            if (dt != DataType.INT) {
                throw new ASTInvalidDynamicTypeException(
                        "Cannot increment : " + ident.identifier + " is not an integer"
                );
            }

            return "int";

        } catch (ASTInvalidMemoryException e) {
            throw new ASTInvalidDynamicTypeException(
                    "Error accessing variable " + ident.identifier + " : " + e.getMessage()
            );
        } catch (Exception e) {
            throw new ASTInvalidDynamicTypeException(
                    "Unknown error while checkingType of " + ident.identifier + " : " + e.getMessage()
            );
        }
    }

}