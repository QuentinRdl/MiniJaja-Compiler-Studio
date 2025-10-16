package fr.ufrst.m1info.pvm.group5;

import fr.ufrst.m1info.pvm.group5.MiniJaJaLexer;
import fr.ufrst.m1info.pvm.group5.MiniJaJaParser;
import org.antlr.v4.runtime.*;
import fr.ufrst.m1info.pvm.group5.Nodes.ClassNode;

import java.io.IOException;
import java.util.List;

public class AbstractSyntaxTree {
    /**
     * Root of the tree used by this class
     */
    public ClassNode root;

    private AbstractSyntaxTree() {}

    /**
     * Generate a new AST from a text input.
     * The existing AST will be erased.
     * @param input : MiniJaJa code to generate a tree from
     */
    public static AbstractSyntaxTree fromString(String input){
        CharStream in = CharStreams.fromString(input);
        return fromCharStream(in);
    }

    /**
     * Generate a new AST from an existing file.
     * The existing AST will be erased.
     * @param filePath Path to an extisting file, containing miniJaJA code.
     *                 No check on the existence or the permissions of the file will be done in this function, so they have to be done from the caller
     */
    public static AbstractSyntaxTree fromFile(String filePath) throws IOException {
        CharStream in = CharStreams.fromFileName(filePath);
        return fromCharStream(in);
    }

    /**
     * Generate a new AST from an antlr charstream
     * Used as an utility for fromFile and fromString
     * @param in input file stream
     * @return generated AST
     */
    private static AbstractSyntaxTree fromCharStream(CharStream in){
        MiniJaJaLexer lexer = new MiniJaJaLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniJaJaParser parser = new MiniJaJaParser(tokens);
        AbstractSyntaxTree tree = new AbstractSyntaxTree();
        tree.root = parser.classe().node;
        return tree;
    }

    /**
     * Interprets the tree and returns the resulting memory.
     * @return memory resulting from the evaluation of the tree
     */
    public Memory interpret() throws Exception{
        Memory m = new Memory();
        interpret(m);
        return m;
    }

    /**
     * Interprets the tree from an existing memory, and returns the resulting memory.
     * The memory passed in parameter will be used for the evaluation, and therefor modified.
     * The returned object will be the same as the inputted one.
     * @param m memory before the interpretation
     * @return memory after the interpretation
     */
    public Memory interpret(Memory m) throws Exception{
        root.interpret(m);
        return m;
    }

    /**
     * Compiles the tree into JaJaCode.
     * @return string containing the compiled code
     */
    public String compileAsString(){
        List<String> compiledTree = compileAsList();
        return String.join("\n", compiledTree);
    }

    /**
     * Compiles the tree into a list of JaJaCode instructions.
     * @return list of instructions of the compiled code
     */
    public List<String> compileAsList(){
        return root.compile(1);
    }

    /**
     * Compiles the tree into JaJaCode and prints the result to a file.
     * @param filePath path to the output file. The file must exist and have sufficient permission to write in it.
     */
    public void compileToFile(String filePath){
        compileToFile(filePath, 1);
    }

    /**
     * Compiles the tree into JaJaCode and prints the result into a file, starting from a given address (line)
     * @param filePath path to the output file. The file must exist and have sufficient permission to write in it.
     * @param startingAddress address to start to write the code from
     */
    public void compileToFile(String filePath, int startingAddress){
        // TODO
    }
}
