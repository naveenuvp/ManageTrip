package deputy.ttb.com.deputy.RestClientService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by naveenu on 13/03/2017.
 */

public class RestClient {
    public static final String  BASE_URL    =   "https://apjoqdqpi3.execute-api.us-west-2.amazonaws.com/dmc/";
    private ApiService apiService;

    public RestClient(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit    =   new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService  =   retrofit.create(ApiService.class);
    }

    public ApiService getApiService(){
        return apiService;
    }
}
