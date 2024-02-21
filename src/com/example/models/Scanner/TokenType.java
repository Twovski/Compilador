package com.example.models.Scanner;

public enum TokenType {
    IDENTIFIER,         // Identificador
    SEMICOLON,          // Punto y coma ";"
    LEFT_PAREN,         // Paréntesis de apertura "("
    RIGHT_PAREN,        // Paréntesis de cierre ")"
    LEFT_BRACE,         // Llave de apertura "{"
    RIGHT_BRACE,        // Llave de cierre "}"
    TYPE_INTEGER,       // Palabra clave "integer"
    INTEGER,            // Número entero
    TYPE_BOOLEAN,       // Palabra clave "boolean"
    BOOLEAN,            // Valor booleano (true/false)
    IF_RESERVED,                 // Palabra clave "if"
    ELSE_RESERVED,               // Palabra clave "else"
    WHILE_RESERVED,              // Palabra clave "while"
    AND,                // Palabra clave "and"
    OR,                 // Palabra clave "or"
    NOT,                // Palabra clave "not"
    GREATER_EQUAL,      // Operador de comparación ">="
    GREATER_THAN,       // Operador de comparación ">"
    LESS_THAN,          // Operador de comparación "<"
    LESS_EQUAL,         // Operador de comparación "<="
    NOT_EQUAL,          // Operador de comparación "!="
    EQUAL_EQUAL,        // Operador de comparación "=="
    MULTIPLY,           // Operador de multiplicación "*"
    PLUS,               // Operador de suma "+"
    MINUS,              // Operador de resta "-"
    DIVIDE,              // Operador de división "/"
    PERCENT,            // Operador de módulo "%"
    EQUAL,              // Operador de asignación "="
    UNKNOWN    // Token desconocido o no reconocido
}
