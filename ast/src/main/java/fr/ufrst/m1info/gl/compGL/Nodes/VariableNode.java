package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Memory.Memory;
import fr.ufrst.m1info.gl.compGL.Value;
import fr.ufrst.m1info.gl.compGL.ValueType;
import fr.ufrst.m1info.gl.compGL.WithradawableNode;

import java.util.ArrayList;
import java.util.List;

public class VariableNode extends ASTNode implements WithradawableNode {
    TypeNode typemeth;
    IdentNode ident;
    ASTNode vexp;

    public VariableNode(TypeNode typemeth, IdentNode ident, ASTNode vexp){
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
        if(vexp == null){
            m.declVar(ident.identifier, null, ValueType.toDataType(typemeth.valueType));
        }
        else {
            Value v = ((EvaluableNode) vexp).eval(m);
            m.declVar(ident.identifier, v, ValueType.toDataType(typemeth.valueType));
        }
    }

    @Override
    public void WithradawInterpret(Memory m) {
        m.withdrawDecl(ident.identifier);
    }

    @Override
    public List<String> WithdrawCompile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.add("swap");
        jajacodes.add("pop");
        return jajacodes;
    }
}
