package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.Memory;
import fr.ufrst.m1info.pvm.group5.WithradawableNode;

import java.util.ArrayList;
import java.util.List;

public class FinalNode extends ASTNode implements WithradawableNode {
    TypeNode type;
    ASTNode ident;
    ASTNode expression;

    public FinalNode(TypeNode type, ASTNode ident, ASTNode expression){
        this.type = type;
        this.ident = ident;
        this.expression = expression;
    }


    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.addAll(expression.compile(address));
        jajacodes.add("new(" + ident + "," + type + ",cst,0)");
        return jajacodes;
    }

    //TODO : do this when we have access to the memory
    @Override
    public void interpret(Memory m) throws Exception {

    }

    //TODO : do this when we have access to the memory
    @Override
    public void WithradawInterpret(Memory m) {

    }

    @Override
    public List<String> WithdrawCompile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.add("swap");
        jajacodes.add("pop");
        return jajacodes;
    }
}
