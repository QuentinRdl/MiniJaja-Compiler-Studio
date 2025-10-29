package fr.ufrst.m1info.pvm.group5.ast.Nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.ArrayList;
import java.util.List;

public class IfNode extends ASTNode{
    ASTNode condition;
    ASTNode instrThen;
    ASTNode instrElse;

    public IfNode(ASTNode condition, ASTNode instrThen, ASTNode instrElse) {
        this.condition = condition;
        this.instrThen = instrThen;
        this.instrElse = instrElse;
        if(condition == null){
            throw new ASTBuildException("If node must have a condition");
        }
        if(!(condition instanceof EvaluableNode)){
            throw new ASTBuildException("If node must have an evaluable condition");
        }
    }

    @Override
    public List<String> compile(int address) {
        List<String> JJCodes = new ArrayList<>();
        List<String> pe = condition.compile(address);
        List<String> ps1 = new ArrayList<>();
        List<String> ps = new ArrayList<>();
        if (instrElse != null){
            ps1 = instrElse.compile(address + pe.size() + 1);
        }
        if (instrThen != null){
            ps = instrThen.compile(address + pe.size() + ps1.size() + 2);
        }

        JJCodes.addAll(pe);
        JJCodes.add("if("+ (address + pe.size() + ps1.size() + 2) + ")");
        JJCodes.addAll(ps1);
        JJCodes.add("goto("+ (address + pe.size() + ps1.size() + ps.size() + 2) + ")");
        JJCodes.addAll(ps);

        return JJCodes;
    }

    @Override
    public void interpret(Memory m) throws ASTInvalidMemoryException, ASTInvalidOperationException {
        boolean exp = ((EvaluableNode)condition).eval(m).valueBool;
        if(exp){
            if(instrThen != null) {
                instrThen.interpret(m);
            }
        }
        else if(instrElse!=null){
            instrElse.interpret(m);
        }
    }

    @Override
    public String checkType(Memory m) throws ASTInvalidDynamicTypeException {
        String condType = condition.checkType(m);
        if (!condType.equals("bool")) {
            throw new ASTInvalidDynamicTypeException("The condition of an if must be of type bool");
        }
        if (instrThen != null) {
            instrThen.checkType(m);
        }
        if (instrElse != null) {
            instrElse.checkType(m);
        }
        return "void";
    }

}
