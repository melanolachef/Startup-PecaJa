package com.example.pecaja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messageList;
    private Context context;
    private static final int VIEW_TYPE_USER_TEXT = 0;
    private static final int VIEW_TYPE_BOT_TEXT = 1;
    private static final int VIEW_TYPE_BOT_PRODUCT = 2;

    public interface OnProductClickListener {
        void onAddToCartClick(Produto produto);
    }
    private OnProductClickListener productClickListener;

    public MessageAdapter(List<Message> messageList, OnProductClickListener listener) {
        this.messageList = messageList;
        this.productClickListener = listener;
    }


    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (message.isUser()) {
            return VIEW_TYPE_USER_TEXT;
        } else if (message.isProductMessage()) {
            return VIEW_TYPE_BOT_PRODUCT;
        } else {
            return VIEW_TYPE_BOT_TEXT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEW_TYPE_USER_TEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_user, parent, false);
                return new TextMessageViewHolder(view);
            case VIEW_TYPE_BOT_TEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_bot, parent, false);
                return new TextMessageViewHolder(view);
            case VIEW_TYPE_BOT_PRODUCT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_product, parent, false);
                return new ProductViewHolder(view);
            default:
                throw new IllegalArgumentException("Tipo de view desconhecido: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_USER_TEXT:
            case VIEW_TYPE_BOT_TEXT:
                ((TextMessageViewHolder) holder).bind(message);
                break;
            case VIEW_TYPE_BOT_PRODUCT:
                ((ProductViewHolder) holder).bind(message.getProduto());
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (messageList == null) {
            return 0;
        }
        return messageList.size();
    }

    static class TextMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage;

        TextMessageViewHolder(View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);
        }

        void bind(Message message) {
            textMessage.setText(message.getText());
        }
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduto;
        TextView tvNomeProduto, tvDescricaoProduto, tvPrecoProduto;
        Button btnAddToCart;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduto = itemView.findViewById(R.id.ivProduto);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvDescricaoProduto = itemView.findViewById(R.id.tvDescricaoProduto);
            tvPrecoProduto = itemView.findViewById(R.id.tvPrecoProduto);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }

        void bind(Produto produto) {
            tvNomeProduto.setText(produto.getNome());
            tvDescricaoProduto.setText(produto.getDescricao());

            Locale ptBr = new Locale("pt", "BR");
            NumberFormat format = NumberFormat.getCurrencyInstance(ptBr);
            tvPrecoProduto.setText(format.format(produto.getPreco()));

            Glide.with(context)
                    .load(produto.getUrlFoto())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivProduto);

            btnAddToCart.setOnClickListener(v -> {
                if (productClickListener != null) {
                    productClickListener.onAddToCartClick(produto);
                }
            });
        }
    }
}