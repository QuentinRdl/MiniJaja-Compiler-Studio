package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.EvaluableNode;
import fr.ufrst.m1info.pvm.group5.Memory;
import fr.ufrst.m1info.pvm.group5.Value;

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
    public void interpret(Memory m) throws ASTInvalidOperationException {
        throw new ASTInvalidOperationException("Cannot interpret Boolean node");
    }

    @Override
    public Value eval(Memory m) {
        return new Value(this.bool);
    }
}
