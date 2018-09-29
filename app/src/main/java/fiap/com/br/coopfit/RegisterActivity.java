package fiap.com.br.coopfit;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {



    java.text.DateFormat dateFormatFrom = new java.text.SimpleDateFormat("DD/MM/YYYY");
    java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("YYYY-MM-DD");

    EditText txtEmail;
    EditText txtSenha;
    EditText txtNome;
    EditText txtPeso;
    EditText txtAltura;
    EditText txtNasc;
    Spinner spinnerGenero;
    EditText txtObs;

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

    @TargetApi(Build.VERSION_CODES.O)
    public void saveChanges(View view) {



        try {

            if(!txtEmail.getText().toString().contains("@") && txtEmail.getText().toString().length() < 5){
                Toast.makeText(this, "Informe um email válido contendo @ e acima de 4 caracteres.", Toast.LENGTH_SHORT).show();
            } else if(txtSenha.getText().toString().length() < 3) {
                Toast.makeText(this, "Informe uma senha válida acima de 4 caracteres.", Toast.LENGTH_SHORT).show();
            } else {

                Pessoa p = new Pessoa();
                p.setNome(txtNome.getText().toString());


//                Date dataOrigem = !String.valueOf(txtNasc.getText()).equals("") ? new Date(dateFormatFrom.format(txtNasc.getText().toString())) : new Date();
//                Date data = !String.valueOf(txtNasc.getText()).equals("") ? dateFormat.parse(String.valueOf(txtNasc.getText()).replace("/", "-")) : new Date();
//                Date data = dateFormat.parse(String.valueOf(dataOrigem));


                p.setNascimento(txtNasc.getText().toString());

                //DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                String Data = fmt.format(Calendar.getInstance().getTime());
                p.setCadastro(fmt.parse(Data));

                p.setGenero(spinnerGenero.getSelectedItem().toString().equals("MASCULINO") ? 0 : 1);
                //p.setFoto(new byte[]{1});
                p.setEmail(txtEmail.getText().toString());
                p.setSenha(txtSenha.getText().toString());

//                String dataCadastroOrigem = dateFormatFrom.format(new Date());
//                String dataCadastro = dateFormat.format(new Date(dataCadastroOrigem));

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


//                        Date data = !String.valueOf(txtNasc.getText()).equals("") ? dateFormat.parse(String.valueOf(txtNasc.getText()).replace("/", "-")) : new Date();

//                        p.setCadastro(data);

                        Gson gson2 = new Gson();
                        String x = gson2.toJson(p);

                        CoopFitService api = retrofit.create(CoopFitService.class);
                        api.setPessoa(p).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                if(response.code() == 201) {

                                    String id = response.headers().get("https://coopfit.herokuapp.com/pessoas/{id}");

                                    Toast.makeText(RegisterActivity.this, "Usuário " + id + " cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

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
