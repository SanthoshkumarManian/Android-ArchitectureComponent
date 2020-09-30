package in.dotworld.paging;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL="https://api.stackexchange.com/2.2/";
    public static RetrofitClient retrofitClient;
    public Retrofit retrofit;


    public RetrofitClient() {
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance()
    {
        if(retrofitClient==null)
        {
            retrofitClient=new RetrofitClient();
        }
        return retrofitClient;
    }

    public API getapi()
    {
        return retrofit.create(API.class);
    }
}
