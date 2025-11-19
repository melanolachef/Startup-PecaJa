package com.example.pecaja;

public class ViaCepResponse {
    private String logradouro; // Rua
    private String bairro;
    private String localidade; // Cidade
    private String uf;         // Estado

    // Getters
    public String getLogradouro() { return logradouro; }
    public String getBairro() { return bairro; }
    public String getLocalidade() { return localidade; }
    public String getUf() { return uf; }
}