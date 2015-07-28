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
    public Result createPhoto(int userId, int evntId) {
        //User user = new User(); recibir token, validar que el token exista, que el usuario este invitado al evento
        Photo photo = Json.fromJson(request().body().asJson(), Photo.class);
        //photo.setUser(user);
        //PAQUETE PLAY.LIBS.CODEC
        String base64=photo.getBase64();
        byte [] bytes= Base64.getDecoder().decode(base64);
        //byte [] bytesBase64.decode(base64);
        //String random = usar UUID para generar nombre aleatorio
        String extension = photo.getType();
        String filename = rendom + "." + extension;
        photo.save();
        return ok(Json.toJson(photo));
    }

}
