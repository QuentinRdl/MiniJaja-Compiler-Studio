package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.EvaluableNode;
import fr.ufrst.m1info.pvm.group5.Memory;
import fr.ufrst.m1info.pvm.group5.Value;
import fr.ufrst.m1info.pvm.group5.WithradawableNode;

import java.util.ArrayList;
import java.util.List;

public class VariableNode extends ASTNode implements WithradawableNode {
    TypeNode typemeth;
    ASTNode ident;
    ASTNode vexp;

    public VariableNode(TypeNode typemeth, ASTNode ident, ASTNode vexp){
        this.typemeth=typemeth;
        this.ident=ident;
        this.vexp=vexp;
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.addAll(vexp.compile(address));
        jajacodes.add("new(" + ident + "," + typemeth + ",var,0)" );
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        Value v = ((EvaluableNode)vexp).eval(m);
        m.DeclVar();
    }

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
