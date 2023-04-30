package com.example.Models;

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
            if (matcher.group("FUNCTION") != null)
                token = new Token(TokenType.FUNCTION, matcher.group("FUNCTION"));
            else if (matcher.group("IDENTIFIER") != null)
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
            else if (matcher.group("COMMA") != null)
                token = new Token(TokenType.COMMA, matcher.group("COMMA"));
            else if (matcher.group("WHILE") != null)
                token = new Token(TokenType.WHILE, matcher.group("WHILE"));
            else if (matcher.group("IF") != null)
                token = new Token(TokenType.IF, matcher.group("IF"));
            else if (matcher.group("ELSE") != null)
                token = new Token(TokenType.ELSE, matcher.group("ELSE"));
            else if (matcher.group("ELSEIF") != null)
                token = new Token(TokenType.ELSE_IF, matcher.group("ELSEIF"));
            else if (matcher.group("TYPEVOID") != null)
                token = new Token(TokenType.TYPE_VOID, matcher.group("TYPEVOID"));
            else if (matcher.group("TYPEBOOLEAN") != null)
                token = new Token(TokenType.TYPE_BOOLEAN, matcher.group("TYPEBOOLEAN"));
            else if (matcher.group("BOOLEAN") != null)
                token = new Token(TokenType.BOOLEAN, matcher.group("BOOLEAN"));
            else if (matcher.group("TYPESTRING") != null)
                token = new Token(TokenType.TYPE_STRING, matcher.group("TYPESTRING"));
            else if (matcher.group("STRING") != null)
                token = new Token(TokenType.STRING, matcher.group("STRING"));
            else if (matcher.group("TYPEFLOAT") != null)
                token = new Token(TokenType.TYPE_FLOAT, matcher.group("TYPEFLOAT"));
            else if (matcher.group("FLOAT") != null)
                token = new Token(TokenType.FLOAT, matcher.group("FLOAT"));
            else if (matcher.group("TYPEINTEGER") != null)
                token = new Token(TokenType.TYPE_INTEGER, matcher.group("TYPEINTEGER"));
            else if (matcher.group("INTEGER") != null)
                token = new Token(TokenType.INTEGER, matcher.group("INTEGER"));
            else if (matcher.group("INCREMENT") != null)
                token = new Token(TokenType.INCREMENT, matcher.group("INCREMENT"));
            else if (matcher.group("DECREMENT") != null)
                token = new Token(TokenType.DECREMENT, matcher.group("DECREMENT"));
            else if (matcher.group("MINUSEQUALS") != null)
                token = new Token(TokenType.MINUS_EQUALS, matcher.group("MINUSEQUALS"));
            else if (matcher.group("PLUSEQUALS") != null)
                token = new Token(TokenType.PLUS_EQUALS, matcher.group("PLUSEQUALS"));
            else if (matcher.group("TIMESEQUALS") != null)
                token = new Token(TokenType.TIMES_EQUALS, matcher.group("TIMESEQUALS"));
            else if (matcher.group("DIVIDEEQUALS") != null)
                token = new Token(TokenType.DIVIDE_EQUALS, matcher.group("DIVIDEEQUALS"));
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
