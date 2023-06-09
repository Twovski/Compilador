package com.example.Models;

import java.util.regex.Pattern;

public interface LexerRegex {
    String IDENTIFIER_PATTERN = "[a-zA-Z]\\w*";
    String FUNCTION_PATTERN = "\\bfunction\\b";
    String TYPE_VOID_PATTERN = "\\bvoid\\b";
    String LEFT_PAREN_PATTERN = "\\(";
    String RIGHT_PAREN_PATTERN = "\\)";
    String LEFT_BRACE_PATTERN = "\\{";
    String RIGHT_BRACE_PATTERN = "}";
    String SEMICOLON_PATTERN = ";";
    String COMMA_PATTERN = ",";
    String WHILE_PATTERN = "\\bwhile\\b";
    String IF_PATTERN= "\\bif\\b";
    String ELSE_PATTERN= "\\belse\\b";
    String ELSE_IF_PATTERN= ELSE_PATTERN + " " + IF_PATTERN;
    String TYPE_STRING_PATTERN = "\\bstring\\b";
    String STRING_PATTERN = "\"[^\"]*\"";
    String TYPE_FLOAT_PATTERN = "\\bfloat\\b";
    String FLOAT_PATTERN = "(-)?\\b(\\d+).(\\d+)";
    String TYPE_INTEGER_PATTERN = "\\bint\\b";
    String INTEGER_PATTERN = "(-)?\\b(\\d+)";
    String TYPE_BOOLEAN_PATTERN = "\\bboolean\\b";
    String BOOLEAN_PATTERN = "true|false";
    String INCREMENT_PATTERN = "\\+\\+";
    String DECREMENT_PATTERN = "--";
    String MINUS_EQUALS_PATTERN = "-=";
    String PLUS_EQUALS_PATTERN = "\\+=";
    String TIMES_EQUALS_PATTERN = "\\*=";
    String DIVIDE_EQUALS_PATTERN = "/=";
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
            IDENTIFIER_PATTERN, FUNCTION_PATTERN, LEFT_PAREN_PATTERN, RIGHT_PAREN_PATTERN,
            LEFT_BRACE_PATTERN, RIGHT_BRACE_PATTERN, SEMICOLON_PATTERN, COMMA_PATTERN,
            WHILE_PATTERN, IF_PATTERN, ELSE_PATTERN, ELSE_IF_PATTERN,
            TYPE_STRING_PATTERN, STRING_PATTERN, TYPE_FLOAT_PATTERN, FLOAT_PATTERN,
            TYPE_INTEGER_PATTERN, INTEGER_PATTERN, TYPE_BOOLEAN_PATTERN, BOOLEAN_PATTERN,
            INCREMENT_PATTERN, DECREMENT_PATTERN, MINUS_EQUALS_PATTERN, PLUS_EQUALS_PATTERN,
            TIMES_EQUALS_PATTERN, DIVIDE_EQUALS_PATTERN, GREATER_EQUAL_PATTERN, GREATER_THAN_PATTERN,
            LESS_THAN_PATTERN, LESS_EQUAL_PATTERN, NOT_EQUAL_PATTERN, EQUAL_EQUAL_PATTERN,
            MULTIPLY_PATTERN, PLUS_PATTERN, MINUS_PATTERN, DIVIDE_PATTERN,
            PERCENT_PATTERN, EQUAL_PATTERN, AND_PATTERN, OR_PATTERN, NOT_PATTERN, "\\s"
    };
    String UNKNOWN_PATTERN = "(?!.*\\b(" + String.join("|", ALL_REGEX) + ")\\b)[^\\s]+";

    Pattern PATTERN = Pattern.compile(
            "(?<FUNCTION>" + FUNCTION_PATTERN + ")"
                    + "|(?<LEFTBRACE>" + LEFT_BRACE_PATTERN + ")"
                    + "|(?<RIGHTBRACE>" + RIGHT_BRACE_PATTERN + ")"
                    + "|(?<LEFTPAREN>" + LEFT_PAREN_PATTERN + ")"
                    + "|(?<RIGHTPAREN>" + RIGHT_PAREN_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<COMMA>" + COMMA_PATTERN + ")"
                    + "|(?<WHILE>" + WHILE_PATTERN + ")"
                    + "|(?<IF>" + IF_PATTERN + ")"
                    + "|(?<ELSE>" + ELSE_PATTERN + ")"
                    + "|(?<ELSEIF>" + ELSE_IF_PATTERN + ")"
                    + "|(?<TYPEVOID>" + TYPE_VOID_PATTERN + ")"
                    + "|(?<TYPEBOOLEAN>" + TYPE_BOOLEAN_PATTERN + ")"
                    + "|(?<BOOLEAN>" + BOOLEAN_PATTERN + ")"
                    + "|(?<TYPESTRING>" + TYPE_STRING_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<TYPEFLOAT>" + TYPE_FLOAT_PATTERN + ")"
                    + "|(?<FLOAT>" + FLOAT_PATTERN + ")"
                    + "|(?<TYPEINTEGER>" + TYPE_INTEGER_PATTERN + ")"
                    + "|(?<INTEGER>" + INTEGER_PATTERN + ")"
                    + "|(?<AND>" + AND_PATTERN + ")"
                    + "|(?<OR>" + OR_PATTERN + ")"
                    + "|(?<NOT>" + NOT_PATTERN + ")"
                    + "|(?<IDENTIFIER>" + IDENTIFIER_PATTERN + ")"
                    + "|(?<INCREMENT>" + INCREMENT_PATTERN + ")"
                    + "|(?<DECREMENT>" + DECREMENT_PATTERN + ")"
                    + "|(?<MINUSEQUALS>" + MINUS_EQUALS_PATTERN + ")"
                    + "|(?<PLUSEQUALS>" + PLUS_EQUALS_PATTERN + ")"
                    + "|(?<TIMESEQUALS>" + TIMES_EQUALS_PATTERN + ")"
                    + "|(?<DIVIDEEQUALS>" + DIVIDE_EQUALS_PATTERN + ")"
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
