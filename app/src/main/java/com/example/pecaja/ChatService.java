package com.example.pecaja;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ChatService {

    @POST("/chat")
    Call<ChatResponse> enviarMensagem(
            @Body ChatRequest chatRequest
    );
}