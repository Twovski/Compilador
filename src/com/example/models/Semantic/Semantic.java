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
    private final HashMap<String, VariableData> stack;
    private int index;
    private boolean isComplete;
    public Semantic(ArrayList<Token> tokens){
        this.tokens = tokens;
        this.stack = new HashMap<>();
        this.isComplete = true;
        index = 0;
    }

    public void run() throws Exception {
        while(index < tokens.size() - 1)
            sentences();
    }

    private void sentences() throws Exception {
        TokenType type;
        String name;
        VariableData variable;
        switch (tokens.get(index).getType()){
            case TYPE_INTEGER:
                type = getToken(TokenType.TYPE_INTEGER).getType();
                name = getToken(TokenType.IDENTIFIER).getValue();
                if(existID(name)){
                    isComplete = false;
                    throw new Exception("Repeated variable");
                }

                variable = new VariableData(type, name);
                if(isToken(TokenType.EQUAL)){
                    getToken(TokenType.EQUAL);
                    variable.value = expressionNumber();
                }

                stack.put(name, variable);
                break;
            case TYPE_BOOLEAN:
                type = getToken(TokenType.TYPE_BOOLEAN).getType();
                name = getToken(TokenType.IDENTIFIER).getValue();
                if(existID(name)){
                    isComplete = false;
                    throw new Exception("Repeated variable");
                }

                variable = new VariableData(type, name);
                if(isToken(TokenType.EQUAL)){
                    getToken(TokenType.EQUAL);
                    variable.value = expressionBoolean();
                }

                stack.put(name, variable);
                break;
            case IDENTIFIER:
                name = getToken(TokenType.IDENTIFIER).getValue();
                if(!existID(name)){
                    isComplete = false;
                    throw new Exception("Undeclared variable");
                }
                variable = stack.get(name);
                getToken(TokenType.EQUAL);
                if(variable.type == TokenType.TYPE_INTEGER)
                    variable.value = expressionNumber();
                else
                    variable.value = expressionBoolean();

                stack.replace(name, variable);
                break;
            case LEFT_PAREN:
                getToken(TokenType.LEFT_PAREN);
                expressionBoolean();
                getToken(TokenType.RIGHT_PAREN);
                break;
            default:
                index++;
        }
    }

    private String expressionBoolean() throws Exception {
        String express = "";
        int negative = 0;
        do{
            while (isToken(TokenType.NOT)){
                negative++;
                getToken(TokenType.NOT);
            }

            if(negative % 2 == 1)
                express += "!";

            switch (tokens.get(index).getType()){
                case INTEGER:
                    express += expressionNumber();
                    express += getToken(TokenType.GREATER_EQUAL, TokenType.GREATER_THAN,
                            TokenType.EQUAL_EQUAL, TokenType.NOT_EQUAL,
                            TokenType.LESS_EQUAL, TokenType.LESS_THAN).getValue();
                    express += expressionNumber();
                    break;
                case BOOLEAN:
                    express += getToken(TokenType.BOOLEAN).getValue();
                    break;
                case IDENTIFIER:
                    Token token = tokens.get(index);
                    VariableData data = stack.get(token.getValue());
                    if(data == null){
                        isComplete = false;
                        throw new Exception("Undeclared variable");
                    }

                    if(data.type == TokenType.TYPE_INTEGER){
                        express += expressionNumber();
                        express += getToken(TokenType.GREATER_EQUAL, TokenType.GREATER_THAN,
                                TokenType.EQUAL_EQUAL, TokenType.NOT_EQUAL,
                                TokenType.LESS_EQUAL, TokenType.LESS_THAN).getValue();
                        express += expressionNumber();
                    }
                    else {
                        getToken(TokenType.IDENTIFIER);
                        express += data.value;
                    }
                    break;
            }

            if(!isToken(TokenType.OR, TokenType.AND))
                break;
            express += getToken(TokenType.OR, TokenType.AND).getValue();
            negative = 0;
        }while (true);

        express = express
                .replace("AND", "&&")
                .replace("OR", "||")
                .replace("NOT", "!");

        Expression expression = new Expression(express);
        EvaluationValue result = expression.evaluate();
        return result.getBooleanValue().toString();
    }

    private String expressionNumber() throws Exception {
        String express = "";
        do {
            express += getInteger();
            if(!isToken(TokenType.PLUS, TokenType.DIVIDE, TokenType.MINUS, TokenType.MULTIPLY))
                break;

            express += getToken(TokenType.PLUS, TokenType.DIVIDE, TokenType.MINUS, TokenType.MULTIPLY).getValue();
        }
        while (true);

        Expression expression = new Expression(express);
        EvaluationValue result = expression.evaluate();
        return result.getNumberValue().toBigInteger().toString();
    }

    private String getInteger() throws Exception {
        Token token = getToken(TokenType.IDENTIFIER, TokenType.INTEGER);

        if(token.getType() == TokenType.IDENTIFIER)
            return isIntegerID(token).value;
        return  token.getValue();
    }

    private VariableData isIntegerID(Token token) throws Exception {
        VariableData variable = stack.get(token.getValue());
        if(!existID(variable.name)){
            isComplete = false;
            throw new Exception("Undeclared variable");
        }
        if(variable.type != TokenType.TYPE_INTEGER){
            isComplete = false;
            throw new Exception("Variable is not defined as integer");
        }
        return variable;
    }

    private boolean existID(String name){
        return stack.get(name) != null;
    }

    private boolean isToken(TokenType ...list){
        TokenType token = tokens.get(index).getType();
        List<TokenType> types = List.of(list);
        return types.contains(token);
    }

    private Token getToken(TokenType ...list){
        Token token = tokens.get(index);
        List<TokenType> types = List.of(list);
        if(!types.contains(token.getType()))
            return null;

        index++;
        return token;
    }
}
