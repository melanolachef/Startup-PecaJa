package com.example.pecaja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent; // Importante para mudar de tela
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    // Campos Pessoais
    EditText etNome, etEmail, etSenha, etConfirmarSenha, etTelefone, etDocumento, etTipoPessoa;

    // Campos de Endereço
    EditText etRua, etNumero, etComplemento, etBairro, etCidade, etEstado, etCep;

    Button btnFinalizarCadastro;

    private AuthService authService;
    private SessionManager sessionManager; // NOVO: Precisamos disso para salvar o token

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authService = RetrofitClient.getAuthService();
        sessionManager = new SessionManager(this); // NOVO: Inicializa a sessão

        inicializarComponentes();

        btnFinalizarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarCadastro();
            }
        });
    }

    private void inicializarComponentes() {
        // Pessoais
        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmailCadastro);
        etSenha = findViewById(R.id.etSenhaCadastro);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha);
        etTelefone = findViewById(R.id.etTelefone);
        etDocumento = findViewById(R.id.etDocumento);
        etTipoPessoa = findViewById(R.id.etTipoPessoa);

        // Endereço
        etRua = findViewById(R.id.etRua);
        etNumero = findViewById(R.id.etNumero);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etEstado = findViewById(R.id.etEstado);
        etCep = findViewById(R.id.etCep);

        btnFinalizarCadastro = findViewById(R.id.btnFinalizarCadastro);
    }

    private void realizarCadastro() {
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();
        String confirmar = etConfirmarSenha.getText().toString().trim();

        if (!senha.equals(confirmar)) {
            Toast.makeText(this, "As senhas não conferem!", Toast.LENGTH_SHORT).show();
            return;
        }

        Endereco endereco = new Endereco(
                etRua.getText().toString(),
                etNumero.getText().toString(),
                etComplemento.getText().toString(),
                etBairro.getText().toString(),
                etCidade.getText().toString(),
                etEstado.getText().toString(),
                etCep.getText().toString()
        );

        RegisterRequest request = new RegisterRequest(
                etNome.getText().toString(),
                email,
                senha,
                etTelefone.getText().toString(),
                etDocumento.getText().toString(),
                etTipoPessoa.getText().toString(),
                endereco
        );

        // 1. TENTA CADASTRAR
        authService.cadastrar(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Cadastro OK! Entrando...", Toast.LENGTH_SHORT).show();

                    // 2. SE DEU CERTO, JÁ FAZ O LOGIN AUTOMATICAMENTE
                    realizarLoginAutomatico(email, senha);

                } else {
                    // Tratamento de erro melhorado (do passo anterior)
                    try {
                        String erroMensagem = response.errorBody().string();
                        Log.e("CadastroErro", "Erro: " + erroMensagem);
                        Toast.makeText(RegisterActivity.this, "Erro: " + erroMensagem, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, "Erro desconhecido ao cadastrar.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // NOVO MÉTODO: Faz o login nos bastidores
    private void realizarLoginAutomatico(String email, String senha) {
        LoginRequest loginRequest = new LoginRequest(email, senha);

        authService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    // 3. PEGA O TOKEN
                    String token = response.body().getToken();

                    // 4. SALVA NA SESSÃO
                    sessionManager.saveAuthToken(token);

                    // 5. VAI PARA A HOME
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    // Estas flags limpam o histórico para o usuário não voltar ao cadastro ao clicar em "Voltar"
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(RegisterActivity.this, "Cadastro feito, mas falha no login automático.", Toast.LENGTH_LONG).show();
                    finish(); // Volta para a tela de login manual
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Erro de rede no login automático.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}