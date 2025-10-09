package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Memory;
import fr.ufrst.m1info.gl.compGL.Value;

import java.util.ArrayList;
import java.util.List;

public class BooleanNode extends ASTNode implements EvaluableNode {
    boolean bool;

    public BooleanNode(boolean bool){
        this.bool = bool;
    }
    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        if (this.bool){
            jajacodes.add("push(jcvrai)");
        } else {
            jajacodes.add("push(jcvrai)");
        }
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        throw new Exception ("Can not interpret boolean node");
    }

    @Override
    public Value eval(Memory m) throws Exception {
        return new Value(this.bool);
    }
}
