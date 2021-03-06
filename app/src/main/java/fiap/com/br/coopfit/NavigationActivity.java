package fiap.com.br.coopfit;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.enums.TipoSentimento;
import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.Pessoa;
import fiap.com.br.coopfit.to.Questionario;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout dynamicContent ;
    TextView tvAltura;
    TextView tvPeso;

    TextView tvUser;

    SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

    //Variaveis ConfigActivity
    EditText txtEmail;
    EditText txtSenha;
    EditText txtNome;
    EditText txtPeso;
    EditText txtAltura;
    EditText txtNasc;
//    Spinner spinnerGenero;
//    EditText txtObs;


    Spinner spDispositivos;
    EditText txtInformacao;

    BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
    BluetoothSocket soquete = null;
    OutputStream saida = null;

    Set<BluetoothDevice> dispositivosPareados;

    String token;
    String email;
    String nome;
    String senha;
    long idPessoa;
    Pessoa p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        * INICIO CONTEUDO
        *
        * */
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        SharedPreferences spToken = getSharedPreferences("auth", MODE_PRIVATE);
        token = spToken.getString("token", null);

        dynamicContent = (LinearLayout) findViewById(R.id.conteudo);


        View wizardView = getLayoutInflater()
                .inflate(R.layout.activity_home, dynamicContent, false);

        // add the inflated View to the layout
        dynamicContent.addView(wizardView);

        tvUser = findViewById(R.id.txt_user_name);
        tvAltura = findViewById(R.id.txt_altura);
        tvPeso = findViewById(R.id.txt_peso);

        Intent i = getIntent();

        if(i != null){
            Bundle bundle = i.getExtras();
            if(bundle != null){

                email = bundle.getString("email");

                CoopFitDB db = new CoopFitDB(this);
                Pessoa p1 = db.findPessoa(email);

                e.putString("idPessoa", String.valueOf(p1.getId()));
                e.putString("nome", p1.getNome());
                e.putString("altura", String.valueOf(p1.getAltura()));
                e.putString("peso", String.valueOf(p1.getPeso()));
                e.commit();

                tvUser.setText(p1.getNome());
                tvAltura.setText(String.valueOf(p1.getAltura()));
                tvPeso.setText(String.valueOf(p1.getPeso()));

            }
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (dynamicContent != null) {
            dynamicContent.removeAllViews();
        }
            if (id == R.id.nav_camera) {
                // Handle the camera action
                // assuming your Wizard content is in content_wizard.xml
                View wizardView = getLayoutInflater()
                        .inflate(R.layout.activity_home, dynamicContent, false);

                // add the inflated View to the layout
                dynamicContent.addView(wizardView);

                tvUser = findViewById(R.id.txt_user_name);
                tvAltura = findViewById(R.id.txt_altura);
                tvPeso = findViewById(R.id.txt_peso);

                SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);

                tvUser.setText(sp.getString("nome", null));
                tvAltura.setText(sp.getString("altura", null));
                tvPeso.setText(sp.getString("peso", null));

                carregarChart();
            } else if (id == R.id.nav_gallery) {
                View wizardView = getLayoutInflater()
                        .inflate(R.layout.activity_config, dynamicContent, false);

                dynamicContent.addView(wizardView);

                txtEmail = findViewById(R.id.txt_email);
                txtSenha = findViewById(R.id.txt_senha);
                txtNome  = findViewById(R.id.txt_nome);
                txtPeso  = findViewById(R.id.txt_peso);
                txtAltura  = findViewById(R.id.txt_altura);
                txtNasc  = findViewById(R.id.txt_nasc);
//                spinnerGenero = findViewById(R.id.spinner_genero);
//                txtObs = findViewById(R.id.txt_obs);

              try {
                  CoopFitDB db = new CoopFitDB(this);
                  p = db.findPessoa(email);

                  txtEmail.setText(p.getEmail());
                  txtSenha.setText(p.getSenha());
                  txtNome.setText(p.getNome());
                  txtPeso.setText(String.valueOf(p.getPeso()));
                  txtAltura.setText(String.valueOf(p.getAltura()));

                  txtNasc.setText(fmt.format(p.getNascimento()));

//                  txtObs.setText(p.getObservacao());

              }catch (Exception e){

              }

            }else if (id == R.id.nav_share) {

                View wizardView = getLayoutInflater()
                        .inflate(R.layout.activity_measure, dynamicContent, false);

                dynamicContent.addView(wizardView);

                carregarDadosMedimento();

//            } else if (id == R.id.nav_send) {

//                View wizardView = getLayoutInflater()
//                        .inflate(R.layout.activity_quiz, dynamicContent, false);
//
//                dynamicContent.addView(wizardView);

            } else if (id == R.id.nav_slideshow) {
                finish();
            }


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
    }

    public void abrirDialogoBatimento(View view) {
        HomeActivity h = new HomeActivity();
        h.abrirDialogoBatimentoHome(view);
    }

    public void abrirDialogoTempoOcioso(View view) {
        HomeActivity h = new HomeActivity();
        h.abrirDialogoTempoOciosoHome(view);
    }

    public void abrirDialogoTempoSono(View view) {
        HomeActivity h = new HomeActivity();
        h.abrirDialogoTempoSonoHome(view);

    }

    public void abrirDialogoAtividade(View view) {
        HomeActivity h = new HomeActivity();
        h.abrirDialogoAtividadeHome(view);

    }

    public void abrirDialogoLiquido(View view) {
        HomeActivity h = new HomeActivity();
        h.abrirDialogoLiquidoHome(view);
    }

    public void abrirDialogoPuzzle(View view) {
        HomeActivity h = new HomeActivity();
        h.abrirDialogoPuzzleHome(view);
    }




    public void carregarDadosMedimento(){

        spDispositivos = (Spinner) findViewById(R.id.spDispositivos);
        txtInformacao = (EditText) findViewById(R.id.txtInformacao);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.add("Selecione um dispositivo");
        if (bluetooth != null) {
            if (!bluetooth.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                int REQUEST_ENABLE_BT = 1;
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            dispositivosPareados = bluetooth.getBondedDevices();
            if ( dispositivosPareados.size() > 0 ) {
                for (BluetoothDevice item : dispositivosPareados) {
                    adapter.add(item.getName());
                }
            }
        }
        spDispositivos.setAdapter(adapter);

    }

    private BluetoothSocket criarSoqueteBluetooth(BluetoothDevice dispositivo) throws IOException {
        Method metodo;
        BluetoothSocket tmpSoquete = null;
        try {
            metodo = dispositivo.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
            tmpSoquete = (BluetoothSocket) metodo.invoke(dispositivo, 1);
        } finally {
            return tmpSoquete;
        }
    }

    public void enviar(View view) {

        for (BluetoothDevice item : dispositivosPareados) {
            if (spDispositivos.getSelectedItem().toString().equalsIgnoreCase(item.getName())) {
                try {
                    BluetoothDevice dispositivoRemoto = bluetooth.getRemoteDevice(item.getAddress());
                    soquete = criarSoqueteBluetooth(dispositivoRemoto);
                    soquete.connect();
                    bluetooth.cancelDiscovery();
                    saida = soquete.getOutputStream();
                    byte[] buffer = txtInformacao.getText().toString().getBytes();
                    saida.write(buffer);
                    saida.close();
                    soquete.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void carregarChart(){

        LineChart chart = (LineChart) findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(2, 4));
        entries.add(new Entry(4, 6));
        entries.add(new Entry(7, 8));

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(10);
        dataSet.setValueTextColor(12);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }


    public void salvarConfig(View view) {

        try {
            p.setNome(txtNome.getText().toString());

            String dataNasc = txtNasc.getText().toString();

            p.setNascimento(new Date(dataNasc));
//            p.setCadastro(new Date());

//            p.setGenero(spinnerGenero.getSelectedItem().toString().equals("MASCULINO") ? 0 : 1);
            //p.setFoto(new byte[]{1});
            p.setEmail(txtEmail.getText().toString());
            p.setSenha(txtSenha.getText().toString());

            p.setNotificacao(true);

            p.setPeso(!txtPeso.getText().toString().equals("") ? Double.valueOf(txtPeso.getText().toString()) : 0);
            p.setAltura(!txtAltura.getText().toString().equals("") ? Double.valueOf(txtAltura.getText().toString()) : 0);

//            p.setObservacao(String.valueOf(txtObs.getText()));
            p.setPerfis(1);

            CoopFitDB db = new CoopFitDB(this);

            db.updatePessoa(p);
            db.close();

            try {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd")
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(CoopFitService.API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                CoopFitService api = retrofit.create(CoopFitService.class);

                api.updatePessoa(p.getId(), p).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(NavigationActivity.this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(NavigationActivity.this, "Erro ao conectar no servidor!", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                Toast.makeText(NavigationActivity.this, "Erro ao atualizar os dados!", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(this, "Erro ao atualizar", Toast.LENGTH_SHORT).show();
        }

    }


    public void Sincronizar(MenuItem item) {
        CoopFitDB db = new CoopFitDB(this);
        db.syncData(token);
        db.close();
    }
}
