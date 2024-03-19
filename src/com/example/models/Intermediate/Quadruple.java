package com.example.models.Intermediate;

public class Quadruple {
    public String operator, arg1, arg2, result;
    public Quadruple(String operator, String arg1, String arg2, String result) {
        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
    }

    @Override
    public String toString() {
        return "Quadruples{" +
                "operator='" + operator + '\'' +
                ", arg1='" + arg1 + '\'' +
                ", arg2='" + arg2 + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
