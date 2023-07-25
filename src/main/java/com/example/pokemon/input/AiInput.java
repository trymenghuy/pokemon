package com.example.pokemon.input;

public class AiInput {
    private String text;

    public AiInput() {
    }

    public AiInput(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}