package com.example.pecaja;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// A estrutura é idêntica à do CartAdapter, mas adaptada para o histórico
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Produto> listaHistorico;

    public HistoryAdapter(List<Produto> listaHistorico) {
        this.listaHistorico = listaHistorico;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto_carrinho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = listaHistorico.get(position);
        holder.tvNomeProduto.setText(produto.getNome());
    }

    @Override
    public int getItemCount() {
        return listaHistorico.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeProduto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
        }
    }
}