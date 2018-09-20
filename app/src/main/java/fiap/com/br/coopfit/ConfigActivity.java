package fiap.com.br.coopfit;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import fiap.com.br.coopfit.dao.PessoaPreferences;
import fiap.com.br.coopfit.to.Pessoa;

public class ConfigActivity extends AppCompatActivity {

    EditText etNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        etNome = findViewById(R.id.txt_nome);
    }

    public void salvarConfig(View view) {

        Pessoa p = new Pessoa();
        p.setNome(String.valueOf(etNome.getText()));
        p.setSenha("123");

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        PessoaPreferences dao = new PessoaPreferences(preferences);
        dao.salvarPessoaPreference(p.getNome(),p.getNome());
        dao.salvarPessoaPreference(p.getSenha(),p.getSenha());

    }


}
