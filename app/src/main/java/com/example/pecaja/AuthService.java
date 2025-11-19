package com.example.pecaja;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/usuario/create")
    Call<Void> cadastrar(@Body RegisterRequest registerRequest);
}