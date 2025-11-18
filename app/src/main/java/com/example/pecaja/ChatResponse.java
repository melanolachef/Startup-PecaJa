package com.example.pecaja;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ChatResponse {

    @SerializedName("resposta")
    private List<Produto> resposta;

    public List<Produto> getResposta() {
        return resposta;
    }
}