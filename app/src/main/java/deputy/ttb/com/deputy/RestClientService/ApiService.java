package deputy.ttb.com.deputy.RestClientService;

import java.util.Map;

import deputy.ttb.com.deputy.Model.Shifts;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by naveenu on 13/03/2017.
 */

public interface ApiService {
    // naveenu 74fc5ccd03ffacdaf90fdc6b2ce6bef10a344fff
    // Naveenu 4d8830c996e3ef226b630a92f692d71daaef2891

    // https://apjoqdqpi3.execute-api.us-west-2.amazonaws.com/dmc
    @Headers("Authorization: Deputy 74fc5ccd03ffacdaf90fdc6b2ce6bef10a344fff")
    @POST("shift/start")
    Call<ResponseBody> startShift(@QueryMap Map<String, String> params);

    @Headers("Authorization: Deputy 74fc5ccd03ffacdaf90fdc6b2ce6bef10a344fff")
    @POST("shift/end")
    Call<ResponseBody> endShift(@QueryMap Map<String, String> params);

    @Headers("Authorization: Deputy 74fc5ccd03ffacdaf90fdc6b2ce6bef10a344fff")
    @GET("shifts")
    Call<Shifts> getShiftDetails();
}
