package fr.ufrst.m1info.pvm.group5.driver;

import org.fxmisc.richtext.InlineCssTextArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Syntax highlighter for MiniJaja and JajaCode languages
 * Provides syntax highlighting by identifying keywords, types, operators, and literals
 */
public class SyntaxHighlighter {

    private SyntaxHighlighter() {
        throw new IllegalStateException("Utility class");
    }

    // MiniJaja keywords
    private static final String[] MINIJAJA_KEYWORDS = {
        "main", "class", "int", "boolean", "void", "if", "else", "while",
        "return", "true", "false", "final", "write", "writeln", "length"
    };

    // JajaCode instructions
    private static final String[] JAJACODE_KEYWORDS = {
        "init", "new", "newarray", "nop", "pop", "push", "aload", "astore", "dup", "load",
        "store", "inc", "neg", "cmp", "and", "not", "or", "goto", "if", "invoke", "return",
        "swap", "add", "sub", "mul", "div", "jcstop", "write", "writeln", "sup", "length", "ainc"
    };

    // Operators and special characters
    private static final String NUMBER_PATTERN = "\\b\\d+\\b";
    private static final String COMMENT_PATTERN = "//[^\n]*";

    // CSS styles for each syntax element
    private static final String KEYWORD_STYLE = "-fx-fill: #FFD270;";
    private static final String NUMBER_STYLE = "-fx-fill: #EDADC7;";
    private static final String COMMENT_STYLE = "-fx-fill: #86A17D; -fx-font-style: italic;";
    private static final String DEFAULT_STYLE = "-fx-fill: white;";

    /**
     * Applies syntax highlighting to an InlineCssTextArea
     *
     * @param textArea the InlineCssTextArea to apply highlighting to
     * @param text the text to highlight
     * @param isMiniJaja true if the text is MiniJaja code, false for JajaCode
     */
    public static void applySyntaxHighlighting(InlineCssTextArea textArea, String text, boolean isMiniJaja) {
        if (text == null || text.isEmpty()) {
            return;
        }

        // Clear all existing styles first
        textArea.setStyle(0, text.length(), DEFAULT_STYLE);

        String[] keywords = isMiniJaja ? MINIJAJA_KEYWORDS : JAJACODE_KEYWORDS;
        String keywordPattern = "\\b(" + String.join("|", keywords) + ")\\b";

        Pattern pattern = Pattern.compile(
            "(?<KEYWORD>" + keywordPattern + ")"
            + "|(?<NUMBER>" + NUMBER_PATTERN + ")"
            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
        );

        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String style = null;

            if (matcher.group("KEYWORD") != null) {
                style = KEYWORD_STYLE;
            } else if (matcher.group("NUMBER") != null) {
                style = NUMBER_STYLE;
            } else if (matcher.group("COMMENT") != null) {
                style = COMMENT_STYLE;
            }

            if (style != null) {
                textArea.setStyle(matcher.start(), matcher.end(), style);
            }
        }
    }
}
