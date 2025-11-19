package com.example.pecaja;

import java.util.ArrayList;
import java.util.List;

public class HistoryManager {

    private static HistoryManager instance;
    private List<Produto> historicoDeCompras;

    private HistoryManager() {
        historicoDeCompras = new ArrayList<>();
    }

    public static synchronized HistoryManager getInstance() {
        if (instance == null) {
            instance = new HistoryManager();
        }
        return instance;
    }

    public void adicionarCompra(List<Produto> produtosComprados) {
        historicoDeCompras.addAll(new ArrayList<>(produtosComprados));
    }

    public List<Produto> getHistorico() {
        return historicoDeCompras;
    }
}