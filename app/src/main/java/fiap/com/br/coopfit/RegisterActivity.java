package fiap.com.br.coopfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.Date;
import java.util.Iterator;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.Pessoa;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {



    java.text.SimpleDateFormat fmtOrigem = new java.text.SimpleDateFormat("dd/MM/yyyy");
    java.text.SimpleDateFormat fmtSaida = new java.text.SimpleDateFormat("yyyy-MM-dd");

    EditText txtEmail;
    EditText txtSenha;
    EditText txtNome;
    EditText txtPeso;
    EditText txtAltura;
    EditText txtNasc;
    Spinner spinnerGenero;
    EditText txtObs;
    Pessoa p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = findViewById(R.id.txt_email);
        txtSenha = findViewById(R.id.txt_senha);
        txtNome  = findViewById(R.id.txt_nome);
        txtPeso  = findViewById(R.id.txt_peso);
        txtAltura  = findViewById(R.id.txt_altura);
        txtNasc  = findViewById(R.id.txt_nasc);
        spinnerGenero = findViewById(R.id.spinner_genero);
        txtObs = findViewById(R.id.txt_obs);

    }

    public void saveChanges(View view) {

        try {

            if(!txtEmail.getText().toString().contains("@") && txtEmail.getText().toString().length() < 5){
                Toast.makeText(this, "Informe um email válido contendo @ e acima de 4 caracteres.", Toast.LENGTH_SHORT).show();
            } else if(txtSenha.getText().toString().length() < 3) {
                Toast.makeText(this, "Informe uma senha válida acima de 4 caracteres.", Toast.LENGTH_SHORT).show();
            } else {

                p = new Pessoa();
                p.setNome(txtNome.getText().toString());


                String dataNasc = txtNasc.getText().toString();

                p.setNascimento(new Date(dataNasc));
                p.setCadastro(new Date());

                p.setGenero(spinnerGenero.getSelectedItem().toString().equals("MASCULINO") ? 0 : 1);
                //p.setFoto(new byte[]{1});
                p.setEmail(txtEmail.getText().toString());
                p.setSenha(txtSenha.getText().toString());


                p.setNotificacao(true);

                p.setPeso(!txtPeso.getText().toString().equals("") ? Double.valueOf(txtPeso.getText().toString()) : 0);
                p.setAltura(!txtAltura.getText().toString().equals("") ? Double.valueOf(txtAltura.getText().toString()) : 0);

                p.setObservacao(String.valueOf(txtObs.getText()));
                p.setPerfis(1);

                CoopFitDB db = new CoopFitDB(this);

                Pessoa pessoaValidar = db.findPessoa(p.getEmail());

                if (pessoaValidar.getEmail() != null) {
                    Toast.makeText(this, "O e-mail " + p.getEmail() + " já consta cadastrado", Toast.LENGTH_SHORT).show();
                    db.close();
                } else {
                    db.insertPessoa(p);
                    db.close();
                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                    try {


                        Gson gson = new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd")
                                .create();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(CoopFitService.API_BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();


                        CoopFitService api = retrofit.create(CoopFitService.class);

                        api.setPessoa(p).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                if(response.code() == 201) {
                                    String strId = "";
                                    Headers headers = response.headers();

                                    strId = headers.value(0).replace("http://54.89.196.181:8080/pessoas/", "");

                                    Pessoa pessoa = new Pessoa();
                                    pessoa.setId(!strId.equals("") ? Long.valueOf(strId) : -1);
                                    pessoa.setEmail(p.getEmail());

                                    CoopFitDB db2 = new CoopFitDB(RegisterActivity.this);
                                    db2.setIdPessoa(pessoa);

                                    Toast.makeText(RegisterActivity.this, "Usuário " + strId + " cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, "Erro ao realizar o cadastro.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (Exception e) {
                        Toast.makeText(this, "Erro de servico " + e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
                //Encerrar a Activity de cadastro
                finish();
            }

        }catch (Exception e){
            Toast.makeText(this, "Erro ao cadastrar " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
