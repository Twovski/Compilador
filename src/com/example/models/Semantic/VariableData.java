package com.example.models.Semantic;

import com.example.models.Scanner.TokenType;

public class VariableData {
    public TokenType type;
    public String name, value;

    public VariableData(TokenType type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TokenData{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
