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

    // MUDANÇA AQUI: Agora retorna boolean
    public boolean adicionarProduto(Produto novoProduto) {
        for (Produto p : produtosDoCarrinho) {
            if (p.getId() == novoProduto.getId()) {
                // Verifica se aumentar 1 vai estourar o estoque
                if (p.getQuantidade() < p.getEstoqueMaximo()) {
                    p.incrementarQuantidade();
                    return true; // Sucesso
                } else {
                    return false; // Estoque cheio
                }
            }
        }

        // Se é um produto novo, verifica se tem pelo menos 1 no estoque
        if (novoProduto.getEstoqueMaximo() > 0) {
            novoProduto.setQuantidade(1);
            produtosDoCarrinho.add(novoProduto);
            return true;
        } else {
            return false; // Produto sem estoque nenhum
        }
    }

    public void removerProduto(Produto produto) {
        produtosDoCarrinho.remove(produto);
    }

    // Pegar a lista
    public List<Produto> getProdutos() {
        return produtosDoCarrinho;
    }

    // Calcular o total
    public double getValorTotal() {
        double total = 0.0;
        for (Produto p : produtosDoCarrinho) {
            total += (p.getPreco() * p.getQuantidade());
        }
        return total;
    }

    // Limpar carrinho (útil para depois de finalizar compra)
    public void limparCarrinho() {
        produtosDoCarrinho.clear();
    }
}