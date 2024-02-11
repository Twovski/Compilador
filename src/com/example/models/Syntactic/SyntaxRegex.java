package com.example.models.Syntactic;

import java.util.regex.Pattern;

public interface SyntaxRegex {
    String LETTER = "[a-zA-Z]";
    String DIGITS = "[0-9]";
    String BOOLEAN = "\\btrue\\b|\\bfalse\\b";
    String NUMBERS = "(-)?\\b(" + DIGITS + "+)";
    String IDENTIFIER = "\\b(" + LETTER + "(" + "\\w" + ")*" + ")\\b";
    String DATA_TYPE = "int|boolean";
    String ARITHMETIC_OPERATORS = "[+\\-*/%]";
    String RELATIONAL_OPERATORS = "==|!=|>=|>|<=|<";
    String NUMBER_EXPRESSION = "(" + NUMBERS + "|" + IDENTIFIER + ")"
            + "\\s*(" + ARITHMETIC_OPERATORS + "\\s*(" + NUMBERS + "|" + IDENTIFIER + ")" + ")*";
    String BOOLEAN_EXPRESSION = "(NOT\\s+|!)*" + "(" + BOOLEAN + "|"
            + NUMBER_EXPRESSION + "(" + RELATIONAL_OPERATORS + ")" + NUMBER_EXPRESSION + "|"
            + IDENTIFIER + ")"
            + "((\\s+AND\\s+|\\s+OR\\s+|&&|\\|\\|)"
            + "(NOT\\s+|!)*" + "(" + BOOLEAN + "|"
            + NUMBER_EXPRESSION + "(" + RELATIONAL_OPERATORS + ")" + NUMBER_EXPRESSION + "|"
            + IDENTIFIER + "))*";
    String EXPRESSION = BOOLEAN_EXPRESSION + "|" + NUMBER_EXPRESSION;
    String SENTENCES = DATA_TYPE + "\\s+" + IDENTIFIER + "\\s*=\\s*" + EXPRESSION + "|"
            + IDENTIFIER + "\\s*=\\s*" + EXPRESSION;
    String SENTENCE_ELSE = "else" + "(<block>|<sentence-if>)";
    String SENTENCE_IF = "if\\((" + BOOLEAN_EXPRESSION + ")+\\)<block>\\s+(" + SENTENCE_ELSE + ")?" ;
    String PART_SENTENCE_WHILE = "while\\((" + BOOLEAN_EXPRESSION + ")+\\)(<block>|;)";

    String BLOCK = "";

    Pattern PATTERN = Pattern.compile("");
}
