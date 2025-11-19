package com.example.pecaja;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static CartManager instance;
    private List<Produto> produtosDoCarrinho;

    private CartManager() {
        produtosDoCarrinho = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public boolean adicionarProduto(Produto novoProduto) {
        for (Produto p : produtosDoCarrinho) {
            if (p.getId() == novoProduto.getId()) {
                if (p.getQuantidade() < p.getEstoqueMaximo()) {
                    p.incrementarQuantidade();
                    return true;
                } else {
                    return false;
                }
            }
        }

        if (novoProduto.getEstoqueMaximo() > 0) {
            novoProduto.setQuantidade(1);
            produtosDoCarrinho.add(novoProduto);
            return true;
        } else {
            return false;
        }
    }

    public void removerProduto(Produto produto) {
        produtosDoCarrinho.remove(produto);
    }

    public List<Produto> getProdutos() {
        return produtosDoCarrinho;
    }

    public double getValorTotal() {
        double total = 0.0;
        for (Produto p : produtosDoCarrinho) {
            total += (p.getPreco() * p.getQuantidade());
        }
        return total;
    }

    public void limparCarrinho() {
        produtosDoCarrinho.clear();
    }
}