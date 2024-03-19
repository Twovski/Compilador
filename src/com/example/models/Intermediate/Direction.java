package com.example.models.Intermediate;

import com.example.models.Scanner.Token;
import com.example.models.Scanner.TokenType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Direction {
    public final ArrayList<Quadruple> quadruples;
    public final HashSet<String> temporal;
    private final ArrayList<Token> tokens;
    private final Stack<Stack<Integer>> stacksIf, stacksWhile;
    private boolean isOnlyElse;
    private int index;

    public Direction(ArrayList<Token> tokens){
        quadruples = new ArrayList<>();
        temporal = new HashSet<>();
        stacksIf = new Stack<>();
        stacksWhile = new Stack<>();
        isOnlyElse = false;
        index = 0;
        this.tokens = tokens;
    }

    public void run(){
        while(tokens.size() > index)
            sentences();
    }

    public void sentences(){
        Stack<Integer> stackWhile;
        switch (tokens.get(index).getType()){
            case LEFT_BRACE:
                stacksIf.push(new Stack<>());
                stacksWhile.push(new Stack<>());
                index++;
                break;
            case WHILE_RESERVED:
                stackWhile = stacksWhile.peek();
                stackWhile.push(quadruples.size());
                resolveIf_While(TokenType.LEFT_BRACE);
                break;
            case IF_RESERVED:
                resolveIf_While(TokenType.LEFT_BRACE);
                break;
            case IDENTIFIER:
                resolveIdentifier(TokenType.SEMICOLON);
                break;
            case ELSE_RESERVED:
                resolveElse();
                index++;
                break;
            case RIGHT_BRACE:
                resolveBrace();
                index++;
                break;
            default:
                index++;
                break;
        }
    }

    private void resolveBrace(){
        Stack<Integer> stackIf = stacksIf.pop();
        while (!stackIf.isEmpty()) {
            int position = stackIf.pop();
            Quadruple quadruple = quadruples.get(position);
            int result = quadruple.operator.equals("while") ? quadruples.size() + 1 : quadruples.size();
            quadruple.result = String.valueOf(result);
        }

        Stack<Integer> stackWhile = stacksWhile.pop();
        while (!stackWhile.isEmpty()) {
            int position = stackWhile.pop();
            quadruples.add(new Quadruple("jump", null, null, String.valueOf(position)));
        }

        if(!isOnlyElse)
            return;

        isOnlyElse = false;
        stackIf = stacksIf.pop();
        while (!stackIf.isEmpty()) {
            int position = stackIf.pop();
            Quadruple quadruple = quadruples.get(position);
            quadruple.result = String.valueOf(quadruples.size());
            stacksIf.push(stackIf);
        }
    }

    private void resolveElse(){
        quadruples.add(new Quadruple(
                "else",
                null,
                null,
                null
        ));

        Stack<Integer> stackIf = stacksIf.peek();
        if(stackIf.isEmpty())
            return;

        int position = stackIf.pop();
        Quadruple quadruple = quadruples.get(position);
        quadruple.result = String.valueOf(quadruples.size());
        stackIf.push(quadruples.size() - 1);
        isOnlyElse = tokens.get(index + 1).getType() == TokenType.LEFT_BRACE;
    }

    private void resolveIf_While(TokenType ...type){
        int x = 0, count = 1;
        ArrayList<String> postfix = getPostfix(type);
        while(postfix.size() != 1) {
            String operator = postfix.get(x);
            String result = "_B" + count;
            if(Postfix.ops.get(operator) == null){
                x++;
                continue;
            }

            switch (operator){
                case "!":
                case "NOT":
                    x -= 1;
                    quadruples.add(new Quadruple(
                            operator,
                            postfix.remove(x),
                            null,
                            result
                    ));
                    temporal.add(result);
                    break;
                case "while":
                case "if":
                    Stack<Integer> stack = stacksIf.pop();
                    stack.push(quadruples.size());
                    x -= 1;
                    quadruples.add(new Quadruple(
                            operator,
                            postfix.remove(x),
                            null,
                            null
                    ));
                    stacksIf.push(stack);
                    break;
                default:
                    x -= 2;
                    quadruples.add(new Quadruple(
                            operator,
                            postfix.remove(x),
                            postfix.remove(x),
                            result
                    ));
                    temporal.add(result);
                    break;
            }
            postfix.set(x, result);

            count++;
        }
    }

    private void resolveIdentifier(TokenType ...type){
        int x = 0, count = 1;
        ArrayList<String> postfix = getPostfix(type);
        while(postfix.size() != 1) {
            String operator = postfix.get(x);
            String result = "_T" + count;
            temporal.add(result);
            if(Postfix.ops.get(operator) == null){
                x++;
                continue;
            }

            x -= 2;

            String value1 = postfix.remove(x);
            String value2 = postfix.remove(x);
            if(operator.equals("=")){
                result = value1;
                value1 = value2;
                value2 = null;
            }

            quadruples.add(new Quadruple(
                    operator,
                    value1,
                    value2,
                    result
            ));
            temporal.add(result);
            postfix.set(x, result);
            count++;
        }
    }

    private ArrayList<String> getPostfix(TokenType ...type){
        ArrayList<Token> list = new ArrayList<>(tokens.subList(index, getIndex(type)));
        return Postfix.run(list);
    }

    private int getIndex(TokenType ...token){
        while(!isToken(token))
            index++;
        return index;
    }

    private boolean isToken(TokenType ...list){
        TokenType token = tokens.get(index).getType();
        List<TokenType> types = List.of(list);
        return types.contains(token);
    }
}
