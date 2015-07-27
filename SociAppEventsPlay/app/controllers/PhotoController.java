package controllers;

import models.Photo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.Base64;

/**
 * Created by Shary on 23/07/2015.
 */
public class PhotoController extends Controller {
    public Result createPhoto() {
        //User user = new User();
        Photo photo = Json.fromJson(request().body().asJson(), Photo.class);
        //photo.setUser(user);
        //PAQUETE PLAY.LIBS.CODEC
        String base64=photo.getBase64();
        byte [] bytes= Base64.getDecoder().decode(base64);
        //byte [] bytesBase64.decode(base64);
        photo.save();
        return ok(Json.toJson(photo));
    }

}
