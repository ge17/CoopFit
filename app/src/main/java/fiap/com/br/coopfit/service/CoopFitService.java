package fiap.com.br.coopfit.service;


import java.util.List;

import fiap.com.br.coopfit.to.Credenciais;
import fiap.com.br.coopfit.to.DispositivoSensor;
import fiap.com.br.coopfit.to.EmailDTO;
import fiap.com.br.coopfit.to.Pessoa;
import fiap.com.br.coopfit.to.Questionario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CoopFitService {

    String API_BASE_URL = "http://54.89.196.181:8080/";
//    String API_BASE_URL = "https://coopfit.herokuapp.com/";
//    String API_LOCAL_URL = "http://10.0.2.2:8080/";

        @GET("pessoas/{id}")
        Call<Pessoa> getPessoa(@Path("id") int id);

        @GET("pessoas")
        Call<List<Pessoa>> listPessoas(@Header("Authorization") String token);

        @POST("pessoas")
        Call<Void> setPessoa(@Body Pessoa pessoa);

        @POST("login")
        Call<Void> logar(@Body Credenciais credenciais);

        @POST("auth/forgot")
        Call<Void> recuperarSenha(@Body EmailDTO email);

        @GET("sensores/{id}")
        Call<DispositivoSensor> getDispositivoSensor(@Path("id") long id);

        @POST("questionarios")
        Call<Void> setQuiz(@Body Questionario questionario, @Header("Authorization") String token);

        @GET("sensores/maximo")
        Call<Double> getValorSensor(@Query("id") long id, @Query("tipo") String tipo, @Header("Authorization") String token);

}
