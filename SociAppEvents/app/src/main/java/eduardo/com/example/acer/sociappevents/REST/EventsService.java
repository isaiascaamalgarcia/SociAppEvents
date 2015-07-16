package eduardo.com.example.acer.sociappevents.Rest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by ACER on 14/07/2015.
 */
public interface EventsService {

    @GET("/")
    public void getEvents (@Header("ACCESS-TOKEN") String token);

}
