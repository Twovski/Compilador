package com.example.models.Intermediate;

public enum Operator {
    MULTIPLY(5),
    DIVIDE(5),
    ADD(4),
    SUBTRACT(4),
    GREATER_EQUAL(3),
    GREATER_THAN(3),
    LESS_THAN(3),
    LESS_EQUAL(3),
    NOT_EQUAL(3),
    EQUAL_EQUAL(3),
    NOT(2),
    AND(1),
    OR(1),
    EQUAL(0),
    IF_RESERVED(0),
    ELSE_RESERVED(0),
    WHILE_RESERVED(0);
    final int precedence;
    Operator(int precedence) {
        this.precedence = precedence;
    }
}
