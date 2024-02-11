package com.example.models.Syntactic;

import com.example.models.Lexical.Token;
import com.example.models.Lexical.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Syntax {
    private final ArrayList<Token> tokens;
    private int index;
    public Syntax(ArrayList<Token> tokens){
        this.tokens = tokens;
        index = 0;
    }

    public void parse() throws Exception {
        checkToken("Start brace {", TokenType.LEFT_BRACE);
        while(index < tokens.size() - 1)
            sentences();
        checkToken("End brace }", TokenType.RIGHT_BRACE);
    }

    private void sentences() throws Exception {
        switch (tokens.get(index).getType()){
            case IF:
                sentenceIf();
                break;
            case WHILE:
                sentenceWhile();
                break;
            case TYPE_INTEGER:
                checkToken("", TokenType.TYPE_INTEGER);
                checkToken("Identifier", TokenType.IDENTIFIER);
                if(isToken(TokenType.EQUAL))
                    expressionNumber();
                checkToken("Close ;", TokenType.SEMICOLON);
                break;
            case TYPE_BOOLEAN:
                checkToken("", TokenType.TYPE_BOOLEAN);
                checkToken("Identifier", TokenType.IDENTIFIER);
                if(isToken(TokenType.EQUAL))
                    expressionBoolean();
                checkToken("Close ;", TokenType.SEMICOLON);
                break;
            case IDENTIFIER:
                checkToken("Identifier", TokenType.IDENTIFIER);
                checkToken("Equal =", TokenType.EQUAL);
                expression();
                checkToken("Close ;", TokenType.SEMICOLON);
                break;
            default:
                throw new Exception("Sentences Incorrect");
        }

    }

    private void expression() throws Exception {
        switch (tokens.get(index).getType()){
            case IDENTIFIER:
                checkToken("", TokenType.IDENTIFIER);
                if(isToken(TokenType.PLUS, TokenType.DIVIDE, TokenType.MINUS, TokenType.MULTIPLY))
                    expressionNumber();
                if(isToken(TokenType.AND, TokenType.OR))
                    expressionBoolean();
                break;
            case INTEGER:
                expressionNumber();
            case BOOLEAN:
                expressionBoolean();
            default:
                throw new Exception("Expression Incorrect");
        }
    }
    private void sentenceWhile() throws Exception {
        checkToken("", TokenType.WHILE);
        checkToken("Left Paren (", TokenType.LEFT_PAREN);
        expressionBoolean();
        checkToken("Right Paren )", TokenType.RIGHT_PAREN);
        if(isToken(TokenType.SEMICOLON))
            return;
        block();
    }
    private void sentenceElse() throws Exception {
        if(isToken(TokenType.IF))
            sentenceIf();
        else
            block();
    }

    private void sentenceIf() throws Exception {
        checkToken("", TokenType.IF);
        checkToken("Left Paren (", TokenType.LEFT_PAREN);
        expressionBoolean();
        checkToken("Right Paren )", TokenType.RIGHT_PAREN);
        block();
        if(isToken(TokenType.ELSE))
            sentenceElse();
    }

    private void block() throws Exception{
        if(isToken(TokenType.LEFT_BRACE)){
            sentences();
            checkToken("Right Brace }", TokenType.RIGHT_BRACE);
            return;
        }

        sentences();
    }
    private void expressionBoolean() throws Exception {
        do {
            while (isToken(TokenType.NOT));
            switch (tokens.get(index).getType()){
                case INTEGER:
                    expressionNumber();
                    break;
                case BOOLEAN:
                    checkToken("", TokenType.BOOLEAN);
                    break;
                case IDENTIFIER:
                    checkToken("", TokenType.IDENTIFIER);
                    if(isToken(TokenType.PLUS, TokenType.DIVIDE, TokenType.MINUS, TokenType.MULTIPLY))
                        expressionNumber();
                    break;
                default:
                    throw new Exception("Boolean Expression Incorrect");
            }
        }
        while (isToken(TokenType.AND, TokenType.OR,
                TokenType.GREATER_EQUAL, TokenType.GREATER_THAN,
                TokenType.EQUAL_EQUAL, TokenType.NOT_EQUAL,
                TokenType.LESS_EQUAL, TokenType.LESS_THAN));
    }
    private void expressionNumber() throws Exception{
        do checkToken("Need number or identifier", TokenType.INTEGER, TokenType.IDENTIFIER);
        while (isToken(TokenType.PLUS, TokenType.DIVIDE, TokenType.MINUS, TokenType.MULTIPLY));
    }
    private boolean isToken(TokenType...list) throws Exception {
        if(index >= tokens.size())
            throw new Exception("Tokens are not complete");

        TokenType token = tokens.get(index).getType();
        List<TokenType> types = List.of(list);
        if(types.contains(token)){
            index++;
            return true;
        }
        return false;
    }

    private void checkToken(String message, TokenType...list) throws Exception {
        if(index >= tokens.size())
            throw new Exception(message);

        TokenType token = tokens.get(index).getType();
        List<TokenType> types = List.of(list);
        if(!types.contains(token))
            throw new Exception(message);

        index++;
    }
}
