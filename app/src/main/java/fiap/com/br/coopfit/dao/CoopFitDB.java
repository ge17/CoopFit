package fiap.com.br.coopfit.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import fiap.com.br.coopfit.to.Pessoa;

/**
 * Created by georg on 9/16/2018.
 */

public class CoopFitDB extends SQLiteOpenHelper {

    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

    public static final String DATABASE_NAME = "CoopFitDB";
    public static final int VERSION = 1;
    public static final String TB_PESSOA = "T_PESSOA";
    public static final String T_DISPOSITIVO_SENSOR = "T_DISPOSITIVO_SENSOR";



    public CoopFitDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists T_PESSOA(id_pessoa integer, email text, senha text, nome text, peso real, altura real, data text)");
        db.execSQL("create table if not exists T_DISPOSITIVO(id_dispositivo integer, id_pessoa integer, descricao text)");
        db.execSQL("create table if not exists T_DISPOSITIVO_SENSOR(id_dispositivo_sensor integer, id_sensor integer, valor real, tipo text, data text)");
        db.execSQL("create table if not exists T_QUESTIONARIO(id_questionario integer, id_pessoa integer, qt_copo_agua integer, tp_situacao text, data text, respondido text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insertPessoa(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("email", pessoa.getEmail());
        cv.put("senha", pessoa.getSenha());
        cv.put("nome", pessoa.getNome());
        cv.put("peso", pessoa.getPeso());
        cv.put("altura", pessoa.getAltura());
        cv.put("data", String.valueOf(pessoa.getNascimento()));

        db.insert(TB_PESSOA, null, cv);
    }

    public void updatePessoa(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("email", pessoa.getEmail());
        cv.put("senha", pessoa.getSenha());
        cv.put("nome", pessoa.getNome());
        cv.put("peso", pessoa.getPeso());
        cv.put("altura", pessoa.getAltura());
        cv.put("data", String.valueOf(pessoa.getNascimento()));

        db.update(TB_PESSOA, cv, "email = ?", new String[]{pessoa.getEmail()});
    }

    public Pessoa findPessoa(String email) {

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

        Pessoa p = new Pessoa();
        if ( cursor.moveToNext() ) {
            p.setId( cursor.getLong(0) );
            p.setEmail( cursor.getString(1) );
            p.setSenha( cursor.getString(2) );
            p.setNome( cursor.getString(3) );
            p.setPeso( cursor.getDouble(4) );
            p.setAltura( cursor.getDouble(5) );

//            String dataNasc = dateFormat.format(cursor.getString(5));
//            Date data = (dataNasc);
//            p.setNascimento(data);

        }

        cursor.close();


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

        if(email.equals(p.getEmail()) && senha.equals(p.getSenha())){
            return p;
        }

        return null;
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
