package fiap.com.br.coopfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IntegrationActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integration);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://douglascabral.com.br/aula-api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoopFitService api = retrofit.create(CoopFitService.class);


        api.getPessoa(1).enqueue(new Callback<Pessoa>() {
                @Override
                public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                    Pessoa contatos = response.body();
                    Toast.makeText(IntegrationActivity.this, "CONTATO" + contatos.getNome(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Pessoa> call, Throwable t) {
                    Toast.makeText(IntegrationActivity.this, "Erro" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });

    }
}
