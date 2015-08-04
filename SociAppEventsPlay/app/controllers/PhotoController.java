package controllers;

import models.AccessToken;
import models.Event;
import models.Photo;
import models.User;
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
        String base64Small = photo.getBase64Small();

        byte[] bytes = Base64.getDecoder().decode(base64);
        System.out.println("IMAGEN DECODER 1 " + bytes);

        byte[] bytesSmall = Base64.getDecoder().decode(base64Small);
        System.out.println("IMAGEN DECODER 2 " + bytesSmall);

        String randomName = UUID.randomUUID().toString();
        String extension = photo.getType();
        String filename = randomName + "." + extension;

        InputStream in = new ByteArrayInputStream(bytes);
        InputStream inSmall = new ByteArrayInputStream(bytesSmall);
        BufferedImage bImageFromConvert = null;
        BufferedImage bImageFromConvertSmall = null;
        try {
            bImageFromConvert = ImageIO.read(in);
            bImageFromConvertSmall = ImageIO.read(inSmall);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File outputFile = new File("public/photos/"+filename);
        File outputFileSmall = new File("public/photos_small/"+filename);
        try {
            ImageIO.write(bImageFromConvert,extension,outputFile);
            ImageIO.write(bImageFromConvert,extension,outputFileSmall);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = "/assets/photos/"+filename;
        String urlSmall = "/assets/photos_small/"+filename;
        photo.setUrl(url);
        photo.setUrlSmall(urlSmall);
        photo.save();


        ArrayList<Photo> newPhoto;
        if(event.getPhotos() != null)
            newPhoto = new ArrayList<>(event.getPhotos());
        else
            newPhoto = new ArrayList<>();
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
