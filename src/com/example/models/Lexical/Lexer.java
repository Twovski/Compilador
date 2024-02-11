package com.example.models.Lexical;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class Lexer implements LexerRegex{
    private final ArrayList<Token> tokenList;

    public Lexer() {
        this.tokenList = new ArrayList<>();
    }

    public void tokenize(String text){
        Matcher matcher = PATTERN.matcher(text);
        Token token;
        while(matcher.find()){
            if (matcher.group("IDENTIFIER") != null)
                token = new Token(TokenType.IDENTIFIER, matcher.group("IDENTIFIER"));
            else if (matcher.group("LEFTBRACE") != null)
                token = new Token(TokenType.LEFT_BRACE, matcher.group("LEFTBRACE"));
            else if (matcher.group("RIGHTBRACE") != null)
                token = new Token(TokenType.RIGHT_BRACE, matcher.group("RIGHTBRACE"));
            else if (matcher.group("LEFTPAREN") != null)
                token = new Token(TokenType.LEFT_PAREN, matcher.group("LEFTPAREN"));
            else if (matcher.group("RIGHTPAREN") != null)
                token = new Token(TokenType.RIGHT_PAREN, matcher.group("RIGHTPAREN"));
            else if (matcher.group("SEMICOLON") != null)
                token = new Token(TokenType.SEMICOLON, matcher.group("SEMICOLON"));
            else if (matcher.group("WHILE") != null)
                token = new Token(TokenType.WHILE, matcher.group("WHILE"));
            else if (matcher.group("IF") != null)
                token = new Token(TokenType.IF, matcher.group("IF"));
            else if (matcher.group("ELSE") != null)
                token = new Token(TokenType.ELSE, matcher.group("ELSE"));
            else if (matcher.group("TYPEBOOLEAN") != null)
                token = new Token(TokenType.TYPE_BOOLEAN, matcher.group("TYPEBOOLEAN"));
            else if (matcher.group("BOOLEAN") != null)
                token = new Token(TokenType.BOOLEAN, matcher.group("BOOLEAN"));
            else if (matcher.group("TYPEINTEGER") != null)
                token = new Token(TokenType.TYPE_INTEGER, matcher.group("TYPEINTEGER"));
            else if (matcher.group("INTEGER") != null)
                token = new Token(TokenType.INTEGER, matcher.group("INTEGER"));
            else if (matcher.group("GREATEREQUAL") != null)
                token = new Token(TokenType.GREATER_EQUAL, matcher.group("GREATEREQUAL"));
            else if (matcher.group("GREATERTHAN") != null)
                token = new Token(TokenType.GREATER_THAN, matcher.group("GREATERTHAN"));
            else if (matcher.group("LESSTHAN") != null)
                token = new Token(TokenType.LESS_THAN, matcher.group("LESSTHAN"));
            else if (matcher.group("LESSEQUAL") != null)
                token = new Token(TokenType.LESS_EQUAL, matcher.group("LESSEQUAL"));
            else if (matcher.group("NOTEQUAL") != null)
                token = new Token(TokenType.NOT_EQUAL, matcher.group("NOTEQUAL"));
            else if (matcher.group("EQUALEQUAL") != null)
                token = new Token(TokenType.EQUAL_EQUAL, matcher.group("EQUALEQUAL"));
            else if (matcher.group("AND") != null)
                token = new Token(TokenType.AND, matcher.group("AND"));
            else if (matcher.group("OR") != null)
                token = new Token(TokenType.OR, matcher.group("OR"));
            else if (matcher.group("NOT") != null)
                token = new Token(TokenType.NOT, matcher.group("NOT"));
            else if (matcher.group("MULTIPLY") != null)
                token = new Token(TokenType.MULTIPLY, matcher.group("MULTIPLY"));
            else if (matcher.group("PLUS") != null)
                token = new Token(TokenType.PLUS, matcher.group("PLUS"));
            else if (matcher.group("MINUS") != null)
                token = new Token(TokenType.MINUS, matcher.group("MINUS"));
            else if (matcher.group("DIVIDE") != null)
                token = new Token(TokenType.DIVIDE, matcher.group("DIVIDE"));
            else if (matcher.group("PERCENT") != null)
                token = new Token(TokenType.PERCENT, matcher.group("PERCENT"));
            else if (matcher.group("EQUAL") != null)
                token = new Token(TokenType.EQUAL, matcher.group("EQUAL"));
            else
                token = new Token(TokenType.UNKNOWN, matcher.group("UNKNOWN"));

            tokenList.add(token);
        }
    }

    public ArrayList<Token> getTokenList() {
        return tokenList;
    }
}
