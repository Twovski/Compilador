package com.example.models.Intermediate;

import com.example.models.Scanner.Token;

import java.util.*;
import java.util.stream.Collectors;

public class Translate {
    private ArrayList<Quadruple> quadruples;
    private final HashMap<String, Quadruple> variable;
    private HashMap<Integer, StringBuilder> tags;
    private final ArrayList<StringBuilder> assembly, code;
    public Translate(ArrayList<Token> tokens){
        assembly = new ArrayList<>();
        code = new ArrayList<>();
        variable = new HashMap<>();
        tags = new HashMap<>();
        startDirection(tokens);
    }

    public void run(){
        assembly.add(new StringBuilder(".model small"));
        assembly.add(new StringBuilder(".stack"));
        assembly.add(new StringBuilder(".data"));
        startData();
        assembly.add(new StringBuilder(".code"));
        for(Quadruple quadruple: quadruples)
            startCode(quadruple);
        addTags();
        assembly.addAll(code);
        assembly.add(new StringBuilder(".exit"));
    }

    private void startData(){
        assembly.add(new StringBuilder("\t").append("true").append("\t").append("db").append("\t").append("1"));
        assembly.add(new StringBuilder("\t").append("false").append("\t").append("db").append("\t").append("0"));

        for (Quadruple quadruple: quadruples){
            if(quadruple.result.matches("-?\\d+"))
                continue;
            else if(variable.get(quadruple.result) != null)
                continue;

            variable.put(quadruple.result, quadruple);
        }

        variable.forEach((key, value) -> {
            StringBuilder result = new StringBuilder();
            switch (value.operator) {
                case ">":
                case ">=":
                case "!=":
                case "==":
                case "<":
                case "<=":
                case "AND":
                case "&&":
                case "OR":
                case "||":
                case "NOT":
                case "!":
                    result.append("\t")
                            .append(value.result)
                            .append("\t")
                            .append("db")
                            .append("\t")
                            .append("?");
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                case "%":
                    result.append("\t")
                            .append(value.result)
                            .append("\t")
                            .append("dw")
                            .append("\t")
                            .append("?");
                    break;
                case "=":
                    String type = value.arg1.matches("-?\\d+") || value.arg1.startsWith("_T") ? "dw" : "db";
                    result.append("\t")
                            .append(value.result)
                            .append("\t")
                            .append(type)
                            .append("\t")
                            .append("?");
                    break;
            }

            assembly.add(result);
        });
    }


    private void startCode(Quadruple quadruple){
        int size;
        StringBuilder result = new StringBuilder();
        switch (quadruple.operator){
            case "=":
                result.append("\t").append("MOV AX, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AX");
                break;
            case "+":
                result.append("\t").append("MOV AX, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("ADD AX, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AX");
                break;
            case "-":
                result.append("\t").append("MOV AX, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("SUB AX, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AX");
                break;
            case "*":
                result.append("\t").append("MOV AX, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("MOV BX, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("MUL BX").append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AX");
                break;
            case "/":
                result.append("\t").append("MOV AX, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("MOV BX, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("DIV BX").append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AX");
                break;
            case ">=":
                result.append("\t").append("MOV AX, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("CMP AX, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("LAHF").append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AH").append("\n");
                result.append("\t").append("NOT ").append(quadruple.result).append("\n");
                result.append("\t").append("ROL ").append(quadruple.result).append(", 1").append("\n");
                result.append("\t").append("AND ").append(quadruple.result).append(", 1");
                break;
            case "<":
                result.append("\t").append("MOV AX, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("CMP AX, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("LAHF").append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AH").append("\n");
                result.append("\t").append("ROL ").append(quadruple.result).append(", 1").append("\n");
                result.append("\t").append("AND ").append(quadruple.result).append(", 1");
                break;
            case ">":
                result.append("\t").append("MOV AX, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("MOV BX, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("ADD BX, 1").append("\n");
                result.append("\t").append("CMP AX, BX").append("\n");
                result.append("\t").append("LAHF").append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AH").append("\n");
                result.append("\t").append("NOT ").append(quadruple.result).append("\n");
                result.append("\t").append("ROL ").append(quadruple.result).append(", 1").append("\n");
                result.append("\t").append("AND ").append(quadruple.result).append(", 1");
                break;
            case "<=":
                result.append("\t").append("MOV AX, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("MOV BX, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("ADD BX, 1").append("\n");
                result.append("\t").append("CMP AX, BX").append("\n");
                result.append("\t").append("LAHF").append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AH").append("\n");
                result.append("\t").append("ROL ").append(quadruple.result).append(", 1").append("\n");
                result.append("\t").append("AND ").append(quadruple.result).append(", 1");
                break;
            case "==":
                result.append("\t").append("MOV AX, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("CMP AX, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("LAHF").append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AH").append("\n");
                result.append("\t").append("ROL ").append(quadruple.result).append(", 2").append("\n");
                result.append("\t").append("AND ").append(quadruple.result).append(", 1");
                break;
            case "!=":
                result.append("\t").append("MOV AX, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("CMP AX, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("LAHF").append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AH").append("\n");
                result.append("\t").append("ROL ").append(quadruple.result).append(", 2").append("\n");
                result.append("\t").append("NOT ").append(quadruple.result).append("\n");
                result.append("\t").append("AND ").append(quadruple.result).append(", 1");
                break;
            case "AND":
            case "&&":
                result.append("\t").append("MOV AL, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("AND AL, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AL");
                break;
            case "OR":
            case "||":
                result.append("\t").append("MOV AL, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("OR AL, ").append(quadruple.arg2).append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AL");
                break;
            case "NOT":
            case "!":
                result.append("\t").append("MOV AL, ").append(quadruple.arg1).append("\n");
                result.append("\t").append("NOT AL").append("\n");
                result.append("\t").append("MOV ").append(quadruple.result).append(", AL");
                break;
            case "if":
                size = Integer.parseInt(quadruple.result);
                result.append("\t").append("CMP ").append(quadruple.arg1).append(", 1").append("\n");
                result.append("\t").append("JNZ ").append("Else").append(size);
                tags.put(size,
                        new StringBuilder("\t").append("Else").append(size).append(":")
                );
                break;
            case "else":
                size = Integer.parseInt(quadruple.result);
                result.append("\t").append("JMP FinishIF").append(size);
                tags.put(Integer.parseInt(quadruple.result),
                        new StringBuilder("\t").append("FinishIF").append(size).append(":")
                );
                break;
            case "while":
                size = Integer.parseInt(quadruple.result);
                result.append("\t").append("CMP ").append(quadruple.arg1).append(", 1").append("\n");
                result.append("\t").append("JNZ ").append("FinishWhile").append(size);
                tags.put(Integer.parseInt(quadruple.result),
                        new StringBuilder("\t").append("FinishWhile").append(size).append(":"));
                break;
            case "jump":
                size = Integer.parseInt(quadruple.result);
                result.append("\t").append("JMP StartWhile").append(quadruple.result);
                tags.put(size,
                        new StringBuilder("\t").append("StartWhile").append(size).append(":"));
                break;
        }
        result.append("\n");
        code.add(result);
    }

    private void addTags(){
        tags = sortTags();
        tags.forEach(code::add);
    }

    public HashMap<Integer, StringBuilder> sortTags() {
        return tags.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    public void startDirection(ArrayList<Token> tokens){
        Direction direction = new Direction(tokens);
        direction.run();
        quadruples = direction.quadruples;
    }

    public ArrayList<StringBuilder> getAssembly() {
        return assembly;
    }
}
