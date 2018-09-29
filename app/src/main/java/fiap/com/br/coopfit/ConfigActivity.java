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

    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

    EditText txtEmail;
    EditText txtSenha;
    EditText txtNome;
    EditText txtPeso;
    EditText txtAltura;
    EditText txtNasc;
    Spinner spinnerGenero;
    EditText txtObs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        txtEmail = findViewById(R.id.txt_email);
        txtSenha = findViewById(R.id.txt_senha);
        txtNome  = findViewById(R.id.txt_nome);
        txtPeso  = findViewById(R.id.txt_peso);
        txtAltura  = findViewById(R.id.txt_altura);
        txtNasc  = findViewById(R.id.txt_nasc);
        spinnerGenero = findViewById(R.id.spinner_genero);
        txtObs = findViewById(R.id.txt_obs);
    }

    public void salvarConfig(View view) {

        try {

            Pessoa p = new Pessoa();
            p.setNome(txtNome.getText().toString());


//                Date dataOrigem = !String.valueOf(txtNasc.getText()).equals("") ? new Date(dateFormatFrom.format(txtNasc.getText().toString())) : new Date();
//                Date data = !String.valueOf(txtNasc.getText()).equals("") ? dateFormat.parse(String.valueOf(txtNasc.getText()).replace("/", "-")) : new Date();
//                Date data = dateFormat.parse(String.valueOf(dataOrigem));


            p.setNascimento(txtNasc.getText().toString());

            //DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            String Data = fmt.format(Calendar.getInstance().getTime());
            p.setCadastro(fmt.parse(Data));

            p.setGenero(spinnerGenero.getSelectedItem().toString().equals("MASCULINO") ? 0 : 1);
            //p.setFoto(new byte[]{1});
            p.setEmail(txtEmail.getText().toString());
            p.setSenha(txtSenha.getText().toString());

//                String dataCadastroOrigem = dateFormatFrom.format(new Date());
//                String dataCadastro = dateFormat.format(new Date(dataCadastroOrigem));

            p.setNotificacao(true);

            p.setPeso(!txtPeso.getText().toString().equals("") ? Double.valueOf(txtPeso.getText().toString()) : 0);
            p.setAltura(!txtAltura.getText().toString().equals("") ? Double.valueOf(txtAltura.getText().toString()) : 0);

            p.setObservacao(String.valueOf(txtObs.getText()));
            p.setPerfis(1);

            CoopFitDB db = new CoopFitDB(this);

            db.updatePessoa(p);
            db.close();

        }catch (Exception e){
            Toast.makeText(this, "Erro ao atualizar", Toast.LENGTH_SHORT).show();
        }

    }


}
