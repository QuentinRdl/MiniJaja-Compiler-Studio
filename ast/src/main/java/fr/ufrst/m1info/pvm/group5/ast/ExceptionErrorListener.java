package fr.ufrst.m1info.pvm.group5.ast;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

/**
 * ANTLR error listener that throws exceptions instead of printing to the console
 */
public class ExceptionErrorListener extends BaseErrorListener {

    public static final ExceptionErrorListener INSTANCE = new ExceptionErrorListener();

    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) throws ParseCancellationException {
        throw new ParseCancellationException("Error while parsing file : \n" +
                "line " + line + ":" + charPositionInLine + " " + msg);
    }
}
