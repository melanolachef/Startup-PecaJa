package com.example.pecaja;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    String username;

    @SerializedName("password") // Assumindo que a API espera "password"
    String senha;

    public LoginRequest(String email, String senha) {
        this.username = email;
        this.senha = senha;
    }
}