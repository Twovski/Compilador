package com.example.models.Semantic;

import com.example.models.Scanner.Token;
import com.example.models.Scanner.TokenType;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Semantic {
    private final ArrayList<Token> tokens;
    private final HashMap<String, VariableData> map;
    private int index;
    private boolean isComplete;
    public Semantic(ArrayList<Token> tokens){
        this.tokens = tokens;
        this.map = new HashMap<>();
        this.isComplete = false;
        index = 0;
    }

    public void run() throws Exception {
        while(index < tokens.size())
            sentences();
        isComplete = true;
    }

    private void sentences() throws Exception {
        TokenType type;
        String name;
        VariableData variable;
        switch (tokens.get(index).getType()){
            case TYPE_INTEGER:
                type = getToken("Need Type Value", TokenType.TYPE_INTEGER).getType();
                name = getToken("Need Identifier", TokenType.IDENTIFIER).getValue();
                if(existID(name))
                    throw new Exception("Repeated variable");

                variable = new VariableData(type, name);
                if(isToken(TokenType.EQUAL)){
                    getToken("Need Equal =", TokenType.EQUAL);
                    variable.value = expressionNumber();
                }

                map.put(name, variable);
                break;
            case TYPE_BOOLEAN:
                type = getToken("Need Type Value", TokenType.TYPE_BOOLEAN).getType();
                name = getToken("Need Identifier", TokenType.IDENTIFIER).getValue();
                if(existID(name))
                    throw new Exception("Repeated variable");

                variable = new VariableData(type, name);
                if(isToken(TokenType.EQUAL)){
                    getToken("Need Equal =", TokenType.EQUAL);
                    variable.value = expressionBoolean();
                }

                map.put(name, variable);
                break;
            case IDENTIFIER:
                name = getToken("Need Identifier", TokenType.IDENTIFIER).getValue();
                variable = map.get(name);
                if(variable == null)
                    throw new Exception("Undeclared variable");

                getToken("Need Equal =", TokenType.EQUAL);
                if(variable.type == TokenType.TYPE_INTEGER)
                    variable.value = expressionNumber();
                else
                    variable.value = expressionBoolean();

                map.replace(name, variable);
                break;
            case LEFT_PAREN:
                getToken("Need Left Paren (", TokenType.LEFT_PAREN);
                expressionBoolean();
                getToken("Need Right Paren )", TokenType.RIGHT_PAREN);
                break;
            default:
                index++;
        }
    }

    private String expressionBoolean() throws Exception {
        StringBuilder express = new StringBuilder();
        int negative = 0;
        do{
            while (isToken(TokenType.NOT)){
                negative++;
                getToken("", TokenType.NOT);
            }

            if(negative % 2 == 1)
                express.append("!");

            switch (tokens.get(index).getType()){
                case INTEGER:
                    express.append(expressionNumber());
                    express.append(getToken("Need Operator Relational", TokenType.GREATER_EQUAL, TokenType.GREATER_THAN,
                            TokenType.EQUAL_EQUAL, TokenType.NOT_EQUAL,
                            TokenType.LESS_EQUAL, TokenType.LESS_THAN).getValue());
                    express.append(expressionNumber());
                    break;
                case BOOLEAN:
                    express.append(getToken("", TokenType.BOOLEAN).getValue());
                    break;
                case IDENTIFIER:
                    Token token = tokens.get(index);
                    VariableData data = map.get(token.getValue());
                    if(data == null)
                        throw new Exception("Undeclared variable");

                    if(data.type == TokenType.TYPE_INTEGER){
                        express.append(expressionNumber());
                        express.append(getToken("Need Operator Relational", TokenType.GREATER_EQUAL, TokenType.GREATER_THAN,
                                TokenType.EQUAL_EQUAL, TokenType.NOT_EQUAL,
                                TokenType.LESS_EQUAL, TokenType.LESS_THAN).getValue());
                        express.append(expressionNumber());
                    }
                    else {
                        getToken("Need Identifier", TokenType.IDENTIFIER);
                        express.append(data.value);
                    }
                    break;
            }

            if(!isToken(TokenType.OR, TokenType.AND))
                break;
            express.append(getToken("Expression boolean", TokenType.OR, TokenType.AND).getValue());
            negative = 0;
        }while (true);

        express = new StringBuilder(express.toString()
                .replace("AND", "&&")
                .replace("OR", "||")
                .replace("NOT", "!"));

        Expression expression = new Expression(express.toString());
        EvaluationValue result = expression.evaluate();
        return result.getBooleanValue().toString();
    }

    private String expressionNumber() throws Exception {
        StringBuilder express = new StringBuilder();
        do {
            express.append(getInteger());
            if(!isToken(TokenType.PLUS, TokenType.DIVIDE, TokenType.MINUS, TokenType.MULTIPLY))
                break;

            express.append(getToken("Need Operator", TokenType.PLUS, TokenType.DIVIDE, TokenType.MINUS, TokenType.MULTIPLY).getValue());
        }
        while (true);

        Expression expression = new Expression(express.toString());
        EvaluationValue result = expression.evaluate();
        return result.getNumberValue().toBigInteger().toString();
    }

    private String getInteger() throws Exception {
        Token token = getToken("Need a integer", TokenType.IDENTIFIER, TokenType.INTEGER);

        if(token.getType() == TokenType.IDENTIFIER)
            return isIntegerID(token).value;
        return  token.getValue();
    }

    private VariableData isIntegerID(Token token) throws Exception {
        VariableData variable = map.get(token.getValue());
        if(variable == null)
            throw new Exception("Undeclared variable");

        if(variable.type != TokenType.TYPE_INTEGER)
            throw new Exception("Variable is not defined as integer");

        return variable;
    }

    private boolean existID(String name) {
        return map.get(name) != null;
    }

    private boolean isToken(TokenType ...list){
        TokenType token = tokens.get(index).getType();
        List<TokenType> types = List.of(list);
        return types.contains(token);
    }

    private Token getToken(String message, TokenType ...list) throws Exception {
        Token token = tokens.get(index);
        List<TokenType> types = List.of(list);
        if(!types.contains(token.getType()))
            throw new Exception(message);

        index++;
        return token;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
