package com.example.pecaja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher; // Importante
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton; // Importante
import android.widget.RadioGroup; // Importante
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etNome, etEmail, etSenha, etConfirmarSenha, etTelefone, etDocumento;

    RadioGroup rgTipoPessoa;
    RadioButton rbFisica, rbJuridica;

    EditText etRua, etNumero, etComplemento, etBairro, etCidade, etEstado, etCep;

    Button btnFinalizarCadastro;

    private AuthService authService;
    private ViaCepService viaCepService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authService = RetrofitClient.getAuthService();
        viaCepService = RetrofitClient.getViaCepService();
        sessionManager = new SessionManager(this);

        inicializarComponentes();
        configurarBuscaCep();

        btnFinalizarCadastro.setOnClickListener(v -> realizarCadastro());
    }

    private void inicializarComponentes() {
        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmailCadastro);
        etSenha = findViewById(R.id.etSenhaCadastro);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha);
        etTelefone = findViewById(R.id.etTelefone);
        etDocumento = findViewById(R.id.etDocumento);

        rgTipoPessoa = findViewById(R.id.rgTipoPessoa);
        rbFisica = findViewById(R.id.rbFisica);
        rbJuridica = findViewById(R.id.rbJuridica);

        etRua = findViewById(R.id.etRua);
        etNumero = findViewById(R.id.etNumero);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etEstado = findViewById(R.id.etEstado);
        etCep = findViewById(R.id.etCep);

        btnFinalizarCadastro = findViewById(R.id.btnFinalizarCadastro);
    }

    private void configurarBuscaCep() {
        etCep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 8) {
                    buscarEndereco(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void buscarEndereco(String cep) {
        Toast.makeText(this, "Buscando CEP...", Toast.LENGTH_SHORT).show();

        viaCepService.buscarEndereco(cep).enqueue(new Callback<ViaCepResponse>() {
            @Override
            public void onResponse(Call<ViaCepResponse> call, Response<ViaCepResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ViaCepResponse endereco = response.body();

                    etRua.setText(endereco.getLogradouro());
                    etBairro.setText(endereco.getBairro());
                    etCidade.setText(endereco.getLocalidade());
                    etEstado.setText(endereco.getUf());

                    etNumero.requestFocus();
                } else {
                    Toast.makeText(RegisterActivity.this, "CEP não encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViaCepResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Erro ao buscar CEP.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void realizarCadastro() {
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();
        String confirmar = etConfirmarSenha.getText().toString().trim();

        if (!senha.equals(confirmar)) {
            Toast.makeText(this, "As senhas não conferem!", Toast.LENGTH_SHORT).show();
            return;
        }

        String tipoPessoaSelecionada = "Fisica";
        int selectedId = rgTipoPessoa.getCheckedRadioButtonId();
        if (selectedId == R.id.rbJuridica) {
            tipoPessoaSelecionada = "Juridica";
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
                tipoPessoaSelecionada, // Passa o valor selecionado
                endereco
        );

        authService.cadastrar(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Cadastro OK! Entrando...", Toast.LENGTH_SHORT).show();
                    realizarLoginAutomatico(email, senha);
                } else {

                    try {
                        String erroBruto = response.errorBody().string();

                        org.json.JSONObject jsonObject = new org.json.JSONObject(erroBruto);

                        String mensagemAmigavel;

                        if (jsonObject.has("message")) {
                            mensagemAmigavel = jsonObject.getString("message");
                        } else if (jsonObject.has("error")) {
                            mensagemAmigavel = jsonObject.getString("error");
                        } else {

                            mensagemAmigavel = "Erro no servidor (" + response.code() + ")";
                        }

                        Toast.makeText(RegisterActivity.this, mensagemAmigavel, Toast.LENGTH_LONG).show();

                        Log.e("CadastroErro", "JSON Completo: " + erroBruto);

                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, "Ocorreu um erro desconhecido. Tente novamente.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void realizarLoginAutomatico(String email, String senha) {
        LoginRequest loginRequest = new LoginRequest(email, senha);

        authService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sessionManager.saveAuthToken(response.body().getToken());
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Falha no login automático.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Erro de rede no login.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}