package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.JajaCodeLexer;
import fr.ufrst.m1info.pvm.group5.JajaCodeParser;
import fr.ufrst.m1info.pvm.group5.ast.Instructions.Instruction;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.IOException;
import java.util.List;

public class Jajacode {


    /**
     * Parse a JajaCode input stream and generate a list of instructions.
     *
     * @param in input file stream
     *
     * @return list of instructions
     *
     * @throws ParseCancellationException throws exceptions if the input file is invalid
     */
    private static List<Instruction> fromCharStream(CharStream in) throws ParseCancellationException {
        JajaCodeLexer lexer = new JajaCodeLexer(in);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ExceptionErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JajaCodeParser parser = new JajaCodeParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ExceptionErrorListener.INSTANCE);

        return parser.eval().instrs;
    }

    /**
     * Parse a JajaCode source string and generate a list of instructions.
     *
     * @param input JajaCode source code to parse
     *
     * @return list of parsed instructions
     */
    public static List<Instruction> fromString(String input){
        CharStream in = CharStreams.fromString(input);
        return fromCharStream(in);
    }

    /**
     * Parse a JajaCode file and generate a list of instructions.
     *
     * @param filePath path to an existing file containing JajaCode source code.
     *
     * @return list of parsed instructions
     *
     * @throws IOException if an error occurs while reading the file
     */
    public static List<Instruction> fromFile(String filePath) throws IOException {
        CharStream in = CharStreams.fromFileName(filePath);
        return fromCharStream(in);
    }
}
