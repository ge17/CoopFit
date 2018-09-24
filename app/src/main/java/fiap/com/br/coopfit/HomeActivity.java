package fiap.com.br.coopfit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.to.Pessoa;

public class HomeActivity extends AppCompatActivity {

    Pessoa p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);





    }


    public void abrirDialogoBatimento(View view) {

        CoopFitDB db = new CoopFitDB(view.getContext());
        double valor = db.getBatimento();

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

    public void abrirDialogoTempoOcioso(View view) {

        CoopFitDB db = new CoopFitDB(view.getContext());
        double valor = db.getTempoOcioso();

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

    public void abrirDialogoTempoSono(View view) {
        CoopFitDB db = new CoopFitDB(view.getContext());
        double valor = db.getTempoSono();

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
        CoopFitDB db = new CoopFitDB(view.getContext());
        double valor = db.getTempoAtividade();

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
        CoopFitDB db = new CoopFitDB(view.getContext());
        double valor = db.getLiquidoDiario();

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
        CoopFitDB db = new CoopFitDB(view.getContext());
        double valor = db.getQtdPassos();

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
