package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

import java.util.ArrayList;
import java.util.List;

public class IncNode extends ASTNode{
    IdentNode ident;

    public IncNode(IdentNode ident){
        if(ident == null){
            throw new ASTBuildException("IncNode cannot have a null identifier");
        }
        this.ident = ident;
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        JJCodes.add("push(1)");
        JJCodes.add("inc("+ident.identifier+")");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException {
        Value v = ident.eval(m);
        Value res = new Value(v.valueInt + 1);
        m.affectValue(ident.identifier, res);
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        try {
            DataType dataType = m.dataTypeOf(ident.identifier);

            if (dataType != DataType.INT) {
                throw new ASTInvalidDynamicTypeException(
                        "Cannot increment : " + ident.identifier + " is not an integer"
                );
            }

            return "int";

        } catch (ASTInvalidMemoryException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new ASTInvalidMemoryException(
                        "Memory error while checkingType of " + ident.identifier + " : " + e.getMessage()
            );
        } catch (Exception e) {
            throw new ASTInvalidDynamicTypeException(
                        "Unknown error while checkingType of " + ident.identifier + " : " + e.getMessage()
                );
            }
        }

    @Override
    protected List<ASTNode> getChildren() {
        return List.of(ident);
    }

}