package com.example.pecaja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Produto> listaProdutos;
    private Context context;
    private CartListener listener;

    public interface CartListener {
        void onCartUpdated();
    }

    public CartAdapter(List<Produto> listaProdutos, CartListener listener) {
        this.listaProdutos = listaProdutos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_produto_carrinho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);

        holder.tvNomeProduto.setText(produto.getNome());
        holder.tvQuantidade.setText(String.valueOf(produto.getQuantidade()));

        double totalItem = produto.getPreco() * produto.getQuantidade();
        Locale ptBr = new Locale("pt", "BR");
        String precoFormatado = NumberFormat.getCurrencyInstance(ptBr).format(totalItem);
        holder.tvPrecoProduto.setText(precoFormatado);

        Glide.with(context)
                .load(produto.getUrlFoto())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.ivProdutoCarrinho);


        holder.btnMais.setOnClickListener(v -> {
            if (produto.getQuantidade() < produto.getEstoqueMaximo()) {
                produto.incrementarQuantidade();
                notifyItemChanged(holder.getAdapterPosition());
                listener.onCartUpdated();
            } else {

               Toast.makeText(context, "Máximo em estoque", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnMenos.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();

            if (currentPosition == RecyclerView.NO_POSITION) return;

            if (produto.getQuantidade() > 1) {

                produto.decrementarQuantidade();
                notifyItemChanged(currentPosition);
            } else {

                CartManager.getInstance().removerProduto(produto);

                notifyItemRemoved(currentPosition);

                notifyItemRangeChanged(currentPosition, listaProdutos.size());
            }

            listener.onCartUpdated();
        });
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeProduto, tvPrecoProduto, tvQuantidade;
        ImageView ivProdutoCarrinho;
        ImageButton btnMais, btnMenos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvPrecoProduto = itemView.findViewById(R.id.tvPrecoProduto);
            ivProdutoCarrinho = itemView.findViewById(R.id.ivProdutoCarrinho);
            tvQuantidade = itemView.findViewById(R.id.tvQuantidade);
            btnMais = itemView.findViewById(R.id.btnMais);
            btnMenos = itemView.findViewById(R.id.btnMenos);
        }
    }
}