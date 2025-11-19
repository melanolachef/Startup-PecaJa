package com.example.pecaja;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Produto> listaHistorico;

    public HistoryAdapter(List<Produto> listaHistorico) {
        this.listaHistorico = listaHistorico;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Reutilizando o layout do carrinho para facilitar, ou mantenha o seu item_produto_carrinho
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto_carrinho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = listaHistorico.get(position);

        holder.tvNomeProduto.setText(produto.getNome());

        // Mostra quanto foi comprado e o preço
        String info = "Qtd: " + produto.getQuantidade() + " - R$ " + produto.getPreco();
        holder.tvPrecoProduto.setText(info);

        // Esconde os botões de editar quantidade, pois é histórico
        holder.itemView.findViewById(R.id.btnMais).setVisibility(View.GONE);
        holder.itemView.findViewById(R.id.btnMenos).setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return listaHistorico.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeProduto, tvPrecoProduto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvPrecoProduto = itemView.findViewById(R.id.tvPrecoProduto);
        }
    }
}