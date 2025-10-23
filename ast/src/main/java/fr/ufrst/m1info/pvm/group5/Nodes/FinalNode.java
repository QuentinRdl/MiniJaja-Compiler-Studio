package fr.ufrst.m1info.pvm.group5.Nodes;

import fr.ufrst.m1info.pvm.group5.*;


import java.util.ArrayList;
import java.util.List;

public class FinalNode extends ASTNode implements WithradawableNode {
    TypeNode type;
    IdentNode ident;
    ASTNode expression;

    public FinalNode(TypeNode type, IdentNode ident, ASTNode expression){
        this.type = type;
        this.ident = ident;
        this.expression = expression;
        if(this.type == null){
            throw new ASTBuildException("Final nodes must have a type");
        }
        if(this.ident == null){
            throw new ASTBuildException("Final nodes must have a identifier");
        }
    }


    @Override
    public List<String> compile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.addAll(expression.compile(address));
        jajacodes.add("new(" + ident + "," + type + ",cst,0)");
        return jajacodes;
    }

    @Override
    public void interpret(Memory m) {
        Value v = ((EvaluableNode)expression).eval(m);
        m.declCst(ident.identifier, v, ValueType.toDataType(type.valueType));
    }

    @Override
    public String checkType() throws ASTInvalidDynamicTypeException {
        String exprType = expression.checkType();

        String declaredType;
        switch (type.valueType) {
            case INT -> declaredType = "int";
            case BOOL -> declaredType = "bool";
            default -> throw new ASTInvalidDynamicTypeException(
                    "Unsupported type for constant " + ident.identifier
            );
        }

        if (!exprType.equals(declaredType)) {
            throw new ASTInvalidDynamicTypeException(
                    "Type of expression (" + exprType +
                            ") does not match the declared type (" + declaredType +
                            ") for the variable " + ident.identifier
            );
        }

        return "void";
    }


    @Override
    public void withradawInterpret(Memory m) {
        m.withdrawDecl(ident.identifier);
    }

    @Override
    public List<String> withdrawCompile(int address) {
        List<String> jajacodes = new ArrayList<String>();
        jajacodes.add("swap");
        jajacodes.add("pop");
        return jajacodes;
    }
}
