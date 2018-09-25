package fiap.com.br.coopfit.service;


import android.net.http.HttpResponseCache;

import fiap.com.br.coopfit.to.Credenciais;
import fiap.com.br.coopfit.to.DispositivoSensor;
import fiap.com.br.coopfit.to.Pessoa;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CoopFitService {

    String API_BASE_URL = "https://coopfit.herokuapp.com/";
    String API_LOCAL_URL = "\"http://10.0.2.2:8080/";
    String API_LOCAL_EXT_URL = "https://your.api-base.url";


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


        @GET("pessoas/{id}")
        Call<Pessoa> getPessoa(@Path("id") int id);

        @POST("pessoas")
        Call<Pessoa> setPessoa(@Body Pessoa pessoa);

        @POST("login")
        Call<Void> logar(@Body Credenciais credenciais);

        @POST("auth/forgot")
        Call<Pessoa> recuperarSenha(@Body String email);

        @POST("auth/refresh_token")
        Call<ResponseBody> getToken();

        @GET("sensores/{id}")
        Call<DispositivoSensor> getValorSensor(@Field("id") long id);


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();



}
