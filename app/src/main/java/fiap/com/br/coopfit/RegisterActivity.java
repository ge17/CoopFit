package fiap.com.br.coopfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.to.Pessoa;

public class RegisterActivity extends AppCompatActivity {

    EditText txtEmail;
    EditText txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = findViewById(R.id.txt_email);
        txtSenha = findViewById(R.id.txt_senha);
    }

    public void saveChanges(View view) {
        Pessoa p = new Pessoa();
        p.setEmail(txtEmail.getText().toString());
        p.setSenha(txtSenha.getText().toString());

        CoopFitDB db = new CoopFitDB(this);
        db.insertPessoa(p);
    }
}
