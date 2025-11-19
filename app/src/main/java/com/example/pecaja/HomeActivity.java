package com.example.pecaja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements MessageAdapter.OnProductClickListener {

    FloatingActionButton fabCart;
    Button btnHistorico;
    RecyclerView recyclerChat;
    EditText etPedido;
    ImageButton btnSend;
    MessageAdapter adapter;
    List<Message> messageList;

    private ChatService chatService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fabCart = findViewById(R.id.fabCart);
        btnHistorico = findViewById(R.id.btnHistorico);
        recyclerChat = findViewById(R.id.recyclerChat);
        etPedido = findViewById(R.id.etPedido);
        btnSend = findViewById(R.id.btnSend);

        chatService = RetrofitClient.getChatService();
        sessionManager = new SessionManager(this);

        messageList = new ArrayList<>();

        adapter = new MessageAdapter(messageList, this);

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

                addMessage(text, true);
                etPedido.setText("");

                enviarMensagemParaApi(text);
            }
        });

        addMessage("Olá! Sou o assistente Peça Já!. Digite o que você precisa.", false);
    }

    private void enviarMensagemParaApi(String textoMensagem) {
        String token = sessionManager.getAuthToken();
        if (token == null) {
            Toast.makeText(this, "Sessão expirada. Faça login novamente.", Toast.LENGTH_LONG).show();
            return;
        }

        ChatRequest request = new ChatRequest(textoMensagem, token);

        chatService.enviarMensagem(request).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Produto> produtos = response.body().getResposta();

                    if (produtos != null && !produtos.isEmpty()) {

                        for (Produto p : produtos) {
                            addProductMessage(p);
                        }
                    } else {
                        addMessage("Poxa, procurei aqui mas não encontrei nada sobre '" + textoMensagem + "'. 😕\n\nTente buscar por termos mais simples, como 'Freio', 'Óleo' ou o modelo da moto.", false);
                    }
                } else {
                    if (response.code() == 404) {
                        addMessage("Não encontrei nenhum produto com essa descrição no nosso catálogo.", false);
                    } else {
                        addMessage("Opa, tive um soluço aqui nos meus sistemas. Tente perguntar novamente. (Erro " + response.code() + ")", false);
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                addMessage("Parece que minha conexão caiu. Verifique seu Wi-Fi ou 4G e tente novamente.", false);
                Log.e("ChatAPI", "Falha técnica: " + t.getMessage());
            }
        });
    }

    private void addMessage(String text, boolean isUser) {
        messageList.add(new Message(text, isUser));
        adapter.notifyItemInserted(messageList.size() - 1);
        recyclerChat.scrollToPosition(messageList.size() - 1);
    }

    private void addProductMessage(Produto produto) {
        messageList.add(new Message(produto));
        adapter.notifyItemInserted(messageList.size() - 1);
        recyclerChat.scrollToPosition(messageList.size() - 1);
    }

    @Override
    public void onAddToCartClick(Produto produto) {
        boolean sucesso = CartManager.getInstance().adicionarProduto(produto);

        if (sucesso) {
            Toast.makeText(this, produto.getNome() + " adicionado!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Estoque limite atingido para este item!", Toast.LENGTH_LONG).show();
        }
    }
}