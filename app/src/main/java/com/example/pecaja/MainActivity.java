package com.example.pecaja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Importe o Log
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call; // Importe
import retrofit2.Callback; // Importe
import retrofit2.Response; // Importe

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etSenha;
    Button btnCadastrar, btnEntrar;
    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnEntrar = findViewById(R.id.btnEntrar);

        apiService = RetrofitClient.getApiService();
        sessionManager = new SessionManager(this);


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String senha = etSenha.getText().toString().trim();

                if (email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha email e senha!", Toast.LENGTH_SHORT).show();
                    return;
                }

                fazerLogin(email, senha);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fazerLogin(String email, String senha) {
        // 1. Criar o objeto de requisição
        LoginRequest request = new LoginRequest(email, senha);


        apiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    String token = response.body().getToken();

                    // Salva o token na sessão
                    sessionManager.saveAuthToken(token);

                    Toast.makeText(MainActivity.this, "Login com sucesso!", Toast.LENGTH_SHORT).show();

                    //
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Erro de login (email/senha errados, HTTP 401, 403, etc.)
                    Toast.makeText(MainActivity.this, "Email ou senha inválidos.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // 3.B. Erro de rede (sem internet, servidor fora, URL errada)
                Toast.makeText(MainActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("LoginError", "Erro: " + t.getMessage());
                // Lembrete: Se demorar, pode ser o servidor do Render "acordando"
            }
        });
    }
}
