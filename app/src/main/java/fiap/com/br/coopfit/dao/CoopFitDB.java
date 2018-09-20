package fiap.com.br.coopfit.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fiap.com.br.coopfit.to.Pessoa;

/**
 * Created by georg on 9/16/2018.
 */

public class CoopFitDB extends SQLiteOpenHelper {



    public static final String DATABASE_NAME = "CoopFitDB";
    public static final int VERSION = 1;
    public static final String TB_PESSOA = "t_pessoa";



    public CoopFitDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists T_PESSOA(email text, senha text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertPessoa(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("email", pessoa.getEmail());
        cv.put("senha", pessoa.getSenha());

        db.insert(TB_PESSOA, null, cv);
    }

    public Pessoa findPessoa(String email, String senha) {

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


}
