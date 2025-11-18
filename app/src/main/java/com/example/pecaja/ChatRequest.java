package com.example.pecaja;

import com.google.gson.annotations.SerializedName;

public class ChatRequest {

    String mensagem;

    @SerializedName("token")
            String token;

    public ChatRequest(String mensagem, String token) {
        this.mensagem = mensagem;
        this.token = token;
    }
}