package eduardo.com.example.acer.sociappevents.Rest;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by ACER on 14/07/2015.
 */
public interface EventsService {

    @GET("/users/{id}/events")
    public void getUserEvents (@Path("id")int id, @Header("ACCESS_TOKEN") String token, Callback<List<EventsData>> cb);

}
