package com.example.pecaja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

// Implementa a interface do Adapter
public class CartActivity extends AppCompatActivity implements CartAdapter.CartListener {

    RecyclerView rvProdutos;
    CartAdapter cartAdapter;
    List<Produto> listaDeProdutos;
    TextView tvTotalCarrinho;
    Button btnFinalizarCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvProdutos = findViewById(R.id.rvProdutos);
        tvTotalCarrinho = findViewById(R.id.tvTotalCarrinho);
        btnFinalizarCompra = findViewById(R.id.btnPagar);

        listaDeProdutos = CartManager.getInstance().getProdutos();

        // Passa "this" como listener
        cartAdapter = new CartAdapter(listaDeProdutos, this);

        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        rvProdutos.setAdapter(cartAdapter);

        atualizarTotal();

        btnFinalizarCompra.setOnClickListener(v -> {
            if(listaDeProdutos.isEmpty()) {
                Toast.makeText(this, "Seu carrinho está vazio!", Toast.LENGTH_SHORT).show();
            } else {
                // 1. Adicionar itens ao Histórico
                HistoryManager.getInstance().adicionarCompra(listaDeProdutos);

                // 2. Limpar o Carrinho
                CartManager.getInstance().limparCarrinho();

                // 3. Feedback e Navegação
                Toast.makeText(this, "Compra realizada com sucesso!", Toast.LENGTH_LONG).show();

                // Atualiza a tela (lista fica vazia e total zero)
                cartAdapter.notifyDataSetChanged();
                atualizarTotal();

                // Opcional: Fechar a tela e voltar pra home
                finish();
            }
        });
    }

    @Override
    public void onCartUpdated() {
        atualizarTotal();
    }

    private void atualizarTotal() {
        double total = CartManager.getInstance().getValorTotal();
        Locale ptBr = new Locale("pt", "BR");
        String totalFormatado = NumberFormat.getCurrencyInstance(ptBr).format(total);
        tvTotalCarrinho.setText("Total: " + totalFormatado);
    }
}