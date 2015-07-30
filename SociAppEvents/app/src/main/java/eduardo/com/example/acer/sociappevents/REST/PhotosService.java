package eduardo.com.example.acer.sociappevents.Rest;

import java.util.List;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by ACER on 29/07/2015.
 */
public interface PhotosService {

    @GET("/users/{iduser}/events/{idevent}/photos")
    public void getEventPhotos(@Header("ACCESS_TOKEN") String token, @Path("iduser") int id, @Path("idevent") int idevent, Callback<List<Photos>> cb);

}
