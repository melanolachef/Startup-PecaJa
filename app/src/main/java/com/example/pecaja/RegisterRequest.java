package com.example.pecaja;

public class RegisterRequest {
    String email;
    String password;
    String telefone;
    String nomeCompleto;
    String documento;
    String tipoPessoa;
    String tipoUsuario;
    Endereco endereco;

    public RegisterRequest(String nomeCompleto, String email, String password, String telefone, String documento, String tipoPessoa, Endereco endereco) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.password = password;
        this.telefone = telefone;
        this.documento = documento;
        this.tipoPessoa = tipoPessoa;
        this.endereco = endereco;
        this.tipoUsuario = "COMPRADOR";
    }
}