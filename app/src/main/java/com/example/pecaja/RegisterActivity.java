package com.example.pecaja;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText etNome, etEmailCadastro, etSenhaCadastro, etConfirmarSenha;
    Button btnFinalizarCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Vincular os componentes da tela
        etNome = findViewById(R.id.etNome);
        etEmailCadastro = findViewById(R.id.etEmailCadastro);
        etSenhaCadastro = findViewById(R.id.etSenhaCadastro);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha);
        btnFinalizarCadastro = findViewById(R.id.btnFinalizarCadastro);

        // Configurar o clique do botão
        btnFinalizarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aqui entraria a lógica de validação e salvamento do usuário
                // Por enquanto, vamos apenas mostrar uma mensagem e voltar para o login

                Toast.makeText(RegisterActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                // Fecha a tela de cadastro e volta para a anterior (Login)
                finish();
            }
        });
    }
}