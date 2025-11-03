package com.example.pecaja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton fabCart;
    Button btnHistorico;
    RecyclerView recyclerChat;
    EditText etPedido;
    ImageButton btnSend;
    MessageAdapter adapter;
    List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fabCart = findViewById(R.id.fabCart);
        btnHistorico = findViewById(R.id.btnHistorico);
        recyclerChat = findViewById(R.id.recyclerChat);
        etPedido = findViewById(R.id.etPedido);
        btnSend = findViewById(R.id.btnSend);

        messageList = new ArrayList<>();
        adapter = new MessageAdapter(messageList);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerChat.setAdapter(adapter);

        fabCart.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        });

        btnHistorico.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
        });

        btnSend.setOnClickListener(v -> {
            String text = etPedido.getText().toString().trim();
            if (!text.isEmpty()) {
                addMessage(text, true); // mensagem do usuário
                etPedido.setText("");

                // Resposta simulada da IA
                recyclerChat.postDelayed(() -> {
                    addMessage("Olá eu sou o Bot do Peça Ja! Aguarde um momento pois a equipe esta trabalhando no meu funcionamento, obrigado pela compreensão", false);
                }, 800);
            }
        });
    }

    private void addMessage(String text, boolean isUser) {
        messageList.add(new Message(text, isUser));
        adapter.notifyItemInserted(messageList.size() - 1);
        recyclerChat.scrollToPosition(messageList.size() - 1);
    }
}
