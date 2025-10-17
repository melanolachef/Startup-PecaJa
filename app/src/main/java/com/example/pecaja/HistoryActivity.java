package com.example.pecaja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView rvHistorico;
    HistoryAdapter historyAdapter;
    List<Produto> listaDeHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // 1. Vincular o RecyclerView
        rvHistorico = findViewById(R.id.rvHistorico);

        // 2. Criar a lista de dados de exemplo
        listaDeHistorico = new ArrayList<>();
        listaDeHistorico.add(new Produto("Pedido #1: Jogo de Pistões"));
        listaDeHistorico.add(new Produto("Pedido #2: Bomba d'água"));
        listaDeHistorico.add(new Produto("Pedido #3: Kit de Juntas Completo"));
        listaDeHistorico.add(new Produto("Pedido #4: Filtro de Óleo"));

        // 3. Configurar o Adapter
        historyAdapter = new HistoryAdapter(listaDeHistorico);

        // 4. Configurar o RecyclerView
        rvHistorico.setLayoutManager(new LinearLayoutManager(this));
        rvHistorico.setAdapter(historyAdapter);
    }
}