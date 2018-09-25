package fiap.com.br.coopfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fiap.com.br.coopfit.service.CoopFitService;
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
        email = String.valueOf(txtEmail.getText());
    }

    public void recuperarEmail(View view) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CoopFitService.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        CoopFitService api = retrofit.create(CoopFitService.class);

        api.recuperarSenha(email).enqueue(new Callback<Pessoa>() {
            @Override
            public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                Toast.makeText(PassRecoverActivity.this, "Nova senha enviada para seu e-mail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Pessoa> call, Throwable t) {

            }
        });




        finish();
    }
}
