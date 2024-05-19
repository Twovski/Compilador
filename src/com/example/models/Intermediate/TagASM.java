package com.example.models.Intermediate;

public class TagASM {
    private int index;
    private StringBuilder tag;

    public TagASM(int index, StringBuilder tag) {
        this.index = index;
        this.tag = tag;
    }

    public int getIndex() {
        return index;
    }

    public StringBuilder getTag() {
        return tag;
    }
}
