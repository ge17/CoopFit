package fiap.com.br.coopfit.dao;

import android.content.SharedPreferences;


public class PessoaPreferences {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public PessoaPreferences(SharedPreferences preferences){
        this.sp = preferences;
        this.editor = this.sp.edit();
    }

    public void salvarPessoaPreference(String key, String value){
        this.editor.putString(key,value);
        this.editor.commit();
    }

    public String getPessoaPreference(String key){
        return this.sp.getString(key,"");
    }

}
