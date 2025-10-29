package com.example.pecaja;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    String email;

    @SerializedName("password") // Assumindo que a API espera "password"
    String senha;

    public LoginRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
}