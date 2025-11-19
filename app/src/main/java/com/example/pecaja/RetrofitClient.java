package com.example.pecaja;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String JAVA_BASE_URL = "https://prj-startup-java.onrender.com/";

    private static final String CHAT_BASE_URL = "https://chat-bot-vanguard.onrender.com/";
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/";

    private static Retrofit retrofitJava = null;
    private static Retrofit retrofitChat = null;
    private static Retrofit retrofitViaCep = null;

    private static OkHttpClient buildHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }


    public static AuthService getAuthService() {
        if (retrofitJava == null) {
            retrofitJava = new Retrofit.Builder()
                    .baseUrl(JAVA_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(buildHttpClient())
                    .build();
        }
        return retrofitJava.create(AuthService.class);
    }


    public static ChatService getChatService() {
        if (retrofitChat == null) {
            retrofitChat = new Retrofit.Builder()
                    .baseUrl(CHAT_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(buildHttpClient())
                    .build();
        }
        return retrofitChat.create(ChatService.class);
    }

    public static ViaCepService getViaCepService() {
        if (retrofitViaCep == null) {
            retrofitViaCep = new Retrofit.Builder()
                    .baseUrl(VIA_CEP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(buildHttpClient()) // Usa o mesmo cliente
                    .build();
        }
        return retrofitViaCep.create(ViaCepService.class);
    }
}