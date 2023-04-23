package com.example.Models;

import javafx.concurrent.Task;
import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;
import java.util.regex.Pattern;

public interface Keywords {
    String[] KEYWORDS = new String[] {
            "if", "else", "else if", "while",
            "void", "return",
            "int", "boolean", "float", "string",
            "AND", "OR", "NOT"
    };

    String[] OPERATORS = new String[] {
            "[><=+\\-/*!]",
            "\\>\\=", "\\<\\=", "\\!\\=", "\\=\\=",
            "\\+\\=", "\\-\\=", "\\*\\=", "\\/\\=",
            "\\&\\&", "\\|\\|"
    };

    String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";

    String OPERATORS_PATTERN = "(" + String.join("|", OPERATORS) + ")";
    String LETTER_FUNCTION_PATTERN = "\\b(function)\\b";
    String PAREN_PATTERN = "[()]";
    String BRACE_PATTERN = "[{}]";
    String BRACKET_PATTERN = "[\\[\\]]";
    String SEMICOLON_PATTERN = ";";
    String STRING_PATTERN = "\"[^\"]*\"";
    String NUMBER_PATTERN = "\\d+\\.?\\d*";
    String BOOLEAN_PATTERN = "(true|false)";
    Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<OPERATOR>" + OPERATORS_PATTERN + ")"
                    + "|(?<FUNCTION>" + LETTER_FUNCTION_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<BOOLEAN>" + BOOLEAN_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<NUMBER>" + NUMBER_PATTERN + ")"
    );

    Task<StyleSpans<Collection<String>>> computeHighlightingAsync();
    void applyHighlighting(StyleSpans<Collection<String>> highlighting);
    StyleSpans<Collection<String>> computeHighlighting(String text);
}
