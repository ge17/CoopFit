package fiap.com.br.coopfit;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.dao.PessoaPreferences;
import fiap.com.br.coopfit.enums.Genero;
import fiap.com.br.coopfit.enums.TipoUsuario;
import fiap.com.br.coopfit.to.Pessoa;

public class ConfigActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);


    }


}
