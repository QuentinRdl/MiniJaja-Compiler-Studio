package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.Memory;

import java.util.ArrayList;
import java.util.List;

public class VariablesNode extends ASTNode{
    ASTNode variable;
    ASTNode variables;

    public VariablesNode(ASTNode variable, ASTNode variables){
        this.variable=variable;
        this.variables=variables;
    }

    @Override
    public List<String> compile() {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.addAll(variable.compile());
        if (variables != null){
            jajacodes.addAll(variables.compile());
        }
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        variable.interpret(m);
        variables.interpret(m);
    }
}
