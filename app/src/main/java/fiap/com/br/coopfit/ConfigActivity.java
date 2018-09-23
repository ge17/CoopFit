package fiap.com.br.coopfit;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.dao.PessoaPreferences;
import fiap.com.br.coopfit.to.Pessoa;

public class ConfigActivity extends AppCompatActivity {


    EditText txtEmail;
    EditText txtSenha;
    EditText txtNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        txtEmail = findViewById(R.id.txt_email);
        txtSenha = findViewById(R.id.txt_senha);
        txtNome = findViewById(R.id.txt_nome);
    }

    public void salvarConfig(View view) {

        try {
            Pessoa p = new Pessoa();
            p.setEmail(String.valueOf(txtEmail.getText()));
            p.setSenha(String.valueOf(txtSenha.getText()));
            p.setNome(String.valueOf(txtNome.getText()));

            CoopFitDB db = new CoopFitDB(this);

            db.updatePessoa(p);
            db.close();

//        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
//
//        PessoaPreferences dao = new PessoaPreferences(preferences);
//        dao.salvarPessoaPreference(p.getNome(),p.getNome());
//        dao.salvarPessoaPreference(p.getSenha(),p.getSenha());

        }catch (Exception e){
            Toast.makeText(this, "Erro ao atualizar", Toast.LENGTH_SHORT).show();
        }

    }


}
