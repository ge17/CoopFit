package fiap.com.br.coopfit.service;

import java.util.List;

import fiap.com.br.coopfit.to.Contato;
import fiap.com.br.coopfit.to.Pessoa;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CoopFitService {


        @GET("contatos.php")
        Call<List<Contato>> getContatos();

        @GET("pessoas/{id}")
        Call<Pessoa> getPessoa(@Path("id") int id);

        @POST("pessoas")
        Call<Pessoa> setPessoa(@Body Pessoa pessoa);




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



}
