package fiap.com.br.coopfit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.Contato;
import fiap.com.br.coopfit.to.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeActivity extends AppCompatActivity {

    Pessoa p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);





    }


    public void abrirDialogoBatimento(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Batimentos");
        alert.setMessage("Batimentos...");
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

    public void abrirDialogoTempoOcioso(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Tempo ocioso");
        alert.setMessage("Tempo Ocioso...");
        alert.setCancelable(true);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void abrirDialogoTempoSono(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Tempo de sono");
        alert.setMessage("Horas dormidas...");
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

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Atividades");
        alert.setMessage("Atividades...");
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

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Liquidos Ingeridos");
        alert.setMessage("Liquidos ingeridos...");
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

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Outros");
        alert.setMessage("...");
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
