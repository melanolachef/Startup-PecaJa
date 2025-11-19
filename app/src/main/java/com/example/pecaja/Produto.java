package com.example.pecaja;

import com.google.gson.annotations.SerializedName;

public class Produto {

    @SerializedName("id")
    private int id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("descricao")
    private String descricao;

    @SerializedName("preco")
    private double preco;


    @SerializedName("urlFoto")
    private String urlFoto;

    @SerializedName("quantidadeEstoque")
    private int estoqueMaximo;

    private int quantidade = 1;


    public Produto(String nome) {
        this.nome = nome;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void incrementarQuantidade() {
        this.quantidade++;
    }

    public int getEstoqueMaximo() {
        return estoqueMaximo;
    }

    public void decrementarQuantidade() {
        if (this.quantidade > 0) {
            this.quantidade--;
        }
    }
}