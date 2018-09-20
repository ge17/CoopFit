package fiap.com.br.coopfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PassRecoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_recover);
    }

    public void recuperarEmail(View view) {
        Toast.makeText(this, "Nova senha enviada para seu e-mail", Toast.LENGTH_SHORT).show();
        finish();
    }
}
