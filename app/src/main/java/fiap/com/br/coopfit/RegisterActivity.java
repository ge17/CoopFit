package fiap.com.br.coopfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.to.Pessoa;

public class RegisterActivity extends AppCompatActivity {

    EditText txtEmail;
    EditText txtSenha;
    EditText txtNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = findViewById(R.id.txt_email);
        txtSenha = findViewById(R.id.txt_senha);
        txtNome = findViewById(R.id.txt_nome);
    }

    public void saveChanges(View view) {

        try {
            Pessoa p = new Pessoa();
            p.setEmail(txtEmail.getText().toString());
            p.setSenha(txtSenha.getText().toString());
            p.setNome(txtNome.getText().toString());

            CoopFitDB db = new CoopFitDB(this);
            db.insertPessoa(p);
            db.close();
            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }catch (Exception e){
            Toast.makeText(this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
        }
    }
}
