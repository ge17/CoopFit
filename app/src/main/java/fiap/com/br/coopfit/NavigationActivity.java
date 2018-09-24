package fiap.com.br.coopfit;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
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

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fiap.com.br.coopfit.dao.CoopFitDB;
import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout dynamicContent ;
    TextView tvUserName;
    TextView tvAltura;
    TextView tvPeso;

    EditText etNome;
    EditText etPeso;
    EditText etAltura;
    EditText etNasc;
    TextView tvUser;

    String email;
    String nome;
    String senha;

    //Variaveis ConfigActivity
    EditText txtEmail;
    EditText txtSenha;
    EditText txtNome;
    EditText txtPeso;
    EditText txtAltura;
    EditText txtNasc;


    Spinner spDispositivos;
    EditText txtInformacao;

    BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
    BluetoothSocket soquete = null;
    OutputStream saida = null;
    Set<BluetoothDevice> dispositivosPareados;


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
                tvUser.setText(p1.getNome());
                tvAltura.setText(String.valueOf(p1.getAltura()));
                tvPeso.setText(String.valueOf(p1.getPeso()));

            }
        }

//        carregarDadosHome();

//
//        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
//
//        PessoaPreferences dao = null;
//        String nome = dao.getPessoaPreference("user");
//
//        tvUser = findViewById(R.id.txt_user_name);
//        tvUser.setText(nome);



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

                CoopFitDB db = new CoopFitDB(this);
                Pessoa p1 = db.findPessoa(email);
                tvUser.setText(p1.getNome());

//                carregarDadosHome();
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

              CoopFitDB db = new CoopFitDB(this);
              db.findPessoa(email);

                txtEmail.setText(nome);
                txtSenha.setText(senha);
                txtNome.setText(nome);

//                carregarDadosConfig();
//            } else if (id == R.id.nav_manage) {
//                View wizardView = getLayoutInflater()
//                        .inflate(R.layout.activity_pass_recover, dynamicContent, false);
//
//                dynamicContent.addView(wizardView);
//            }
//            }else if (id == R.id.nav_share) {
//
//                View wizardView = getLayoutInflater()
//                        .inflate(R.layout.activity_measure, dynamicContent, false);
//
//                dynamicContent.addView(wizardView);
//
//                carregarDadosMedimento();

//            } else if (id == R.id.nav_send) {
//
//
//                View wizardView = getLayoutInflater()
//                        .inflate(R.layout.activity_question, dynamicContent, false);
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
        h.abrirDialogoBatimento(view);
    }

    public void abrirDialogoTempoOcioso(View view) {
        HomeActivity h = new HomeActivity();
        h.abrirDialogoTempoOcioso(view);
    }

    public void abrirDialogoTempoSono(View view) {
        HomeActivity h = new HomeActivity();
        h.abrirDialogoTempoSono(view);

    }

    public void abrirDialogoAtividade(View view) {
        HomeActivity h = new HomeActivity();
        h.abrirDialogoAtividade(view);

    }

    public void abrirDialogoLiquido(View view) {
        HomeActivity h = new HomeActivity();
        h.abrirDialogoLiquido(view);
    }

    public void abrirDialogoPuzzle(View view) {
        HomeActivity h = new HomeActivity();
        h.abrirDialogoPuzzle(view);
    }

    public void carregarDadosHome(){
        tvUserName = findViewById(R.id.txt_user_name);
        tvAltura = findViewById(R.id.txt_altura);
        tvPeso = findViewById(R.id.txt_peso);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        CoopFitService api = retrofit.create(CoopFitService.class);

        api.getPessoa(1).enqueue(new Callback<Pessoa>() {
            @Override
            public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                Pessoa p = response.body();
                tvUserName.setText(p.getNome());
                tvAltura.setText(String.valueOf(p.getAltura()));
                tvPeso.setText(String.valueOf(p.getPeso()));

            }

            @Override
            public void onFailure(Call<Pessoa> call, Throwable t) {
                Toast.makeText(NavigationActivity.this, "Erro" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }


    public void carregarDadosConfig(){

        etNome = findViewById(R.id.txt_nome);
        etPeso = findViewById(R.id.txt_peso);
        etAltura = findViewById(R.id.txt_altura);
        etNasc = findViewById(R.id.txt_nasc);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if(!retrofit.baseUrl().equals("http://10.0.2.2:8080/")){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.106:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        CoopFitService api = retrofit.create(CoopFitService.class);

        api.getPessoa(1).enqueue(new Callback<Pessoa>() {
            @Override
            public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                Pessoa p = response.body();

                etNome.setText(p.getNome());
                etPeso.setText(String.valueOf(p.getPeso()));
                etAltura.setText(String.valueOf(p.getAltura()));
                etNasc.setText(String.valueOf(p.getNascimento()));

            }

            @Override
            public void onFailure(Call<Pessoa> call, Throwable t) {
                Toast.makeText(NavigationActivity.this, "Erro" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }


//    public void salvarConfig(View view) {
//        etNome = findViewById(R.id.txt_nome);
//        etPeso = findViewById(R.id.txt_peso);
//        etAltura = findViewById(R.id.txt_altura);
//        etNasc = findViewById(R.id.txt_nasc);
//
//        Pessoa p = new Pessoa();
//
//        p.setNome(String.valueOf(etNome.getText()));
//        p.setPeso(Double.valueOf(String.valueOf(etPeso.getText())));
//        p.setAltura(Double.valueOf(String.valueOf(etAltura.getText())));
//        p.setNascimento(Date.valueOf(String.valueOf(etNasc.getText())));
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8080/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        if(!retrofit.baseUrl().equals("http://10.0.2.2:8080/")){
//            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://192.168.0.106:8080/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//
//        CoopFitService api = retrofit.create(CoopFitService.class);
//
//        api.setPessoa(p).enqueue(new Callback<Pessoa>() {
//            @Override
//            public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Pessoa> call, Throwable t) {
//                Toast.makeText(NavigationActivity.this, "Erro" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


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
            }//if
            dispositivosPareados = bluetooth.getBondedDevices();
            if ( dispositivosPareados.size() > 0 ) {
                for (BluetoothDevice item : dispositivosPareados) {
                    adapter.add(item.getName());
                }
            }
        }//if
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
            Pessoa p = new Pessoa();
            p.setEmail(String.valueOf(txtEmail.getText()));
            p.setSenha(String.valueOf(txtSenha.getText()));
            p.setNome(String.valueOf(txtNome.getText()));
            p.setPeso(Double.valueOf(txtNome.getText().toString()));
            p.setAltura(Double.valueOf(txtNome.getText().toString()));
//            p.setNascimento(Date.valueOf(txtNome.getText().toString()));

            CoopFitDB db = new CoopFitDB(this);

            db.updatePessoa(p);
            db.close();


            Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();

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
