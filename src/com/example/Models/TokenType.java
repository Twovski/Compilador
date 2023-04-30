package com.example.Models;

public enum TokenType {
    IDENTIFIER,         // Identificador
    COMMA,              // Coma ","
    SEMICOLON,          // Punto y coma ";"
    LEFT_PAREN,         // Paréntesis de apertura "("
    RIGHT_PAREN,        // Paréntesis de cierre ")"
    LEFT_BRACE,         // Llave de apertura "{"
    RIGHT_BRACE,        // Llave de cierre "}"
    TYPE_VOID,          // Palabra clave "void"
    TYPE_INTEGER,       // Palabra clave "integer"
    INTEGER,            // Número entero
    TYPE_FLOAT,         // Palabra clave "float"
    FLOAT,              // Número flotante
    TYPE_STRING,        // Palabra clave "string"
    STRING,             // Cadena de caracteres
    TYPE_BOOLEAN,       // Palabra clave "boolean"
    BOOLEAN,            // Valor booleano (true/false)
    FUNCTION,           // Palabra clave "function"
    IF,                 // Palabra clave "if"
    ELSE_IF,            // Palabra clave "else if"
    ELSE,               // Palabra clave "else"
    WHILE,              // Palabra clave "while"
    AND,                // Palabra clave "and"
    OR,                 // Palabra clave "or"
    NOT,                // Palabra clave "not"
    INCREMENT,          // Operador de incremento "++"
    DECREMENT,          // Operador de decremento "--"
    MINUS_EQUALS,       // Operador "-="
    PLUS_EQUALS,        // Operador "+="
    TIMES_EQUALS,       // Operador "*="
    DIVIDE_EQUALS,      // Operador "/="
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

