package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Memory;
import fr.ufrst.m1info.gl.compGL.Value;
import fr.ufrst.m1info.gl.compGL.WithradawableNode;

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
    public List<String> compile() {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.addAll(vexp.compile());
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
    public List<String> WithdrawCompile() {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.add("swap");
        jajacodes.add("pop");
        return jajacodes;
    }
}
