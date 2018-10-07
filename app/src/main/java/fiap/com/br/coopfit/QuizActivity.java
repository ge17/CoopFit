package fiap.com.br.coopfit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.enums.TipoSentimento;
import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.Pessoa;
import fiap.com.br.coopfit.to.Questionario;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizActivity extends AppCompatActivity {

    EditText txtQtdCopoAgua;
    EditText txtQtdFruta;
    Spinner txtMeioLocomocao;
    Spinner txtSentimento;

    Pessoa p;
    String email;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQtdCopoAgua = findViewById(R.id.txtQtdCopoAgua);
        txtQtdFruta = findViewById(R.id.txtQtdFruta);
        txtMeioLocomocao = findViewById(R.id.txtMeioLocomocao);
        txtSentimento = findViewById(R.id.txtSentimento);

        SharedPreferences spToken = getSharedPreferences("auth", MODE_PRIVATE);
        token = spToken.getString("token", null);

        Intent i = getIntent();

        if(i != null){
            Bundle bundle = i.getExtras();
            if(bundle != null){

                email = bundle.getString("email");

                try {
                    CoopFitDB db = new CoopFitDB(this);
                    p = db.findPessoa(email);
                    db.close();

                }catch (Exception e){

                }

            }
        }

    }


    public void saveQuiz(View view) {

        try {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CoopFitService.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


            final Questionario questionario = new Questionario();
            questionario.setTipoSentimento(TipoSentimento.valueOf(txtSentimento.getSelectedItem().toString().toUpperCase()));
            questionario.setData(new Date());
            questionario.setEstresse(Integer.valueOf(txtQtdFruta.toString()));
            questionario.setQtdCopoAgua(Integer.valueOf(txtQtdCopoAgua.toString()));
            questionario.setPessoa(p);

            CoopFitService api = retrofit.create(CoopFitService.class);

            api.setQuiz(questionario,token).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Gson g = new Gson();
                    String x = g.toJson(questionario);
                    Toast.makeText(QuizActivity.this, "Questionario enviado", Toast.LENGTH_SHORT).show();

                    if(response.code() == 201) {
                        String strId = "";
                        Headers headers = response.headers();

                        strId = headers.value(0).replace("http://54.89.196.181:8080/questionarios/", "");

                        questionario.setId(!strId.equals("") ? Long.valueOf(strId) : -1);

                        CoopFitDB db2 = new CoopFitDB(QuizActivity.this);
                        db2.insertQuiz(questionario);

                        Toast.makeText(QuizActivity.this, "Usuário " + strId + " cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    }

                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(QuizActivity.this, "Erro ao enviar o Questionario", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Toast.makeText(QuizActivity.this, "Erro ao enviar o Questionario. Favor. rever os campos.", Toast.LENGTH_SHORT).show();
        }finally {
            Intent i = new Intent(QuizActivity.this, NavigationActivity.class);
            i.putExtra("email", email);
            startActivity(i);
            finish();
        }
    }


}
