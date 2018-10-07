package fiap.com.br.coopfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.EmailDTO;
import fiap.com.br.coopfit.to.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PassRecoverActivity extends AppCompatActivity {

    EditText txtEmail;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_recover);

        txtEmail = findViewById(R.id.txt_email);
    }

    public void recuperarEmail(View view) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CoopFitService.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail(String.valueOf(txtEmail.getText()));

        CoopFitService api = retrofit.create(CoopFitService.class);

        api.recuperarSenha(emailDTO).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 204) {
                    Toast.makeText(PassRecoverActivity.this, "Nova senha enviada para seu e-mail", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(PassRecoverActivity.this, "E-mail inválido ou não encontrado", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(PassRecoverActivity.this, "Erro ao tentar recuperar a senha.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
