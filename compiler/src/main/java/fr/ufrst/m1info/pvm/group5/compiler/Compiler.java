package fr.ufrst.m1info.pvm.group5.compiler;

import fr.ufrst.m1info.pvm.group5.ast.AbstractSyntaxTree;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Writer;

public class Compiler {
    Writer output;

    protected Compiler(){
        output = null;
    }

    public Compiler(Writer output) {
        this.output = output;
    }

    /**
     * Compile the given code
     *
     * @param code the code we want to compile
     * @return the compiled code or the error message if an exception is thrown
     */
    public String compileCode(String code) {
        String res;
        try{
            AbstractSyntaxTree ast = AbstractSyntaxTree.fromString(code);
            res=ast.compileAsString();
        } catch (Exception e) {
            res=e.getClass()+" : "+e.getMessage();
        }
        return res;
    }

    /**
     * Compile the given code
     *
     * @param path the path of the file we want to compile
     * @return the compiled code or the error message if an exception is thrown
     */
    public String compileFile(String path) {
        String res;
        try{
            AbstractSyntaxTree ast = AbstractSyntaxTree.fromFile(path);
            res=ast.compileAsString();
        } catch (Exception e) {
            res=e.getClass()+" : "+e.getMessage();
        }
        return res;
    }
}
