package com.example.pecaja;

public class Endereco {
    String rua;
    String numero;
    String complemento;
    String bairro;
    String cidade;
    String estado;
    String cep;

    public Endereco(String rua, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
}