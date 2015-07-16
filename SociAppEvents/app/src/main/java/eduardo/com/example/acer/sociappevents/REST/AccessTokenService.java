package eduardo.com.example.acer.sociappevents.Rest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by LUIS ANGEL on 02/07/2015.
 */

public interface AccessTokenService {

  @POST("/accessTokens")

  public void createAccessToken (@Body Credentials credentials, Callback<AccesToken> cb);

}
