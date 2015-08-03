package controllers;

import models.AccessToken;
import models.Event;
import models.Photo;
import models.User;
import play.Play;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage bImageFromConvert = null;
        try {
            bImageFromConvert = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File outputFile = new File("public/photos/"+filename);
        try {
            ImageIO.write(bImageFromConvert,extension,outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = "/assets/photo/"+filename;
        photo.setUrl(url);
        photo.save();
        ArrayList<Photo> newPhoto = new ArrayList<>(event.getPhotos());
        newPhoto.add(photo);
        List<Photo> pList = newPhoto;
        event.photos = pList;
        event.update();

        return ok(Json.toJson(photo));
    }

    public Result getPhotosEvent(int userId, int eventId){
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
            return forbidden("You don't have permission to add an event for this user.");
        }

        Event event = Event.find.where()
                .eq("id", eventId)
                .findUnique();
        if(event == null){
            return forbidden("This event doesn't exists.");
        }

        List<Photo> photosEvent = event.getPhotos();

        return ok(Json.toJson(photosEvent));
    }
}
