package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IdentNode extends ASTNode implements EvaluableNode {
    String identifier;

    public IdentNode(String identifier){

        this.identifier = identifier;
        if(this.identifier == null){
            throw new ASTBuildException("ident", "identifier", "ident identifier must not be null");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        jajacodes.add("load(" + this.identifier + ")");
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException{
        throw new ASTInvalidOperationException("interpret", this);
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        DataType dataType;
        try{
            dataType = m.dataTypeOf(identifier);
        } catch (Exception e) {
            throw ASTInvalidMemoryException.UndefinedVariable(identifier, this);
        }

        String stringDataType = switch (dataType) {
            case INT -> "int";
            case BOOL -> "bool";
            default ->
                    throw new InterpretationInvalidTypeException(this, "[int, bool]", dataType.toString());
        };

        if(MemoryCallUtil.safeCall(() -> m.isArray(this.identifier), this)){
            return "Array<"+stringDataType+">";
        }
        else {
            return stringDataType;
        }
    }

    @Override
    protected Map<String, String> getProperties(){
        return Map.ofEntries(Map.entry("identifier",identifier));
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of();
    }


    @Override
    public Value eval(Memory m) throws ASTInvalidMemoryException{
        Value v = (Value) MemoryCallUtil.safeCall(() -> m.val(identifier), this);
        if(v == null || v.type == ValueType.EMPTY){
            throw ASTInvalidMemoryException.UndefinedVariable(identifier, this);
        }
        return v;
    }

    public String toString(){return "variable:"+identifier;}
}
