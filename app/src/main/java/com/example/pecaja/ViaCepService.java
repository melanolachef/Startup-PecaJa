package com.example.pecaja;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {
    @GET("{cep}/json/")
    Call<ViaCepResponse> buscarEndereco(@Path("cep") String cep);
}