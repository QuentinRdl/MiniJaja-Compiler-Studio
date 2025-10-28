package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;


import java.util.ArrayList;
import java.util.List;

public class IdentNode extends ASTNode implements EvaluableNode {
    String identifier;

    public IdentNode(String identifier){

        this.identifier = identifier;
        if(this.identifier == null){
            throw new ASTBuildException("Ident node cannot have null identifier");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.add("load(" + this.identifier + ")");
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException{
        throw new ASTInvalidOperationException("Ident node cannot be interpreted");
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        try {
            Value v = (Value) m.val(identifier);
            if (v == null) {
                throw new ASTInvalidDynamicTypeException(
                        "Variable " + identifier + " not defined"
                );
            }
            DataType dataType = ValueType.toDataType(v.Type);

            switch (dataType) {
                case INT:
                    return "int";
                case BOOL:
                    return "bool";
                case VOID:
                    throw new ASTInvalidDynamicTypeException(
                            "Variable " + identifier + " cannot be of type void"
                    );
                default:
                    throw new ASTInvalidDynamicTypeException(
                            "Invalid type for variable " + identifier
                    );
            }

        } catch (Exception e) {
            throw new ASTInvalidDynamicTypeException(
                    "Error accessing variable " + identifier + " : " + e.getMessage()
            );
        }
    }





    @Override
    public Value eval(Memory m) throws ASTInvalidMemoryException{
        Value v = (Value) m.val(identifier);
        if(v == null){
            throw new ASTInvalidMemoryException("Variable " + identifier + " is undefined");
        }
        return v;
    }

}
