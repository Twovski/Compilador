package com.example.models.Intermediate;

import com.example.models.Scanner.Token;

import java.util.*;

public class Postfix {
    public static final Map<String, Operator> ops = new HashMap<>() {{
        put("=", Operator.EQUAL);
        put("+", Operator.ADD);
        put("-", Operator.SUBTRACT);
        put("*", Operator.MULTIPLY);
        put("/", Operator.DIVIDE);
        put(">=", Operator.GREATER_EQUAL);
        put(">", Operator.GREATER_THAN);
        put("<=", Operator.LESS_EQUAL);
        put("<", Operator.LESS_THAN);
        put("==", Operator.EQUAL_EQUAL);
        put("!=", Operator.NOT_EQUAL);
        put("&&", Operator.AND);
        put("AND", Operator.AND);
        put("||", Operator.OR);
        put("OR", Operator.OR);
        put("!", Operator.NOT);
        put("NOT", Operator.NOT);
        put("while", Operator.WHILE_RESERVED);
        put("if", Operator.IF_RESERVED);
        put("else", Operator.ELSE_RESERVED);
    }};

    private static boolean isHigerPrec(String op, String sub) {
        return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
    }

    public static ArrayList<String> run(ArrayList<Token> args) {
        String token;
        ArrayList<String> output = new ArrayList<>();
        Stack<String> stack  = new Stack<>();
        for (Token arg : args) {
            token = arg.getValue();
            if (ops.containsKey(token)) {
                while (!stack.isEmpty() && isHigerPrec(token, stack.peek()))
                    output.add(stack.pop());
                stack.push(token);
            }
            else if (token.equals("("))
                stack.push(token);
            else if (token.equals(")")) {
                while (!stack.peek().equals("("))
                    output.add(stack.pop());
                stack.pop();
            }
            else
                output.add(token);
        }

        while (!stack.isEmpty())
            output.add(stack.pop());

        return output;
    }
}
