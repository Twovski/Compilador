package com.example.models.Scanner;

import java.util.regex.Pattern;

public interface LexerRegex {
    String IDENTIFIER_PATTERN = "\\b([a-zA-Z][\\w_]*)\\b";
    String LEFT_PAREN_PATTERN = "\\(";
    String RIGHT_PAREN_PATTERN = "\\)";
    String LEFT_BRACE_PATTERN = "\\{";
    String RIGHT_BRACE_PATTERN = "}";
    String SEMICOLON_PATTERN = ";";
    String WHILE_PATTERN = "\\bwhile\\b";
    String IF_PATTERN= "\\bif\\b";
    String ELSE_PATTERN= "\\belse\\b";
    String TYPE_INTEGER_PATTERN = "\\bint\\b";
    String INTEGER_PATTERN = "(-)?\\b(\\d+)";
    String TYPE_BOOLEAN_PATTERN = "\\bboolean\\b";
    String BOOLEAN_PATTERN = "\\btrue\\b|\\bfalse\\b";
    String GREATER_EQUAL_PATTERN = ">=";
    String GREATER_THAN_PATTERN = ">";
    String LESS_THAN_PATTERN = "<";
    String LESS_EQUAL_PATTERN = "<=";
    String NOT_EQUAL_PATTERN = "!=";
    String EQUAL_EQUAL_PATTERN = "==";
    String MULTIPLY_PATTERN = "\\*";
    String PLUS_PATTERN = "\\+";
    String MINUS_PATTERN = "-";
    String DIVIDE_PATTERN = "/";
    String PERCENT_PATTERN = "%";
    String EQUAL_PATTERN = "=";
    String AND_PATTERN = "&&|AND";
    String OR_PATTERN = "\\|\\||OR";
    String NOT_PATTERN = "!|NOT";
    String[] ALL_REGEX = {
            LEFT_PAREN_PATTERN, RIGHT_PAREN_PATTERN,
            LEFT_BRACE_PATTERN, RIGHT_BRACE_PATTERN, SEMICOLON_PATTERN,
            WHILE_PATTERN, IF_PATTERN, ELSE_PATTERN,
            TYPE_INTEGER_PATTERN, INTEGER_PATTERN, TYPE_BOOLEAN_PATTERN, BOOLEAN_PATTERN,
            IDENTIFIER_PATTERN, GREATER_EQUAL_PATTERN, GREATER_THAN_PATTERN,
            LESS_THAN_PATTERN, LESS_EQUAL_PATTERN, NOT_EQUAL_PATTERN, EQUAL_EQUAL_PATTERN,
            MULTIPLY_PATTERN, PLUS_PATTERN, MINUS_PATTERN, DIVIDE_PATTERN,
            PERCENT_PATTERN, EQUAL_PATTERN, AND_PATTERN, OR_PATTERN, NOT_PATTERN, "\\s"
    };
    String UNKNOWN_PATTERN = "(?!\\b(" + String.join("|", ALL_REGEX) + ")\\b)[\\S_]+";

    Pattern PATTERN = Pattern.compile(
            "(?<LEFTBRACE>" + LEFT_BRACE_PATTERN + ")"
                    + "|(?<RIGHTBRACE>" + RIGHT_BRACE_PATTERN + ")"
                    + "|(?<LEFTPAREN>" + LEFT_PAREN_PATTERN + ")"
                    + "|(?<RIGHTPAREN>" + RIGHT_PAREN_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<WHILE>" + WHILE_PATTERN + ")"
                    + "|(?<IF>" + IF_PATTERN + ")"
                    + "|(?<ELSE>" + ELSE_PATTERN + ")"
                    + "|(?<TYPEBOOLEAN>" + TYPE_BOOLEAN_PATTERN + ")"
                    + "|(?<BOOLEAN>" + BOOLEAN_PATTERN + ")"
                    + "|(?<TYPEINTEGER>" + TYPE_INTEGER_PATTERN + ")"
                    + "|(?<INTEGER>" + INTEGER_PATTERN + ")"
                    + "|(?<AND>" + AND_PATTERN + ")"
                    + "|(?<OR>" + OR_PATTERN + ")"
                    + "|(?<NOT>" + NOT_PATTERN + ")"
                    + "|(?<IDENTIFIER>" + IDENTIFIER_PATTERN + ")"
                    + "|(?<GREATEREQUAL>" + GREATER_EQUAL_PATTERN + ")"
                    + "|(?<GREATERTHAN>" + GREATER_THAN_PATTERN + ")"
                    + "|(?<LESSEQUAL>" + LESS_EQUAL_PATTERN + ")"
                    + "|(?<LESSTHAN>" + LESS_THAN_PATTERN + ")"
                    + "|(?<NOTEQUAL>" + NOT_EQUAL_PATTERN + ")"
                    + "|(?<EQUALEQUAL>" + EQUAL_EQUAL_PATTERN + ")"
                    + "|(?<MULTIPLY>" + MULTIPLY_PATTERN + ")"
                    + "|(?<PLUS>" + PLUS_PATTERN + ")"
                    + "|(?<MINUS>" + MINUS_PATTERN + ")"
                    + "|(?<DIVIDE>" + DIVIDE_PATTERN + ")"
                    + "|(?<PERCENT>" + PERCENT_PATTERN + ")"
                    + "|(?<EQUAL>" + EQUAL_PATTERN + ")"
                    + "|(?<UNKNOWN>" + UNKNOWN_PATTERN + ")"
    );
}
