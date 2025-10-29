package fr.ufrst.m1info.pvm.group5.ast.Nodes;

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
            throw new ASTBuildException("While node must have a condition");
        }
        if(!(condition instanceof EvaluableNode)){
            throw new ASTBuildException("While node condition must be evaluable");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        // Compiling sub-instructions
        List<String> pe = condition.compile(address);
        List<String> piss = (iss == null)?List.of() : iss.compile(address + pe.size() + 2);
        // Node compilation
        JJCodes.addAll(pe);
        JJCodes.add("not");
        JJCodes.add("if(" + (address + pe.size() + piss.size() + 3) +")");
        JJCodes.addAll(piss);
        JJCodes.add("goto("+address+")");
        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException, ASTInvalidOperationException {
        Value e = ((EvaluableNode)condition).eval(m);
        if(e.valueBool){ // Rule [tantquevrai]
            if(iss != null)
                iss.interpret(m);
            interpret(m);
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        String condType = condition.checkType(m);
        if (!condType.equals("bool")) {
            throw new ASTInvalidDynamicTypeException(
                    "While loop condition must be bool, found : " + condType
            );
        }
        if (iss != null) {
            iss.checkType(m);
        }
        return "void";
    }

}
