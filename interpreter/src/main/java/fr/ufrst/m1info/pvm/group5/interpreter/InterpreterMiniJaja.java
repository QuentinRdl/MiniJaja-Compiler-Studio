package fr.ufrst.m1info.pvm.group5.interpreter;


import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.AbstractSyntaxTree;

public class InterpreterMiniJaja implements Interpreter{
    /**
     * Interpret the given code
     *
     * @param code the code we want to interpret
     * @return the error message
     */
    @Override
    public String interpretCode(String code) {
        Memory mem = new Memory();
        AbstractSyntaxTree ast = AbstractSyntaxTree.fromString(code);
        String errMessage= null;
        try{
            ast.interpret(mem);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
        }
        return errMessage;
    }

    /**
     * Interpret the given file
     *
     * @param path the path of the file we want to interpret
     * @return the error message
     */
    @Override
    public String interpretFile(String path)  {
        Memory mem = new Memory();
        String errMessage= null;
        try{
            AbstractSyntaxTree ast = AbstractSyntaxTree.fromFile(path);
            ast.interpret(mem);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
        }
        return errMessage;
    }
}
