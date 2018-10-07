package fiap.com.br.coopfit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.internal.t;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.DispositivoSensor;
import fiap.com.br.coopfit.to.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    Pessoa p;
    double valor = 0;
    String token;
    long idPessoa;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(CoopFitService.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    public void abrirDialogoBatimentoHome(final View view) {

//        CoopFitDB db = new CoopFitDB(view.getContext());
//        double valor = db.getBatimento();

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);
            SharedPreferences spToken = view.getContext().getSharedPreferences("auth", MODE_PRIVATE);
            token = spToken.getString("token",null);

            SharedPreferences sp = view.getContext().getSharedPreferences("user", MODE_PRIVATE);

            idPessoa = Long.valueOf(sp.getString("idPessoa","-1"));

            api.getValorSensor(idPessoa,"Batimento", token).enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if(response.body() != null)
                    valor = response.body();

                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("Batimentos");
                    alert.setMessage("" + valor);
                    alert.setCancelable(true);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

        //        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        //            @Override
        //            public void onClick(DialogInterface dialog, int which) {
        //                Toast.makeText(HomeActivity.this, "kkkkkkk", Toast.LENGTH_SHORT).show();
        //            }
        //        });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void abrirDialogoTempoOciosoHome(final View view) {

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);
            SharedPreferences sp = view.getContext().getSharedPreferences("auth", MODE_PRIVATE);
            token = sp.getString("token",null);

            api.getValorSensor(idPessoa,"Ocioso", token).enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {

                    if(response.body() != null)
                    valor = response.body();

                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("Tempo Ocioso");
                    alert.setMessage("" + valor);
                    alert.setCancelable(true);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void abrirDialogoTempoSonoHome(final View view) {

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);
            SharedPreferences sp = view.getContext().getSharedPreferences("auth", MODE_PRIVATE);
            token = sp.getString("token",null);

            api.getValorSensor(idPessoa,"Sono", token).enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if(response.body() != null)
                    valor = response.body();

                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("Tempo de Sono");
                    alert.setMessage("" + valor);
                    alert.setCancelable(true);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void abrirDialogoAtividadeHome(final View view) {

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);
            SharedPreferences sp = view.getContext().getSharedPreferences("auth", MODE_PRIVATE);
            token = sp.getString("token",null);

            api.getValorSensor(idPessoa,"Atividade", token).enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if(response.body() != null)
                    valor = response.body();

                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("Passos");
                    alert.setMessage("" + valor);
                    alert.setCancelable(true);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void abrirDialogoLiquidoHome(final View view) {

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);
            SharedPreferences sp = view.getContext().getSharedPreferences("auth", MODE_PRIVATE);
            token = sp.getString("token",null);

            api.getValorSensor(idPessoa,"Liquido", token).enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if(response.body() != null)
                    valor = response.body();

                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("Quantidade de Líquido");
                    alert.setMessage("" + valor);
                    alert.setCancelable(true);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void abrirDialogoPuzzleHome(final View view) {

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);
            SharedPreferences sp = view.getContext().getSharedPreferences("auth", MODE_PRIVATE);
            token = sp.getString("token",null);

            api.getValorSensor(idPessoa,"Outro", token).enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if(response.body() != null)
                    valor = response.body();

                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("Outros");
                    alert.setMessage("" + valor);
                    alert.setCancelable(true);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


}
