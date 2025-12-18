package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.memory.Event;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.MiniJaJaLexer;
import fr.ufrst.m1info.pvm.group5.MiniJaJaParser;
import org.antlr.v4.runtime.*;
import fr.ufrst.m1info.pvm.group5.ast.nodes.ClassNode;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AbstractSyntaxTree {
    /**
     * Root of the tree used by this class
     */
    public ClassNode root;

    public Event<InterpretationStoppedData> interpretationStoppedEvent = new Event<>();

    private AbstractSyntaxTree() {
    }

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
     *
     * @return generated AST
     *
     * @throws ParseCancellationException throws exceptions if the input file is invalid
     */
    private static AbstractSyntaxTree fromCharStream(CharStream in) throws ParseCancellationException {
        MiniJaJaLexer lexer = new MiniJaJaLexer(in);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ExceptionErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniJaJaParser parser = new MiniJaJaParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ExceptionErrorListener.INSTANCE);

        AbstractSyntaxTree tree = new AbstractSyntaxTree();
        tree.root = parser.classe().node;

        tree.root.setAsRoot();
        tree.root.interpretationStoppedEvent().subscribe(d -> tree.interpretationStoppedEvent.trigger(d));
        return tree;
    }

    /**
     * Interprets the tree and returns the resulting memory.
     * @return memory resulting from the evaluation of the tree
     */
    public Memory interpret() throws Exception{
        return interpret(InterpretationMode.DIRECT);
    }

    /**
     * Interprets the tree in a given interpretation mode and returns the resulting memory.
     * @param mode interpretation mode used for the interpretation
     * @return memory resulting from the interpretation of the tree
     */
    public Memory interpret(InterpretationMode mode) throws Exception{
        root.setInterpretationMode(mode);
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
        return interpret(m, InterpretationMode.DIRECT);
    }

    /**
     * Interprets the tree from an existing memory, and returns the resulting memory.
     * The memory passed in parameter will be used for the evaluation, and therefor modified.
     * The returned object will be the same as the inputted one.
     * @param m memory before the interpretation
     * @param mode interpretation mode to use for the interpretation
     * @return memory after the interpretation
     */
    public Memory interpret(Memory m, InterpretationMode mode) throws Exception{
        root.setInterpretationMode(mode);
        root.checkType(new Memory());
        root.interpret(m);
        return m;
    }

    /**
     * Allows to change the interpretation mode of the AST, event during an interpretation
     * @param mode new interpretation mode
     */
    public void changeInterpretationMode(InterpretationMode mode){
        root.setInterpretationMode(mode);
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
     * Compiles the tree into JaJaCode and prints the result into a file, starting from a given address (line)
     * @param filePath path to the output file. The file must exist and have sufficient permission to write in it.
     */
    public void compileToFile(String filePath) throws IOException{
        BufferedWriter out = null;
        try {
            FileWriter fstream = new FileWriter(filePath+".jjc", false);
            out = new BufferedWriter(fstream);
            out.write(compileAsString());
            out.close();
        }
        catch (IOException e) {
            if(out != null) out.close();
            throw e;
        }
    }

    /**
     * Dumps the content of the tree in a JSON string
     * @return dump of the tree
     */
    public String dump(){
        return root.dump();
    }

    /**
     * Dumps the content of the tree as a JSON string in a file
     * @param filePath path to the output file. The file must exist and have sufficient permission to write to it.
     * @throws IOException can throw IOException in case of IO error.
     *                     Any file opened by the method is closed, even if this exception is thrown.
     */
    public void dumpToFile(String filePath) throws IOException{
        BufferedWriter out = null;
        try {
            FileWriter fstream = new FileWriter("out.txt", false);
            out = new BufferedWriter(fstream);
            out.write(dump());
            out.close();
        }
        catch (IOException e) {
            if(out != null) out.close();
            throw e;
        }
    }
}
