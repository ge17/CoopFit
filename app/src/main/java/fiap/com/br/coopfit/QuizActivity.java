package fiap.com.br.coopfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.Questionario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizActivity extends AppCompatActivity {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(CoopFitService.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }

    public void enviar(View view) {

        try {

            Questionario questionario = new Questionario();
            questionario.setTipoSentimento("");
            questionario.setData(new Date());
            questionario.setEstresse(1);
            questionario.setQtdCopoAgua(2);
//        questionario.setPessoa();

            CoopFitService api = retrofit.create(CoopFitService.class);

            api.setQuiz(questionario).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(QuizActivity.this, "Questionario enviado", Toast.LENGTH_SHORT).show();

                    finish();
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(QuizActivity.this, "Erro ao enviar o Questionario", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){

        }

    }
}
