package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.ASTBuildException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.Memory;
import fr.ufrst.m1info.pvm.group5.WithradawableNode;

import java.util.ArrayList;
import java.util.List;

public class VariablesNode extends ASTNode implements WithradawableNode {
    ASTNode variable;
    ASTNode variables;

    public VariablesNode(ASTNode variable, ASTNode variables){
        this.variable=variable;
        this.variables=variables;
        if(this.variable==null){
            throw new ASTBuildException("Invalid variable declaration");
        }
        if(!(this.variable instanceof WithradawableNode)){
            throw new ASTBuildException("Variable declarations must be withdrawable");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
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
    public void withradawInterpret(Memory m) {
        if(variables != null)
            ((WithradawableNode)variables).withradawInterpret(m);
        ((WithradawableNode)variable).withradawInterpret(m);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        if(variables != null)
            jajacodes.addAll(((WithradawableNode)variables).withdrawCompile(address));
        jajacodes.addAll(((WithradawableNode)variable).withdrawCompile(address + jajacodes.size()));
        return jajacodes;
    }
}
