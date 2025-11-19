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
    HistoryAdapter historyAdapter; // Você pode reutilizar o CartAdapter se quiser, ou manter o HistoryAdapter
    List<Produto> listaDeHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistorico = findViewById(R.id.rvHistorico);

        // 1. PEGAR DADOS REAIS DO GERENCIADOR
        listaDeHistorico = HistoryManager.getInstance().getHistorico();

        // 2. Configurar Adapter
        // (Nota: O HistoryAdapter precisa ser capaz de exibir os dados do Produto igual o CartAdapter)
        // Se o seu HistoryAdapter for muito simples, considere usar o CartAdapter aqui também,
        // mas passando null no listener se não quiser botões de editar.
        // Para simplificar, vou assumir que você vai atualizar o HistoryAdapter ou usar uma lógica similar.

        historyAdapter = new HistoryAdapter(listaDeHistorico);

        rvHistorico.setLayoutManager(new LinearLayoutManager(this));
        rvHistorico.setAdapter(historyAdapter);
    }
}