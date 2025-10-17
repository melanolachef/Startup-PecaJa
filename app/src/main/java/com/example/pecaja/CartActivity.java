package com.example.pecaja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvProdutos;
    CartAdapter cartAdapter;
    List<Produto> listaDeProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvProdutos = findViewById(R.id.rvProdutos);


        listaDeProdutos = new ArrayList<>();
        listaDeProdutos.add(new Produto("Filtro de Óleo - Modelo X"));
        listaDeProdutos.add(new Produto("Vela de Ignição - Spark V2"));
        listaDeProdutos.add(new Produto("Pastilha de Freio - StopMax"));
        listaDeProdutos.add(new Produto("Correia Dentada - PowerGrip"));
        listaDeProdutos.add(new Produto("Amortecedor Dianteiro - ConfortRide"));


        cartAdapter = new CartAdapter(listaDeProdutos);


        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        rvProdutos.setAdapter(cartAdapter);
    }
}