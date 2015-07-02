package REST;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Izzy-Izumi on 02/07/2015.
 */
public interface AccessTokenService {
    @POST("/access_token")
    public void createAccessToken(@Body Credentials credentials, Callback<AccessToken> cb);
}
