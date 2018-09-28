package fiap.com.br.coopfit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
    DispositivoSensor ds;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(CoopFitService.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }


    public void abrirDialogoBatimento(View view) {

//        CoopFitDB db = new CoopFitDB(view.getContext());
//        double valor = db.getBatimento();

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);

            api.getValorSensor(1).enqueue(new Callback<DispositivoSensor>() {
                @Override
                public void onResponse(Call<DispositivoSensor> call, Response<DispositivoSensor> response) {
                    ds = response.body();
                }

                @Override
                public void onFailure(Call<DispositivoSensor> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Erro", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        double valor = 0;
        if(ds != null){
            valor = ds.getValor();
        }

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

    public void abrirDialogoTempoOcioso(final View view) {

//        CoopFitDB db = new CoopFitDB(view.getContext());
//        double valor = db.getTempoOcioso();

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);

            api.getValorSensor(2).enqueue(new Callback<DispositivoSensor>() {
                @Override
                public void onResponse(Call<DispositivoSensor> call, Response<DispositivoSensor> response) {
                    ds = response.body();

                    double valor = 0;
                    if(ds != null){
                        valor = ds.getValor();
                    }

                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("Tempo ocioso");
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
                public void onFailure(Call<DispositivoSensor> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }


    }

    public void abrirDialogoTempoSono(View view) {

//        CoopFitDB db = new CoopFitDB(view.getContext());
//        double valor = db.getTempoSono();

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);

            api.getValorSensor(3).enqueue(new Callback<DispositivoSensor>() {
                @Override
                public void onResponse(Call<DispositivoSensor> call, Response<DispositivoSensor> response) {
                    ds = response.body();
                }

                @Override
                public void onFailure(Call<DispositivoSensor> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

        double valor = 0;
        if(ds != null){
            valor = ds.getValor();
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Tempo de sono");
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

    public void abrirDialogoAtividade(View view) {
//        CoopFitDB db = new CoopFitDB(view.getContext());
//        double valor = db.getTempoAtividade();

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);

            api.getValorSensor(4).enqueue(new Callback<DispositivoSensor>() {
                @Override
                public void onResponse(Call<DispositivoSensor> call, Response<DispositivoSensor> response) {
                    ds = response.body();
                }

                @Override
                public void onFailure(Call<DispositivoSensor> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

        double valor = 0;
        if(ds != null){
            valor = ds.getValor();
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Atividades");
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

    public void abrirDialogoLiquido(View view) {
//        CoopFitDB db = new CoopFitDB(view.getContext());
//        double valor = db.getLiquidoDiario();

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);

            api.getValorSensor(5).enqueue(new Callback<DispositivoSensor>() {
                @Override
                public void onResponse(Call<DispositivoSensor> call, Response<DispositivoSensor> response) {
                    ds = response.body();
                }

                @Override
                public void onFailure(Call<DispositivoSensor> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

        double valor = 0;
        if(ds != null){
            valor = ds.getValor();
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Total de líquidio ingerido");
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

    public void abrirDialogoPuzzle(View view) {
//        CoopFitDB db = new CoopFitDB(view.getContext());
//        double valor = db.getQtdPassos();

        try {
            CoopFitService api = retrofit.create(CoopFitService.class);

            api.getValorSensor(6).enqueue(new Callback<DispositivoSensor>() {
                @Override
                public void onResponse(Call<DispositivoSensor> call, Response<DispositivoSensor> response) {
                    ds = response.body();
                }

                @Override
                public void onFailure(Call<DispositivoSensor> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }

        double valor = 0;
        if(ds != null){
            valor = ds.getValor();
        }

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



}
