package fr.ufrst.m1info.gl.compGL.Nodes;

import fr.ufrst.m1info.gl.compGL.EvaluableNode;
import fr.ufrst.m1info.gl.compGL.Memory;
import fr.ufrst.m1info.gl.compGL.Value;

import java.util.ArrayList;
import java.util.List;

public class NumberNode extends ASTNode implements EvaluableNode {
    int number;

    public NumberNode(int number){
        this.number = number;
    }

    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.add("push(" + this.number + ")");
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) throws Exception {
        throw new Exception ("Can not interpret number node");
    }

    @Override
    public Value eval(Memory m) throws Exception {
        return new Value(this.number);
    }
}
