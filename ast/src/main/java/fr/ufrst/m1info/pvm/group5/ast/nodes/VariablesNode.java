package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.ArrayList;
import java.util.List;

public class VariablesNode extends ASTNode implements WithdrawalNode {
    ASTNode variable;
    ASTNode variables;

    public VariablesNode(ASTNode variable, ASTNode variables){
        this.variable=variable;
        this.variables=variables;
        if(this.variable==null){
            throw new ASTBuildException("Invalid variable declaration");
        }
        if(!(this.variable instanceof WithdrawalNode)){
            throw new ASTBuildException("Variable declarations must be withdrawable");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<>();
        jajacodes.addAll(variable.compile(address));
        if (variables != null){
            jajacodes.addAll(variables.compile(address + jajacodes.size()));
        }
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException {
        variable.interpret(m);
        if(variables != null) {
            variables.interpret(m);
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        variable.checkType(m);
        if (variables != null) {
            variables.checkType(m);
        }
        return "void";
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(variable);
        if(variables!=null)
            children.add(variables);
        return children;
    }


    @Override
    public void withdrawInterpret(Memory m) {
        if(variables != null)
            ((WithdrawalNode)variables).withdrawInterpret(m);
        ((WithdrawalNode)variable).withdrawInterpret(m);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> jajacodes = new ArrayList<>();
        if(variables != null)
            jajacodes.addAll(((WithdrawalNode)variables).withdrawCompile(address));
        jajacodes.addAll(((WithdrawalNode)variable).withdrawCompile(address + jajacodes.size()));
        return jajacodes;
    }
}
