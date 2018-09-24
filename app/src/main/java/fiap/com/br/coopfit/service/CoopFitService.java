package fiap.com.br.coopfit.service;


import fiap.com.br.coopfit.to.Pessoa;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CoopFitService {


        @GET("pessoas/{id}")
        Call<Pessoa> getPessoa(@Path("id") int id);

        @POST("pessoas")
        Call<Pessoa> setPessoa(@Body Pessoa pessoa);

        @POST("login")
        Call<Pessoa> logar(@Field("email") String email, @Field("senha") String senha);


        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8080/")
                .baseUrl("https://coopfit.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



}
