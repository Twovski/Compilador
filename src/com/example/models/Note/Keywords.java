package com.example.models.Note;

import javafx.concurrent.Task;
import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;
import java.util.regex.Pattern;

public interface Keywords {
    String[] KEYWORDS = new String[] {
            "if", "else", "else if", "while",
            "int", "boolean", "AND", "OR", "NOT"
    };
    String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    String OPERATORS_PATTERN = "[><=+\\-/*!|&]";
    String PAREN_PATTERN = "[()]";
    String BRACE_PATTERN = "[{}]";
    String SEMICOLON_PATTERN = ";";
    String NUMBER_PATTERN = "\\b(\\d+)";
    String BOOLEAN_PATTERN = "(\\btrue\\b|\\bfalse\\b)";
    Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<OPERATOR>" + OPERATORS_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<BOOLEAN>" + BOOLEAN_PATTERN + ")"
                    + "|(?<NUMBER>" + NUMBER_PATTERN + ")"
    );

    Task<StyleSpans<Collection<String>>> computeHighlightingAsync();
    void applyHighlighting(StyleSpans<Collection<String>> highlighting);
    StyleSpans<Collection<String>> computeHighlighting(String text);
}
