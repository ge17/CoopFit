package fiap.com.br.coopfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

    EditText txtEmail;
    EditText txtSenha;
    EditText txtNome;
    EditText txtPeso;
    EditText txtAltura;
    EditText txtNasc;

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
    }

    public void saveChanges(View view) {

        try {

            if(!txtEmail.getText().toString().contains("@") && txtEmail.getText().toString().length() < 5){
                Toast.makeText(this, "Informe um email válido contendo @ e acima de 4 caracteres.", Toast.LENGTH_SHORT).show();
            } else if(txtSenha.getText().toString().length() < 3) {
                Toast.makeText(this, "Informe uma senha válida acima de 4 caracteres.", Toast.LENGTH_SHORT).show();
            } else {

                Pessoa p = new Pessoa();


                p.setEmail(txtEmail.getText().toString());
                p.setSenha(txtSenha.getText().toString());
                p.setNome(txtNome.getText().toString());
                p.setPeso(!txtPeso.getText().toString().equals("") ? Double.valueOf(txtPeso.getText().toString()) : 0);
                p.setAltura(!txtAltura.getText().toString().equals("") ? Double.valueOf(txtAltura.getText().toString()) : 0);

                Date data = !String.valueOf(txtNasc.getText()).equals("") ? dateFormat.parse(String.valueOf(txtNasc.getText()).replace("/", "-")) : new Date();
                p.setNascimento(data);

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


                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(CoopFitService.API_BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

//        if(!retrofit.baseUrl().equals("http://10.0.2.2:8080/")){
//            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://192.168.0.106:8080/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }


                        CoopFitService api = retrofit.create(CoopFitService.class);

                        api.setPessoa(p).enqueue(new Callback<Pessoa>() {


                            @Override
                            public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {


                                Toast.makeText(RegisterActivity.this, "Servico ok ", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Pessoa> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, "Erro " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (Exception e) {
                        Toast.makeText(this, "Erro de servico " + e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
                //Encerrar a Activity de login
                finish();
            }

        }catch (Exception e){
            Toast.makeText(this, "Erro ao cadastrar " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
