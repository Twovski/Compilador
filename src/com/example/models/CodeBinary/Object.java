package com.example.models.CodeBinary;

import com.example.models.Intermediate.Translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Object {
    private final HashMap<String, String> data;
    private final ArrayList<String> assembly;
    private final ArrayList<StringBuilder> binaryCode;
    private int index;
    public Object(ArrayList<String> assembly) {
        this.data = new HashMap<>();
        this.binaryCode = new ArrayList<>();
        this.assembly = assembly;
    }

    public void run(){
        index = 3;
        tagData();
        tagCode();
    }

    private void tagData(){
        String[] values;
        ArrayList<String> instructions;
        int offset = 0;
        while(assembly.size() > index){
            values = assembly.get(index)
                    .toString()
                    .trim()
                    .split("\\s+");
            instructions = new ArrayList<>(List.of(values));
            index++;
            if(instructions.contains(".code"))
                break;

            data.put(instructions.get(0), Integer.toBinaryString(offset));
            if(instructions.get(1).equals("db"))
                offset++;
            else
                offset += 2;
        }
    }

    private void tagCode(){
        String[] values;
        ArrayList<String> instructions;
        while(assembly.size() > index) {
            values = assembly.get(index)
                    .toString()
                    .trim()
                    .split("\\s+");
            instructions = new ArrayList<>(List.of(values));
            instructions.replaceAll((value) -> value.replace(",", ""));

            index++;
            if (instructions.contains(".exit"))
                break;
            
            switch (instructions.get(0)){
                case "MOV":
                    instructionMOV(instructions);
                    break;
                case"ADD":
                    instructionADD(instructions);
                    break;
                case"SUB":
                    instructionSUB(instructions);
                    break;
                case"MUL":
                    instructionMUL(instructions);
                    break;
                case"DIV":
                    instructionDIV(instructions);
                    break;
                case "LAHF":
                    binaryCode.add(new StringBuilder("10011111"));
                    break;
                case "NOT":
                    instructionNOT(instructions);
                    break;
                case "ROL":
                    instructionROL(instructions);
                    break;
                case "AND":
                    instructionAND(instructions);
                    break;
                case "OR":
                    instructionOR(instructions);
                    break;
                case "CMP":
                    instructionCMP(instructions);
                    break;
                case "JMP":
                    binaryCode.add(
                            new StringBuilder()
                                    .append("11101011")
                                    .append(formatBinary("0", 8))
                    );
                    break;
                case "JNZ":
                    binaryCode.add(
                            new StringBuilder()
                                    .append(formatBinary("1110101", 8))
                                    .append(formatBinary("0", 8))
                    );
                    break;
                default:
                    binaryCode.add(new StringBuilder());
                    break;
            }
        }
    }
    private void instructionCMP(ArrayList<String> instructions) {
        StringBuilder builder = new StringBuilder();
        String arg1 = instructions.get(1), arg2 = instructions.get(2);

        if(arg1.equals("AX") && arg2.equals("BX")){
            builder.append(formatBinary("111011", 8))
                    .append("11000011");
        }
        else if(arg1.equals("AX") && isNumber(arg2)){
            builder.append(formatBinary("111101", 8))
                    .append(formatBinary(convertToBinary(arg2), 8));
        }
        else if(arg1.equals("AX") && !isNumber(arg2)){
            builder.append(formatBinary("111011", 8))
                    .append(formatBinary("110", 8))
                    .append(formatBinary(convertToBinary(data.get(arg2)), 8));
        }
        else if(!isNumber(arg1)){
            builder.append("10000011")
                    .append(formatBinary("111110", 8))
                    .append(formatBinary(convertToBinary(data.get(arg1)), 8))
                    .append(formatBinary(convertToBinary(arg2), 8));
        }
        binaryCode.add(builder);
    }

    private void instructionOR(ArrayList<String> instructions) {
        StringBuilder builder = new StringBuilder();
        String arg2 = instructions.get(2);

        if(isNumber(arg2)){
            builder.append(formatBinary("1100", 8))
                    .append(formatBinary(convertToBinary(arg2), 8));
        }
        else {
            builder.append(formatBinary("1010", 8))
                    .append(formatBinary("110", 8))
                    .append(formatBinary(convertToBinary(data.get(arg2)), 8));
        }

        binaryCode.add(builder);
    }

    private void instructionAND(ArrayList<String> instructions) {
        StringBuilder builder = new StringBuilder();
        String arg1 = instructions.get(1), arg2 = instructions.get(2);

        if(arg1.equals("AL") && isNumber(arg2)){
            builder.append(formatBinary("100100", 8))
                    .append(formatBinary(convertToBinary(arg2), 8));
        }
        else if(arg1.equals("AL") && !isNumber(arg2)){
            builder.append(formatBinary("100010", 8))
                    .append(formatBinary("110", 8))
                    .append(formatBinary(convertToBinary(data.get(arg2)), 8));
        }
        else if(!isNumber(arg1)){
            builder.append("10000000")
                    .append(formatBinary("100110", 8))
                    .append(formatBinary(convertToBinary(data.get(arg1)), 8))
                    .append(formatBinary(convertToBinary(arg2), 8));
        }
        binaryCode.add(builder);
    }
    private void instructionROL(ArrayList<String> instructions){
        StringBuilder builder = new StringBuilder();
        String arg1 = instructions.get(1);

        builder.append("11010000")
                .append(formatBinary("110", 8))
                .append(formatBinary(convertToBinary(data.get(arg1)), 8));

        binaryCode.add(builder);
    }
    private void instructionNOT(ArrayList<String> instructions){
        StringBuilder builder = new StringBuilder();
        String arg1 = instructions.get(1);

        if(arg1.equals("AL")){
            builder.append("11110110")
                    .append("11010000");
        }
        else{
            builder.append("11110110")
                    .append(formatBinary("10110", 8))
                    .append(formatBinary(convertToBinary(data.get(arg1)), 8));
        }

        binaryCode.add(builder);
    }
    private void instructionMUL(ArrayList<String> instructions){
        StringBuilder builder = new StringBuilder();

        builder.append("11110111")
                .append("11100011");

        binaryCode.add(builder);
    }
    private void instructionDIV(ArrayList<String> instructions){
        StringBuilder builder = new StringBuilder();

        builder.append("11110111")
                .append("11110011");

        binaryCode.add(builder);
    }
    private void instructionSUB(ArrayList<String> instructions){
        StringBuilder builder = new StringBuilder();
        String arg2 = instructions.get(2);

        if(isNumber(arg2)){
            builder.append(formatBinary("101101", 8))
                    .append(formatBinary(convertToBinary(arg2), 16));
        }
        else {
            builder.append(formatBinary("101011", 8))
                    .append(formatBinary("110", 8))
                    .append(formatBinary(convertToBinary(data.get(arg2)), 16));
        }

        binaryCode.add(builder);
    }
    private void instructionADD(ArrayList<String> instructions){
        StringBuilder builder = new StringBuilder();
        String arg1 = instructions.get(1), arg2 = instructions.get(2);

        if(arg1.equals("AX") && isNumber(arg2)){
            builder.append(formatBinary("101", 8))
                    .append(formatBinary(convertToBinary(arg2), 16));
        }
        else if(arg1.equals("AX") && !isNumber(arg2)){
            builder.append(formatBinary("11", 8))
                    .append(formatBinary("110", 8))
                    .append(formatBinary(convertToBinary(data.get(arg2)), 8));
        }
        else if(arg1.equals("BX")){
            builder.append(formatBinary("10000011", 8))
                    .append(formatBinary("11000011", 8))
                    .append(formatBinary(convertToBinary(arg2), 16));
        }

        binaryCode.add(builder);
    }
    private void instructionMOV(ArrayList<String> instructions){
        StringBuilder builder = new StringBuilder();
        String arg1 = instructions.get(1), arg2 = instructions.get(2);

        if(arg1.equals("AX") && isNumber(arg2)){
            builder.append("10111000")
                    .append(formatBinary(convertToBinary(arg2), 16));
        }
        else if(arg1.equals("AX") && !isNumber(arg2)) {
            builder.append("10100001")
                    .append(formatBinary(data.get(arg2), 16));
        }
        else if(!isNumber(arg1) && arg2.equals("AX")){
            builder.append("10100011")
                    .append(formatBinary(data.get(arg1), 16));
        }
        else if(arg1.equals("AL") && isNumber(arg2)){
            builder.append("10110000")
                    .append(formatBinary(convertToBinary(arg2), 8));
        }
        else if(arg1.equals("AL") && !isNumber(arg2)){
            builder.append("10100000")
                    .append(formatBinary(data.get(arg2), 8));
        }
        else if(!isNumber(arg1) && arg2.equals("AL")){
            builder.append("10100010")
                    .append(formatBinary(data.get(arg1), 8));
        }
        else if(arg1.equals("BX") && isNumber(arg2)){
            builder.append("10111011")
                    .append(formatBinary(convertToBinary(arg2), 16));
        }
        else if(arg1.equals("BX") && !isNumber(arg2)){
            builder.append("10001011")
                    .append(formatBinary("11110", 8))
                    .append(formatBinary(data.get(arg2), 16));
        }
        else if(!isNumber(arg1) && arg2.equals("AH")){
            builder.append("10001000")
                    .append(formatBinary("100110", 8))
                    .append(formatBinary(data.get(arg1), 8));
        }

        binaryCode.add(builder);
    }

    private String formatBinary(String value, int size){
        String result = String.format("%"+ size +"s", value)
                .replace(' ', '0');
        if(size < 9)
            return result;

        String high, low;
        low = result.substring(0, 8);
        high = result.substring(8, 16);
        return high + low;
    }

    private String convertToBinary(String value){
        return Integer.toBinaryString(Integer.parseInt(value));
    }

    private boolean isNumber(String value){
        if(value == null || value.isEmpty())
            return false;
        return value.matches("\\d+");
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public ArrayList<StringBuilder> getBinaryCode() {
        return binaryCode;
    }
}
