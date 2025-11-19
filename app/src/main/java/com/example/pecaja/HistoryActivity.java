package com.example.pecaja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView; // Importe se quiser mostrar mensagem de vazio

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView rvHistorico;
    HistoryAdapter historyAdapter;
    List<Produto> listaDeHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistorico = findViewById(R.id.rvHistorico);

        // 1. PEGAR DADOS REAIS DO GERENCIADOR
        listaDeHistorico = HistoryManager.getInstance().getHistorico();

        historyAdapter = new HistoryAdapter(listaDeHistorico);

        rvHistorico.setLayoutManager(new LinearLayoutManager(this));
        rvHistorico.setAdapter(historyAdapter);
    }
}