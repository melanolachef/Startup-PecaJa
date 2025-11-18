package com.example.pecaja;

public class Message {
    private String text;
    private boolean isUser;
    private Produto produto;

    public Message(String text, boolean isUser) {
        this.text = text;
        this.isUser = isUser;
        this.produto = null;
    }

    public Message(Produto produto) {
        this.text = null;
        this.isUser = false;
        this.produto = produto;
    }

    public String getText() {
        return text;
    }

    public boolean isUser() {
        return isUser;
    }

    public Produto getProduto() {
        return produto;
    }

    public boolean isProductMessage() {
        return produto != null;
    }
}