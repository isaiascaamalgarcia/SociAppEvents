package controllers;

import models.AccessToken;
import models.Credentials;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;

/**
 * Created by Shary on 30/06/2015.
 */
public class AccessTokenController extends Controller {
    public Result createAccessToken() {

        Credentials credentials = Json.fromJson(request().body().asJson(), Credentials.class);
        User user = User.find.where()
                .eq("email", credentials.getEmail())
                .eq("password", credentials.getPassword())
                .findUnique();

        if (user == null) {
            return unauthorized();
        }
        AccessToken accessToken = new AccessToken();
        String token = UUID.randomUUID().toString();
        accessToken.setToken(token);
        accessToken.setUser(user);
        accessToken.save();
        return created(Json.toJson(accessToken));
    }

}

