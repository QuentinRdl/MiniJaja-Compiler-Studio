package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.ArrayList;
import java.util.List;

public class WhileNode extends ASTNode{
    ASTNode condition;
    ASTNode iss;

    public WhileNode(ASTNode condition, ASTNode iss){
        this.condition = condition;
        this.iss = iss;
        if(this.condition == null){
            throw new ASTBuildException("While", "condition", "While node must have a non-null condition");
        }
        if(!(condition instanceof EvaluableNode)){
            throw new ASTBuildException("While", "condition", "While node must have an evaluable condition");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> jjcodes = new ArrayList<>();
        // Compiling sub-instructions
        List<String> pe = condition.compile(address);
        List<String> piss = (iss == null)?List.of() : iss.compile(address + pe.size() + 2);
        // Node compilation
        jjcodes.addAll(pe);
        jjcodes.add("not");
        jjcodes.add("if(" + (address + pe.size() + piss.size() + 3) +")");
        jjcodes.addAll(piss);
        jjcodes.add("goto("+address+")");
        return jjcodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException, ASTInvalidOperationException {
        Value e = ((EvaluableNode)condition).eval(m);
        while(e.valueBool){ // Rule [tantquevrai]
            if(iss != null)
                iss.interpret(m);
            e = ((EvaluableNode)condition).eval(m);
        }
    }

    @Override
    public String checkType(Memory m) throws InterpretationInvalidTypeException {
        String condType = condition.checkType(m);
        if (!condType.equals("bool")) {
            throw new InterpretationInvalidTypeException(this, "bool", condType);
        }
        if (iss != null) {
            iss.checkType(m);
        }
        return "void";
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(condition);
        if(iss != null)
            children.add(iss);
        return children;
    }

    public String toString(){return "while";}
}
