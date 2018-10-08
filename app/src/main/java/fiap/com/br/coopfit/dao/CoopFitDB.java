package fiap.com.br.coopfit.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import fiap.com.br.coopfit.service.CoopFitService;
import fiap.com.br.coopfit.to.Pessoa;
import fiap.com.br.coopfit.to.Questionario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by georg on 9/16/2018.
 */

public class CoopFitDB extends SQLiteOpenHelper {

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static final String DATABASE_NAME = "CoopFitDB";
    public static final int VERSION = 1;
    public static final String TB_PESSOA = "T_PESSOA";
    public static final String T_DISPOSITIVO_SENSOR = "T_DISPOSITIVO_SENSOR";
    public static final String T_QUESTIONARIO = "T_QUESTIONARIO";



    public CoopFitDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists T_PESSOA(id_pessoa integer, email text, senha text, nome text, peso real, altura real, data text)");
        db.execSQL("create table if not exists T_DISPOSITIVO(id_dispositivo integer, id_pessoa integer, descricao text)");
        db.execSQL("create table if not exists T_DISPOSITIVO_SENSOR(id_dispositivo_sensor integer, id_sensor integer, valor real, tipo text, data text)");
        db.execSQL("create table if not exists T_QUESTIONARIO(id_questionario integer, id_pessoa integer, qt_copo_agua integer, tp_situacao text, data text, respondido text)");
        db.execSQL("create table if not exists T_INFORME_TRATATIVAS(d_informativo integer, descricao text, data text)");
        db.execSQL("create table if not exists T_NOTIFICACAO(id_notificacao, descricao text, data text)");
        db.execSQL("create table if not exists T_ROTINA(id_rotina, descricao text, data text, tipo integer)");
        db.execSQL("create table if not exists T_INFORMATIVO_SAUDE(id_informacao_saude, descricao text, data text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void syncData(String token){

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CoopFitService.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CoopFitService api = retrofit.create(CoopFitService.class);

        api.listPessoas(token).enqueue(new Callback<List<Pessoa>>() {
            @Override
            public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                List<Pessoa> pessoas = response.body();

                for(Pessoa pessoa : pessoas){
                    Pessoa p = findPessoa(pessoa.getEmail());
                    if(p.getEmail() != null){
                        updatePessoa(pessoa);
                    }else {
                        insertPessoa(pessoa);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Pessoa>> call, Throwable t) {
            }
        });
    }


    public void insertPessoa(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("email", pessoa.getEmail());
        cv.put("id_pessoa", pessoa.getId());
        cv.put("senha", pessoa.getSenha());
        cv.put("nome", pessoa.getNome());
        cv.put("peso", pessoa.getPeso());
        cv.put("altura", pessoa.getAltura());

        cv.put("data", pessoa.getNascimento() != null ? dateFormat.format(pessoa.getNascimento()) : null);

        db.insert(TB_PESSOA, null, cv);
        db.close();
    }

    public void updatePessoa(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("id_pessoa", pessoa.getId());
        cv.put("senha", pessoa.getSenha());
        cv.put("nome", pessoa.getNome());
        cv.put("peso", pessoa.getPeso());
        cv.put("altura", pessoa.getAltura());
        cv.put("data", pessoa.getNascimento() != null ? dateFormat.format(pessoa.getNascimento()) : null);

        db.update(TB_PESSOA, cv, "email = ?", new String[]{pessoa.getEmail()});
        db.close();
    }


    public void setIdPessoa(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("id_pessoa", pessoa.getId());

        db.update(TB_PESSOA, cv, "email = ?", new String[]{pessoa.getEmail()});
        db.close();
    }


    public Pessoa findPessoa(String email) {

        Pessoa p = null;
        try {
            SQLiteDatabase db = getReadableDatabase();

            Cursor cursor = db.query(
                    TB_PESSOA,
                    null,
                    "email = ?",
                    new String[]{email},
                    null,
                    null,
                    null
            );

            p = new Pessoa();
            if (cursor.moveToNext()) {
                p.setId(cursor.getLong(0));
                p.setEmail(cursor.getString(1));
                p.setSenha(cursor.getString(2));
                p.setNome(cursor.getString(3));
                p.setPeso(cursor.getDouble(4));
                p.setAltura(cursor.getDouble(5));

                String dataNasc = cursor.getString(6);
                Date data = !dataNasc.equals("") && dataNasc != null ? dateFormat.parse(dataNasc) : null;

                p.setNascimento(data);
            }

            cursor.close();
            db.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return p;
    }



    public Pessoa validarLoginPessoa(String email, String senha) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TB_PESSOA,
                new String[]{"email", "senha"},
                "email = ? and senha = ?",
                new String[]{email, senha},
                null,
                null,
                null
        );

        Pessoa p = new Pessoa();
        if ( cursor.moveToNext() ) {
            p.setEmail( cursor.getString(0) );
            p.setSenha( cursor.getString(1) );
        }

        cursor.close();
        db.close();

        if(email.equals(p.getEmail()) && senha.equals(p.getSenha())){
            return p;
        }

        return null;
    }


    public void insertQuiz(Questionario questionario) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("id_questionario", questionario.getId());
        cv.put("id_pessoa", questionario.getPessoa().getId());
        cv.put("tp_situacao", String.valueOf(questionario.getTipoSentimento()));
        cv.put("respondido", true);


        db.insert(T_QUESTIONARIO, null, cv);
        db.close();
    }

    public boolean getQuiz(long id) {

        String respondido = "false";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                T_QUESTIONARIO,
                new String[]{"respondido"},
                "id_pessoa = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if ( cursor.moveToNext() ) {
            respondido = cursor.getString(0);
        }

        cursor.close();
        db.close();

        if(respondido.equals("true")){
            return true;
        }

        return false;
    }




    public double getBatimento(){

        double valor = 0;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                T_DISPOSITIVO_SENSOR,
                new String[]{"max(id_dispositivo_sensor)"},
//                "id_pessoa = ?",
                null,
//                new String[]{id_pessoa},
                null,
                null,
                null,
                null
        );

        if ( cursor.moveToNext() ) {
//            valor = Double.valueOf(cursor.getString(0));
        }

        cursor.close();
        db.close();

        return valor;
    }


    public double getTempoOcioso() {
        double valor = 0;
        return valor;
    }

    public double getTempoSono() {
        double valor = 0;
        return valor;
    }

    public double getTempoAtividade() {
        double valor = 0;
        return valor;
    }

    public double getLiquidoDiario() {
        double valor = 0;
        return valor;
    }

    public double getQtdPassos() {
        double valor = 0;
        return valor;
    }
}
