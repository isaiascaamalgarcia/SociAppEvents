package controllers;

import models.AccessToken;
import models.Event;
import models.Photo;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Base64;
import java.util.UUID;

/**
 * Created by Shary on 23/07/2015.
 */
public class PhotoController extends Controller {
    public Result addPhotoEvent(int userId, int eventId) {

        if (!request().hasHeader("ACCESS_TOKEN")) {
            return badRequest("ACCESS_TOKEN header is required.");
        }

        String token = request().getHeader("ACCESS_TOKEN");

        AccessToken accessToken = AccessToken.find.where()
                .eq("token", token)
                .findUnique();

        if (accessToken == null) {
            return unauthorized("Invalid access token.");
        }

        User user = accessToken.getUser();

        if (user.getId() != userId) {
            return forbidden("You don't have permission to load photos to this event.");
        }

        Event event = Event.find.byId(eventId);

        if (event == null) {
            return notFound("Event doesn't exist.");
        }

        if (event.getHost().getId() != userId) {
            return forbidden("You don't have permission to load photos to this event.");
        }

        //Recibir token, validar que el token exista, que el usuario este invitado al evento
        Photo photo = Json.fromJson(request().body().asJson(), Photo.class);

        String base64 = photo.getBase64();
        byte[] bytes = Base64.getDecoder().decode(base64);
        System.out.println("IMAGEN DECODER " + bytes);
        String randomName = UUID.randomUUID().toString();
        String extension = photo.getType();
        String filename = randomName + "." + extension;
        photo.save();
        return ok(Json.toJson(photo));
    }

}
