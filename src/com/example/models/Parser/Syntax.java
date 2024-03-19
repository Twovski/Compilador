package com.example.models.Parser;

import com.example.models.Scanner.Token;
import com.example.models.Scanner.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Syntax {
    private final ArrayList<Token> tokens;
    private int index;
    private boolean isComplete;
    public Syntax(ArrayList<Token> tokens){
        this.tokens = tokens;
        index = 0;
        isComplete = false;
    }

    public void run() throws Exception {
        isToken("Start brace {", TokenType.LEFT_BRACE);
        while(index < tokens.size() - 1)
            sentences();
        isToken("End brace }", TokenType.RIGHT_BRACE);
        isComplete = true;
    }

    private void sentences() throws Exception {
        switch (tokens.get(index).getType()){
            case IF_RESERVED:
                isToken("", TokenType.IF_RESERVED);
                sentenceIf();
                break;
            case WHILE_RESERVED:
                sentenceWhile();
                break;
            case TYPE_BOOLEAN:
            case TYPE_INTEGER:
                isToken("", TokenType.TYPE_INTEGER, TokenType.TYPE_BOOLEAN);
                isToken("Identifier", TokenType.IDENTIFIER);
                if(isOptionalToken(TokenType.EQUAL))
                    expression();
                isToken("Close ;", TokenType.SEMICOLON);
                break;
            case IDENTIFIER:
                isToken("Identifier", TokenType.IDENTIFIER);
                isToken("Equal =", TokenType.EQUAL);
                expression();
                isToken("Close ;", TokenType.SEMICOLON);
                break;
            default:
                throw new Exception("Sentences Incorrect");
        }

    }

    private void expression() throws Exception {
        switch (tokens.get(index).getType()){
            case IDENTIFIER:
                isToken("", TokenType.IDENTIFIER);
                if(isOptionalToken(TokenType.PLUS, TokenType.DIVIDE, TokenType.MINUS, TokenType.MULTIPLY))
                    expressionNumber();
                if(isOptionalToken(TokenType.AND, TokenType.OR))
                    expressionBoolean();
                break;
            case INTEGER:
                expressionNumber();
                break;
            case BOOLEAN:
                expressionBoolean();
                break;
            default:
                throw new Exception("Expression Incorrect");
        }
    }
    private void sentenceWhile() throws Exception {
        isToken("", TokenType.WHILE_RESERVED);
        isToken("Left Paren (", TokenType.LEFT_PAREN);
        expressionBoolean();
        isToken("Right Paren )", TokenType.RIGHT_PAREN);
        if(isOptionalToken(TokenType.SEMICOLON))
            return;
        block();
    }
    private void sentenceElse() throws Exception {
        if(isOptionalToken(TokenType.IF_RESERVED))
            sentenceIf();
        else
            block();
    }

    private void sentenceIf() throws Exception {
        isToken("Left Paren (", TokenType.LEFT_PAREN);
        expressionBoolean();
        isToken("Right Paren )", TokenType.RIGHT_PAREN);
        block();
        if(isOptionalToken(TokenType.ELSE_RESERVED))
            sentenceElse();
    }

    private void block() throws Exception{
        isToken("Left Brace {", TokenType.LEFT_BRACE);
        while(!isOptionalToken(TokenType.RIGHT_BRACE))
            sentences();
    }

    private void expressionBoolean() throws Exception {
        do {
            while (isOptionalToken(TokenType.NOT));
            switch (tokens.get(index).getType()){
                case INTEGER:
                    expressionNumber();
                    isToken("Need Relational Operator", TokenType.GREATER_EQUAL, TokenType.GREATER_THAN,
                            TokenType.EQUAL_EQUAL, TokenType.NOT_EQUAL,
                            TokenType.LESS_EQUAL, TokenType.LESS_THAN);
                    expressionNumber();
                    break;
                case BOOLEAN:
                    isToken("", TokenType.BOOLEAN);
                    break;
                case IDENTIFIER:
                    isToken("", TokenType.IDENTIFIER);
                    if(isOptionalToken(TokenType.GREATER_EQUAL, TokenType.GREATER_THAN, TokenType.EQUAL_EQUAL, TokenType.NOT_EQUAL, TokenType.LESS_EQUAL, TokenType.LESS_THAN))
                        expressionNumber();
                    else if(isOptionalToken(TokenType.PLUS, TokenType.DIVIDE, TokenType.MINUS, TokenType.MULTIPLY)){
                        expressionNumber();
                        isToken("Need Relational Operator", TokenType.GREATER_EQUAL, TokenType.GREATER_THAN,
                                TokenType.EQUAL_EQUAL, TokenType.NOT_EQUAL,
                                TokenType.LESS_EQUAL, TokenType.LESS_THAN);
                        expressionNumber();
                    }


                    break;
                default:
                    throw new Exception("Boolean Expression Incorrect");
            }
        }
        while (isOptionalToken(TokenType.AND, TokenType.OR));
    }
    private void expressionNumber() throws Exception{
        do isToken("Need number or identifier", TokenType.INTEGER, TokenType.IDENTIFIER);
        while (isOptionalToken(TokenType.PLUS, TokenType.DIVIDE, TokenType.MINUS, TokenType.MULTIPLY));
    }
    private boolean isOptionalToken(TokenType...list) throws Exception {
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

    private void isToken(String message, TokenType...list) throws Exception {
        if(index >= tokens.size()){
            throw new Exception(message);
        }

        TokenType token = tokens.get(index).getType();
        List<TokenType> types = List.of(list);
        if(!types.contains(token)){
            throw new Exception(message);
        }

        index++;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
