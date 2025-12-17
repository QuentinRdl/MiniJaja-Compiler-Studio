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

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) throws ParseCancellationException {
        throw new ParseCancellationException(String.format("Error while parsing file : %s (at line %d:%d)", msg, line, charPositionInLine));
    }
}
